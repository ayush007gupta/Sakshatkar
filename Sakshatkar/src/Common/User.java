
package Common;

import java.io.Serializable;

public class User implements Serializable {
   public String username;
   public String password;
   
   public User(String a,String b)
   {
       username=a;
       password=b;
   }
   
}
