package questao_1_strategy;

// Estratégia Concreta 2
public class ExpectedShortfallStrategy implements RiskAlgorithm {
    @Override
    public void calculate(FinancialContext context) {
        // Cálculo dummy
        System.out.println("Calculando Expected Shortfall (ES)...");
        double es = context.getPortfolioValue() * context.getMarketVolatility() * 0.025;
        System.out.printf("  -> ES 97.5%%: $%.2f%n", es);
    }
}