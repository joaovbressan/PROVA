package questao_4_chain.validadores;

import questao_4_chain.Validador;
import questao_4_chain.ValidationContext;

public class ValidadorCertificadoDigital implements Validador {
    @Override
    public void validar(ValidationContext context) {
        System.out.println("[Validando] 2. Certificado Digital...");
        // Simulação de falha
        if (context.getDocumento().getNumero().equals("999")) {
            context.addError("Certificado revogado (LCR).");
        }
    }

    @Override public long getTimeoutMillis() { return 1000; }
}