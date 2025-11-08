package questao_2_adapter;

import questao_2_adapter.legacy.SistemaBancarioLegado;
import questao_2_adapter.modern.ProcessadorTransacoes;
import questao_2_adapter.modern.RespostaModerna;

public class Q2Demo {
    public static void main(String[] args) {
        // O cliente só conhece a interface moderna
        ProcessadorTransacoes processador;

        // 1. Criamos a instância do legado
        SistemaBancarioLegado sistemaAntigo = new SistemaBancarioLegado();

        // 2. "Embrulhamos" o legado no Adapter
        // O cliente recebe uma classe que IMPLEMENTA ProcessadorTransacoes
        processador = new LegadoBancarioAdapter(sistemaAntigo);

        // 3. O cliente usa a interface moderna, sem saber
        // da complexidade do legado por trás.
        System.out.println("--- Teste 1: Transação BRL válida ---");
        RespostaModerna resp1 = processador.autorizar("1234-5678", 1500.0, "BRL");
        System.out.println("[CLIENTE] Resposta final: " + resp1);
        
        System.out.println("\n--- Teste 2: Transação EUR com valor alto ---");
        RespostaModerna resp2 = processador.autorizar("8765-4321", 6000.0, "EUR");
        System.out.println("[CLIENTE] Resposta final: " + resp2);
        
        System.out.println("\n--- Teste 3: Transação com moeda inválida ---");
        RespostaModerna resp3 = processador.autorizar("1111-2222", 100.0, "ARS");
        System.out.println("[CLIENTE] Resposta final: " + resp3);
    }
}