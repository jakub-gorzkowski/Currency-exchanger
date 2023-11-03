import java.util.ArrayList;
import java.util.List;

public class CurrencyCollection {
    private List<MyCurrency> currencyList = new ArrayList<>();

    public MyCurrency getItem(MyCurrency currency) {
        for(MyCurrency c: currencyList) {
            if (c.equals(currency)) {
                return c;
            }
        }
        return null;
    }

    public void setItem(MyCurrency currency) {
        for (int i = 0; i < currencyList.size(); i++) {
            if (currencyList.get(i).equals(currency)) {
                currencyList.set(i, currency);
                return;
            }
        }
        currencyList.add(currency);
    }

    public void addItem(MyCurrency currency) {
        for (MyCurrency value : currencyList) {
            if (value.equals(currency)) {
                return;
            }
        }
        currencyList.add(currency);
    }

    public boolean removeItem(MyCurrency currency) {
        for (int i = 0; i < currencyList.size(); i++) {
            if (currencyList.get(i).equals(currency)) {
                currencyList.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<MyCurrency> getCurrencies() {
        return currencyList;
    }
}
