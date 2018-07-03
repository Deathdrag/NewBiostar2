package CLASSES;

import java.net.MalformedURLException;
import java.net.URL;

public class BASE_URL {
    URL baseUrl;
    String Domain;
    String DomainName;

    public BASE_URL() throws MalformedURLException {
        this.baseUrl = new URL("http://127.0.0.1:8795/v2");
        this.Domain = "127.0.0.1";
        this.DomainName = "admin";
    }
}
