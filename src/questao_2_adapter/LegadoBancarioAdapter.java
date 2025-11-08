package questao_2_adapter;

import questao_2_adapter.legacy.SistemaBancarioLegado;
import questao_2_adapter.modern.ProcessadorTransacoes;
import questao_2_adapter.modern.RespostaModerna;

import java.util.HashMap;
import java.util.Map;

/**
 * ADAPTER (LegadoBancarioAdapter)
 * * Esta classe implementa a interface moderna (ProcessadorTransacoes) e,
 * internamente, "embrulha" (wraps) uma instância do sistema legado.
 * * JUSTIFICATIVA DE DESIGN (Adapter Pattern):
 * O padrão Adapter foi escolhido para "traduzir" chamadas da moderna
 * interface 'ProcessadorTransacoes' para a incompatível
 * 'SistemaBancarioLegado'.
 * * 1. Conversão de Interface: O cliente usa o método moderno `autorizar()`.
 * 2. Tradução de Dados (Ida): O Adapter converte os parâmetros simples
 * (String, double) no `HashMap` complexo que o legado espera.
 * 3. Lógica de Negócio (Restrições): O Adapter lida com as regras
 * específicas, como a codificação de moedas (USD=1) e a adição
 * de campos obrigatórios do legado ("id_loja").
 * 4. Bidirecionalidade (Volta): O Adapter recebe a resposta do legado
 * (outro HashMap) e a traduz de volta para um objeto `RespostaModerna`,
 * cumprindo o requisito de ser bidirecional.
 */
public class LegadoBancarioAdapter implements ProcessadorTransacoes {

    // O Adapter "possui" (por composição) a classe que ele está adaptando
    private final SistemaBancarioLegado sistemaLegado;
    
    // Mapeamento da restrição de moedas
    private static final Map<String, Integer> MAPA_MOEDAS_LEGADO = Map.of(
        "USD", 1,
        "EUR", 2,
        "BRL", 3
    );

    public LegadoBancarioAdapter(SistemaBancarioLegado sistemaLegado) {
        this.sistemaLegado = sistemaLegado;
    }

    @Override
    public RespostaModerna autorizar(String cartao, double valor, String moeda) {
        System.out.println("[ADAPTER] Recebida requisição moderna. Traduzindo para o legado...");

        // 1. Tradução de DADOS (IDA) e tratamento de regras
        HashMap<String, Object> parametrosLegado = new HashMap<>();
        parametrosLegado.put("numero_cartao", cartao);
        parametrosLegado.put("valor", valor);
        
        // 2. Tratamento da restrição de MOEDA
        Integer codigoMoeda = MAPA_MOEDAS_LEGADO.get(moeda.toUpperCase());
        if (codigoMoeda == null) {
            return new RespostaModerna(false, "Moeda invalida: " + moeda, null);
        }
        parametrosLegado.put("cod_moeda", codigoMoeda);

        // 3. Tratamento de CAMPO OBRIGATÓRIO (Restrição)
        // A interface moderna não tem "id_loja", mas o legado exige.
        // O Adapter é o local correto para injetar esse dado.
        parametrosLegado.put("id_loja", "LOJA_ONLINE_001");

        // 4. Chamada ao sistema legado
        HashMap<String, Object> respostaLegada = sistemaLegado.processarTransacao(parametrosLegado);

        // 5. Tradução de DADOS (VOLTA) - Bidirecional
        System.out.println("[ADAPTER] Resposta legada recebida. Traduzindo para o moderno...");
        return converterRespostaLegada(respostaLegada);
    }
    
    private RespostaModerna converterRespostaLegada(HashMap<String, Object> respostaLegada) {
        int status = (Integer) respostaLegada.get("status");
        
        if (status == 200) {
            String authId = (String) respostaLegada.get("id_autorizacao");
            return new RespostaModerna(true, "Aprovada", authId);
        } else {
            String erro = (String) respostaLegada.get("msg_erro");
            return new RespostaModerna(false, "Negada pelo legado: " + erro, null);
        }
    }
}