
package Common;

import java.io.Serializable;

public class Request implements Serializable {
    
    public String from;
    public String to;
    
    public Request(String a,String b)
    {
        from=a;
        to=b;
    }
}
