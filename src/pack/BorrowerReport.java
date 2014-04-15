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

public class BorrowerReport extends javax.swing.JInternalFrame {

    /** Creates new form MonthlyReport */
    JFrame mainframe;
    Vector date,investor_id,lmonth,debit,credit,interest,balance,name,fname,investor;
    long totalavailable=0;
    public BorrowerReport(JFrame mainframe) {
        super("Borrower Report",true,true,true,true);
        this.mainframe=mainframe;
        date=new Vector();
        investor_id=new Vector();
        lmonth =new Vector();
        debit =new Vector();
        credit =new Vector();
        interest =new Vector();
        balance =new Vector();
        name=new Vector();
        fname=new Vector();
        investor=new Vector();
        initComponents();
        l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex()))
);
        Dbcon db=new Dbcon();
        db.connect();
        try {
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
            name.clear();
            fname.clear();
            investor.clear();
            investor_id.clear();
            System.out.println("1");
            db.rslt=db.stmt.executeQuery("select ac.br_id,ac.br_no,ac.name,ac.name from borrower ac where  ac.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' && ac.status=1");
            while(db.rslt.next())
            {
                investor_id.addElement(db.rslt.getString(1));
                investor.addElement(lottery.elementAt(c_lottery.getSelectedIndex())+"-"+db.rslt.getString(2));
                name.addElement(db.rslt.getString(3));
                fname.addElement(db.rslt.getString(4));
            }
            debit.clear();
            credit.clear();
            balance.clear();
            interest.clear();
            for(int i=0;i<investor_id.size();i++)
            {
                System.out.println("2");
                db.rslt=db.stmt.executeQuery("select debit,credit,interest,balance from borrowersheet where br_id="+investor_id.elementAt(i));
                int idebit=0,icredit=0,iinterest=0,ibalance=0;
                while(db.rslt.next())
                {
                    idebit+=Integer.parseInt(db.rslt.getString(1));
                    icredit+=Integer.parseInt(db.rslt.getString(2));
                    iinterest+=Integer.parseInt(db.rslt.getString(3));
                    ibalance=Integer.parseInt(db.rslt.getString(4));
                }
                debit.addElement(idebit+"");
                credit.addElement(icredit+"");
                balance.addElement(ibalance+"");
                interest.addElement(iinterest+"");
            }
            Object data[][]=new Object[investor_id.size()+1][7];
            Object header[]={"S.No.","Borrower Id","Name","Total Debit","Total Credit","Total Interest","Current Balance"};
            long _ti=0,_tdr=0,_tcr=0,_tb=0;
            for(int i=0;i<investor_id.size();i++)
            {
                data[i][0]=(i+1)+"";
                data[i][1]=investor.elementAt(i);
                data[i][2]=name.elementAt(i);
                data[i][3]=debit.elementAt(i);
                data[i][4]=credit.elementAt(i);
                data[i][5]=interest.elementAt(i);
                data[i][6]=balance.elementAt(i);

                _tdr+=Long.parseLong(debit.elementAt(i)+"");
                _tcr+=Long.parseLong(credit.elementAt(i)+"");
                _ti+=Long.parseLong(interest.elementAt(i)+"");
                _tb+=Long.parseLong(balance.elementAt(i)+"");
               
            }
            data[investor_id.size()][0]="Total";
            data[investor_id.size()][3]=""+_tdr;
            data[investor_id.size()][4]=""+_tcr;
            data[investor_id.size()][5]=""+_ti;
            data[investor_id.size()][6]=""+_tb;
            table.setModel(new DefaultTableModel(data,header));
            for(int i=0;i<investor.size();i++)
            {
               // if(deposite.elementAt(i)==null)
                {
                    System.out.println("i=="+i);
                    table.getColumn("Current Balance").setCellRenderer(new ButtonRenderer11());
                    //table.getColumn("S.No.").setCellRenderer(new ButtonRenderer1());
                }

            }
            table.setEnabled(false);
            table.setRowHeight(25);
            table.updateUI();
            
            
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
                date.addElement(db.rslt.getString(4));
                lmonth.addElement(db.rslt.getString(5));
                //l_ldate.setText();
            }

        }
        catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        c_lottery = new javax.swing.JComboBox();
        tf_available = new javax.swing.JLabel();
        l_ldate = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Borrower Report");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        c_lottery.setModel(new javax.swing.DefaultComboBoxModel(lottery.toArray()));
        c_lottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_lotteryItemStateChanged(evt);
            }
        });

        tf_available.setText("Available Balance :");

        l_ldate.setText("00-00-0000");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                .addContainerGap()
                .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tf_available, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(c_lottery, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(l_ldate))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3)
                            .addComponent(tf_available))))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
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
            l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex()))
);
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
           name.clear();
            fname.clear();
            investor.clear();
            investor_id.clear();
            System.out.println("1");
            db.rslt=db.stmt.executeQuery("select ac.br_id,ac.br_no,ac.name,ac.name from borrower ac where  ac.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' && ac.status=1");
            while(db.rslt.next())
            {
                investor_id.addElement(db.rslt.getString(1));
                investor.addElement(lottery.elementAt(c_lottery.getSelectedIndex())+"-"+db.rslt.getString(2));
                name.addElement(db.rslt.getString(3));
                fname.addElement(db.rslt.getString(4));
            }
            debit.clear();
            credit.clear();
            balance.clear();
            interest.clear();
            for(int i=0;i<investor_id.size();i++)
            {
                System.out.println("2");
                db.rslt=db.stmt.executeQuery("select debit,credit,interest,balance from borrowersheet where br_id="+investor_id.elementAt(i));
                int idebit=0,icredit=0,iinterest=0,ibalance=0;
                while(db.rslt.next())
                {
                    idebit+=Integer.parseInt(db.rslt.getString(1));
                    icredit+=Integer.parseInt(db.rslt.getString(2));
                    iinterest+=Integer.parseInt(db.rslt.getString(3));
                    ibalance=Integer.parseInt(db.rslt.getString(4));
                }
                debit.addElement(idebit+"");
                credit.addElement(icredit+"");
                balance.addElement(ibalance+"");
                interest.addElement(iinterest+"");
            }
            Object data[][]=new Object[investor_id.size()+1][7];
            Object header[]={"S.No.","Borrower Id","Name","Total Debit","Total Credit","Total Interest","Current Balance"};
            long _ti=0,_tdr=0,_tcr=0,_tb=0;
            for(int i=0;i<investor_id.size();i++)
            {
                data[i][0]=(i+1)+"";
                data[i][1]=investor.elementAt(i);
                data[i][2]=name.elementAt(i);
                data[i][3]=debit.elementAt(i);
                data[i][4]=credit.elementAt(i);
                data[i][5]=interest.elementAt(i);
                data[i][6]=balance.elementAt(i);

                _tdr+=Long.parseLong(debit.elementAt(i)+"");
                _tcr+=Long.parseLong(credit.elementAt(i)+"");
                _ti+=Long.parseLong(interest.elementAt(i)+"");
                _tb+=Long.parseLong(balance.elementAt(i)+"");

            }
            data[investor_id.size()][0]="Total";
            data[investor_id.size()][3]=""+_tdr;
            data[investor_id.size()][4]=""+_tcr;
            data[investor_id.size()][5]=""+_ti;
            data[investor_id.size()][6]=""+_tb;
            table.setModel(new DefaultTableModel(data,header));
            for(int i=0;i<investor.size();i++)
            {
               // if(deposite.elementAt(i)==null)
                {
                    System.out.println("i=="+i);
                    table.getColumn("Current Balance").setCellRenderer(new ButtonRenderer11());
                    //table.getColumn("S.No.").setCellRenderer(new ButtonRenderer1());
                }

            }
            table.setEnabled(false);
            table.setRowHeight(25);
            table.updateUI();
            /*table.setModel(new DefaultTableModel(data,header));
            for(int i=0;i<investor.size();i++)
            {
               // if(deposite.elementAt(i)==null)
                {
                    System.out.println("i=="+i);
                   // table.getColumn("Current Balance").setCellRenderer(new ButtonRenderer1());
                    //table.getColumn("S.No.").setCellRenderer(new ButtonRenderer1());
                    
                }

            }
            table.setEnabled(false);
            table.setRowHeight(25);
            table.updateUI();
             * */
             

        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
}//GEN-LAST:event_c_lotteryItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model=(DefaultTableModel)table.getModel();
        Vector d=new Vector();
        // String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
        String header[]={"S.No.","Borrower Id","Name","Total Debit","Total Credit","Total Interest","Current Balance"};
        String s2[][]=new String[table.getRowCount()][8];
        for (int i=0;i<table.getRowCount();i++) {
            s2[i][0]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(0));
            s2[i][1]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(1));
            s2[i][2]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(2));
            s2[i][3]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(3));
            s2[i][4]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(4));
            s2[i][5]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(5));
            s2[i][6]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(6));
          //  s2[i][7]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(7));


        }
        // String s1[]=null;//(Sttable.getTableHeader().getComponents();
        table t=new table(c_lottery.getSelectedItem()+"",header,s2);
        //t.printGradesTable();
        t.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JButton jButton1;
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

class ButtonRenderer11 extends JComponent implements TableCellRenderer
{
    int x=0,y=0;
    Object value;
    public ButtonRenderer11()
    {
            y=getHeight();
            x=getWidth();
            System.out.println("cont...");
            this.setOpaque(true);
           
    }
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column)
    {
            this.value=value;
            System.out.println("getcellrender..");
            return this;
    }
    public void paint(Graphics g)
    {
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
        else
        {
            g.setColor(Color.black);
            g.drawString(""+value,5,15);
        }

    }

}//
