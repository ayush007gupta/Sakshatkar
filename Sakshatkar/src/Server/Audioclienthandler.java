
package Server;

import Common.AudioPacket;
import Common.Systeminfo;
import Common.Targetuser;
import Common.callcut;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Audioclienthandler extends Thread {
    ObjectInputStream ooi;
    ObjectOutputStream oos; 
    Serverfile server;
    boolean k=true;
    String username;
    public Audioclienthandler(ObjectInputStream a,ObjectOutputStream b,String user,Serverfile ss)
    {
         ooi=a;
         oos=b;
         username=user;
         server=ss;
    }
    public void run()
    {
        Object obj=null;
       
      
       while(k)
        {    
                obj=null;
            try {
                obj = ooi.readObject();
            } catch (IOException ex) {
                Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
            }
                 if(obj instanceof AudioPacket)
                 {
                     AudioPacket ap = (AudioPacket) obj;
                     String name = ap.name;
                      
                      if(!server.currentstatus.containsKey(name))
                           continue;
                
                      try {
                        Audioclienthandler temp = null;
                        temp=Serverfile.server.audiouserthread.get(name);
                        ImageIcon ic=null;
                        
                        System.out.println("writing to other per"+ username);
                        temp.oos.writeObject(ap);
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

                              oos.writeObject(c);
                              oos.flush();
                              Thread.sleep(500);
                          } catch (IOException ex) {
                              System.out.println("error in audioclienthandler closing my own videoreciever" + username);
                              Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                          } catch (InterruptedException ex) {
                              Logger.getLogger(Videoclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                          }
                       
                          try {
                              Audioclienthandler temp = null;
                              temp = server.audiouserthread.get(c.name);
                              temp.oos.writeObject(new callcut(2, ""));
                              temp.oos.flush();
                           } catch (IOException ex) {

                              Logger.getLogger(Audioclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                          }

                       }
                    
                    else if(c.i==2)
                    {
                        try {
                            oos.flush();
                            break;
                         } catch (IOException ex) {
                           System.out.println("error in audioclienthandler cutting my  videoreciever" + username);
                            Logger.getLogger(Audioclienthandler.class.getName()).log(Level.SEVERE, null, ex);
                          }
                    }
                    
                    server.audiouserthread.remove(username);
                    System.out.println("videoclienthandler closing"+username);
                    break;
                   
                  }
         
          }
    }
  
}
