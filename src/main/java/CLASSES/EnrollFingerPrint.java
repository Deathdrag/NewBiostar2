package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.client.utils.URIBuilder;

public class EnrollFingerPrint {
    
    
    public String enroll(int user_id,String FingerPrintArrylist,String sessionID) throws URISyntaxException, MalformedURLException, IOException
    {
        
        
        String userFile = "/users/"+user_id+"/fingerprint_templates";
        String json = ""+FingerPrintArrylist+"";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpPutClass putlist = new HttpPutClass();
        String Results = putlist.httpput(uri, json, sessionID);
        
        return Results;
        
    }
//    
}
