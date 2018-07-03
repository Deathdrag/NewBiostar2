package CLASSES;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScanFingerPrint {
    public String sessionID;
    
    public String scan(int DeviceID,int enrollQuality) throws MalformedURLException, URISyntaxException, IOException
    {
        AdminClass snID = new AdminClass();
        sessionID = snID.LoginAction();
        
        String userFile = "/devices/"+DeviceID+"/scan_fingerprint";
        String json = "{\n" +
                        "\"enroll_quality\": "+enrollQuality+",\n" +
                        "\"retrieve_raw_image\": true\n" +
                        "}";
        
        BASE_URL domainName = new BASE_URL();
        URL baseURL = domainName.baseUrl;
        
        URI uri = new URIBuilder(baseURL+userFile)
                .build();
        
        HttpPostClass post = new HttpPostClass();
        String Results = post.httppost(uri, json, sessionID);
        
        return Results;
    }
    
    public String ScanFingerNumber(int fingers,int DeviceID,int enrollQuality) throws URISyntaxException, IOException
    {
        
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        
        String Results = null;
        String Results2 = null;
        String template0 = null;
        String template1 = null;
        String message = null;
        
        
        int a=1;
        
        ScanFingerPrint scan = new ScanFingerPrint(); 
        
        for(int idx =0; idx < fingers; idx++)
        {
            JOptionPane.showMessageDialog(null,"Click Ok to scan the "+a+" finger.");
            Results = scan.scan(DeviceID,enrollQuality);
            while("Scan quality is low.".equals(Results))
            {
                JOptionPane.showMessageDialog(null,"Scan your fingerprint again");
                Results = scan.scan(DeviceID,enrollQuality);

            }
             if(!"Scan quality is low.".equals(Results))
            {
                try
                {
                    Thread.sleep(500);
                }catch(InterruptedException e)
                {}
                Results2 = scan.scan(DeviceID,enrollQuality);
                while("Scan quality is low.".equals(Results2))
                    {
                        JOptionPane.showMessageDialog(null,"Scan your fingerprint again");
                        Results2 = scan.scan(DeviceID,enrollQuality);
                    }
            }
             if(Results2!=null && Results!=null)
             {
                JSONObject jObject = new JSONObject(Results);
                template0 = (String) jObject.get("template0");
                JSONObject jObj = new JSONObject(Results2);
                template1 = (String) jObj.get("template0");
                
                VerifyFingerPrint verify = new VerifyFingerPrint();
                String Results3 = verify.verify(DeviceID, template0, template1);
                
                if(Results3!=null)
                {
                    Base64Decoder myimage = new Base64Decoder();
                    myimage.decode((String) jObject.get("template_image0"));
                    myimage.decode2((String) jObj.get("template_image0"));
                    
                    ImageIcon imageT1 = new ImageIcon("C:/Users/gk/Desktop/Deathrow projects/NEWBIOSTAR2/images/sample1.PNG");
                    ImageIcon imageT2 = new ImageIcon("C:/Users/gk/Desktop/Deathrow projects/NEWBIOSTAR2/images/sample2.PNG");
                    Image image1 = imageT1.getImage();
                    Image image2 = imageT2.getImage();
                    Image newimg1 = image1.getScaledInstance(144, 160,  Image.SCALE_SMOOTH);
                    Image newimg2 = image2.getScaledInstance(144, 160,  Image.SCALE_SMOOTH);
                    imageT1 = new ImageIcon(newimg1);
                    imageT2 = new ImageIcon(newimg2);
                    
//                    temp1.setIcon(imageT1);
//                    temp0.setIcon(imageT2);
                }
             }
             item.put("is_prepare_for_duress", false);
             item.put("template0", template0);
             item.put("template1", template1);
             array.put(item);
             
             json.put("fingerprint_template_list",array);
             message = json.toString();
             a++;
        }
        return message;
    }
    public static void main(String args[]) throws MalformedURLException, IOException, URISyntaxException{
    ScanFingerPrint lg = new ScanFingerPrint();
    lg.ScanFingerNumber(2,539571364, 80);
    }
}
