
package questao_3_state.estados;

public class EstadoAlertaAmarelo extends EstadoUsina {
    // Para a regra "por mais de 30s", precisaríamos de timestamps
    // Para simplificar (dummy), vamos apenas checar a temp > 400
    public EstadoAlertaAmarelo(UsinaNuclear usina) { super(usina); }

    @Override
    public void onAtualizarSensores() {
        // REGRA: ALERTA_AMARELO → ALERTA_VERMELHO: se temperatura > 400°C
        if (usina.getTemperatura() > 400) {
            System.err.println("  -> PERIGO: Temperatura > 400°C!");
            usina.setEstado(new EstadoAlertaVermelho(usina));
        }
        // Regra de retorno
        else if (usina.getTemperatura() <= 300) {
            System.out.println("  -> Temperatura normalizada. Retornando à Operação Normal.");
            usina.setEstado(new EstadoOperacaoNormal(usina));
        }
    }
}