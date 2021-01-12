package Common;

import java.io.Serializable;

public class Systeminfo implements Serializable {

   public int l;
   public String from,to;
   public Systeminfo(String s,String s2,int i)
   {
       l=i;
       from=s;
       to=s2;
       
   }
}
// l=1 logout
// l=2 call cut