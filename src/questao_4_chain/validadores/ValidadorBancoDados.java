package questao_4_chain.validadores;

import questao_4_chain.Validador;
import questao_4_chain.ValidationContext;

public class ValidadorBancoDados implements Validador {
    @Override
    public void validar(ValidationContext context) {
        System.out.println("[Validando] 4. Duplicidade no Banco de Dados...");
        String numero = context.getDocumento().getNumero();
        
        // Simulação de modificação no DB (Restrição de Rollback)
        System.out.println("  -> Inserindo chave " + numero + " no DB (lock temporário)...");
        
        // ADICIONA O COMANDO DE ROLLBACK
        // Se algo falhar *depois* daqui, esta ação será executada.
        context.addRollbackAction(() -> {
            System.out.println("  -> [ROLLBACK DB] Removendo chave " + numero + " do DB.");
        });

        // Simulação de falha
        if (numero.equals("123")) {
            context.addError("Duplicidade de NF-e detectada (Nº 123).");
        }
    }
    @Override public long getTimeoutMillis() { return 1500; }
}