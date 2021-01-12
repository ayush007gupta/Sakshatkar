
package Client;

import Common.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartVideoWindow implements  Runnable{
    
    String from,to;
    ObjectOutputStream oos,oosv,oosa;
    ObjectInputStream ooi,ooiv,ooia;
    Socket aud=null,vid=null;
    public StartVideoWindow(String fr,String tl){
        from=fr;
        to=tl;
    }
    
    

    @Override
    public void run() {
        
        Audioreceiver audioreceiver=null;
        Audiosender audiosender=null;
        VideoReceiver videoreceiver=null;
        VideoSender videosender=null;
        /*
        a=new Audioreceiver();
        b=new Audiosender();
        // videoreceiver=new VideoReceiver;
        //videosender=new VideoSender(vid,cr,oosv);
        // video thread sarting 
        //Thread t1=new Thread(a);
        //t1.start();
        //Thread t2=new Thread(b);
        //t2.start();
        Videowindow vw = new Videowindow();
        // System.out.println("vid ids starting");
        //videosender.vid=vw;
        //videoreceiver.vid=vw;
        cr.video=vw;
        vw.clientname.setText(cr.to );
        vw.myname.setText(cr.username);
        videosender.start();
        videoreceiver.start(); 
        vw.setVisible(true);*/
        Socket audio=null,video=null;
        try {
             System.out.println("start video window of "+from);  
            audio=new Socket("localhost",6667);
            oosa=new ObjectOutputStream(audio.getOutputStream());
            oosa.flush();
            ooia=new ObjectInputStream(audio.getInputStream());
            oosa.writeObject(new User(from,""));
            audioreceiver=new Audioreceiver(ooia,oosa);
            audiosender=new Audiosender(from,to,oosa,ooia);
                    
            video= new Socket("localhost",6668);
            oosv=new ObjectOutputStream(video.getOutputStream());
            oosv.flush();
            ooiv=new ObjectInputStream(video.getInputStream());
            oosv.writeObject(new User(from,""));
             
            videosender=new VideoSender(from,to,oosv,ooiv);
            videoreceiver=new VideoReceiver(ooiv,oosv);
             videosender.start();
             videoreceiver.start();
             audiosender.start();
             audioreceiver.start();
              
             videoreceiver.vs=videosender;
             audioreceiver.as=audiosender;
             Videowindow vw=new Videowindow(oosv,ooiv,oosa,ooia,videosender,audiosender,to);
             videosender.fun(vw);
             videoreceiver.fun(vw);
             vw.clientname.setText(to);
             vw.myname.setText(from);
            
                         
            
             vw.setVisible(true);
            

             videosender.join();
             videoreceiver.join();
             audiosender.join();
             audioreceiver.join();
          
            
        } catch (IOException ex) {
            Logger.getLogger(StartVideoWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) { 
            Logger.getLogger(StartVideoWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
         
       
        
        
        System.out.println("start video window ended");
     }
    
}
