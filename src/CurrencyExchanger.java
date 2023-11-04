import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.List;

public class CurrencyExchanger extends JFrame {
    JLabel resultField;
    JButton button;
    JTextField textField;
    JComboBox<MyCurrency> currency1;
    JComboBox<MyCurrency> currency2;

    private CurrencyExchanger() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        Exchange exchange = Exchange.getInstance();
        DataProvider dataProvider = DataProvider.getInstance();
        dataProvider.setUrl("https://www.nbp.pl/kursy/xml/lasta.xml");

        XmlDataFormatter xmlDataFormatter = XmlDataFormatter.getInstance();

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
            }
            if (attempts == 3) {
                System.out.println();
                System.exit(1);
            }
        }

        CurrencyCollection collection = xmlDataFormatter.getCollection();

        collection.addItem(new MyCurrency("złoty polski", "PLN", 1, 1));

        List<MyCurrency> currencies = collection.getCurrencies();

        // GUI
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Kantor wymiany walut NBP");
        this.setResizable(false);
        this.setLayout(new FlowLayout());

        resultField = new JLabel(" ");
        resultField.setPreferredSize(new Dimension(100, 25));

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 25));
        this.add(textField);

        DefaultComboBoxModel<MyCurrency> currencyList1 = new DefaultComboBoxModel<>();
        currency1 = new JComboBox<>(currencyList1);
        this.add(currency1);

        this.add(resultField);

        DefaultComboBoxModel<MyCurrency> currencyList2 = new DefaultComboBoxModel<>();
        currency2 = new JComboBox<>(currencyList2);
        this.add(currency2);

        for (MyCurrency c: currencies) {
            currencyList1.addElement(c);
            currencyList2.addElement(c);
        }

        currency1.addActionListener(e -> {
            if (e.getSource() == currency1) {
                exchange.setCurrency1((MyCurrency) currency1.getSelectedItem());
            }
        });
        currency1.setSelectedItem(currencies.get(0));

        currency2.addActionListener(e -> {
            if (e.getSource() == currency2) {
                exchange.setCurrency2((MyCurrency) currency2.getSelectedItem());
            }
        });
        currency2.setSelectedItem(currencies.get(0));

        button = new JButton("Oblicz");
        button.addActionListener(e -> {
            if(e.getSource() == button) {
                try {
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    double amount = Double.parseDouble(textField.getText());
                    exchange.setAmount(amount);
                    resultField.setText(String.valueOf(decimalFormat.format(exchange.result())));
                } catch (NumberFormatException exception) {
                    exchange.setAmount(0);
                    resultField.setText(("Niepoprawne dane"));
                }

            }
        });
        button.setBounds(160, 100, 100, 25);
        this.add(button);
        this.pack();
    }

    private static CurrencyExchanger currencyExchanger = null;

    public static CurrencyExchanger getInstance() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        if (currencyExchanger == null) {
            currencyExchanger = new CurrencyExchanger();
        }

        return currencyExchanger;
    }
}
