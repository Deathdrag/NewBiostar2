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
import org.json.JSONObject;

public class UsersList {
    
    public String userslist(String sessionID) throws IOException, MalformedURLException, URISyntaxException
    {
        
        String userFile = "/users";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
//                .addParameter("group_id", ""+user_group_id+"")
                .addParameter("limit", "0")
                .addParameter("offset", "0")
                .build();
        
        HttpGetClass post = new HttpGetClass();
        String Results = post.httpget(uri, sessionID);
        
        UsersList ulist = new UsersList();
        ulist.userIDarry(Results);
        
        return Results;
    }
    
    public void userIDarry(String Results)
    {
        JSONObject jsonObject = new JSONObject(Results);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
       
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> Group = new ArrayList<>();
        
        String[] columnNames = { "user_id", "name", "email","user_group_name", "access_groups_name",  "status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for(int i=0; i<tsmresponse.length(); i++)
        {
            JSONObject dataArray = tsmresponse.getJSONObject(i);
            JSONArray InterestArray = dataArray.getJSONArray("access_groups");
            
            
            for(int j= 0; j<InterestArray.length(); j++)
        {
             Group.add(""+InterestArray.getJSONObject(j).getString("name")+"");
        }
            list.add(""+tsmresponse.getJSONObject(i).getString("user_id")+"");
            list.add(""+tsmresponse.getJSONObject(i).getString("name")+"");
            list.add(""+tsmresponse.getJSONObject(i).getString("email")+"");
            list.add(""+tsmresponse.getJSONObject(i).getJSONObject("user_group").getString("name")+"");
            list.add(""+Group+"");
            list.add(""+tsmresponse.getJSONObject(i).getString("status")+"");
        model.addRow(list.toArray());
        Group.clear();
        list.clear();
         
        }
        JTable table = new JTable( model );
        JOptionPane.showMessageDialog(null, new JScrollPane(table));
        
        
    }
    
    
}
