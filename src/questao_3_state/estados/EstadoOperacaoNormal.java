package questao_3_state.estados;

public class EstadoOperacaoNormal extends EstadoUsina {
    public EstadoOperacaoNormal(UsinaNuclear usina) { super(usina); }

    @Override
    public void onAtualizarSensores() {
        // REGRA: OPERACAO_NORMAL → ALERTA_AMARELO: se temperatura > 300°C
        if (usina.getTemperatura() > 300) {
            System.err.println("  -> ALERTA: Temperatura > 300°C!");
            usina.setEstado(new EstadoAlertaAmarelo(usina));
        } else {
            System.out.println("  -> Reator estável.");
        }
    }
    
    @Override
    public void onIniciarManutencao() {
        System.out.println("  -> Desligando reator para manutenção...");
        usina.setEstado(new EstadoDesligada(usina));
        // A partir de Desligada, o Demo chamará onIniciarManutencao()
    }
}