package Client;

import Common.callcut;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;


public class VideoReceiver extends Thread {
    Videowindow vid=null;
    ObjectInputStream  ooi=null;
    ObjectOutputStream oos;
    ImageIcon im;
    VideoSender vs;
    VideoReceiver(ObjectInputStream iv,ObjectOutputStream is)
    {
        ooi=iv;
        oos=is;
      //  vs=vrr;
    }

   public void fun(Videowindow vv)
    {
        vid=vv;
    }
    
    public void run ()
    {   
        
        try {

            while (true) {

                Object obj = null;
                try {
                    obj = ooi.readObject();
                    if (obj instanceof callcut) {
                        callcut c = (callcut) obj;
                        if (c.i == 2) {

                            System.out.println("video reciever call cut from other side");
                            vs.fun3(3);
                            //oos.writeObject(new callcut(2,""));

                            // oos.flush();
                        } else {
                            System.out.println("call cut by me now reciever about to close");
                        }

                        if (vid != null) {
                            vid.dispose();
                        }

                        break;
                    } else if (obj instanceof ImageIcon) {
                        System.out.println("image recieving from other side");
                        im = (ImageIcon) obj;
                        vid.clientvideo.setIcon(im);
                        System.gc();
                    }

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(VideoReceiver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("reciever closed");
        } catch (IOException ex) {
            Logger.getLogger(VideoReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
   
}