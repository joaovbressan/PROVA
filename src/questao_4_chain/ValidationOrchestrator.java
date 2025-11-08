package questao_4_chain;

import questao_4_chain.validadores.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * ORQUESTRADOR DA VALIDAÇÃO (Gerenciador da Chain of Responsibility)
 * * JUSTIFICATIVA DE DESIGN (Chain of Responsibility Gerenciada):
 * Um CoR "puro" (onde A chama B, B chama C) é simples, mas não atende
 * às restrições complexas (circuit breaker, condicionais).
 * * Esta classe atua como um Orquestrador (ou "Mediator") que *possui* a
 * lista de validadores (a cadeia) e os invoca, aplicando a lógica de
 * controle de fluxo.
 * * 1. Cadeia: A lista 'validadores' forma a cadeia.
 * 2. Condicionais: O orquestrador verifica `context.hasFailed()` antes
 * de chamar validadores 3 e 5.
 * 3. Circuit Breaker: O orquestrador verifica `context.getFailureCount() >= 3`
 * após cada etapa.
 * 4. Rollback: O orquestrador é responsável por chamar `context.executeRollbacks()`
 * no final, se a validação falhar.
 * 5. Timeout: O orquestrador usa um `ExecutorService` para impor o
 * timeout individual de cada validador.
 */
public class ValidationOrchestrator {
    
    private final List<Validador> validadores;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final int CIRCUIT_BREAKER_LIMIT = 3;

    public ValidationOrchestrator() {
        // Define a ordem da cadeia
        this.validadores = List.of(
            new ValidadorSchemaXML(),
            new ValidadorCertificadoDigital(),
            new ValidadorBancoDados(),        // Movido para antes (para testar rollback)
            new ValidadorRegrasFiscais(),
            new ValidadorServicoSefaz()
        );
    }

    public void processar(DocumentoFiscal doc) {
        System.out.println("--- Iniciando processamento da NF-e: " + doc.getNumero() + " ---");
        ValidationContext context = new ValidationContext(doc);
        
        try {
            for (Validador validador : validadores) {
                // RESTRIÇÃO: Condicional (só executa 3 e 5 se anteriores passarem)
                if ((validador instanceof ValidadorRegrasFiscais || validador instanceof ValidadorServicoSefaz) 
                        && context.hasFailed()) {
                    System.out.println("[Orquestrador] Pulando " + validador.getClass().getSimpleName() + " devido a falhas anteriores.");
                    continue;
                }

                // Executa a validação com timeout
                executarComTimeout(validador, context);

                // RESTRIÇÃO: Circuit Breaker
                if (context.getFailureCount() >= CIRCUIT_BREAKER_LIMIT) {
                    System.err.println("[CIRCUIT BREAKER] Limite de " + CIRCUIT_BREAKER_LIMIT + " falhas atingido. Interrompendo cadeia.");
                    break;
                }
            }
        } finally {
            // RESTRIÇÃO: Rollback
            // No final, se houver QUALQUER falha, executa os rollbacks
            if (context.hasFailed()) {
                context.executeRollbacks();
            } else {
                System.out.println("[Orquestrador] Validação concluída com SUCESSO.");
            }
            // Não chamei executor.shutdown() para que o orquestrador seja reutilizável
            // Em um app real, o ciclo de vida do executor seria gerenciado.
        }
    }

    /**
     * RESTRIÇÃO: Timeout individual
     * Usa um Future para executar o validador em uma thread separada
     * e impõe um limite de tempo.
     */
    private void executarComTimeout(Validador validador, ValidationContext context) {
        Future<?> future = executor.submit(() -> validador.validar(context));
        try {
            // Obtém o timeout individual do validador
            future.get(validador.getTimeoutMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // Interrompe a thread do validador
            context.addError(validador.getClass().getSimpleName() + " excedeu o timeout de " + validador.getTimeoutMillis() + "ms.");
        } catch (Exception e) {
            context.addError("Erro inesperado em " + validador.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}