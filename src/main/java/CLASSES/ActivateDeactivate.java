package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import org.apache.http.client.utils.URIBuilder;

public class ActivateDeactivate {
    public String sessionID;
    public String activate(String access_groups_id,String expiry_datetime,String start_datetime,String user_group_id,String permission_name,String status_name,String [] users_ID_list) throws IOException, MalformedURLException, URISyntaxException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        int permission_id = 0;
        
        switch (permission_name) {
                case "Administrator":
                    permission_id = 1;
                    break;
                case "User Operator":
                    permission_id = 2;
                    break;
                case "Monitoring operator":
                    permission_id = 3;
                    break;
                case "T&A Operator":
                    permission_id = 254;
                    break;
                case "User":
                    permission_id = 255;
                    break;
                default:
                    break;
            }
        
        String userFile = "/users/update";
        System.out.println("access_groups_id = " +access_groups_id);
        System.out.println("expiry_datetime = " +expiry_datetime);
        System.out.println("permission_id = " +permission_id);
        System.out.println("start_datetime = " +start_datetime);
        System.out.println("status_name = " +status_name);
        System.out.println("user_group_id = " +user_group_id);
        System.out.println("users_ID_list = " +Arrays.toString(users_ID_list));
        String json = "{\n" +
                        " \"access_groups\": [\n" +
                        "    {\n" +
                        "      \"id\": "+access_groups_id+"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"expiry_datetime\": \""+expiry_datetime+"\",\n" +
                        "  \"permission\": {\n" +
                        "    \"id\": "+permission_id+"\n" +
                        "  },\n" +
                        "  \"start_datetime\": \""+start_datetime+"\",\n" +
                        "  \"status\": \""+status_name+"\",\n" +
                        "  \"user_group\": {\n" +
                        "    \"id\": "+user_group_id+"\n" +
                        "  },\n" +
                        "  \"users\": "+Arrays.toString(users_ID_list)+"\n" +
                        "}";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpPostClass post = new HttpPostClass();
        String Results = post.httppost(uri, json, sessionID);
        
        return Results;
    }
    
}
