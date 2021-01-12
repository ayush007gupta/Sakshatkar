
package Common;

import java.io.Serializable;
import javax.swing.ImageIcon;


public class Targetuser implements Serializable{
    public String Targetuser;
    public ImageIcon iin;
    public Targetuser(String s,ImageIcon ic)
    {
        Targetuser=s;
        iin=ic;
    }
}
