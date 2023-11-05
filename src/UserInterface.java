import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface UserInterface {
    void pickCurrency() throws IOException, ParserConfigurationException, SAXException;
    void exchange();
}
