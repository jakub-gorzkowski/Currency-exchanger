public class MyCurrency {
    private String currencyName;
    private String currencyCode;
    private double conversionFactor;
    private double averageExchangeRate;

    MyCurrency(){}

    MyCurrency(String currencyName, String currencyCode, double conversionFactor, double averageExchangeRate) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.conversionFactor = conversionFactor;
        this.averageExchangeRate = averageExchangeRate;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public void setAverageExchangeRate(double averageExchangeRate) {
        this.averageExchangeRate = averageExchangeRate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public double getAverageExchangeRate() {
        return averageExchangeRate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (!(obj instanceof MyCurrency)) {
            return false;
        }

        MyCurrency waluta = (MyCurrency) obj;

        if (!this.currencyName.equals(waluta.getCurrencyName())) {
            return false;
        }

        if (this.conversionFactor != waluta.getConversionFactor()) {
            return false;
        }

        if (!this.currencyCode.equals(waluta.getCurrencyCode())) {
            return false;
        }

        if (this.averageExchangeRate != waluta.getAverageExchangeRate()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return this.currencyCode;
    }
}
