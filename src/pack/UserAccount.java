package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserAccount.java
 *
 * Created on Aug 29, 2012, 3:27:18 PM
 */

/**
 *
 * @author om
 */
import javax.swing.*;
public class UserAccount extends javax.swing.JDialog {

    /** Creates new form UserAccount */
    JFrame frame;
    String username="",password="";
    public UserAccount(JFrame parent, boolean modal,String username1,String password1) {
        super(parent, modal);
        frame = parent;
        username=username1;
        password=password1;
        initComponents();
        this.setLocation(300, 150);
      

   
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        base_panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tf_old = new javax.swing.JPasswordField();
        tf_new = new javax.swing.JPasswordField();
        tf_re = new javax.swing.JPasswordField();
        bt_save = new javax.swing.JButton();
        bt_change = new javax.swing.JButton();
        bt_close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("User Panel");

        base_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Type Old Password :");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("New Password :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Re-Type New Password :");

        tf_old.setEditable(false);
        tf_old.setFont(new java.awt.Font("Tahoma", 1, 18));
        tf_old.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tf_new.setEditable(false);
        tf_new.setFont(new java.awt.Font("Tahoma", 1, 18));
        tf_new.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        tf_re.setEditable(false);
        tf_re.setFont(new java.awt.Font("Tahoma", 1, 18));
        tf_re.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout base_panelLayout = new javax.swing.GroupLayout(base_panel);
        base_panel.setLayout(base_panelLayout);
        base_panelLayout.setHorizontalGroup(
            base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(base_panelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .addGap(89, 89, 89)
                .addGroup(base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_old, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_re, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_new, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89))
        );

        base_panelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tf_new, tf_old, tf_re});

        base_panelLayout.setVerticalGroup(
            base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(base_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_old)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_new, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(base_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_re)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        base_panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, jLabel5});

        bt_save.setFont(new java.awt.Font("Tahoma", 1, 14));
        bt_save.setText("SAVE");
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });

        bt_change.setFont(new java.awt.Font("Tahoma", 1, 14));
        bt_change.setText("Change Password");
        bt_change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_changeActionPerformed(evt);
            }
        });

        bt_close.setFont(new java.awt.Font("Tahoma", 1, 14));
        bt_close.setText("CLOSE");
        bt_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_closeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(base_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addComponent(bt_change)
                .addGap(53, 53, 53)
                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bt_change, bt_close, bt_save});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(base_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bt_close, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bt_change, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(bt_save, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_changeActionPerformed
        tf_old.setEditable(true);
        tf_new.setEditable(true);
        tf_re.setEditable(true);
            
    }//GEN-LAST:event_bt_changeActionPerformed

    private void bt_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_closeActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_bt_closeActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
        if(tf_old.getText().equals(""))
        {
            JOptionPane.showMessageDialog(frame,"Enter Old Password !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(tf_new.getText().equals(""))
        {
            JOptionPane.showMessageDialog(frame,"Enter New Password !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(tf_re.getText().equals(""))
        {
            JOptionPane.showMessageDialog(frame,"Enter Re-Enter New Password !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!tf_re.getText().equals(tf_new.getText()))
        {
            JOptionPane.showMessageDialog(frame,"Both New And  Re-Enter New Password Are Not Same!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!tf_old.getText().equals(password))
        {
            JOptionPane.showMessageDialog(frame,"Old Password Did Not Matched !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else
        try
        {
            Dbcon db=new Dbcon();
            db.connect();
            db.stmt.executeUpdate("update login set password='"+tf_new.getText()+"' where username='"+username+"'");
            db.conn.commit();
            db.disconnect();
            JOptionPane.showMessageDialog(frame,"Password Successfully Chanded !","Error",JOptionPane.PLAIN_MESSAGE);
            dispose();
        }
        catch(Exception e)
        {

            System.out.println(e);
            JOptionPane.showMessageDialog(frame,"Password Can Not Be Changed!","Error",JOptionPane.ERROR_MESSAGE);


        }
    }//GEN-LAST:event_bt_saveActionPerformed

    /**
    * @param args the command line arguments
    */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel base_panel;
    private javax.swing.JButton bt_change;
    private javax.swing.JButton bt_close;
    private javax.swing.JButton bt_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField tf_new;
    private javax.swing.JPasswordField tf_old;
    private javax.swing.JPasswordField tf_re;
    // End of variables declaration//GEN-END:variables

}
