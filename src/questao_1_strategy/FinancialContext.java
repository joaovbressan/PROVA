package questao_1_strategy;


public class FinancialContext {
    private final double portfolioValue;
    private final double marketVolatility;
    private final int timeHorizonDays;

    public FinancialContext(double portfolioValue, double marketVolatility, int timeHorizonDays) {
        this.portfolioValue = portfolioValue;
        this.marketVolatility = marketVolatility;
        this.timeHorizonDays = timeHorizonDays;
    }

    public double getPortfolioValue() { return portfolioValue; }
    public double getMarketVolatility() { return marketVolatility; }
    public int getTimeHorizonDays() { return timeHorizonDays; }
}