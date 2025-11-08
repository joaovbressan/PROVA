package questao_1_strategy;

// Estratégia Concreta 3
public class StressTestingStrategy implements RiskAlgorithm {
    @Override
    public void calculate(FinancialContext context) {
        // Cálculo dummy
        System.out.println("Executando Stress Testing...");
        System.out.println("  -> Cenário: Queda de 50% no mercado.");
        System.out.printf("  -> Perda Potencial: $%.2f%n", context.getPortfolioValue() * 0.5);
    }
}