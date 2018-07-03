/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLASSES;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

public class Base64Decoder {
    public FileOutputStream image=null;
    public FileOutputStream image2=null;
    public FileOutputStream decode(String imputimage){
    String imageString=imputimage;
    
            try {
            
            //Decoding Base64 encoded Byte Array to Image Byte array
            byte[] base64DecodedByteArray = Base64.decodeBase64(imageString);
            
            image = new FileOutputStream("C:/Users/gk/Desktop/Deathrow projects/NEWBIOSTAR2/images/sample1.PNG");
            image.write(base64DecodedByteArray);
            image.close();
        }
        catch (FileNotFoundException e) {
        }
        catch (IOException ex) {
        }
        return image;
}
    
    public FileOutputStream decode2(String imputimage2){
    String imageString=imputimage2;
    
            try {
            
            //Decoding Base64 encoded Byte Array to Image Byte array
            byte[] base64DecodedByteArray = Base64.decodeBase64(imageString);
            image2 = new FileOutputStream("C:/Users/gk/Desktop/Deathrow projects/NEWBIOSTAR2/images/sample2.PNG");
            image2.write(base64DecodedByteArray);
            image2.close();
        }
        catch (FileNotFoundException e) {
        }
        catch (IOException ex) {
        }
        return image2;
}
}
