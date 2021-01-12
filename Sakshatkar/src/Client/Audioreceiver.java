package Client;

import Common.AudioPacket;
import Common.callcut;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;

public class Audioreceiver extends Thread{
    Videowindow vid=null;
    ObjectInputStream  ooi=null;
    ObjectOutputStream oos;
    Audiosender as=null;
    
    Audioreceiver(ObjectInputStream iv,ObjectOutputStream is)
    {
        ooi=iv;
        oos=is;
    }
    
   
        public void run()
         {   
              Clip clip;
              AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED , 44100, 16 , 2 , 4, 44100, false);
              try 
              {

                while (true) 
                {

                    Object obj = null;
                    try {
                        obj = ooi.readObject();
                        
                        if (obj instanceof callcut) 
                        {
                            callcut c = (callcut) obj;
                            if (c.i == 2) {

                                System.out.println("audio reciever call cut from other side");
                                as.fun3(3);
                            } 
                            else 
                            {
                                System.out.println("call cut by me now reciever about to close");
                            }

                            break;
                        } 
                        else if (obj instanceof AudioPacket) 
                        {
                            System.out.println("audio recieving from other side");
                            clip = AudioSystem.getClip();
                            AudioPacket ap = (AudioPacket) obj;
                            clip.open(format, ap.b, 0, 10000);
                            clip.start();
                            System.gc();
                        }

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(VideoReceiver.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Audioreceiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("reciever closed");
            } catch (IOException ex) {
                Logger.getLogger(VideoReceiver.class.getName()).log(Level.SEVERE, null, ex);
            }

         }
}    

