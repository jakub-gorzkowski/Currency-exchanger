import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class CurrencyExchangerGUI extends JFrame implements UserInterface {
    private JPanel IOBox;
    private JPanel amountBox;
    private JPanel currenciesList;
    private JLabel resultField;
    private JButton exchangeButton;
    private JTextField currencyInput;
    private JComboBox<MyCurrency> currency1;
    private JComboBox<MyCurrency> currency2;
    private Exchange exchange;
    private XmlDataFormatter xmlDataFormatter;
    private static CurrencyExchangerGUI currencyExchanger = null;

    private CurrencyExchangerGUI(Exchange exchange, XmlDataFormatter xmlDataFormatter) {
        this.exchange = exchange;
        this.xmlDataFormatter = xmlDataFormatter;
    }

    public void generateInterface() throws IOException, ParserConfigurationException, SAXException {
        IOBox = new JPanel();
        IOBox.setLayout(new BoxLayout(IOBox, BoxLayout.X_AXIS));

        currenciesList = new JPanel();
        currenciesList.setLayout(new BoxLayout(currenciesList, BoxLayout.Y_AXIS));

        amountBox = new JPanel();
        amountBox.setLayout(new BoxLayout(amountBox, BoxLayout.Y_AXIS));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("NBP currency exchanger");
        this.setResizable(true);
        this.setSize(250, 100);

        DefaultComboBoxModel<MyCurrency> currencyList1 = new DefaultComboBoxModel<>();
        currency1 = new JComboBox<>(currencyList1);
        currency1.setPreferredSize(new Dimension(50, 25));
        currenciesList.add(currency1);

        DefaultComboBoxModel<MyCurrency> currencyList2 = new DefaultComboBoxModel<>();
        currency2 = new JComboBox<>(currencyList2);
        currency2.setPreferredSize(new Dimension(50, 25));
        currenciesList.add(currency2);

        currencyInput = new JTextField();
        currencyInput.setPreferredSize(new Dimension(100, 30));
        amountBox.add(currencyInput);

        resultField = new JLabel("");
        resultField.setPreferredSize(new Dimension(100, 30));
        amountBox.add(resultField);

        for (MyCurrency c: xmlDataFormatter.getCollection().getCurrencies()) {
            currencyList1.addElement(c);
            currencyList2.addElement(c);
        }

        exchangeButton = new JButton("Exchange");
        exchangeButton.setPreferredSize(new Dimension(100, 60));
        exchangeButton.setMaximumSize(new Dimension(100, 60));

        IOBox.add(amountBox);
        IOBox.add(currenciesList);
        IOBox.add(exchangeButton);
        this.add(IOBox);

        this.setVisible(true);
    }

    public void pickCurrency() throws IOException, ParserConfigurationException, SAXException {
        currency1.addActionListener(e -> {
            if (e.getSource() == currency1) {
                exchange.setCurrency1((MyCurrency) currency1.getSelectedItem());
            }
        });
        currency1.setSelectedItem(xmlDataFormatter.getCollection().getCurrencies().get(0));

        currency2.addActionListener(e -> {
            if (e.getSource() == currency2) {
                exchange.setCurrency2((MyCurrency) currency2.getSelectedItem());
            }
        });
        currency2.setSelectedItem(xmlDataFormatter.getCollection().getCurrencies().get(0));
    }

    public void exchange() {
        exchangeButton.addActionListener(e -> {
            if(e.getSource() == exchangeButton) {
                try {
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    double amount = Double.parseDouble(currencyInput.getText());

                    if (amount > 0) {
                        exchange.setAmount(amount);
                        resultField.setText(String.valueOf(decimalFormat.format(exchange.result())));
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (NumberFormatException exception) {
                    resultField.setText(("Illegal data type"));
                } catch (IllegalArgumentException exception) {
                    resultField.setText(("Invalid amount of currency"));
                }
            }
        });
    }

    public static CurrencyExchangerGUI getInstance(Exchange exchange, XmlDataFormatter xmlDataFormatter) {
        if (currencyExchanger == null) {
            currencyExchanger = new CurrencyExchangerGUI(exchange, xmlDataFormatter);
        }

        return currencyExchanger;
    }
}