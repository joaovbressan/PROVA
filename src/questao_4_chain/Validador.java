package questao_4_chain;

/**
 * HANDLER (Interface Validador)
 * * Define a interface para cada "elo" da cadeia (Chain of Responsibility).
 * Cada validador implementa esta interface.
 */
public interface Validador {
    /**
     * Executa uma validação específica no documento.
     * @param context O contexto compartilhado que armazena erros e estado.
     */
    void validar(ValidationContext context);
    
    /**
     * Retorna o timeout individual para este validador em milissegundos.
     */
    long getTimeoutMillis();
}