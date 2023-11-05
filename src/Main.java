import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        CurrencyExchanger currencyExchanger = CurrencyExchanger.getInstance();
        currencyExchanger.pickCurrency();
        currencyExchanger.exchange();
    }
}
