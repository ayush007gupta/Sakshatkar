package Server;
import Common.User;
import com.mysql.jdbc.Connection;
import com.sun.org.glassfish.external.statistics.impl.StatisticImpl;
import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serverfile {

        
        
        Hashtable<String,Clienthandler>  userthread; // threads
        Hashtable<String,Audioclienthandler>  audiouserthread; 
        Hashtable<String,Videoclienthandler>  videouserthread; 
        
        Hashtable<String,Boolean> currentstatus;
        
        Hashtable<String,String>  database;
        Hashtable<String,Socket>  Activelist;// logged in
       
        static Serverfile server;
        
        public static void main(String[] args) throws IOException, SQLException 
        {
            try { 
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
            }
           Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:4400/sakshatkar","root","");
          
          server=new Serverfile();
          
          server.Activelist=new Hashtable<String,Socket>();
          server.userthread=new Hashtable<String,Clienthandler>();
          server.currentstatus=new Hashtable<String,Boolean>();
          
          server.database = new Hashtable<>();
          server.database.put("aman","123");
          server.database.put("ayush","123");
         

      
        
           
          server.audiouserthread=new Hashtable<String,Audioclienthandler>(); 
          server.videouserthread=new Hashtable<String,Videoclienthandler>(); 
          
          ServerSocket ss=new ServerSocket(6666);
          
          ServerSocket sa=new ServerSocket(6667);
          
          ServerSocket sv=new ServerSocket(6668);
        
            Thread e1 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("1");
                    while (true) {
                        Socket s;
                        try {
                            s = ss.accept();
                            ObjectInputStream ooi = new ObjectInputStream(s.getInputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                            System.out.println("clienthandler");
                            Thread t3 = new Thread(new Clienthandler(oos, ooi,server, s,connection));
                            t3.start();
                        } catch (IOException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            });              
          
                          
            Thread e2 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("2");
                    while (true) {
                        Socket s1 = null;
                         try {
                            s1 = sa.accept();
                         } catch (IOException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                         }
                         try {
                            if (s1 != null) {
                                server.Audio(s1,server);
                            }
                         } catch (IOException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });               
                  
                    
               
                  
              
            Thread e3 = new Thread(new Runnable() {
                public void run() {
                    System.out.println("3");
                    while (true) {
                        Socket s2 = null;
                        try {
                            s2 = sv.accept();
                         } catch (IOException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            if (s2 != null) {
                                server.Video(s2, server);
                             }
                        } catch (IOException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            e1.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
            }
            e2.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Serverfile.class.getName()).log(Level.SEVERE, null, ex);
            }
            e3.start();   
                   
              
      }
        
       
        
       
       public void Audio(Socket s, Serverfile ssi) throws IOException, ClassNotFoundException {
          String name ;
         ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
         oos.flush();
         ObjectInputStream ooi = new ObjectInputStream(s.getInputStream());
          
          
         Object obj=ooi.readObject();
         User u=(User)obj;
         name=u.username;
            System.out.println(name);
         Audioclienthandler t = new Audioclienthandler(ooi,oos,name,this);
         System.out.println("starting in auddeo"+name); 
         System.out.println("ending in audeo ");                                 // video client handler
         this.audiouserthread.put(name, t);
         t.start();
         //System.out.println(this.videouserthread.size());
        }
    
        public void Video(Socket s,Serverfile ssi) throws IOException, ClassNotFoundException
        {
        
         String name ;
         ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
         oos.flush();
         ObjectInputStream ooi = new ObjectInputStream(s.getInputStream());
          
          
         Object obj=ooi.readObject();
         User u=(User)obj;
         name=u.username;
            System.out.println(name);
         Videoclienthandler t = new Videoclienthandler(ooi,oos,name,this);
         System.out.println("starting in video"+name); 
         System.out.println("ending in video ");                                 // video client handler
         this.videouserthread.put(name, t);
         t.start();
         System.out.println(this.videouserthread.size());
         
        // int len=this.videouserthread.size();
            
        }
       
}
