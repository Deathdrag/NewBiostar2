package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AvailabeDeviceList {
     
    
     public String devislist(String sessionID) throws IOException, MalformedURLException, URISyntaxException
    {
        
        String userFile = "/devices";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .addParameter("limit", "0")
                .addParameter("offset", "0")
                .build();
        
        HttpGetClass post = new HttpGetClass();
        String Results = post.httpget(uri, sessionID);
        
        AvailabeDeviceList devislist = new AvailabeDeviceList();
        devislist.deviceID(Results);
    
        return Results;
    }
     
    public void deviceID(String results) throws JSONException
    {
        JSONObject jsonObject = new JSONObject(results);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
        ArrayList<String> list = new ArrayList<>();

        String[] columnNames = { "device id", "device name", "device_group_name","device_type_name", "device_ip",  "status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for(int i=0; i<tsmresponse.length(); i++){
            list.add(""+tsmresponse.getJSONObject(i).getInt("id")+"");
            list.add(""+tsmresponse.getJSONObject(i).getString("name")+"");
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("device_group").getString("name")+"");
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("device_type").getString("name")+"");
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("lan").getJSONObject("dhcp").getString("device_ip")+"");
            list.add(""+tsmresponse.getJSONObject(i).getString("status")+"");
            model.addRow(list.toArray());
            list.clear();
        }
        JTable table = new JTable( model );
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
    
    } 
    
}
