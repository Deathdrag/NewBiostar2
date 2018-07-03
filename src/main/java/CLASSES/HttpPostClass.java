package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.json.*;


public class HttpPostClass{
    public String httppost(URI uri,String json,String snID) throws MalformedURLException, JSONException, URISyntaxException
    {
        BASE_URL domn = new BASE_URL();
        String domain = domn.Domain;
        String content;
        String message = null;
                
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        
        HttpPost post = new HttpPost(uri);
                 post.setEntity(new StringEntity(json, "UTF8"));
                 post.setHeader("Content-type", "application/json");
                 
        CookieStore cookieStore = new BasicCookieStore();
	BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",snID);
	cookie.setDomain(domain);
	cookie.setPath("/");
	cookieStore.addCookie(cookie);
        
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        
        try (CloseableHttpResponse httpResponse = httpClient.execute(post,context)) 
        {
            content = EntityUtils.toString(httpResponse.getEntity());
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode== 200)
            { 
                message= content;
            }
            else if(statusCode != 200)
            {
//                System.out.println("access_groups_name" +content);
                JSONObject jObject = new JSONObject(content);
                JOptionPane.showMessageDialog(null,(String) jObject.get("message"));
            }
        } catch (IOException e) {
        }
        
        return message;
    }
    
    
    
}
