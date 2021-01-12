
package Common;

import java.io.Serializable;

public class Authenticate implements Serializable{
    public boolean status;
    public Authenticate(boolean b)
    {
        status=b;
    }
    
}
