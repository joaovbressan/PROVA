package questao_4_chain.validadores;

import questao_4_chain.Validador;
import questao_4_chain.ValidationContext;

public class ValidadorRegrasFiscais implements Validador {
    @Override
    public void validar(ValidationContext context) {
        System.out.println("[Validando] 3. Regras Fiscais (Impostos)...");
        // Simulação de falha
        if (context.getDocumento().getXmlContent().contains("IMPOSTO_ERRADO")) {
            context.addError("Cálculo de ICMS inválido.");
        }
    }
    @Override public long getTimeoutMillis() { return 800; }
}