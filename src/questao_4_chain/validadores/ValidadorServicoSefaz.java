package questao_4_chain.validadores;

import questao_4_chain.Validador;
import questao_4_chain.ValidationContext;

public class ValidadorServicoSefaz implements Validador {
    @Override
    public void validar(ValidationContext context) {
        System.out.println("[Validando] 5. Consulta Online SEFAZ...");
        // Simulação de timeout (serviço lento)
        if (context.getDocumento().getNumero().equals("777")) {
            try {
                Thread.sleep(5000); // Dorme por 5s (timeout é 2s)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    @Override public long getTimeoutMillis() { return 2000; }
}