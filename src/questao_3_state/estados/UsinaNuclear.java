package questao_3_state.estados;

/**
 * CONTEXTO (UsinaNuclear)
 * * Esta classe mantém a referência ao estado atual (currentState) e
 * delega todo o comportamento para o objeto de estado.
 * * JUSTIFICATIVA DE DESIGN (State Pattern):
 * O padrão State foi escolhido porque o comportamento da Usina
 * (o que acontece ao receber um input) depende inteiramente de
 * seu estado interno (Desligada, Normal, Alerta, etc.).
 * * 1. Coesão (SOLID - SRP): Em vez de um `switch` gigante
 * na classe Usina, cada estado encapsula sua própria lógica
 * (ex: `EstadoOperacaoNormal` sabe que deve ir para `AlertaAmarelo`
 * se temp > 300).
 * 2. Gerenciamento de Transição: Os próprios objetos de estado
 * são responsáveis por dizer à Usina (Contexto) qual é o
 * próximo estado usando `usina.setEstado(...)`.
 * 3. Prevenção de Transições Inválidas: A regra "EMERGENCIA só
 * pode vir de ALERTA_VERMELHO" é garantida porque *apenas*
 * a classe `EstadoAlertaVermelho` possui a lógica para
 * transicionar para `EstadoEmergencia`.
 * 4. Extensibilidade (SOLID - OCP): Adicionar um novo estado
 * (ex: "Resfriamento") significa apenas criar uma nova classe
 * de estado, sem alterar as existentes.
 */
public class UsinaNuclear {

    private EstadoUsina estadoAtual;
    // Dados internos que os estados podem ler
    private double temperatura;
    private double pressao;

    public UsinaNuclear() {
        // Estado inicial
        this.estadoAtual = new EstadoDesligada(this);
    }

    // O Contexto permite que os Estados mudem o estado do Contexto.
    public void setEstado(EstadoUsina novoEstado) {
        System.out.println(">>> TRANSIÇÃO: " + 
                estadoAtual.getClass().getSimpleName() + 
                " -> " + novoEstado.getClass().getSimpleName());
        this.estadoAtual = novoEstado;
    }
    
    // --- Métodos de Delegação ---
    // O contexto não faz nada, apenas delega ao estado atual.
    
    public void atualizarSensores(double temp, double press) {
        this.temperatura = temp;
        this.pressao = press;
        System.out.printf("[USINA] Sensores: Temp=%.1f°C, Pressao=%.1f atm%n", temp, press);
        estadoAtual.onAtualizarSensores();
    }
    
    public void sistemaResfriamentoFalha() {
        System.err.println("[USINA] ALERTA CRÍTICO: Sistema de resfriamento falhou!");
        estadoAtual.onSistemaResfriamentoFalha();
    }
    
    public void iniciarManutencao() {
        System.out.println("[USINA] Solicitando entrada em modo de manutenção...");
        estadoAtual.onIniciarManutencao();
    }
    
    public void pararManutencao() {
        System.out.println("[USINA] Solicitando saída do modo de manutenção...");
        estadoAtual.onPararManutencao();
    }
    
    // Getters para os estados lerem os dados
    public double getTemperatura() { return temperatura; }
    public double getPressao() { return pressao; }
}