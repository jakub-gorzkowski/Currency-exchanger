import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

class XmlDataFormatter {

    private byte data[];

    private XmlDataFormatter() {

    }

    private static XmlDataFormatter xmlDataFormatter = null;

    public static XmlDataFormatter getInstance() {
        if (xmlDataFormatter == null) {
            xmlDataFormatter = new XmlDataFormatter();
        }

        return xmlDataFormatter;
    }

    public void setByte(byte[] data) {
        this.data = data;
    }

    public CurrencyCollection getCollection() throws IOException, SAXException, ParserConfigurationException {
        CurrencyCollection currencyCollection = new CurrencyCollection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new ByteArrayInputStream(data));
        Document document = builder.parse(inputSource);

        NodeList pozycje = document.getElementsByTagName("pozycja");

        for (int i = 0; i < pozycje.getLength(); i++) {
            Element pozycjaElement = (Element) pozycje.item(i);

            MyCurrency currency = new MyCurrency();
            currency.setCurrencyName(pozycjaElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent());
            currency.setCurrencyCode(pozycjaElement.getElementsByTagName("kod_waluty").item(0).getTextContent());

            String przelicznikStr = pozycjaElement.getElementsByTagName("przelicznik").item(0).getTextContent();
            String kursSredniStr = pozycjaElement.getElementsByTagName("kurs_sredni").item(0).getTextContent();

            double przelicznik = Double.parseDouble(przelicznikStr.replaceAll(",", "."));
            double kursSredni = Double.parseDouble(kursSredniStr.replaceAll(",", "."));

            currency.setConversionFactor(przelicznik);
            currency.setAverageExchangeRate(kursSredni);

            currencyCollection.addItem(currency);
        }
        return currencyCollection;
    }
}