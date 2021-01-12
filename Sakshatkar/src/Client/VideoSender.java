
package Client;

import Common.Targetuser;
import Common.callcut;
import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class VideoSender extends Thread{
    Videowindow vid;
   
    ObjectOutputStream oos;
    ObjectInputStream ooi;
    String to,from;
    boolean k;
    Webcam webcam;
    BufferedImage bm;
    ImageIcon iin;
    int i;
    public void fun(Videowindow vv)
    {
        vid=vv;
    }
    public VideoSender(String fro,String name,ObjectOutputStream as,ObjectInputStream is)
    {
       from=fro;
       to=name;
       oos=as;
       ooi=is;
       k=true;
    }
    void fun2() 
    {
        try {
            i=1;
            try {
                Thread.sleep(800);
            } catch (InterruptedException ex) {
                Logger.getLogger(VideoSender.class.getName()).log(Level.SEVERE, null, ex);
            }
             // oos.flush();
             //oos.flush();
             oos.writeObject(new callcut(1,to));
             oos.flush();
             System.out.println("call cut request from "+from); 
        } catch (IOException ex) {
            System.out.println("error in videosender");
            Logger.getLogger(VideoSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
      
    }
    void fun3(int l) throws IOException
    {
             i=3;
             k=false;
             System.out.println(i);
              System.out.println("send to videoclienthandler");
              
             return;
    }
    public void run()
    {
       
              try {
                 if(from.equals("ayush")) 
                 {
                     System.out.println("ayush webcam started");
                     webcam = Webcam.getWebcamByName("USB2.0 VGA UVC WebCam 0");
                 }
                 else 
                 {
                      System.out.println("aman webcam started");
                     webcam = Webcam.getWebcamByName("DroidCam Source 3 1");
                 }
                 webcam.open();
                 
                 while(i==0)
                 {
                 
                    bm = webcam.getImage();
                    iin = new ImageIcon(bm);
                    Targetuser tu=new Targetuser(to,iin); 
                    
                     try {
                            if(i==3)
                              break;
                         System.out.println("writing to other");
                         oos.writeObject(tu);
                         oos.flush();
                          if(i==3)
                               break;

                     } catch (IOException ex) {
                         System.out.println("error in sendind in videosender");
                         Logger.getLogger(VideoSender.class.getName()).log(Level.SEVERE, null, ex);
                     }
                   
                      
                      if(vid!=null)
                      vid.myvideo.setIcon(iin);
                           
                 }
                 webcam.close();
                  System.out.println(i);
                 if(i==3)
                 {
                     oos.writeObject(new callcut(2,""));
                     oos.flush();
                 }
                 System.out.println("Video sender closed");
             
                 } catch (IOException ex) {
                     System.out.println("exception in vedio sender in outr loop");
            Logger.getLogger(VideoSender.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
                }
            
        } 
    
  
    

