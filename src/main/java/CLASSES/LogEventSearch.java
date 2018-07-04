package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import org.apache.http.client.utils.URIBuilder;

public class LogEventSearch {
    
    
    public String logevents(int DeviceID,String enddate,String startdate,String[] codelist,String sessionID) throws MalformedURLException, URISyntaxException, IOException
    {
        
        String userFile = "/monitoring/event_log/search_by_device";
        
        int limit= 0;
        int offset= 0;
        String json = "{\n" +
                        "  \"device_query_list\": [\n" +
                        "    {\n" +
                        "      \"device_id\": "+DeviceID+",\n" +
                        "      \"end_datetime\": \""+enddate+"\",\n" +
                        "      \"start_datetime\": \""+startdate+"\"\n" +
                        "    }\n" +
                        "  ],\n" +
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
        
        MothlyLogEvent monthlylist = new MothlyLogEvent();
        monthlylist.MonthlyLogs(Results);
        
        
        return Results;
    }
    
    
    
}
