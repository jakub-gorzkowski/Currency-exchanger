import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, InterruptedException {

        Exchange exchange = Exchange.getInstance();
        XmlDataFormatter xmlDataFormatter = XmlDataFormatter.getInstance();
        DataProvider dataProvider = DataProvider.getInstance();
        dataProvider.setUrl("https://www.nbp.pl/kursy/xml/lasta.xml");

        ConnectionController connectionController = ConnectionController.getInstance(dataProvider, xmlDataFormatter);
        connectionController.checkAvailability();

        CurrencyExchangerGUI currencyExchanger = CurrencyExchangerGUI.getInstance(exchange, xmlDataFormatter);
        currencyExchanger.generateInterface();
        currencyExchanger.pickCurrency();
        currencyExchanger.exchange();
    }
}
