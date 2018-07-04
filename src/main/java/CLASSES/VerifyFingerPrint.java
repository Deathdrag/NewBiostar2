package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.client.utils.URIBuilder;

public class VerifyFingerPrint {
   
    public String verify(int DeviceID,String template0,String template1,String sessionID) throws IOException, MalformedURLException, URISyntaxException
    {
        
        String userFile = "/devices/"+DeviceID+"/scan_fingerprint";
        String json = "{\n" +
                        "\"security_level\": \"DEFAULT\",\n" +
                        "\"template0\": "+template0+",\n" +
                        "\"template1\": "+template1+"\n" +
                       "}";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpPostClass post = new HttpPostClass();
        String Results = post.httppost(uri, json, sessionID);
        
        return Results;
    }
}
