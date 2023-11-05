import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface UserInterface {
    static void loadData() throws InterruptedException, IOException, ParserConfigurationException, SAXException {}
    void pickCurrency();
    void exchange();
}
