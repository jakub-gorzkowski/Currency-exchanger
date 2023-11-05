import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

public class CurrencyExchanger extends JFrame implements UserInterface {
    private final JLabel resultField;
    private final JButton exchangeButton;
    private final JTextField currencyInput;
    private final JComboBox<MyCurrency> currency1;
    private final JComboBox<MyCurrency> currency2;
    private final Exchange exchange = Exchange.getInstance();
    private static final XmlDataFormatter xmlDataFormatter = XmlDataFormatter.getInstance();
    private static final DataProvider dataProvider = DataProvider.getInstance();
    private static CurrencyExchanger currencyExchanger = null;

    private CurrencyExchanger() throws IOException, ParserConfigurationException, SAXException {

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("NBP currency exchanger");
        this.setResizable(false);
        this.setLayout(new FlowLayout());

        resultField = new JLabel("");
        resultField.setPreferredSize(new Dimension(100, 25));

        currencyInput = new JTextField();
        currencyInput.setPreferredSize(new Dimension(100, 25));
        this.add(currencyInput);

        DefaultComboBoxModel<MyCurrency> currencyList1 = new DefaultComboBoxModel<>();
        currency1 = new JComboBox<>(currencyList1);
        this.add(currency1);

        this.add(resultField);

        DefaultComboBoxModel<MyCurrency> currencyList2 = new DefaultComboBoxModel<>();
        currency2 = new JComboBox<>(currencyList2);
        this.add(currency2);

        for (MyCurrency c: xmlDataFormatter.getCollection().getCurrencies()) {
            currencyList1.addElement(c);
            currencyList2.addElement(c);
        }

        exchangeButton = new JButton("Exchange");
        exchangeButton.setBounds(160, 100, 100, 25);
        this.add(exchangeButton);
        this.pack();
    }

    public static void loadData() throws InterruptedException {
        dataProvider.setUrl("https://www.nbp.pl/kursy/xml/lasta.xml");

        for (int attempts = 1; true; attempts++) {
            try {
                xmlDataFormatter.setByte(dataProvider.getData());
                break;
            } catch (UnknownHostException e) {
                System.out.print("[" + attempts + "] " + "Reconnection attempt");
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(500);
                    System.out.print(".");
                }
                Thread.sleep(3500);
                System.out.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (attempts == 3) {
                System.out.println();
                System.exit(1);
            }
        }
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
        exchangeButton.setBounds(160, 100, 100, 25);
        this.add(exchangeButton);
        this.pack();
    }

    public static CurrencyExchanger getInstance() throws IOException, ParserConfigurationException, SAXException {
        if (currencyExchanger == null) {
            currencyExchanger = new CurrencyExchanger();
        }

        return currencyExchanger;
    }
}