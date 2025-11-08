package questao_3_state.estados;

public class EstadoAlertaVermelho extends EstadoUsina {
    public EstadoAlertaVermelho(UsinaNuclear usina) { super(usina); }

    @Override
    public void onAtualizarSensores() {
        System.err.println("  -> Estado de Alerta Vermelho. Evacuação recomendada.");
    }
    
    @Override
    public void onSistemaResfriamentoFalha() {
        // REGRA: ALERTA_VERMELHO → EMERGENCIA: se sistema de resfriamento falhar
        // Esta é a ÚNICA forma de chegar em EMERGENCIA, cumprindo a restrição.
        System.err.println("  -> CATASTROFE: Resfriamento falhou durante Alerta Vermelho!");
        usina.setEstado(new EstadoEmergencia(usina));
    }
}