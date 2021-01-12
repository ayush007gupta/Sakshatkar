package Server;
import Client.Login;
import Common.Systeminfo;
import Common.Targetuser;
import Common.callcut;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.swing.ImageIcon;

public class Videoclienthandler extends Thread{
    ObjectInputStream ooi;
    ObjectOutputStream oos; 
    Serverfile server;
    boolean k=true;
    String username;
    public Videoclienthandler(ObjectInputStream a,ObjectOutputStream b,String user,Serverfile ss)
    {
         ooi=a;
         oos=b;
         username=user;
         server=ss;
    }
    public void run() 
    {
        Object obj=null;
       
        while(true)
        {    
            obj=null;
            try {
            
                 obj = ooi.readObject();
            } catch (IOException ex) {
                Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            
                 if(obj instanceof Targetuser)
                 {
                     Targetuser tu = (Targetuser) obj;
                     String name = tu.Targetuser;
                      
                      if(!server.currentstatus.containsKey(name))
                           continue;
                
                      try {
                        Videoclienthandler temp = null;
                        temp=Serverfile.server.videouserthread.get(name);
                        ImageIcon ic=null;
                        ic=tu.iin;
                        System.out.println(name); 
                        System.out.println(server.currentstatus.size()); 
                     // System.out.println("temp null in video clienthandler");
                        
                          System.out.println("writing to other per"+ username);
                          temp.oos.writeObject(ic);
                          temp.oos.flush();
                         
                        
                     } catch (IOException ex) {
                        System.out.println("error in videoclienthandler in sending");
                        Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                      
                     }
                  }
                else if(obj instanceof callcut)
                {
                      callcut c=(callcut)obj;
                      if (c.i == 1) 
                      {
                        try {
                            System.out.println(username+" "+c.name);
                            if(server.videouserthread.get(username)!=server.videouserthread.get(c.name))
                            {
                                System.out.println("threads different");
                            }
                            else 
                                System.out.println("threads same");
                            System.out.println("current sattaus changed of cutting person reomed"+username);
                            server.currentstatus.remove(username);
                            oos.writeObject(c);
                            oos.flush();
                            Thread.sleep(500);
                        } catch (IOException ex) {
                            System.out.println("error in videoclienthandler closing my own videoreciever" + username);
                            Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                              Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                          }
                        System.out.println("call cut button pressed" + c.i);
                        try {
                            Videoclienthandler temp = null;
                            temp=server.videouserthread.get(c.name);
                            server.currentstatus.remove(username);
                            System.out.println("current sattaus changed of other person reomed");
                            System.out.println("notiifed to "+c.name);
                            //temp.oos.flush();
                            
                            temp.oos.writeObject(new callcut(2, ""));
                            temp.oos.flush();
                        } catch (IOException ex) {
                            
                            Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        

                        
                        System.out.println(username);

                        }
                    else if(c.i==2){
                        try {
                            System.out.println("video clienthandlercall cut by other side" + c.i + username);
                            server.currentstatus.remove(username);
                            oos.flush();
                            System.out.println("videoclienthandler closing"+username);
                            break;
                        } catch (IOException ex) {
                           System.out.println("error in videoclienthandler cutting my  videoreciever" + username);
                            Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                               
                        }
                    }
                    
                     server.videouserthread.remove(username);
                     System.out.println("videoclienthandler closing"+username);
                     break;
                   
                  }
                
           }
        
    }
    
   
}
    