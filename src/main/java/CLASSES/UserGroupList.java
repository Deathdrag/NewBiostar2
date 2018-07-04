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
import org.json.JSONObject;

public class UserGroupList {
     
    public String sessionID;
    
    public String usergrouplist() throws IOException, MalformedURLException, URISyntaxException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/user_groups";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .addParameter("limit", "0")
                .addParameter("offset", "0")
                .build();
        
        HttpGetClass post = new HttpGetClass();
        String Results = post.httpget(uri, sessionID);
        
        UserGroupList usergroup = new UserGroupList();
        usergroup.usergroupsID(Results);
        
        return Results;
    }
    public String[] usergroupsID(String Results)
    {
        JSONObject jsonObject = new JSONObject(Results);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
       
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        
        
        for(int i=0; i<tsmresponse.length(); i++)
        {
            list.add(""+tsmresponse.getJSONObject(i).getInt("id")+"");
            list1.add(""+tsmresponse.getJSONObject(i).getString("name")+"");
        }
        
        String[] user_groups_id_list = list.toArray(new String[0]);
        String[] user_groups_name = list1.toArray(new String[0]);
        
        System.out.println("user_groups_id" +Arrays.toString(user_groups_id_list));
        System.out.println("user_groups_name" +Arrays.toString(user_groups_name));
        
        return user_groups_id_list;
    }
}
