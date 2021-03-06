package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MemberList.java
 *
 * Created on Sep 16, 2012, 7:44:37 AM
 */

/**
 *
 * @author shyam
 */

import javax.swing.*;

import javax.swing.table.*;
import java.awt.*;
import java.awt.event.AWTEventListener.*;
import java.util.*;
public class MemberList extends javax.swing.JInternalFrame {

    /** Creates new form MemberList */
    JFrame mainframe;
    int member_id;
    public MemberList(JFrame mainframe) {
        super("Member List",true,true,true,true);
        this.mainframe=mainframe;
        
        initComponents();
        {
                DefaultTableModel model=null;
                Object datas[][]=null;//={{false,"aaa","ccc"},{false,"ddd","fff"}};
		Dbcon db=new Dbcon();
                db.connect();
                try
                {
                        db.rslt=db.stmt.executeQuery("SELECT memberaccount.member_no,memberinfo.name,memberinfo.f_name,memberinfo.address,memberinfo.contact_no from lottery2.memberinfo,lottery2.memberaccount where memberinfo.member_id=memberaccount.member_id and memberaccount.status=1  and memberaccount.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
                        db.rslt.afterLast();
                        int count=0;
                        if (db.rslt.previous())count=db.rslt.getRow();
                        datas=new Object[count][6];
                        db.rslt.beforeFirst();
                        count=0;
                        while (db.rslt.next())
                        {
                                datas[count][0]=(count+1)+"";
                                datas[count][1]=lottery.elementAt(c_lottery.getSelectedIndex())+"-"+db.rslt.getString(1);
                                datas[count][2]=db.rslt.getString(2);
                                datas[count][3]=db.rslt.getString(3);
                                datas[count][4]=db.rslt.getString(4);
                                datas[count][5]=db.rslt.getString(5);
                                count++;
                        }
                }
                catch (Exception e)
                {
                        System.out.println(e);
                }
		Object[] columnNamess = {"S.N.","Member ID","Name","Father's Name","Address","Contact No."};
		model = new DefaultTableModel(datas, columnNamess);

		table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               //db.disconnect();
               table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               table.setRowHeight(30);
               table.setFont(new Font("Letter Gothic Std", Font.PLAIN, 15));
                
                db.disconnect();

}
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
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            db.rslt=db.stmt.executeQuery("select lottery_no from lottery");
            while(db.rslt.next())
            {
                lottery.addElement(db.rslt.getString(1));
            }

        }
        catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        c_lottery = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        bt_date = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Member List");

        c_lottery.setModel(new javax.swing.DefaultComboBoxModel(lottery.toArray()));
        c_lottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_lotteryItemStateChanged(evt);
            }
        });
        //Dbcon db=new Dbcon();
        {
            DefaultTableModel model=null;
            Object datas[][]=null;//={{false,"aaa","ccc"},{false,"ddd","fff"}};
            //Dbcon db=new Dbcon();
            db.connect();
            try
            {
                db.rslt=db.stmt.executeQuery("SELECT memberaccount.member_no,memberinfo.name,memberinfo.f_name,memberinfo.address,memberinfo.contact_no from lottery2.memberinfo,lottery2.memberaccount where memberinfo.member_id=memberaccount.member_id and memberaccount.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
                db.rslt.afterLast();
                int count=0;
                if (db.rslt.previous())count=db.rslt.getRow();
                datas=new Object[count][6];
                db.rslt.beforeFirst();
                count=0;
                while (db.rslt.next())
                {
                    datas[count][0]=(count+1)+"";
                    datas[count][1]=lottery.elementAt(c_lottery.getSelectedIndex())+"-"+db.rslt.getString(1);
                    datas[count][2]=db.rslt.getString(2);
                    datas[count][3]=db.rslt.getString(3);
                    datas[count][4]=db.rslt.getString(4);
                    datas[count][5]=db.rslt.getString(5);
                    count++;
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            Object[] columnNamess = {"S.N.","Member ID","Name","Father's Name","Address","Contact No."};
            model = new DefaultTableModel(datas, columnNamess);

            table.setModel(model);
            //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            db.disconnect();

        }

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel7.setText("Member Name :");

        tf_name.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_name.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nameActionPerformed(evt);
            }
        });
        tf_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_nameKeyReleased(evt);
            }
        });

        bt_date.setText("Search");
        bt_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_dateActionPerformed(evt);
            }
        });

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_lotteryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_lotteryItemStateChanged
        // TODO add your handling code here:
 {
               DefaultTableModel model=null;
                Object datas[][]=null;//={{false,"aaa","ccc"},{false,"ddd","fff"}};
		Dbcon db=new Dbcon();
                db.connect();
                try
                {
                        db.rslt=db.stmt.executeQuery("SELECT memberaccount.member_no,memberinfo.name,memberinfo.f_name,memberinfo.address,memberinfo.contact_no from lottery2.memberinfo,lottery2.memberaccount where memberinfo.member_id=memberaccount.member_id and memberaccount.status=1 and memberaccount.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
                        db.rslt.afterLast();
                        int count=0;
                        if (db.rslt.previous())count=db.rslt.getRow();
                        datas=new Object[count][6];
                        db.rslt.beforeFirst();
                        count=0;
                        while (db.rslt.next())
                        {
                                datas[count][0]=(count+1)+"";
                                datas[count][1]=lottery.elementAt(c_lottery.getSelectedIndex())+"-"+db.rslt.getString(1);
                                datas[count][2]=db.rslt.getString(2);
                                datas[count][3]=db.rslt.getString(3);
                                datas[count][4]=db.rslt.getString(4);
                                datas[count][5]=db.rslt.getString(5);
                                count++;
                        }
                }
                catch (Exception e)
                {
                        System.out.println(e);
                }
		Object[] columnNamess = {"S.N.","Member ID","Name","Father's Name","Address","Contact No."};
		model = new DefaultTableModel(datas, columnNamess);

		table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               db.disconnect();
               table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               table.setRowHeight(30);
               table.setFont(new Font("Letter Gothic Std", Font.PLAIN, 15));

                db.disconnect();
}
}//GEN-LAST:event_c_lotteryItemStateChanged

    private void tf_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nameActionPerformed
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
                db.connect();
                Object datas[][]=null;
                DefaultTableModel model=null;
                try
                {
                        db.rslt=db.stmt.executeQuery("SELECT memberaccount.lottery_no,memberaccount.member_no,memberinfo.name,memberinfo.f_name,memberinfo.address,memberinfo.contact_no from lottery2.memberinfo,lottery2.memberaccount where memberinfo.member_id=memberaccount.member_id  && memberaccount.status=1 && memberinfo.name like '%"+tf_name.getText()+"%'");
                        db.rslt.afterLast();
                        int count=0;
                        if (db.rslt.previous())count=db.rslt.getRow();
                        datas=new Object[count][6];
                        db.rslt.beforeFirst();
                        count=0;
                        while (db.rslt.next())
                        {
                                datas[count][0]=(count+1)+"";
                                datas[count][1]=db.rslt.getString(1)+"-"+db.rslt.getString(2);
                                datas[count][2]=db.rslt.getString(3);
                                datas[count][3]=db.rslt.getString(4);
                                datas[count][4]=db.rslt.getString(5);
                                datas[count][5]=db.rslt.getString(6);
                                count++;
                        }
                }
                catch (Exception e)
                {
                        System.out.println(e);
                }
		Object[] columnNamess = {"S.N.","Member ID","Name","Father's Name","Address","Contact No."};
		model = new DefaultTableModel(datas, columnNamess);

		table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               db.disconnect();
               table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               table.setRowHeight(30);
               table.setFont(new Font("Letter Gothic Std", Font.PLAIN, 15));

                db.disconnect();


        
}//GEN-LAST:event_tf_nameActionPerformed

    private void bt_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_dateActionPerformed
        // TODO add your handling code here:
       // tf_name.setText(new DatePicker(mainframe).setPickedDate());
                Dbcon db=new Dbcon();
                db.connect();
                Object datas[][]=null;
                DefaultTableModel model=null;
                try
                {
                        db.rslt=db.stmt.executeQuery("SELECT memberaccount.lottery_no,memberaccount.member_no,memberinfo.name,memberinfo.f_name,memberinfo.address,memberinfo.contact_no from lottery2.memberinfo,lottery2.memberaccount where memberinfo.member_id=memberaccount.member_id  && memberaccount.status=1 && memberinfo.name like '%"+tf_name.getText()+"%'");
                        db.rslt.afterLast();
                        int count=0;
                        if (db.rslt.previous())count=db.rslt.getRow();
                        datas=new Object[count][6];
                        db.rslt.beforeFirst();
                        count=0;
                        while (db.rslt.next())
                        {
                                datas[count][0]=(count+1)+"";
                                datas[count][1]=db.rslt.getString(1)+"-"+db.rslt.getString(2);
                                datas[count][2]=db.rslt.getString(3);
                                datas[count][3]=db.rslt.getString(4);
                                datas[count][4]=db.rslt.getString(5);
                                datas[count][5]=db.rslt.getString(6);
                                count++;
                        }
                }
                catch (Exception e)
                {
                        System.out.println(e);
                }
		Object[] columnNamess = {"S.N.","Member ID","Name","Father's Name","Address","Contact No."};
		model = new DefaultTableModel(datas, columnNamess);

		table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               db.disconnect();
               table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               table.setRowHeight(30);
               table.setFont(new Font("Letter Gothic Std", Font.PLAIN, 15));

                db.disconnect();



}//GEN-LAST:event_bt_dateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_nameKeyReleased
        // TODO add your handling code here:
         Dbcon db=new Dbcon();
                db.connect();
                Object datas[][]=null;
                DefaultTableModel model=null;
                try
                {
                        db.rslt=db.stmt.executeQuery("SELECT memberaccount.lottery_no,memberaccount.member_no,memberinfo.name,memberinfo.f_name,memberinfo.address,memberinfo.contact_no from lottery2.memberinfo,lottery2.memberaccount where memberinfo.member_id=memberaccount.member_id  && memberaccount.status=1 && memberinfo.name like '%"+tf_name.getText()+"%'");
                        db.rslt.afterLast();
                        int count=0;
                        if (db.rslt.previous())count=db.rslt.getRow();
                        datas=new Object[count][6];
                        db.rslt.beforeFirst();
                        count=0;
                        while (db.rslt.next())
                        {
                                datas[count][0]=(count+1)+"";
                                datas[count][1]=db.rslt.getString(1)+"-"+db.rslt.getString(2);
                                datas[count][2]=db.rslt.getString(3);
                                datas[count][3]=db.rslt.getString(4);
                                datas[count][4]=db.rslt.getString(5);
                                datas[count][5]=db.rslt.getString(6);
                                count++;
                        }
                }
                catch (Exception e)
                {
                        System.out.println(e);
                }
		Object[] columnNamess = {"S.N.","Member ID","Name","Father's Name","Address","Contact No."};
		model = new DefaultTableModel(datas, columnNamess);

		table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               db.disconnect();
               table.setModel(model);
                //table.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
               table.setRowHeight(30);
               table.setFont(new Font("Letter Gothic Std", Font.PLAIN, 15));

                db.disconnect();
    }//GEN-LAST:event_tf_nameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_date;
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_name;
    // End of variables declaration//GEN-END:variables

}
