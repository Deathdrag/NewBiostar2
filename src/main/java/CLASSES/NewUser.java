
package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.client.utils.URIBuilder;

public class NewUser {
    public String sessionID;
    public String adduser(String email, String name, String login_id,String expdate, String password, 
          String startdate,String user_id, String phone_no, String operator_name, int operator_no,int user_group_id,String user_group_name,int access_groups_id,String access_groups_name) throws URISyntaxException, MalformedURLException, IOException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/users";
        
        String json = "{\n" +
                        "\"access_groups\": [\n" +"{\n" +"\"id\": "+access_groups_id+",\n" +"\"included_by_user_group\": \"Yes\",\n" +" \"name\": \""+access_groups_name+"\"\n" +" }\n" +"  ],\n" +
                        "\"email\": \""+ email +"\",\n" +
                        "\"expiry_datetime\": \""+ expdate +"\",\n" +
                        "\"login_id\": \""+ login_id +"\",\n" +
                        "\"name\": \""+ name +"\",\n" +
                        "\"password\": \""+ password +"\",\n" +
                        "\"permission\": {\n" +"\"id\": "+operator_no+",\n" +"\"name\": \""+ operator_name +"\",\n" +"\"permissions\": [\n" +"{\n" +"\"allowed_group_id_list\": [\n" +" \"1\"\n" +" ],\n" +"\"module\": \"CARD\",\n" +"\"read\": true,\n" +"\"write\": true\n" +"}\n" +" ]\n" +"},\n" +
                        "\"phone_number\": \""+ phone_no +"\",\n" +
                        "\"pin\": \"\",\n" +
                        "\"security_level\": \"\",\n" +
                        "\"start_datetime\": \""+ startdate +"\",\n" +
                        "\"status\": \"AC\",\n" +
                        "\"user_group\": {\n" +"\"id\": "+user_group_id+",\n"+"\"name\": \""+user_group_name+"\"\n"+"},\n" +
                        "\"user_id\": \""+ user_id +"\"\n" +
                        "}";
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpPostClass post = new HttpPostClass();
        String Results = post.httppost(uri, json, sessionID);
        return Results;
    }
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    NewUser lg = new NewUser();
    lg.adduser("commerceyrd@gmail.com", "commerceryd", "commerceryd", "2019-02-27T23:59:00.00Z", "commerceryd747", "2018-02-27T23:59:00.00Z", "4857", "0715668934", "Monitoring operator", 3,1032,"drag in",3,"VistorGroup");
}
}
