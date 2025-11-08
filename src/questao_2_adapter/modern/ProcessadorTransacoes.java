package questao_2_adapter.modern;

// A interface moderna (Target) que o nosso cliente conhece
// e quer usar.
public interface ProcessadorTransacoes {
    
    /**
     * Autoriza uma transação usando uma interface limpa e moderna.
     */
    RespostaModerna autorizar(String cartao, double valor, String moeda);
}