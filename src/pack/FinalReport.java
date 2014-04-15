package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MonthlyReport.java
 *
 * Created on Oct 8, 2012, 10:06:59 PM
 */

/**
 *
 * @author shyam
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FinalReport extends javax.swing.JInternalFrame {

    /** Creates new form MonthlyReport */
    JFrame mainframe;
Vector recurring,nonrecurring,date,member_id,person,lmonth;
long totalavailable=0;
    public FinalReport(JFrame mainframe) {
        super("Final Report",true,true,true,true);
        this.mainframe=mainframe;
        recurring=new Vector();
        nonrecurring=new Vector();
        date=new Vector();
        member_id=new Vector();
        person=new Vector();
        lmonth =new Vector();
        initComponents();
        jLabel1.setText("Final Report");
        l_ldate.setText(""+lottery.elementAt(c_lottery.getSelectedIndex()));        
        Dbcon db=new Dbcon();
        db.connect();
        try {
            l_ldate.setText(""+date.elementAt(c_lottery.getSelectedIndex()));
            totalavailable=0;
            db.rslt=db.stmt.executeQuery("select recurring,fine,Debit,credit,interest from sheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                totalavailable+=Long.parseLong(db.rslt.getString(1));
                totalavailable+=Long.parseLong(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    totalavailable-=Long.parseLong(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    totalavailable+=Long.parseLong(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                    totalavailable+=Long.parseLong(db.rslt.getString(5));
            }
            db.rslt=db.stmt.executeQuery("select Debit,credit from investsheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                    totalavailable-=Long.parseLong(db.rslt.getString(1));
                if(db.rslt.getString(2)!=null)
                    totalavailable+=Long.parseLong(db.rslt.getString(2));
            }
            long brcr=0,brdr=0;
            db.rslt=db.stmt.executeQuery("select sum(Debit),sum(credit) from borrowersheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                {
                    totalavailable-=Long.parseLong(db.rslt.getString(1));
                    brdr=Long.parseLong(db.rslt.getString(1));
                }
                if (db.rslt.getString(2) != null)
                {
                    totalavailable += Long.parseLong(db.rslt.getString(2));
                    brcr=Long.parseLong(db.rslt.getString(2));                }
            }
            db.rslt=db.stmt.executeQuery("select sum(amount) from expenditure where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                {
                    totalavailable-=Long.parseLong(db.rslt.getString(1));
                }
            }
            tf_available.setText(""+totalavailable);

        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
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
        jPanel1 = new javax.swing.JPanel();
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            db.rslt=db.stmt.executeQuery("select lottery_no,recurring,nonrecurring,date,month from lottery");
            while(db.rslt.next())
            {
                lottery.addElement(db.rslt.getString(1));
                recurring.addElement(db.rslt.getString(2));
                nonrecurring.addElement(db.rslt.getString(3));
                date.addElement(db.rslt.getString(4));
                lmonth.addElement(db.rslt.getString(5));
                //l_ldate.setText();
            }

        }
        catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        c_lottery = new javax.swing.JComboBox();
        c_month = new javax.swing.JComboBox();
        c_year = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        tf_available = new javax.swing.JLabel();
        l_ldate = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Final Member Report");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        c_lottery.setModel(new javax.swing.DefaultComboBoxModel(lottery.toArray()));
        c_lottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_lotteryItemStateChanged(evt);
            }
        });

        c_month.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        c_year.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Year", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025" }));

        jButton1.setText("Ok");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tf_available.setText(" Balance :");

        l_ldate.setText("00-00-0000");

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_month, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_year, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tf_available, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(l_ldate)
                    .addComponent(c_lottery, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(c_month, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_year, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(tf_available))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Member Paid")));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 967, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_lotteryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_lotteryItemStateChanged
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        try {
            l_ldate.setText(""+date.elementAt(c_lottery.getSelectedIndex()));
            totalavailable=0;
            db.rslt=db.stmt.executeQuery("select recurring,fine,Debit,credit,interest from sheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                totalavailable+=Long.parseLong(db.rslt.getString(1));
                totalavailable+=Long.parseLong(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    totalavailable-=Long.parseLong(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    totalavailable+=Long.parseLong(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                    totalavailable+=Long.parseLong(db.rslt.getString(5));
            }
            db.rslt=db.stmt.executeQuery("select Debit,credit from investsheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                    totalavailable-=Long.parseLong(db.rslt.getString(1));
                if(db.rslt.getString(2)!=null)
                    totalavailable+=Long.parseLong(db.rslt.getString(2));
            }
            long brcr=0,brdr=0;
            db.rslt=db.stmt.executeQuery("select sum(Debit),sum(credit) from borrowersheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                {
                    totalavailable-=Long.parseLong(db.rslt.getString(1));
                    brdr=Long.parseLong(db.rslt.getString(1));
                }
                if (db.rslt.getString(2) != null)
                {
                    totalavailable += Long.parseLong(db.rslt.getString(2));
                    brcr=Long.parseLong(db.rslt.getString(2));                }
            }
            db.rslt=db.stmt.executeQuery("select sum(amount) from expenditure where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                {
                    totalavailable-=Long.parseLong(db.rslt.getString(1));
                }
            }
            tf_available.setText(""+totalavailable);
            
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
}//GEN-LAST:event_c_lotteryItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(c_month.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(mainframe,"Select Month","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(c_year.getSelectedIndex()==0)
        {
            JOptionPane.showMessageDialog(mainframe,"Select Year","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int month=0,year=0,day1=1,day2=0;
        month=c_month.getSelectedIndex();
        year=Integer.valueOf(c_year.getSelectedItem().toString());
        Calendar cal=Calendar.getInstance();
        cal.set(year,month, day1);
        day2=cal.getMaximum(cal.DAY_OF_MONTH);
        System.out.println(day2+"   "+cal.getTime().toLocaleString());
        String date1=""+year+"-"+month+"-"+day1,date2=""+year+"-"+month+"-"+day2;
        Dbcon db=new Dbcon();
        Vector memberid=new Vector();
        Vector member=new Vector();
        Vector person=new Vector();
        Vector name=new Vector();
        Vector fname=new Vector();
        Vector address=new Vector();
        Vector contact=new Vector();
        Vector deposite=new Vector();
        Vector balance=new Vector();
        Vector distribut=new Vector();
        Vector netbalance=new Vector();
        Vector tmonth=new Vector();
        db.connect();
        int totalperson=0;
        try {
            db.rslt=db.stmt.executeQuery("select member_id,member_no,person from memberaccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                memberid.addElement(db.rslt.getString(1));
                member.addElement(""+lottery.elementAt(c_lottery.getSelectedIndex())+"-"+db.rslt.getString(2));
                person.addElement(db.rslt.getString(3));
                totalperson+=Integer.parseInt(db.rslt.getString(3));
            }
            System.out.println();
            for(int i=0;i<memberid.size();i++)
            {
                
                db.rslt=db.stmt.executeQuery("select name,f_name,address,contact_no from memberinfo where  member_id="+memberid.elementAt(i));
                while(db.rslt.next())
                {
                    name.addElement(db.rslt.getString(1));
                    fname.addElement(db.rslt.getString(2));
                    address.addElement(db.rslt.getString(3));
                    contact.addElement(db.rslt.getString(4));
                }
            }

            for(int i=0;i<memberid.size();i++)
            {
                String stotal=null,sbalance=null;
                int tmonthadd=0;
                db.rslt=db.stmt.executeQuery("select total,balance from sheet where date>='"+date1+"' and date<='"+date2+"' and member_id="+memberid.elementAt(i));
                while(db.rslt.next())
                {
                    if(db.rslt.getString(1)!=null)
                        stotal=db.rslt.getString(1);
                    if(db.rslt.getString(2)!=null)
                        sbalance=db.rslt.getString(2);
                }
                System.out.println("=>>"+stotal+"   "+sbalance);
                deposite.addElement(stotal);
                balance.addElement(sbalance);
            }
            tmonth.clear();
            for(int i=0;i<memberid.size();i++)
            {
                int tmonthadd=0;
                db.rslt=db.stmt.executeQuery("select month from sheet where  date<='"+date2+"' and member_id="+memberid.elementAt(i));
                while(db.rslt.next())
                {
                     if(db.rslt.getString(1)!=null)
                        tmonthadd+=Integer.parseInt(db.rslt.getString(1));
                }
                tmonth.addElement(tmonthadd+"");
            }
            int perperson=(Math.round(1.0f*totalavailable/totalperson));
            System.out.println("per="+perperson+" total="+totalavailable+" total person"+totalperson);
            for(int i=0;i<memberid.size();i++)
            {
                //int d=Integer.parseInt(lmonth.elementAt(c_lottery.getSelectedIndex())+"")*Integer.parseInt(recurring.elementAt(c_lottery.getSelectedIndex())+"")*Integer.parseInt(person.elementAt(i)+"");
                int d=Integer.parseInt(person.elementAt(i)+"")*perperson;
                int n=Integer.parseInt(""+deposite.elementAt(i));
                distribut.addElement((d)+"");
                netbalance.addElement((d-n)+"");
            }
            Object data[][]=new Object[memberid.size()][12];
            Object header[]={"S.No.","MemberId","No.Of Person","Name","Father's Name","Address","Contact No","Total Month","Total Deposite","Balance","Distribut","Net Balance"};
            for(int i=0;i<memberid.size();i++)
            {
                data[i][0]=(i+1)+"";
                data[i][1]=member.elementAt(i);
                data[i][2]=person.elementAt(i);
                data[i][3]=name.elementAt(i);
                data[i][4]=fname.elementAt(i);
                data[i][5]=address.elementAt(i);
                data[i][6]=contact.elementAt(i);
                data[i][7]=tmonth.elementAt(i);
                data[i][8]=deposite.elementAt(i);
                data[i][9]=balance.elementAt(i);
                data[i][10]=distribut.elementAt(i);
                data[i][11]=netbalance.elementAt(i);
            }

            table.setModel(new DefaultTableModel(data,header));
            for(int i=0;i<deposite.size();i++)
            {
               // if(deposite.elementAt(i)==null)
                {
                    System.out.println("i=="+i);
                    table.getColumn("Net Balance").setCellRenderer(new ButtonRenderer15());
                }
                
            }
            table.setEnabled(false);
            table.setRowSelectionAllowed(true);
            
            table.setRowHeight(25);
            table.updateUI();

            /*jPanel2.setLayout(new BorderLayout());
            jPanel2.add("Center",new JScrollPane(table));
            JPanel pend =new JPanel();
            JLabel l1=new JLabel("select member :");
            pend.add(l1);
            jPanel2.add(pend,BorderLayout.PAGE_END);
            jPanel2.updateUI();
            */
            
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        Vector d=new Vector();
        // String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
         String header[]={"S.No.","MemberId","No.Of Person","Name","Father's Name","Address","Contact No","Total Month","Total Deposite","Balance","Distribut","Net Balance"};
        //String header[]={"S.No.","MemberId","No.Of Person","Name","Father's Name","Recuuring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
        String s2[][]=new String[table.getRowCount()][12];
        for (int i=0;i<table.getRowCount();i++) {
            s2[i][0]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(0));
            s2[i][1]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(1));
            s2[i][2]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(2));
            s2[i][3]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(3));
            s2[i][4]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(4));
            s2[i][5]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(5));
            s2[i][6]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(6));
            s2[i][7]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(7));
            s2[i][8]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(8));
            s2[i][9]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(9));
            s2[i][10]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(10));
            s2[i][11]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(11));

        }
        // String s1[]=null;//(Sttable.getTableHeader().getComponents();
        table t=new table(c_lottery.getSelectedItem()+"  Month of   "+c_month.getSelectedItem()+"  "+c_year.getSelectedItem(),header,s2);
        //t.printGradesTable();
        t.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JComboBox c_month;
    private javax.swing.JComboBox c_year;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel l_ldate;
    private javax.swing.JTable table;
    private javax.swing.JLabel tf_available;
    // End of variables declaration//GEN-END:variables

}
class ButtonRenderer15  extends DefaultTableCellRenderer//implements TableCellRenderer
{///
    int x=0,y=0;
    Object value;
    public ButtonRenderer15()
    {
            y=getHeight();
            x=getWidth();
            System.out.println("cont...");
            this.setOpaque(true);
            this.setBackground(Color.red);
    }
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
            this.value=value;
            System.out.println("getcellrender..");
            //setBackground(Color.green);
            return this;
    }
    public void paint(Graphics g)
    {
        System.out.println(Integer.parseInt(""+value));
        g.setFont(new Font("arial",3,15));
        if(value==null)
        {
            g.setColor(Color.red);
            g.fillRect(0, 0,400,25);
        }
        else if(Integer.parseInt(""+value)>0)
        {
            g.setColor(Color.green);
            g.fillRect(0, 0,400,25);
            g.setColor(Color.blue);
            g.drawString(""+value,5,15);
        }
        else if(Integer.parseInt(""+value)<0)
        {
            g.setColor(Color.yellow);
            g.fillRect(0, 0,400,25);
            g.setColor(Color.blue);
            g.drawString(""+value,5,15);
        }
        else
        {
            g.setColor(Color.black);
            g.drawString(""+value,5,15);
        }
    }
    
}