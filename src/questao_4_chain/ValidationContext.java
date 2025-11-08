package questao_4_chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Contexto da Validação
 * * Este objeto é passado ao longo da cadeia (Chain of Responsibility).
 * Ele carrega o estado da validação, incluindo erros e a pilha de
 * rollback (Command Pattern).
 */
public class ValidationContext {
    private final DocumentoFiscal documento;
    private final List<String> errors = new ArrayList<>();
    private int failureCount = 0;

    /**
     * JUSTIFICATIVA DE DESIGN (Command Pattern para Rollback):
     * A pilha 'rollbackActions' armazena 'Comandos' (na forma de Runnables)
     * que sabem como desfazer uma operação.
     * O ValidadorBancoDados 'adiciona' um comando à pilha.
     * O Orquestrador, se detectar falha, 'executa' os comandos da pilha.
     */
    private final Stack<Runnable> rollbackActions = new Stack<>();

    public ValidationContext(DocumentoFiscal documento) {
        this.documento = documento;
    }

    public DocumentoFiscal getDocumento() { return documento; }

    public void addError(String message) {
        System.err.println("  -> FALHA: " + message);
        this.errors.add(message);
        this.failureCount++;
    }

    public int getFailureCount() { return failureCount; }
    public boolean hasFailed() { return failureCount > 0; }
    
    // Métodos para o mecanismo de Rollback (Command)
    public void addRollbackAction(Runnable action) {
        this.rollbackActions.push(action);
    }

    public void executeRollbacks() {
        System.out.println("[ROLLBACK] Iniciando rollback de " + rollbackActions.size() + " ações...");
        while (!rollbackActions.isEmpty()) {
            rollbackActions.pop().run(); // Executa o Runnable (Comando)
        }
    }
}