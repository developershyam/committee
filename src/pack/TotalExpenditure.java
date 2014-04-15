package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddExpenditure.java
 *
 * Created on Nov 2, 2012, 2:29:22 PM
 */

/**
 *
 * @author shyam
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.AWTEventListener.*;
import java.util.*;
public class TotalExpenditure extends javax.swing.JInternalFrame {

    /** Creates new form AddExpenditure */
    JFrame mainframe;
    String sn[];
    JLabel l_am[];
    JTextField tf_am[];
    JTextArea ta[];
    JButton bt[];
    JLabel l_date[];
    JPanel p1[],p2[];
    JPanel panel;
    public TotalExpenditure(JFrame mainframe) {
        super("Expenditure Detail",true,true,true,true);
        this.mainframe=mainframe;
        initComponents();
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(815, 338));
        center.setLayout(new BorderLayout());
        center.add("Center",new JScrollPane(panel));
        panel.setLayout(new GridLayout(0,2));
        long total=0;
        try
        {
            Dbcon db=new Dbcon();
            db.connect();
            db.rslt=db.stmt.executeQuery("select sn,amount,date,description from expenditure where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' order by date desc");
            db.rslt.afterLast();
            int c=0;
            if(db.rslt.previous())c=db.rslt.getRow();
            sn=new String[c];
            l_am=new JLabel[c];
            l_date=new JLabel[c];
            tf_am=new JTextField[c];
            ta=new JTextArea[c];
            bt=new JButton[c];
            p1=new JPanel[c];
            p2=new JPanel[c];
            db.rslt.beforeFirst();
            c=0;
            while(db.rslt.next())
            {
                sn[c]=db.rslt.getString(1);
                l_am[c]=new JLabel("Amount : ");
                tf_am[c]=new JTextField(db.rslt.getString(2),10);
                if(db.rslt.getString(2)!=null)
                total+=Long.parseLong(db.rslt.getString(2));
                tf_am[c].setEditable(false);
                l_date[c]=new JLabel("Date : "+DateConverter.toDDMMYYYY(db.rslt.getString(3)));
                ta[c]=new JTextArea(db.rslt.getString(4));
                ta[c].setEditable(false);
                bt[c]=new JButton("Delete");
                p1[c]=new JPanel();
                p2[c]=new JPanel();
                final int p=c;
                final JFrame mf=mainframe;
               
                p1[c].add(l_date[c]);
                p1[c].add(l_am[c]);
                p1[c].add(tf_am[c]);
                //p1[c].add(bt[c]);

                p2[c].setLayout(new BorderLayout());
                p2[c].add(ta[c]);
                p1[c].setBorder(BorderFactory.createEtchedBorder());
                p2[c].setBorder(BorderFactory.createEtchedBorder());
                
                panel.add(p1[c]);
                panel.add(p2[c]);




                c++;
            }
            pend.setText("Total Expenditure : "+total);
            panel.updateUI();

            db.disconnect();
        }
        catch(Exception e)
        {
            System.out.println(e);
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
            db.rslt=db.stmt.executeQuery("select lottery_no from lottery where status=1");
            while(db.rslt.next())
            {
                lottery.addElement(db.rslt.getString(1));

            }

        }
        catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        c_lottery = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        center = new javax.swing.JPanel();
        pend = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Total Expenditure");

        c_lottery.setModel(new javax.swing.DefaultComboBoxModel(lottery.toArray()));
        c_lottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_lotteryItemStateChanged(evt);
            }
        });

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        center.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout centerLayout = new javax.swing.GroupLayout(center);
        center.setLayout(centerLayout);
        centerLayout.setHorizontalGroup(
            centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
        );
        centerLayout.setVerticalGroup(
            centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 338, Short.MAX_VALUE)
        );

        pend.setFont(new java.awt.Font("Tahoma", 1, 13));
        pend.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pend.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
            .addComponent(center, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(384, Short.MAX_VALUE))
            .addComponent(pend, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(center, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_lotteryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_lotteryItemStateChanged
        // TODO add your handling code here:
        panel.removeAll();
        panel.setLayout(new GridLayout(0,2));
        try
        {
            Dbcon db=new Dbcon();
            db.connect();
            db.rslt=db.stmt.executeQuery("select sn,amount,date,description from expenditure where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' order by date desc");
            db.rslt.afterLast();
            int c=0;
            if(db.rslt.previous())c=db.rslt.getRow();
            sn=new String[c];
            l_am=new JLabel[c];
            l_date=new JLabel[c];
            tf_am=new JTextField[c];
            ta=new JTextArea[c];
            bt=new JButton[c];
            p1=new JPanel[c];
            p2=new JPanel[c];
            db.rslt.beforeFirst();
            c=0;
            while(db.rslt.next())
            {
                sn[c]=db.rslt.getString(1);
                l_am[c]=new JLabel("Amount : ");
                tf_am[c]=new JTextField(db.rslt.getString(2),10);
                l_date[c]=new JLabel("Date : "+DateConverter.toDDMMYYYY(db.rslt.getString(3)));
                ta[c]=new JTextArea(db.rslt.getString(4));
                bt[c]=new JButton("Delete");
                p1[c]=new JPanel();
                p2[c]=new JPanel();
                
                final int p=c;
                final JFrame mf=mainframe;
                bt[p].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                       try
                       {
                            Dbcon db1=new Dbcon();
                            db1.connect();
                            db1.stmt.executeUpdate("delete from expenditure  where sn="+sn[p]);
                            db1.conn.commit();
                            db1.disconnect();
                            JOptionPane.showMessageDialog(mf,"Delete Successfully","Successfully",JOptionPane.PLAIN_MESSAGE);
                            dispose();
                       }
                       catch(Exception e)
                       {
                            JOptionPane.showMessageDialog(mf,"Can not be deleted","Error",JOptionPane.ERROR_MESSAGE);
                       }
                    }
                });
                System.out.println("add "+c);
                p1[c].add(l_date[c]);
                p1[c].add(l_am[c]);
                p1[c].add(tf_am[c]);
                p1[c].add(bt[c]);

                p2[c].setLayout(new BorderLayout());
                p2[c].add(ta[c]);

                p1[c].setBorder(BorderFactory.createEtchedBorder());
                p2[c].setBorder(BorderFactory.createEtchedBorder());

                panel.add(p1[c]);
                panel.add(p2[c]);
                

                c++;
            }
            panel.updateUI();

            db.disconnect();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
}//GEN-LAST:event_c_lotteryItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JPanel center;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel pend;
    // End of variables declaration//GEN-END:variables

}
