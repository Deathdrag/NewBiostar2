package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MothlyLogEvent {
    public String sessionID;
    
    public String logeventslist(String[] codelist,String[] deviceQueryList) throws MalformedURLException, URISyntaxException, IOException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/monitoring/event_log/search_by_device";
        
        int limit = 0;
        int offset = 0;
        String json = "{\n" +
                        "  \"device_query_list\": "+Arrays.toString(deviceQueryList)+",\n" +
                        "  \"event_type_code_list\": "+Arrays.toString(codelist)+",\n" +
                        "  \"limit\": "+limit+",\n" +
                        "  \"offset\": "+offset+"\n" +
                        "}";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpPostClass post = new HttpPostClass();
        String Results = post.httppost(uri, json, sessionID);
        
        LogEventSearch logeventsearch = new LogEventSearch();
                        logeventsearch.MonthlyLogs(Results);
        return Results;
    }
    
    public String[] DeviceQueryList(String results) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(results);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
        ArrayList<String> list = new ArrayList<>();

        int j = 1;
        for(int i=0; i<tsmresponse.length(); i++)
        {    
            list.add("{\n" +
                     "\"device_id\": \""+tsmresponse.getJSONObject(i).getInt("id")+"\",\n" +
                     "\"end_datetime\": \""+YearMonth.now()+"-"+Month.from(LocalDate.now()).length(true)+"T23:59:00.00Z\",\n" +
                     "\"start_datetime\": \""+YearMonth.now()+"-0"+j+"T00:00:00.00Z\"\n" +
                    "}");
        }
    
            
            String[] valulist = list.toArray(new String[0]);
     
       return valulist;
    }
    
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    AvailabeDeviceList devislist = new AvailabeDeviceList();
    String List = devislist.devislist();
    
    EventTypesList eventtypeslist = new EventTypesList();
    String[] ValuList = eventtypeslist.CodeListName(eventtypeslist.eventstype());
    
    MothlyLogEvent monthlylist = new MothlyLogEvent();
    String[] DeviceList = monthlylist.DeviceQueryList(List);
    
    monthlylist.logeventslist(ValuList, DeviceList);
    
    }
}
