import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataProvider {
    private String url;
    private DataProvider() {

    }
    private static DataProvider dataProvider = null;

    public static DataProvider getInstance() {
        if (dataProvider == null) {
            dataProvider = new DataProvider();
        }

        return dataProvider;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getData() throws IOException {
        URL xmlURL = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) xmlURL.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        byte[] data = inputStream.readAllBytes();

        connection.disconnect();
        return data;
    }
}
