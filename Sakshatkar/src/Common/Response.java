
package Common;

import java.io.Serializable;
public class Response implements Serializable {
  
    public String from;
    public String to;
    public boolean status;

    public Response(String a,String b,boolean ss) {
        from=a;
        to=b;
        status=ss;
   }
}
