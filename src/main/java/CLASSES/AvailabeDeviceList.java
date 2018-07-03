package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AvailabeDeviceList {
     public String sessionID;
    
     public String devislist() throws IOException, MalformedURLException, URISyntaxException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/devices";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .addParameter("limit", "0")
                .addParameter("offset", "0")
                .build();
        
        HttpGetClass post = new HttpGetClass();
        String Results = post.httpget(uri, sessionID);
        return Results;
    }
     
    public String[] deviceID(String results) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(results);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
        ArrayList<String> list = new ArrayList<>();

        for(int i=0; i<tsmresponse.length(); i++){
            list.add(""+tsmresponse.getJSONObject(i).getInt("id")+"");
        }
    
        String[] valuID = list.toArray(new String[0]);
       return valuID;
    } 
    
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    AvailabeDeviceList devislist = new AvailabeDeviceList();
    devislist.deviceID(devislist.devislist());
    
    }
}
