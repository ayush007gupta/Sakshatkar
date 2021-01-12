package Server;
import Common.*;
import com.sun.org.apache.bcel.internal.generic.Select;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.BreakNode;
import sun.security.util.Password;
public class Clienthandler extends Thread {

    ObjectInputStream ooi;
    ObjectOutputStream oos;
    String username;
    String pass;
    Serverfile server;
    Socket ss;
     InetAddress ip;
     boolean  k=true;
     Connection connection;
     public Clienthandler(ObjectOutputStream o,ObjectInputStream  i,Serverfile s,Socket t,Connection cc)
     {
       oos=o;
       ooi=i;
       server=Serverfile.server;
       ss=t;
       connection=cc;
     }
   
   // thread start  
    public void run() 
    {
       while(true)
       {   
        Object obj = null ;
        try {
            obj = ooi.readObject();    //reading object from client
        } catch (IOException ex) {
            Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
        }

           
           if (obj instanceof User) {
                User u = (User) obj;
                username = u.username;
                pass = u.password;

                try {
                    if (authenticate()) {

                        while (true) 
                        {
                            try {
                                obj = ooi.readObject();
                            } catch (Exception ex) {
                                Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            if (obj instanceof Request) {          
                                Request q = (Request) obj;
                                String from, to;
                                from = q.from;
                                to = q.to;

                                System.out.println("Got request from " + from + " to : " + to);

                                if (server.currentstatus.containsKey(to) || server.currentstatus.containsKey(from) || !(server.Activelist.containsKey(to))) {
                                    Response res = new Response(to, from, false);
                                    System.out.println("request decline busy");
                                    try {
                                        //oos.flush();
                                        oos.writeObject(res);
                                        oos.flush();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    Clienthandler temp = server.userthread.get(to);
                                    if(temp==this)
                                         System.out.println("clienthandler same");
                                    
                                    try {
                                       // temp.oos.flush();
                                        temp.oos.writeObject(q);
                                        temp.oos.flush();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                             } // request from the client for call
                            
                            else if (obj instanceof Response) {
                                Response q = (Response) obj;
                                String from, to;
                                from = q.from;
                                to = q.to;

                                System.out.println("Response from : " + from + " to : " + to);

                                Clienthandler temp = null;

                                try {
                                    temp = Serverfile.server.userthread.get(to);
                                } catch (Exception e) {
                                    System.out.println("temp " + temp + "         " + e);
                                }

                                try {                 
                                    if (q.status) {
                                        System.out.println("status changed");
                                        Serverfile.server.currentstatus.put(to, true);
                                        Serverfile.server.currentstatus.put(from, true);
                                    }
                                    if(server.videouserthread.get(to)==server.videouserthread.get(from))
                                    {
                                        System.out.println("videthreads same "+to+from);
                                    }
                                    else
                                        System.out.println("Videothreads diff"+to+from);   
                                    if (temp != null) {
                                     //  temp.oos.flush();
                                        temp.oos.writeObject(q);
                                        temp.oos.flush();
                                    } else {

                                        System.out.println("Temp is null...");
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                            }
                            else if (obj instanceof Systeminfo) {

                                    Systeminfo s = (Systeminfo) obj;
                                    //oos.flush();
                                    oos.writeObject(s);
                                    oos.flush();
                                    break;
                             }
                           oos.flush();
                        }
                        //server.audiouserthread.remove(username);
                        //server.videouserthread.remove(username);
                        Serverfile.server.Activelist.remove(username);
                        Serverfile.server.userthread.remove(username);
                        break;
                         

                    } else {

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            }
            
            else if( obj instanceof SignupInfo ) 
            {
                System.out.println("signip");   
            try {
                SignupInfo si=(SignupInfo) obj;
                String name = si.fname;
                String pass = si.pass;
                System.out.println(name+pass);
                String query1="INSERT INTO `user`(`Username`, `Password`) VALUES (?,?)";
                
                System.out.println("signip");   
                PreparedStatement ps=null;
                   
                ps = connection.prepareStatement(query1);
                ps.setString(1,name);
                ps.setString(2, pass); 
                
                ps.execute();
                System.out.println(name + "  " + pass);
                
                server.database.put(name, pass);
            } catch (SQLException ex) {
                Logger.getLogger(Clienthandler.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
            }
        }
          

    }
   
   
    boolean authenticate() throws IOException, SQLException
    {
        String query="SELECT `Password` FROM `user` WHERE Username='"+(username)+"'";
        PreparedStatement ps=connection.prepareStatement(query);
        ResultSet rs=ps.executeQuery(query);
        boolean b = false;
        
         while(rs.next())
         { 
            String check=rs.getString("Password");
             System.out.println(check);
            if (check.equals(pass)) {
           
                server.Activelist.put(username, ss);
                server.userthread.put(username, this);
                //ip=ss.getInetAddress();
                b = true;
                System.out.println(username);       
                break;
            
            }     
         } 
        Authenticate au = new Authenticate(b);

        oos.writeObject(au);

        return b;

    }
   
 
  
    
    
}
