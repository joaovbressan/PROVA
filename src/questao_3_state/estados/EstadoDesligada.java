package questao_3_state.estados;

// --- Estado 1: Desligada ---
public class EstadoDesligada extends EstadoUsina {
    public EstadoDesligada(UsinaNuclear usina) { super(usina); }

    @Override
    public void onAtualizarSensores() {
        // Pode ligar se a temperatura estiver baixa (ex: < 50)
        if (usina.getTemperatura() < 50) {
            System.out.println("  -> Condições seguras. Iniciando operação.");
            usina.setEstado(new EstadoOperacaoNormal(usina));
        } else {
            System.out.println("  -> Temperatura muito alta para iniciar. Requer resfriamento.");
        }
    }
    
    @Override
    public void onIniciarManutencao() {
        usina.setEstado(new EstadoManutencao(usina));
    }
}

