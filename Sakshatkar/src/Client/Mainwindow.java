
package Client;

import Common.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import Common.*;
import java.net.Socket;
public class Mainwindow extends javax.swing.JFrame {

    ObjectOutputStream oos = null ;
    ObjectInputStream ooi = null;
    String username = null;
    Clientreceiver cr;
    Socket ss;
   
    
    Thread t1=null;
    
    
    
    public Mainwindow() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        label_username = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        contactList = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        front_username = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        call = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Window");

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_username.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        label_username.setText("@username");
        label_username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(label_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 30, 180, 46));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("   DP");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 30, 64, 46));

        jButton2.setText(".");
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 30, 23, 49));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Contact List");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 160, 180, 40));

        contactList.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        contactList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "aman", "ayush", "akshay", "Item 4" }));
        jPanel3.add(contactList, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 220, 220, 30));

        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 0, 10, 790));

        front_username.setFont(new java.awt.Font("Tahoma", 0, 60)); // NOI18N
        front_username.setText("username");
        jPanel3.add(front_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 330, 110));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 60)); // NOI18N
        jLabel6.setText("WELCOME ");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 330, 110));

        call.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        call.setText("Call");
        call.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                callActionPerformed(evt);
            }
        });
        jPanel3.add(call, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 280, 120, 40));

        jButton1.setText("logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1089, 410, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void callActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_callActionPerformed
       
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                Object obj = contactList.getSelectedItem();
                String to = obj.toString();
                Request req = new Request(username, to);
                try {

                    System.out.println("Requesting to " + to);

                    
                     oos.writeObject(req);
                     oos.flush();
                } catch (IOException ex) {
                    Logger.getLogger(Mainwindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
        
        
    }//GEN-LAST:event_callActionPerformed

    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
         try {
             
             oos.writeObject(new Systeminfo("","",1));
             oos.flush();
             this.dispose();
             System.exit(1);
            
          } catch (IOException ex) {
            Logger.getLogger(Mainwindow.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed
 
    
    void start( ObjectOutputStream os,ObjectInputStream is,String name,Socket c)
    {   
        oos = os;
        ooi = is;
        username = name ;
        ss=c;
        cr = new Clientreceiver(oos, ooi, name,ss);
        Thread t = new Thread(cr);
        t.start();
       
    }

   
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton call;
    private javax.swing.JComboBox<String> contactList;
    public javax.swing.JLabel front_username;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JLabel label_username;
    // End of variables declaration//GEN-END:variables
}
