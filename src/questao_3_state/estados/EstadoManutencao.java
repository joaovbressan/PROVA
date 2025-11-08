package questao_3_state.estados;

public class EstadoManutencao extends EstadoUsina {
    public EstadoManutencao(UsinaNuclear usina) { super(usina); }

    @Override
    public void onAtualizarSensores() {
        // REGRA: "sobreescreva temporariamente os estados normais"
        System.out.println("  -> [MANUTENÇÃO] Ignorando leituras de sensor (Temp=" + usina.getTemperatura() + ")");
    }
    
    @Override
    public void onPararManutencao() {
        System.out.println("  -> Manutenção concluída. Sistema pronto para religar.");
        usina.setEstado(new EstadoDesligada(usina));
    }
}