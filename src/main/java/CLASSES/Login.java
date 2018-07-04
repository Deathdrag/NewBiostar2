package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Login {
    public String sessionID;
    
    public String LoginAction(String userid,String password) throws MalformedURLException, IOException
    {
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        String Results = null;
        String content;
        String loginfile = "/login";
        String DName = domainName.DomainName;
        String json = "{\n" +
                        "\"mobile_app_version\": \"\",\n" +
                        "\"mobile_device_type\": \"\",\n" +
                        "\"mobile_os_version\": \"\",\n" +
                        "\"name\": \""+DName+"\",\n" +
                        "\"notification_token\": \"\",\n" +
                        "\"password\": \""+password+"\",\n" +
                        "\"user_id\": \""+userid+"\"\n" +
                        "}";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        HttpPost login = new HttpPost(baseURL+loginfile);
                 login.setEntity(new StringEntity(json, "UTF8"));
                 login.setHeader("Content-type", "application/json");
                 login.getHeaders("set-cookie");
                 
        try (CloseableHttpResponse response = httpclient.execute(login)) 
        {
            content = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode== 200)
                        {
                            Header[] cookies = response.getHeaders("set-cookie");
                            if (cookies == null) {
                            } else if(cookies != null) {
                                String[] strCookieArr = Arrays.toString(cookies).split("bs-cloud-session-id=",0);
                                String[] strCookieArr2 = strCookieArr[1].split(";", 0);
                                sessionID =strCookieArr2[0];
                            } else {
                            }
                            Results = content;
        
                        }
                        else if(statusCode != 200)
                        {
                            JSONObject jObject = new JSONObject(content);
                            JOptionPane.showMessageDialog(null,(String) jObject.get("message"));
                        }
        }
        return Results; 
    }
    
}
