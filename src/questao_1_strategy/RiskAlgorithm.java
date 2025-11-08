package questao_1_strategy;

/**
 * INTERFACE STRATEGY (RiskAlgorithm)
 * * Define a interface comum para todos os algoritmos de risco suportados.
 * * JUSTIFICATIVA DE DESIGN (Strategy Pattern):
 * O padrão Strategy foi escolhido porque o problema exige que diferentes
 * algoritmos (VaR, ES, Stress) sejam "intercambiáveis em tempo de execução".
 * * 1. Encapsulamento: Cada algoritmo é encapsulado em sua própria classe.
 * 2. Intercambialidade: O RiskProcessor (Contexto) pode trocar a 
 * implementação concreta que ele usa a qualquer momento.
 * 3. Desacoplamento (SOLID): O RiskProcessor depende desta *abstração* * (RiskAlgorithm), não de implementações concretas (Princípio da 
 * Inversão de Dependência). Isso também satisfaz o Princípio Aberto/Fechado (OCP),
 * pois podemos adicionar novos algoritmos sem modificar o RiskProcessor.
 */
public interface RiskAlgorithm {
    /**
     * Calcula uma métrica de risco com base no contexto financeiro fornecido.
     * @param context O objeto contendo todos os dados financeiros necessários.
     */
    void calculate(FinancialContext context);
}