package questao_2_adapter.legacy;

import java.util.HashMap;
import java.util.Map;

// A classe legada (Adaptee) com a qual não podemos mexer.
// Ela usa tipos de dados "obsoletos" (HashMap) e assinaturas complexas.
public class SistemaBancarioLegado {

    // O método legado
    public HashMap<String, Object> processarTransacao(HashMap<String, Object> parametros) {
        System.out.println("[LEGADO] Recebendo transação...");
        System.out.println("[LEGADO] Parâmetros: " + parametros);

        HashMap<String, Object> resposta = new HashMap<>();

        // Validação de campos obrigatórios do legado
        if (!parametros.containsKey("id_loja") || !parametros.containsKey("cod_moeda")) {
            resposta.put("status", 400); // Bad Request
            resposta.put("msg_erro", "Campos obrigatorios (id_loja, cod_moeda) faltando.");
            return resposta;
        }
        
        // Simulação de processamento
        double valor = (Double) parametros.get("valor");
        if (valor > 5000) {
            resposta.put("status", 501); // Negado
            resposta.put("msg_erro", "Valor excede limite de R$ 5000.");
        } else {
            resposta.put("status", 200); // Aprovado
            resposta.put("id_autorizacao", "LEGACY_AUTH_" + System.currentTimeMillis());
        }
        
        return resposta;
    }
}