package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.client.utils.URIBuilder;

public class EnrollFingerPrint {
    public String sessionID;
    
    public String enroll(String user_id,String FingerPrintArrylist) throws URISyntaxException, MalformedURLException, IOException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
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
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    ScanFingerPrint lg = new ScanFingerPrint();
    String FingerPrintArrylist = lg.ScanFingerNumber(2,539571364, 80);
    
    EnrollFingerPrint enrl = new EnrollFingerPrint();
    enrl.enroll("789", FingerPrintArrylist);
    }
}
