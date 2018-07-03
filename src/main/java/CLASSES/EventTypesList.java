package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventTypesList {
    public String sessionID;
    
    public String eventstype() throws MalformedURLException, URISyntaxException, IOException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/references/event_types";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpGetClass post = new HttpGetClass();
        String Results = post.httpget(uri, sessionID);
        System.out.println("userlist" +Results);
        return Results;
    }
    
    public String[] CodeListName(String t) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(t);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
       
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        String ArryList;
        String ArryList1;
        
        for(int i=0; i<tsmresponse.length(); i++){
         
        ArryList =("\""+tsmresponse.getJSONObject(i).getInt("code"))+"\"";
        ArryList1 = ("\""+tsmresponse.getJSONObject(i).getString("description"))+"\"";
        list.add(ArryList);
        list1.add(ArryList1);
         
        }
     
        String[] ValuList = list1.toArray(new String[0]);
        String[] eventcode = list.toArray(new String[0]);
        System.out.println("ValuList" +Arrays.toString(ValuList));
        System.out.println("eventcode" +Arrays.toString(eventcode));
       return eventcode;
    } 
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    EventTypesList devislist = new EventTypesList();
    devislist.CodeListName(devislist.eventstype());
    
    
    
    }
}
