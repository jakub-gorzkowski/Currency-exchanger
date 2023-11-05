import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface UserInterface {
    static void loadData() {}
    void pickCurrency() throws IOException, ParserConfigurationException, SAXException;
    void exchange();
}
