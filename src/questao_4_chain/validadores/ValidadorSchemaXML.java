package questao_4_chain.validadores;

import questao_4_chain.ValidationContext;
import questao_4_chain.Validador;

// 1. Validador de Schema XML
public class ValidadorSchemaXML implements Validador {
    @Override
    public void validar(ValidationContext context) {
        System.out.println("[Validando] 1. Schema XML...");
        // Simulação de falha
        if (context.getDocumento().getXmlContent().contains("ERRO_XML")) {
            context.addError("Schema XML fora do padrão XSD.");
        }
    }
    @Override public long getTimeoutMillis() { return 500; }
}

