
package Client;

import Common.AudioPacket;
import Common.Targetuser;
import Common.callcut;
import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;

public class Audiosender extends Thread{
  Videowindow vid;
   
    ObjectOutputStream oos;
    ObjectInputStream ooi;
    String to,from;
    boolean k;
    int i;
    public void fun(Videowindow vv)
    {
        vid=vv;
    }
    public Audiosender(String fro,String name,ObjectOutputStream as,ObjectInputStream is)
    {
       from=fro;
       to=name;
       oos=as;
       ooi=is;
       k=true;
       i=0;
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
             oos.writeObject(new callcut(1,to));
             oos.flush();
             System.out.println("call cut request in audio from "+from); 
        } catch (IOException ex) {
            System.out.println("error in audioosender");
            Logger.getLogger(VideoSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
      
    }
    
    void fun3(int l) throws IOException
    {
             i=l;
             System.out.println(i);
              
             return;
    }
    
    
    public void run()
    {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED , 44100, 16 , 2 , 4, 44100, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format, 44100);
            if(!AudioSystem.isLineSupported(info))
            {
                System.err.println("Line not supported");
            }
            
            try (TargetDataLine targetLine = (TargetDataLine)AudioSystem.getLine(info)) {
                 targetLine.open();
            
                System.out.println("starting recording"+from);
                targetLine.start();
            
                byte data[]=new byte[10000];
            
       
                   try {
                        
                           AudioPacket ap;
                         
                           while(i==0)
                           {
                             targetLine.read(data, 0, 10000);
                            
                              ap=new AudioPacket(to,data);
                              if(i==3)
                                   break;
                              oos.writeObject(ap);
                              oos.flush();
                              if(i==3)
                                   break;
                            }
                           if(i==3)
                           {
                               oos.writeObject(new callcut(2,""));
                               oos.flush();
                           }
                      
                       } catch (IOException ex) {
                       Logger.getLogger(Audiosender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Stopeed Recording.. ");
              
                 
           }  catch (LineUnavailableException ex) {
          Logger.getLogger(Audiosender.class.getName()).log(Level.SEVERE, null, ex);
          }
     }
  } 