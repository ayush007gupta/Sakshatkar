
package Client;


import Common.Request;
import Common.Response;
import Common.Systeminfo;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;


public class Clientreceiver extends Thread{
    
    ObjectOutputStream oos = null,oosv=null;
    ObjectInputStream ooi = null;
    String username = "";
    Videowindow video=null;
    Clientreceiver cr;
    String to;
    boolean k=true;
    Socket ss;
    public Clientreceiver( ObjectOutputStream os , ObjectInputStream is , String name ,Socket si)
    {
        oos = os;
        ooi = is;
        ss=si;
        username = name;
        
    }
    
 
    
    
    public void run()
    {
        Object obj = null;
        while(true)
        {
            try {
                obj = ooi.readObject();
            } catch (Exception ex) {
                Logger.getLogger(Clientreceiver.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
            
            if(obj instanceof  Response)
            {
                     Response res = (Response) obj;
                //Thread thread1 = new Thread(new Runnable() {
                  //  @Override
                    //public void run() {

                        if (res.status) {
                            System.out.println("Request accepted...");
                            System.out.println("caller get respone");
                            Thread t = new Thread(new StartVideoWindow(username,res.from));
                            t.start();
                        } else {
                            JOptionPane.showMessageDialog(null, "Request was declined...");
                        }
                    //}
               // });
                //thread1.start();
                
            }
            
            
            
            else if(obj instanceof  Request)
            {
                Request q = (Request) obj;
             
               //    Thread thread1 = new Thread(new Runnable() {
                   // @Override

                   // public void run() {

                        String from = q.from;
                        System.out.println(" Received request from : " + from + " in clientreceiver..." );
                        int a = showConfirmDialog(null, "call from" + from);
                        if (a == 0) {
                          
                          try {
                           Thread.sleep(1000);
                          } catch (InterruptedException ex) {
                        Logger.getLogger(Clientreceiver.class.getName()).log(Level.SEVERE, null, ex);
                         }
                           
                           Thread t = new Thread(new StartVideoWindow(username,q.from));
                            t.start();
                            System.out.println("respnse accepted in clientreciever");
                            Response res = new Response(username, from, true);
                            try {
              //                  oos.flush();
                                oos.writeObject(res);
                                oos.flush();
                            } catch (IOException ex) {
                                Logger.getLogger(Clientreceiver.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if(a==1||a==2) {
                            Response res = new Response(username, from, false);
                            try {
                //                oos.flush();
                                oos.writeObject(res);
                                oos.flush();
                            } catch (Exception ex) {
                                System.out.println(".run()");
                            }
                        }
                
                    
                //});
            //    thread1.start();
             }
            
            else if (obj instanceof Systeminfo) {
                 break;
            }
            else 
                 break;
        }
        
    }
    
}
