
package Common;

import java.io.Serializable;

public class SignupInfo implements  Serializable{
    
    public String fname;
    public String lname;
    public String email;
    public String username;
    public String pass;
    

    public SignupInfo(String fname , String lname ,String email, String uname , String pass )
    {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.username = uname;
        this.pass = pass;
        
    }
    
    
    
}
