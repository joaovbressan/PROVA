package questao_1_strategy;

// Estratégia Concreta 1
public class ValueAtRiskStrategy implements RiskAlgorithm {
    @Override
    public void calculate(FinancialContext context) {
        // Cálculo dummy (conforme solicitado)
        System.out.println("Calculando Value at Risk (VaR)...");
        double var = context.getPortfolioValue() * context.getMarketVolatility() * 0.01;
        System.out.printf("  -> VaR 95%%: $%.2f%n", var);
    }
}