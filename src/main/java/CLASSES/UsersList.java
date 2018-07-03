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

public class UsersList {
    public String sessionID;
    public String userslist(String user_group_id) throws IOException, MalformedURLException, URISyntaxException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/users";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .addParameter("group_id", ""+user_group_id+"")
                .addParameter("limit", "0")
                .addParameter("offset", "0")
                .build();
        
        HttpGetClass post = new HttpGetClass();
        String Results = post.httpget(uri, sessionID);
        
        return Results;
    }
    
    public String[] userIDarry(String Results)
    {
        JSONObject jsonObject = new JSONObject(Results);
        JSONArray tsmresponse = (JSONArray) jsonObject.get("records");
       
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        
        for(int i=0; i<tsmresponse.length(); i++)
        {
            list.add("{"+"\"user_id\": \""+tsmresponse.getJSONObject(i).getInt("user_id")+"\""+"}");
            list1.add(""+tsmresponse.getJSONObject(i).getString("name")+"");
        }
        
        
        String[] user_id_list = list.toArray(new String[0]);
        System.out.println("userlist" +Arrays.toString(user_id_list));
        
        return user_id_list;
    }
    
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    UsersList lg = new UsersList();
    lg.userIDarry(lg.userslist("1032"));
    }
}
