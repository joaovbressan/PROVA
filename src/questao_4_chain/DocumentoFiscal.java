package questao_4_chain;

// DTO simples representando a NF-e
public class DocumentoFiscal {
    private final String numero;
    private final String xmlContent;
    
    public DocumentoFiscal(String numero, String xmlContent) {
        this.numero = numero;
        this.xmlContent = xmlContent;
    }
    public String getNumero() { return numero; }
    public String getXmlContent() { return xmlContent; }
}