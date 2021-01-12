
package Common;

import com.sun.xml.internal.ws.developer.Serialization;
import java.io.Serializable;


public class callcut implements Serializable{
    public int i;
    public String name;
    public callcut(int l,String n)
    {
        i=l;
        name=n;
    }
}
