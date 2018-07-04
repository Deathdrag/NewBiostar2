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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MothlyLogEvent {
  
    
    public String logeventslist(String[] codelist,String[] deviceQueryList,String sessionID) throws MalformedURLException, URISyntaxException, IOException
    {
      
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
        
        MothlyLogEvent logeventsearch = new MothlyLogEvent();
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
}
