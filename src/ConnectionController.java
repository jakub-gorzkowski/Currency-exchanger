import java.io.IOException;
import java.net.UnknownHostException;

public class ConnectionController {

    private DataProvider dataProvider;
    private XmlDataFormatter xmlDataFormatter;

    private static ConnectionController connectionController = null;

    ConnectionController(DataProvider dataProvider, XmlDataFormatter xmlDataFormatter) {
        this.dataProvider = dataProvider;
        this.xmlDataFormatter = xmlDataFormatter;
    }
    public void checkAvailability() throws InterruptedException {
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

    public static ConnectionController getInstance(DataProvider dataProvider, XmlDataFormatter xmlDataFormatter) {
        if (connectionController == null) {
            connectionController = new ConnectionController(dataProvider, xmlDataFormatter);
        }

        return connectionController;
    }
}
