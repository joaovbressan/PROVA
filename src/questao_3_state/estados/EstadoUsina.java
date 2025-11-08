package questao_3_state.estados;

/**
 * INTERFACE STATE (EstadoUsina)
 * * Define todos os "eventos" que podem acontecer na usina.
 * Cada estado concreto implementará esses métodos de forma diferente.
 * * Usamos uma classe Abstrata para fornecer implementações padrão
 * (geralmente lançando exceção ou ignorando) para que as classes
 * concretas só precisem implementar os eventos que lhes interessam.
 */
public abstract class EstadoUsina {

    protected UsinaNuclear usina; // Referência ao contexto

    public EstadoUsina(UsinaNuclear usina) {
        this.usina = usina;
    }

    // Eventos que podem disparar transições
    
    // Evento: Sensores foram atualizados
    public abstract void onAtualizarSensores();
    
    // Evento: Sistema de resfriamento falhou
    public void onSistemaResfriamentoFalha() {
        System.out.println("  -> Ignorando falha de resfriamento (Estado atual: " + this.getClass().getSimpleName() + ")");
    }
    
    // Evento: Botão de manutenção pressionado
    public void onIniciarManutencao() {
        System.out.println("  -> Ignorando solicitação de manutenção (Estado atual: " + this.getClass().getSimpleName() + ")");
    }
    
    // Evento: Manutenção concluída
    public void onPararManutencao() {
        System.out.println("  -> Não é possível parar manutenção (não está em manutenção)");
    }
}