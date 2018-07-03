package CLASSES;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.json.*;


public class HttpGetClass{
    public String httpget(URI uri,String snID) throws MalformedURLException, JSONException, URISyntaxException
    {
        BASE_URL domn = new BASE_URL();
        String domain = domn.Domain;
        String content;
        String message = null;
                
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        
        HttpGet getresults = new HttpGet(uri);
                
        CookieStore cookieStore = new BasicCookieStore();
	BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",snID);
	cookie.setDomain(domain);
	cookie.setPath("/");
	cookieStore.addCookie(cookie);
        
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        
        try (CloseableHttpResponse httpResponse = httpClient.execute(getresults,context)) 
        {
            content = EntityUtils.toString(httpResponse.getEntity());
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode== 200)
            { 
                message= content;
            }
            else if(statusCode != 200)
            {
                JSONObject jObject = new JSONObject(content);
                JOptionPane.showMessageDialog(null,(String) jObject.get("message"));
            }
        } catch (IOException e) {
        }
        
        return message;
    }
    
    
    
}
