
package Common;

import Client.Login;
import java.io.Serializable;
import java.util.jar.Attributes;


public class AudioPacket implements Serializable{
    public byte b[]; 
    public String name ;
    public AudioPacket(String s,byte data[]) {
        b=new byte[data.length];
        for(int i=0;i<data.length;i++)
           b[i]=data[i];
       name =s;
    }
    
    
    
}
