package questao_1_strategy;

public class Q1Demo {
    public static void main(String[] args) {
        // 1. Cria o processador e o contexto
        RiskProcessor processor = new RiskProcessor();
        FinancialContext context = new FinancialContext(1000000.0, 0.15, 30);

        // 2. Define e usa a primeira estratégia (VaR)
        processor.setAlgorithm(new ValueAtRiskStrategy());
        processor.executeRiskCalculation(context);

        System.out.println();

        // 3. Troca dinamicamente para a segunda estratégia (Stress)
        // O cliente muda o algoritmo sem conhecer os detalhes de implementação.
        processor.setAlgorithm(new StressTestingStrategy());
        processor.executeRiskCalculation(context);
        
        System.out.println();
        
        // 4. Troca para a terceira (ES)
        processor.setAlgorithm(new ExpectedShortfallStrategy());
        processor.executeRiskCalculation(context);
    }
}