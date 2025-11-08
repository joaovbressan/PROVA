package questao_3_state.estados;

public class EstadoEmergencia extends EstadoUsina {
    public EstadoEmergencia(UsinaNuclear usina) { super(usina); }

    @Override
    public void onAtualizarSensores() {
        System.err.println("  -> MELTDOWN IMINENTE. Ignorando sensores.");
    }
    // Este estado é terminal, não permite transições para fora.
    // Previne transições circulares perigosas.
}