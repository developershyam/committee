package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddMember.java
 *
 * Created on Sep 16, 2012, 6:33:19 AM
 */

/**
 *
 * @author shyam
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class ChangeMemberDetail extends javax.swing.JInternalFrame
{

    /** Creates new form AddMember */
    JFrame mainframe;
    int member_id;
    Vector recurring,nonrecurring,date,oldmember,oldmemberid;
    int arr[]=null;
    public ChangeMemberDetail(JFrame mainframe)
    {
        super("Add New Member",true,true,true,true);
        this.mainframe=mainframe;
        recurring=new Vector();
        nonrecurring=new Vector();
        oldmember=new Vector();
        oldmemberid=new Vector();
        date=new Vector();
        initComponents();
            Dbcon db=new Dbcon();
            db.connect();
            oldmember.clear();
            oldmemberid.clear();
            try
            {
                db.rslt=db.stmt.executeQuery("select member_id,member_no from memberaccount where lottery_no='"+oldlottery.elementAt(c_oldlottery.getSelectedIndex())+"'");
                while(db.rslt.next())
                {
                    oldmemberid.addElement(db.rslt.getString(1));
                    oldmember.addElement(db.rslt.getString(2));
                }
                c_oldmember.setModel(new DefaultComboBoxModel(oldmember.toArray()));
                db.rslt=db.stmt.executeQuery("select name,f_name,address,contact_no from memberinfo where member_id="+oldmemberid.elementAt(c_oldmember.getSelectedIndex()));
                while(db.rslt.next())
                {
                    tf_name.setText(db.rslt.getString(1));
                    tf_fname.setText(db.rslt.getString(2));
                    tf_address.setText(db.rslt.getString(3));
                    tf_contact.setText(db.rslt.getString(4));
                     // oldmember.addElement(db.rslt.getString(1));
                }

            }
            catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
            db.disconnect();
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
        jLabel2 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_fname = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        tf_address = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tf_contact = new javax.swing.JTextField();
        l_date = new javax.swing.JLabel();
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            db.rslt=db.stmt.executeQuery("select lottery_no from lottery ");
            while(db.rslt.next())
            {
                oldlottery.addElement(db.rslt.getString(1));

            }

        }
        catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        c_oldlottery = new javax.swing.JComboBox();
        c_oldmember = new javax.swing.JComboBox();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Change Member Detail");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel2.setText("Enter Member Name :");

        tf_name.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel3.setText("Father Name :");

        tf_fname.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_fname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_fnameActionPerformed(evt);
            }
        });

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel5.setText("Address :");

        tf_address.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_address.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_address.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_addressActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel6.setText("Contact No. :");

        tf_contact.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_contact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_contactActionPerformed(evt);
            }
        });

        l_date.setFont(new java.awt.Font("Tahoma", 0, 16));

        c_oldlottery.setModel(new javax.swing.DefaultComboBoxModel(oldlottery.toArray()));
        c_oldlottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_oldlotteryItemStateChanged(evt);
            }
        });

        c_oldmember.setEditable(true);
        c_oldmember.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_oldmember.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_oldmemberItemStateChanged(evt);
            }
        });
        c_oldmember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_oldmemberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(c_oldlottery, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(c_oldmember, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(207, 207, 207))
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tf_contact, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(267, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(321, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_date, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(c_oldlottery, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c_oldmember, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tf_nameActionPerformed

    private void tf_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_fnameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();

        if(tf_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(mainframe,"Enter Member Name ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int t1=0;
        try {
            
            db.stmt.executeUpdate("update memberinfo set name='"+tf_name.getText()+"', f_name='"+tf_fname.getText()+"',address='"+tf_address.getText()+"',contact_no='"+tf_contact.getText()+"' where member_id="+oldmemberid.elementAt(c_oldmember.getSelectedIndex()));

            db.conn.commit();
            JOptionPane.showMessageDialog(mainframe,"Member Successfully Saved!");
        } catch(Exception e){
            JOptionPane.showMessageDialog(mainframe,"Member Can Not Be Saved !");System.out.println(e);}

        db.disconnect();
}//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tf_addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_addressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_addressActionPerformed

    private void tf_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_contactActionPerformed

    private void c_oldlotteryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_oldlotteryItemStateChanged
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
            db.connect();
            oldmember.clear();
            oldmemberid.clear();
            try
            {
                db.rslt=db.stmt.executeQuery("select member_id,member_no from memberaccount where lottery_no='"+oldlottery.elementAt(c_oldlottery.getSelectedIndex())+"'");
                while(db.rslt.next())
                {
                    oldmemberid.addElement(db.rslt.getString(1));
                    oldmember.addElement(db.rslt.getString(2));
                }
                c_oldmember.setModel(new DefaultComboBoxModel(oldmember.toArray()));
                db.rslt=db.stmt.executeQuery("select name,f_name,address,contact_no from memberinfo where member_id="+oldmemberid.elementAt(c_oldmember.getSelectedIndex()));
                while(db.rslt.next())
                {
                    tf_name.setText(db.rslt.getString(1));
                    tf_fname.setText(db.rslt.getString(2));
                    tf_address.setText(db.rslt.getString(3));
                    tf_contact.setText(db.rslt.getString(4));
                   // oldmember.addElement(db.rslt.getString(1));
                }

            }
            catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
            db.disconnect();
    }//GEN-LAST:event_c_oldlotteryItemStateChanged

    private void c_oldmemberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_oldmemberItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_c_oldmemberItemStateChanged

    private void c_oldmemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_oldmemberActionPerformed
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
            db.connect();
            try
            {
                db.rslt=db.stmt.executeQuery("select name,f_name,address,contact_no from memberinfo where member_id="+oldmemberid.elementAt(c_oldmember.getSelectedIndex()));
                while(db.rslt.next())
                {
                    tf_name.setText(db.rslt.getString(1));
                    tf_fname.setText(db.rslt.getString(2));
                    tf_address.setText(db.rslt.getString(3));
                    tf_contact.setText(db.rslt.getString(4));
                   // oldmember.addElement(db.rslt.getString(1));
                }

            }
            catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Enter Correct Code !","Error",JOptionPane.ERROR_MESSAGE);}
            db.disconnect();
    }//GEN-LAST:event_c_oldmemberActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox c_oldlottery;
    java.util.Vector oldlottery=new java.util.Vector();
    private javax.swing.JComboBox c_oldmember;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel l_date;
    private javax.swing.JTextField tf_address;
    private javax.swing.JTextField tf_contact;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_name;
    // End of variables declaration//GEN-END:variables

}
