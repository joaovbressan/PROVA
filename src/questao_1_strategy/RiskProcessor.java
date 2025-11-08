package questao_1_strategy;

/**
 * CLASSE DE CONTEXTO (RiskProcessor)
 * * Esta classe mantém uma referência a um objeto Strategy (RiskAlgorithm)
 * e delega a ele a execução do cálculo.
 * * JUSTIFICATIVA DE DESIGN (Strategy Pattern):
 * Esta classe é o "Contexto" do padrão Strategy. Ela não conhece a lógica
 * interna de nenhum algoritmo. Ela apenas sabe como chamar a interface.
 * O método setAlgorithm() permite que o cliente (ou regras de negócio)
 * troque o algoritmo dinamicamente, satisfazendo o requisito principal.
 */
public class RiskProcessor {
    // A referência para a estratégia atual
    private RiskAlgorithm currentAlgorithm;

    /**
     * Define a estratégia de cálculo a ser usada.
     * Isso pode ser chamado a qualquer momento para trocar o algoritmo.
     */
    public void setAlgorithm(RiskAlgorithm algorithm) {
        System.out.println("--- [RiskProcessor] Mudando estratégia para: " + algorithm.getClass().getSimpleName() + " ---");
        this.currentAlgorithm = algorithm;
    }

    /**
     * Executa o cálculo de risco usando a estratégia atualmente configurada.
     */
    public void executeRiskCalculation(FinancialContext context) {
        if (currentAlgorithm == null) {
            System.err.println("Erro: Nenhum algoritmo de risco foi definido.");
            return;
        }
        // Delega a chamada para o objeto Strategy
        currentAlgorithm.calculate(context);
    }
}