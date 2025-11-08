package questao_2_adapter.modern;

// DTO (Data Transfer Object) moderno
public class RespostaModerna {
    private final boolean autorizada;
    private final String mensagem;
    private final String codigoAutorizacao;

    public RespostaModerna(boolean autorizada, String mensagem, String codigoAutorizacao) {
        this.autorizada = autorizada;
        this.mensagem = mensagem;
        this.codigoAutorizacao = codigoAutorizacao;
    }

    @Override
    public String toString() {
        return "RespostaModerna [autorizada=" + autorizada + 
               ", mensagem='" + mensagem + '\'' + 
               ", codigoAutorizacao='" + codigoAutorizacao + "']";
    }
}