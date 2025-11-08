package questao_3_state;

import questao_3_state.estados.UsinaNuclear;

public class Q3Demo {
    public static void main(String[] args) {
        UsinaNuclear usina = new UsinaNuclear(); // Começa em Desligada

        System.out.println("--- Cenário 1: Inicialização e Alerta ---");
        usina.atualizarSensores(40.0, 1.0);  // Liga -> OperacaoNormal
        usina.atualizarSensores(280.0, 1.1); // Fica em OperacaoNormal
        usina.atualizarSensores(310.0, 1.2); // Normal -> AlertaAmarelo
        usina.atualizarSensores(350.0, 1.3); // Fica em AlertaAmarelo
        usina.atualizarSensores(410.0, 1.5); // Amarelo -> AlertaVermelho

        System.out.println("\n--- Cenário 2: Emergência (Restrição) ---");
        // Tentativa de falha em estado errado (não deve ir para emergência)
        UsinaNuclear usina2 = new UsinaNuclear();
        usina2.atualizarSensores(40.0, 1.0); // Liga -> OperacaoNormal
        usina2.sistemaResfriamentoFalha();   // Deve ser ignorado

        // Cenário correto para Emergência
        usina.sistemaResfriamentoFalha();    // Vermelho -> Emergencia

        System.out.println("\n--- Cenário 3: Manutenção ---");
        UsinaNuclear usina3 = new UsinaNuclear();
        usina3.atualizarSensores(40.0, 1.0);  // Liga -> OperacaoNormal
        usina3.iniciarManutencao();           // Normal -> Desligada
        usina3.iniciarManutencao();           // Desligada -> Manutencao
        
        // Em manutenção, sensores são ignorados
        usina3.atualizarSensores(500.0, 3.0); 
        usina3.atualizarSensores(600.0, 4.0);
        
        usina3.pararManutencao();             // Manutencao -> Desligada
    }
}