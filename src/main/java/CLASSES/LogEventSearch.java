package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogEventSearch {
    public String sessionID;
    
    public String logevents(String DeviceID,String enddate,String startdate,String[] codelist,String limit,String offset) throws MalformedURLException, URISyntaxException, IOException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/monitoring/event_log/search_by_device";
        
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
        
        System.out.println("logevents" +Results);
        return Results;
    }
    
    public String[] MonthlyLogs(String t) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(t);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
       
        ArrayList<String> list = new ArrayList<>();
        String[] columnNames = { "datetime", "device id", "device name", "user group name","userid", "user name",  "event type description"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for(int i=0; i<tsmresponse.length(); i++){
            
        list.add(""+tsmresponse.getJSONObject(i).getString("datetime")+"");
        list.add(""+tsmresponse.getJSONObject(i).getJSONObject("device").getInt("id")+"");
        list.add(""+tsmresponse.getJSONObject(i).getJSONObject("device").getString("name")+"");
        if(tsmresponse.getJSONObject(i).has("user_group"))
        {
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("user_group").getString("name")+"");
        }else
        {
            list.add(" ");
        }
        if(tsmresponse.getJSONObject(i).has("user"))
        {
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("user").getInt("user_id")+"");
        }else
        {
            list.add(" ");
        }
        if(tsmresponse.getJSONObject(i).has("user")&&tsmresponse.getJSONObject(i).getJSONObject("user").has("name"))
        {
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("user").getString("name")+"");
        }else
        {
            list.add(" ");
        }
        
        list.add(""+tsmresponse.getJSONObject(i).getJSONObject("event_type").getString("description")+"");
        model.addRow(list.toArray());
        list.clear();
        }
        JTable table = new JTable( model );
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
        String[] valuLogList = list.toArray(new String[0]);
        return valuLogList;
        
    }
    
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    AvailabeDeviceList devislist = new AvailabeDeviceList();
    String[] device = devislist.deviceID(devislist.devislist());
    
    EventTypesList EventTypesList = new EventTypesList();
    String[] ValuList = EventTypesList.CodeListName(EventTypesList.eventstype());
    
    LogEventSearch LogEventSearch = new LogEventSearch();
    String Results = LogEventSearch.logevents(device[0], "2018-06-30T23:59:00.00Z", "2018-06-01T23:59:00.00Z", ValuList, "0", "0");
    LogEventSearch.MonthlyLogs(Results);
    
    }
}
