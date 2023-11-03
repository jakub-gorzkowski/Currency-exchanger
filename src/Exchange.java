public class Exchange {
    private final MyCurrency currency1 = new MyCurrency();
    private final MyCurrency currency2 = new MyCurrency();
    private double amount;

    private Exchange() {

    }

    private static Exchange exchange = null;

    public static Exchange getInstance() {
        if (exchange == null) {
           exchange = new Exchange();
        }

        return exchange;
    }

    public void setCurrency1(MyCurrency currency1) {
        this.currency1.setCurrencyCode(currency1.getCurrencyCode());
        this.currency1.setCurrencyName(currency1.getCurrencyName());
        this.currency1.setAverageExchangeRate(currency1.getAverageExchangeRate());
        this.currency1.setConversionFactor(currency1.getConversionFactor());
    }

    public void setCurrency2(MyCurrency currency2) {
        this.currency2.setCurrencyCode(currency2.getCurrencyCode());
        this.currency2.setCurrencyName(currency2.getCurrencyName());
        this.currency2.setAverageExchangeRate(currency2.getAverageExchangeRate());
        this.currency2.setConversionFactor(currency2.getConversionFactor());
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double result() {
        return (amount * currency1.getAverageExchangeRate() / currency1.getConversionFactor() / (currency2.getAverageExchangeRate() / currency2.getConversionFactor()));
    }
}
