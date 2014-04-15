package pack;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MemberAccount.java
 *
 * Created on Oct 4, 2012, 3:28:51 PM
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
public class InvestorAccount extends javax.swing.JInternalFrame {

    /** Creates new form MemberAccount */
    JFrame mainframe;
    Vector recurring,nonrecurring,date,member_id,person,_memberid,_membername;
    int tmonth=0,trecurring,tfine,tdebit,tcredit,tinterest,ttotal,tbalance;
    long totalavailable=0;
    int arr[]=null;
    String lastdate="";
    ButtonGroup pbgp=new ButtonGroup();
    public InvestorAccount(JFrame mainframe) {
        super("Investor Account",true,true,true,true);
        this.mainframe=mainframe;
        recurring=new Vector();
        nonrecurring=new Vector();
        date=new Vector();
        member_id=new Vector();
        person=new Vector();
        _membername=new Vector();
        _memberid=new Vector();

        
        initComponents();
        pbgp.add(c_last);
        pbgp.add(c_complete);

        l_cdate.setText(""+(new Date().toLocaleString()));
        l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex()))
);
        Dbcon db=new Dbcon();
        db.connect();
        try {

            db.rslt=db.stmt.executeQuery("select investor_id,investor_no from investoraccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            db.rslt.afterLast();
            int count=0;
            if (db.rslt.previous())count=db.rslt.getRow();
            String s1[]=new String[count];
            db.rslt.beforeFirst();
            count=0;
            member_id.clear();
            while(db.rslt.next()) {
                member_id.addElement(db.rslt.getString(1));
                s1[count]=db.rslt.getString(2);
                count++;
            }
            c_member.setModel(new javax.swing.DefaultComboBoxModel(s1));
            _membername.clear();
            _memberid.clear();
            db.rslt=db.stmt.executeQuery("select investoraccount.investor_id,investorinfo.name,investoraccount.investor_no from investorinfo,investoraccount where investoraccount.investor_id=investorinfo.investor_id && investoraccount.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                _memberid.addElement(db.rslt.getString(1));
                _membername.addElement(db.rslt.getString(2)+"   ID: "+db.rslt.getString(3));
            }
            c_membername.setModel(new javax.swing.DefaultComboBoxModel(_membername.toArray()));
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from investorinfo where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_contact.setText(db.rslt.getString(3));
               ta_address.setText(db.rslt.getString(4));
            }

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

            count=0;
            String data[][]=null;
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            db.rslt=db.stmt.executeQuery("select date,debit,credit,interest,balance from investsheet where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][6];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);

                lastdate=db.rslt.getString(1);
                if(db.rslt.getString(2)!=null)
                    tdebit+=Integer.valueOf(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    tcredit+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    tinterest+=Integer.valueOf(db.rslt.getString(4));
                tbalance=Integer.valueOf(db.rslt.getString(5));
                count++;
            }
            data[count][0]="Total";
            data[count][2]=""+tdebit;
            data[count][3]=""+tcredit;
            data[count][4]=""+tinterest;
            
            
            l_debit.setText("Total Debit : "+tdebit);
            l_investment.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_balance.setText("Current Balance : "+tbalance);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();

        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        ButtonGroup bg=new ButtonGroup();
        bg.add(r_credit);
        bg.add(r_debit);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            db.rslt=db.stmt.executeQuery("select lottery_no,recurring,nonrecurring,date from lottery where status=1");
            while(db.rslt.next())
            {
                lottery.addElement(db.rslt.getString(1));
                recurring.addElement(db.rslt.getString(2));
                nonrecurring.addElement(db.rslt.getString(3));
                date.addElement(db.rslt.getString(4));
                //l_ldate.setText();
            }

        }
        catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        c_lottery = new javax.swing.JComboBox();
        c_member = new javax.swing.JComboBox();
        l_ldate = new javax.swing.JLabel();
        l_cdate = new javax.swing.JLabel();
        c_membername = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        tf_fname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_contact = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_address = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tf_credit = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tf_debit = new javax.swing.JTextField();
        tf_date = new javax.swing.JTextField();
        bt_date = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        bt_close = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        tf_available = new javax.swing.JTextField();
        r_credit = new javax.swing.JRadioButton();
        r_debit = new javax.swing.JRadioButton();
        tf_interest = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tf_max = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        c_last = new javax.swing.JRadioButton();
        c_complete = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        l_investment = new javax.swing.JLabel();
        l_debit = new javax.swing.JLabel();
        l_interest = new javax.swing.JLabel();
        l_balance = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        c_lottery.setModel(new javax.swing.DefaultComboBoxModel(lottery.toArray()));
        c_lottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_lotteryItemStateChanged(evt);
            }
        });

        c_member.setEditable(true);
        c_member.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_member.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_memberItemStateChanged(evt);
            }
        });
        c_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_memberActionPerformed(evt);
            }
        });

        c_membername.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_membername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_membernameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(c_member, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(c_membername, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(l_cdate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_cdate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(c_member, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(c_lottery, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(c_membername, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Investor Detail")));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel2.setText(" Name :");

        tf_name.setEditable(false);
        tf_name.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nameActionPerformed(evt);
            }
        });

        tf_fname.setEditable(false);
        tf_fname.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_fnameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel3.setText("F.Name :");

        tf_contact.setEditable(false);
        tf_contact.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_contactActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel5.setText("Contact:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel6.setText("Address :");

        ta_address.setBackground(new java.awt.Color(240, 240, 240));
        ta_address.setColumns(20);
        ta_address.setEditable(false);
        ta_address.setRows(5);
        jScrollPane1.setViewportView(ta_address);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tf_contact, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tf_fname)
                        .addComponent(tf_name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Current Payment Detail"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Credit Amount :");

        tf_credit.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_credit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_creditActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Debit Amount :");

        tf_debit.setEditable(false);
        tf_debit.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_debit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_debitActionPerformed(evt);
            }
        });

        tf_date.setEditable(false);
        tf_date.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_date.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tf_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_dateActionPerformed(evt);
            }
        });

        bt_date.setText("...");
        bt_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_dateActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel12.setText("Select  Date :");

        bt_close.setText("Close");
        bt_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_closeActionPerformed(evt);
            }
        });

        bt_save.setText("Save");
        bt_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_saveActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Available:");

        tf_available.setEditable(false);
        tf_available.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_available.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_availableActionPerformed(evt);
            }
        });

        r_credit.setSelected(true);
        r_credit.setText("Credit");
        r_credit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                r_creditItemStateChanged(evt);
            }
        });
        r_credit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r_creditActionPerformed(evt);
            }
        });

        r_debit.setText("Debit");
        r_debit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                r_debitItemStateChanged(evt);
            }
        });

        tf_interest.setEditable(false);
        tf_interest.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_interest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_interestActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Interest :");

        tf_max.setEditable(false);
        tf_max.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_max.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_maxActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Max. Amount :");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Print :");

        c_last.setText("Last");

        c_complete.setText("Complete");

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_available, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(184, 184, 184)
                                .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_interest)
                                    .addComponent(tf_credit, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_debit)
                                    .addComponent(tf_max, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                                .addGap(117, 117, 117))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(r_credit)
                        .addGap(100, 100, 100)
                        .addComponent(r_debit)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(c_last)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(c_complete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(163, 163, 163))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_available, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(r_credit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(r_debit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_debit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_credit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_interest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_max, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jButton1)
                    .addComponent(c_last)
                    .addComponent(c_complete))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Past Payment Detail"));

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
        jScrollPane2.setViewportView(table);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Report"));

        l_investment.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_investment.setText("Investment :");

        l_debit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_debit.setText("Debit :");

        l_interest.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_interest.setText("Interest :");

        l_balance.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_balance.setText("Balance");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_investment, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(l_debit, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(l_interest, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(l_balance, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(l_investment, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(l_debit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(l_interest, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(l_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, 0, 1038, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                    .addComponent(jPanel5, 0, 281, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_lotteryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_lotteryItemStateChanged
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        try {

            db.rslt=db.stmt.executeQuery("select investor_id,investor_no from investoraccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            db.rslt.afterLast();
            int count=0;
            if (db.rslt.previous())count=db.rslt.getRow();
            String s1[]=new String[count];
            db.rslt.beforeFirst();
            count=0;
            member_id.clear();
            while(db.rslt.next()) {
                member_id.addElement(db.rslt.getString(1));
                s1[count]=db.rslt.getString(2);
                count++;
            }
            c_member.setModel(new javax.swing.DefaultComboBoxModel(s1));
            _membername.clear();
            _memberid.clear();
            db.rslt=db.stmt.executeQuery("select investoraccount.investor_id,investorinfo.name,investoraccount.investor_no from investorinfo,investoraccount where investoraccount.investor_id=investorinfo.investor_id && investoraccount.lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            while(db.rslt.next())
            {
                _memberid.addElement(db.rslt.getString(1));
                _membername.addElement(db.rslt.getString(2)+"   ID: "+db.rslt.getString(3));
            }
            c_membername.setModel(new javax.swing.DefaultComboBoxModel(_membername.toArray()));
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from investorinfo where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_contact.setText(db.rslt.getString(3));
               ta_address.setText(db.rslt.getString(4));
            }

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

            count=0;
            String data[][]=null;
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            db.rslt=db.stmt.executeQuery("select date,debit,credit,interest,balance from investsheet where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][6];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);

                lastdate=db.rslt.getString(1);
                if(db.rslt.getString(2)!=null)
                    tdebit+=Integer.valueOf(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    tcredit+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    tinterest+=Integer.valueOf(db.rslt.getString(4));
                tbalance=Integer.valueOf(db.rslt.getString(5));
                count++;
            }
             data[count][0]="Total";
            data[count][2]=""+tdebit;
            data[count][3]=""+tcredit;
            data[count][4]=""+tinterest;
           
            l_debit.setText("Total Debit : "+tdebit);
            l_investment.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_balance.setText("Current Balance : "+tbalance);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();

        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();

        tf_credit.setText("");
        tf_date.setText("");
        tf_debit.setText("0");

        
}//GEN-LAST:event_c_lotteryItemStateChanged

    private void tf_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tf_nameActionPerformed

    private void tf_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_fnameActionPerformed

    private void tf_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_contactActionPerformed

    private void tf_creditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_creditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_creditActionPerformed

    private void tf_debitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_debitActionPerformed
        // TODO add your handling code here:
        if(tf_date.getText().equals(""))
        {
            JOptionPane.showMessageDialog(mainframe,"Select Date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int currentbalance=tbalance;
        int interest=0;
        int debit=0;
        int balance=0;
        int credit=0;
        int oldinterest=0,oldcredit=0,olddebit=0;
        try
        {
            if(r_credit.isSelected())
                credit=Integer.valueOf(tf_credit.getText());
            if(r_debit.isSelected())
                debit=Integer.valueOf(tf_debit.getText());
            DefaultTableModel modeld=null;
             for (int i=0;i<table.getRowCount();i++) {
                    modeld=(DefaultTableModel)table.getModel();
                    String dr=(String)((Vector)modeld.getDataVector().elementAt(i)).elementAt(2);
                    String cr=(String)((Vector)modeld.getDataVector().elementAt(i)).elementAt(3);
                    String in=(String)((Vector)modeld.getDataVector().elementAt(i)).elementAt(4);
                    olddebit+=Integer.parseInt(dr);
                    oldcredit+=Integer.parseInt(cr);
                    oldinterest+=Integer.parseInt(in);
             }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Enter Amount In Numeric !","Error",JOptionPane.ERROR_MESSAGE);
            return;

        }
        interest=(int)Result.interest(lastdate,DateConverter.toYYYYMMDD(tf_date.getText())
,currentbalance);
        if(interest<0)
        {
            JOptionPane.showMessageDialog(mainframe,"Error In Date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tf_interest.setText(interest+(oldcredit+oldinterest-olddebit-currentbalance)+"");
        tf_max.setText((tbalance+interest+(oldcredit+oldinterest-olddebit-currentbalance))+"");
    }//GEN-LAST:event_tf_debitActionPerformed

    private void tf_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dateActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tf_dateActionPerformed

    private void bt_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_dateActionPerformed
        // TODO add your handling code here:
        tf_date.setText(DateConverter.toDDMMYYYY(new DatePicker(mainframe).setPickedDate()));

        if(tf_date.getText().equals("")) {
            JOptionPane.showMessageDialog(mainframe,"Select date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }        
    }//GEN-LAST:event_bt_dateActionPerformed

    private void bt_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_closeActionPerformed
        // TODO add your handling code here:
        dispose();
}//GEN-LAST:event_bt_closeActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
        if(tf_date.getText().equals(""))
        {
            JOptionPane.showMessageDialog(mainframe,"Select Date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int currentbalance=tbalance;
        int interest=0;
        int debit=0;
        int balance=0;
        int credit=0;
        int oldinterest=0,oldcredit=0,olddebit=0;
        try
        {

            if(r_credit.isSelected())
                credit=Integer.valueOf(tf_credit.getText());
            if(r_debit.isSelected())
                debit=Integer.valueOf(tf_debit.getText());
             DefaultTableModel modeld=null;
             for (int i=0;i<table.getRowCount();i++) {
                    modeld=(DefaultTableModel)table.getModel();
                    String dr=(String)((Vector)modeld.getDataVector().elementAt(i)).elementAt(2);
                    String cr=(String)((Vector)modeld.getDataVector().elementAt(i)).elementAt(3);
                    String in=(String)((Vector)modeld.getDataVector().elementAt(i)).elementAt(4);
                    olddebit+=Integer.parseInt(dr);
                    oldcredit+=Integer.parseInt(cr);
                    oldinterest+=Integer.parseInt(in);
             }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Enter Amount In Numeric !","Error",JOptionPane.ERROR_MESSAGE);
            return;

        }
        interest=(int)Result.interest(lastdate,DateConverter.toYYYYMMDD(tf_date.getText())
,currentbalance);
        if(interest<0)
        {
            JOptionPane.showMessageDialog(mainframe,"Error In Date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tf_interest.setText((oldcredit+oldinterest-olddebit-currentbalance)+interest+"");
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            if(r_credit.isSelected())
            {

                db.stmt.executeUpdate("insert into investsheet(lottery_no,investor_id,date,debit,credit,interest,balance) "+
                 "values('"+lottery.elementAt(c_lottery.getSelectedIndex())+"',"+member_id.elementAt(c_member.getSelectedIndex())+",'"+DateConverter.toYYYYMMDD(tf_date.getText())
+"',0,"+credit+","+interest+","+(currentbalance+credit)+")");
               //         "values('"+lottery.elementAt(c_lottery.getSelectedIndex())+"',"+member_id.elementAt(c_member.getSelectedIndex())+",'"+tf_date.getText()+"',0,"+credit+","+interest+","+(currentbalance+interest+credit)+")");
                //  JOptionPane.showMessageDialog(mainframe,"Deposit Amount < Recurring+Fine+Interest ","Error",JOptionPane.ERROR_MESSAGE);
                //return;
            }
            if(r_debit.isSelected())
            {
                if(debit>totalavailable)
                {
                    JOptionPane.showMessageDialog(mainframe,"Debit Amount > Available Balance ","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(debit>currentbalance+interest+oldinterest)
                {
                    JOptionPane.showMessageDialog(mainframe,"Investor's Can Not Take Much Amount Than Invest Amount+Interest ","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                db.stmt.executeUpdate("insert into investsheet(lottery_no,investor_id,date,debit,credit,interest,balance) "+

                           "values('"+lottery.elementAt(c_lottery.getSelectedIndex())+"',"+member_id.elementAt(c_member.getSelectedIndex())+",'"+DateConverter.toYYYYMMDD(tf_date.getText())
+"',"+debit+",0,"+interest+","+(currentbalance+(oldcredit+oldinterest-olddebit-currentbalance)+interest-debit)+")");
            }

            tf_credit.setText("");
            tf_date.setText("");
            tf_debit.setText("");
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

            int count=0;
            String data[][]=null;
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            db.rslt=db.stmt.executeQuery("select date,debit,credit,interest,balance from investsheet where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][6];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);

                lastdate=db.rslt.getString(1);
                if(db.rslt.getString(2)!=null)
                    tdebit+=Integer.valueOf(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    tcredit+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    tinterest+=Integer.valueOf(db.rslt.getString(4));
                tbalance=Integer.valueOf(db.rslt.getString(5));
                count++;
            }
             data[count][0]="Total";
            data[count][2]=""+tdebit;
            data[count][3]=""+tcredit;
            data[count][4]=""+tinterest;
            
            l_debit.setText("Total Debit : "+tdebit);
            l_investment.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_balance.setText("Current Balance : "+tbalance);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            db.conn.commit();
         }
        catch(Exception e)
        {
            System.out.println(e);
        }
        db.disconnect();
         
    }//GEN-LAST:event_bt_saveActionPerformed

    private void c_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_memberActionPerformed
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        try {
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from investorinfo where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_contact.setText(db.rslt.getString(3));
               ta_address.setText(db.rslt.getString(4));
            }

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

            int count=0;
            String data[][]=null;
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            db.rslt=db.stmt.executeQuery("select date,debit,credit,interest,balance from investsheet where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][6];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);

                lastdate=db.rslt.getString(1);
                if(db.rslt.getString(2)!=null)
                    tdebit+=Integer.valueOf(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    tcredit+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    tinterest+=Integer.valueOf(db.rslt.getString(4));
                tbalance=Integer.valueOf(db.rslt.getString(5));
                count++;
            }
             data[count][0]="Total";
            data[count][2]=""+tdebit;
            data[count][3]=""+tcredit;
            data[count][4]=""+tinterest;
         
            l_debit.setText("Total Debit : "+tdebit);
            l_investment.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_balance.setText("Current Balance : "+tbalance);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            tf_credit.setText("");
            tf_date.setText("");
            c_membername.setSelectedIndex(c_member.getSelectedIndex());
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();

    }//GEN-LAST:event_c_memberActionPerformed

    private void tf_availableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_availableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_availableActionPerformed

    private void r_creditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r_creditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r_creditActionPerformed

    private void r_creditItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_r_creditItemStateChanged
        // TODO add your handling code here:
        if(r_credit.isSelected())
        {
            tf_credit.setEditable(true);
            tf_debit.setEditable(false);
        }
    }//GEN-LAST:event_r_creditItemStateChanged

    private void r_debitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_r_debitItemStateChanged
        // TODO add your handling code here:
        if(r_debit.isSelected())
        {
            tf_debit.setEditable(true);
            tf_credit.setEditable(false);
        }
    }//GEN-LAST:event_r_debitItemStateChanged

    private void tf_interestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_interestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_interestActionPerformed

    private void tf_maxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_maxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_maxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.print("\n kk");
        if(c_last.isSelected()) {
            System.out.print("\n kk");
            DefaultTableModel model=(DefaultTableModel)table.getModel();
            Vector d=new Vector();
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            String s2[][]=new String[1][6];
            System.out.print("\n kk");
            for (int i=0;i<table.getRowCount();i++) {
                if(i==table.getRowCount()-1) {
                    s2[0][0]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(0));
                    s2[0][1]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(1));
                    s2[0][2]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(2));
                    s2[0][3]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(3));
                    s2[0][4]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(4));
                    s2[0][5]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(5));
                    
                }
            }
            table t=new table("Last Tran.: "+c_lottery.getSelectedItem()+"-"+c_member.getSelectedItem()+", "+tf_name.getText(),header,s2);
            t.setVisible(true);
        } else if(c_complete.isSelected()) {
            DefaultTableModel model=(DefaultTableModel)table.getModel();
            Vector d=new Vector();
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            String s2[][]=new String[table.getRowCount()][6];
            for (int i=0;i<table.getRowCount();i++) {
                s2[i][0]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(0));
                s2[i][1]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(1));
                s2[i][2]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(2));
                s2[i][3]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(3));
                s2[i][4]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(4));
                s2[i][5]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(5));
                
            }
            // String s1[]=null;//(Sttable.getTableHeader().getComponents();
            table t=new table("Monthly Tran.:"+c_lottery.getSelectedItem()+"-"+c_member.getSelectedItem()+", "+tf_name.getText(),header,s2);
            //t.printGradesTable();
            t.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(mainframe, "Select Last or Complete","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void c_membernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_membernameActionPerformed
        // TODO add your handling code here:
        c_member.setSelectedIndex(c_membername.getSelectedIndex());
    }//GEN-LAST:event_c_membernameActionPerformed

    private void c_memberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_memberItemStateChanged
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        try {
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from investorinfo where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_contact.setText(db.rslt.getString(3));
               ta_address.setText(db.rslt.getString(4));
            }

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

            int count=0;
            String data[][]=null;
            String header[]={"S.No.","Date","Debit","Credit","Interest Added","Balance"};
            db.rslt=db.stmt.executeQuery("select date,debit,credit,interest,balance from investsheet where investor_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][6];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);

                lastdate=db.rslt.getString(1);
                if(db.rslt.getString(2)!=null)
                    tdebit+=Integer.valueOf(db.rslt.getString(2));
                if(db.rslt.getString(3)!=null)
                    tcredit+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                    tinterest+=Integer.valueOf(db.rslt.getString(4));
                tbalance=Integer.valueOf(db.rslt.getString(5));
                count++;
            }
             data[count][0]="Total";
            data[count][2]=""+tdebit;
            data[count][3]=""+tcredit;
            data[count][4]=""+tinterest;
            
            l_debit.setText("Total Debit : "+tdebit);
            l_investment.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_balance.setText("Current Balance : "+tbalance);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            tf_credit.setText("");
            tf_date.setText("");
            c_membername.setSelectedIndex(c_member.getSelectedIndex());
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
    }//GEN-LAST:event_c_memberItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_close;
    private javax.swing.JButton bt_date;
    private javax.swing.JButton bt_save;
    private javax.swing.JRadioButton c_complete;
    private javax.swing.JRadioButton c_last;
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JComboBox c_member;
    private javax.swing.JComboBox c_membername;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel l_balance;
    private javax.swing.JLabel l_cdate;
    private javax.swing.JLabel l_debit;
    private javax.swing.JLabel l_interest;
    private javax.swing.JLabel l_investment;
    private javax.swing.JLabel l_ldate;
    private javax.swing.JRadioButton r_credit;
    private javax.swing.JRadioButton r_debit;
    private javax.swing.JTextArea ta_address;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_available;
    private javax.swing.JTextField tf_contact;
    private javax.swing.JTextField tf_credit;
    private javax.swing.JTextField tf_date;
    private javax.swing.JTextField tf_debit;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_interest;
    private javax.swing.JTextField tf_max;
    private javax.swing.JTextField tf_name;
    // End of variables declaration//GEN-END:variables

}
