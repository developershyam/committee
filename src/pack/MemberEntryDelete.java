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
public class MemberEntryDelete extends javax.swing.JInternalFrame {

    /** Creates new form MemberAccount */
    JFrame mainframe;
    Vector recurring,nonrecurring,date,member_id,person,sn;
    int tmonth=0,trecurring,tfine,tdebit,tcredit,tinterest,ttotal,tbalance;
    long totalavailable=0;
    int arr[]=null;

    public MemberEntryDelete(JFrame mainframe) {
        super("Member Account",true,true,true,true);
        this.mainframe=mainframe;
        recurring=new Vector();
        nonrecurring=new Vector();
        date=new Vector();
        member_id=new Vector();
        person=new Vector();
        sn=new Vector();
        
        initComponents();
        l_cdate.setText(""+(new Date().toLocaleString()));
        l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex())));
        Dbcon db=new Dbcon();
        db.connect();
        try {

            db.rslt=db.stmt.executeQuery("select member_id,member_no,person from memberaccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            db.rslt.afterLast();
            int count=0;
            if (db.rslt.previous())count=db.rslt.getRow();
            String s1[]=new String[count];
            db.rslt.beforeFirst();
            count=0;
            member_id.clear();
            person.clear();
            while(db.rslt.next()) {
                member_id.addElement(db.rslt.getString(1));
                s1[count]=db.rslt.getString(2);
                person.addElement(db.rslt.getString(3));
                count++;
            }
            c_member.setModel(new javax.swing.DefaultComboBoxModel(s1));
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from memberinfo where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_person.setText(""+person.elementAt(c_member.getSelectedIndex()));
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
            Object data[][]=null;
            Object header[]={"Select","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month,sn from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new Object[count][9];
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
            sn.clear();
            while(db.rslt.next())
            {
                data[count][0]=false;

                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                sn.addElement(db.rslt.getString(10));
                System.out.println(sn);
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                tfine+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                tdebit+=Integer.valueOf(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                tcredit+=Integer.valueOf(db.rslt.getString(5));
                if(db.rslt.getString(6)!=null)
                tinterest+=Integer.valueOf(db.rslt.getString(6));
                ttotal+=Integer.valueOf(db.rslt.getString(7));
                tbalance=Integer.valueOf(db.rslt.getString(8));
                tmonth+=Integer.valueOf(db.rslt.getString(9));
                count++;
            }
            l_recurring.setText("Total Recurring : "+trecurring);
            l_fine.setText("Total Fine : "+tfine);
            l_debit.setText("Total Debit : "+tdebit);
            l_credit.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_deposit.setText("Total Deposit : "+ttotal);
            l_balance.setText("Current Balance : "+tbalance);
            l_month.setText("Total Month : "+tmonth);
            table.setModel(new DefaultTableModel(data,header));
            //p_table.removeAll();
            //p_table.setLayout(new BorderLayout());
            //p_table.add("Center",table);
            table.setRowHeight(25);
            table.updateUI();
            //p_table.updateUI();

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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_name = new javax.swing.JTextField();
        tf_fname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tf_person = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tf_contact = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_address = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        bt_close = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        tf_available = new javax.swing.JTextField();
        p_table = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    return Boolean.class;
                    default:
                    return String.class;
                }
            }
        };
        ;
        jPanel5 = new javax.swing.JPanel();
        l_recurring = new javax.swing.JLabel();
        l_fine = new javax.swing.JLabel();
        l_debit = new javax.swing.JLabel();
        l_credit = new javax.swing.JLabel();
        l_interest = new javax.swing.JLabel();
        l_deposit = new javax.swing.JLabel();
        l_balance = new javax.swing.JLabel();
        l_month = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        c_lottery.setModel(new javax.swing.DefaultComboBoxModel(lottery.toArray()));
        c_lottery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                c_lotteryItemStateChanged(evt);
            }
        });

        c_member.setEditable(true);
        c_member.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        c_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_memberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(c_member, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(l_cdate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_cdate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(c_member, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(c_lottery, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Member Detail")));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel2.setText("Name :");

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

        tf_person.setEditable(false);
        tf_person.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_person.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_personActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel4.setText("Person :");

        tf_contact.setEditable(false);
        tf_contact.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_contactActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel5.setText("Contact  :");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_contact)
                            .addComponent(tf_person)
                            .addComponent(tf_fname)
                            .addComponent(tf_name, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_person, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Current Payment Detail"));

        bt_close.setText("Close");
        bt_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_closeActionPerformed(evt);
            }
        });

        bt_save.setText("Delete");
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_available, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_available, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        p_table.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Past Payment Detail"));

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

        javax.swing.GroupLayout p_tableLayout = new javax.swing.GroupLayout(p_table);
        p_table.setLayout(p_tableLayout);
        p_tableLayout.setHorizontalGroup(
            p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_tableLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        p_tableLayout.setVerticalGroup(
            p_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Report"));

        l_recurring.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_recurring.setText("Total Recurring :");

        l_fine.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_fine.setText("Total Fine :");

        l_debit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_debit.setText("Total Debit :");

        l_credit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_credit.setText("Total Credit");

        l_interest.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_interest.setText("Total Interest :");

        l_deposit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_deposit.setText("Total Deposit :");

        l_balance.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_balance.setText("Current Balance :");

        l_month.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_month.setText("Total Month :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(l_recurring, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_fine, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_debit, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_credit, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_interest, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_deposit, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_balance, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addComponent(l_month, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(l_recurring, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_fine, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_debit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_credit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_interest, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_deposit, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(l_month, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(p_table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(p_table, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void c_lotteryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_lotteryItemStateChanged
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        try {
            l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex())));
            db.rslt=db.stmt.executeQuery("select member_id,member_no,person from memberaccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"'");
            db.rslt.afterLast();
            int count=0;
            if (db.rslt.previous())count=db.rslt.getRow();
            String s1[]=new String[count];
            db.rslt.beforeFirst();
            count=0;
            member_id.clear();
            person.clear();
            while(db.rslt.next()) {
                member_id.addElement(db.rslt.getString(1));
                s1[count]=db.rslt.getString(2);
                person.addElement(db.rslt.getString(3));
                count++;
            }
            c_member.setModel(new javax.swing.DefaultComboBoxModel(s1));
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
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from memberinfo where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_person.setText(""+person.elementAt(c_member.getSelectedIndex()));
               tf_contact.setText(db.rslt.getString(3));
               ta_address.setText(db.rslt.getString(4));
            }
            count=0;
            Object data[][]=null;
            Object header[]={"Select","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month,sn from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new Object[count][9];
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
            sn.clear();
            while(db.rslt.next())
            {
                data[count][0]=false;
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                sn.addElement(db.rslt.getString(10));
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                tfine+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                tdebit+=Integer.valueOf(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                tcredit+=Integer.valueOf(db.rslt.getString(5));
                if(db.rslt.getString(6)!=null)
                tinterest+=Integer.valueOf(db.rslt.getString(6));
                ttotal+=Integer.valueOf(db.rslt.getString(7));
                tbalance=Integer.valueOf(db.rslt.getString(8));
                tmonth+=Integer.valueOf(db.rslt.getString(9));
                count++;
            }
            l_recurring.setText("Total Recurring : "+trecurring);
            l_fine.setText("Total Fine : "+tfine);
            l_debit.setText("Total Debit : "+tdebit);
            l_credit.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_deposit.setText("Total Deposit : "+ttotal);
            l_balance.setText("Current Balance : "+tbalance);
            l_month.setText("Total Month : "+tmonth);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();

            
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
}//GEN-LAST:event_c_lotteryItemStateChanged

    private void tf_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tf_nameActionPerformed

    private void tf_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_fnameActionPerformed

    private void tf_personActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_personActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_personActionPerformed

    private void tf_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_contactActionPerformed

    private void bt_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_closeActionPerformed
        // TODO add your handling code here:
        dispose();
}//GEN-LAST:event_bt_closeActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
                
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            DefaultTableModel modeld=null;
                for (int i=0;i<table.getRowCount();i++) {
                    modeld=(DefaultTableModel)table.getModel();
                    Boolean b=(Boolean)((Vector)modeld.getDataVector().elementAt(i)).elementAt(0);
                    if (b.booleanValue()==true) {
                        int reply = JOptionPane.showConfirmDialog(mainframe,
                                "Are You Sure\n This Can Be Delete Top Seleted  Selected Entry ?",
                                "Warning" ,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if (reply == JOptionPane.YES_OPTION) {

                                db.stmt.executeUpdate("delete from sheet where sn="+sn.elementAt(i));
                                System.out.println("i="+i+"   sn="+sn.elementAt(i));
                               JOptionPane.showMessageDialog(mainframe,"Successfully  Deleted","Deleted",JOptionPane.PLAIN_MESSAGE);
                               break;

                        }
                    }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Can Not Be Deleted","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
        //db.disconnect();
        totalavailable=0;
        try
        {
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
                Object data[][]=null;
                Object header[]={"Select","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
                db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month,sn from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
                db.rslt.afterLast();
                if(db.rslt.previous())count=db.rslt.getRow();
                data=new Object[count][9];
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
                sn.clear();
                while(db.rslt.next())
                {
                    data[count][0]=false;
                    data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                    data[count][2]=db.rslt.getString(2);
                    data[count][3]=db.rslt.getString(3);
                    data[count][4]=db.rslt.getString(4);
                    data[count][5]=db.rslt.getString(5);
                    data[count][6]=db.rslt.getString(6);
                    data[count][7]=db.rslt.getString(7);
                    data[count][8]=db.rslt.getString(8);
                    sn.addElement(db.rslt.getString(10));
                    trecurring+=Integer.valueOf(db.rslt.getString(2));
                    tfine+=Integer.valueOf(db.rslt.getString(3));
                    if(db.rslt.getString(4)!=null)
                    tdebit+=Integer.valueOf(db.rslt.getString(4));
                    if(db.rslt.getString(5)!=null)
                    tcredit+=Integer.valueOf(db.rslt.getString(5));
                    if(db.rslt.getString(6)!=null)
                    tinterest+=Integer.valueOf(db.rslt.getString(6));
                    ttotal+=Integer.valueOf(db.rslt.getString(7));
                    tbalance=Integer.valueOf(db.rslt.getString(8));
                    tmonth+=Integer.valueOf(db.rslt.getString(9));
                    count++;
                }
                l_recurring.setText("Total Recurring : "+trecurring);
                l_fine.setText("Total Fine : "+tfine);
                l_debit.setText("Total Debit : "+tdebit);
                l_credit.setText("Total Credit : "+tcredit);
                l_interest.setText("Total Interest : "+tinterest);
                l_deposit.setText("Total Deposit : "+ttotal);
                l_balance.setText("Current Balance : "+tbalance);
                l_month.setText("Total Month : "+tmonth);
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
            db.rslt=db.stmt.executeQuery("select name,f_name,contact_no,address from memberinfo where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            if(db.rslt.next()) {
               tf_name.setText(db.rslt.getString(1));
               tf_fname.setText(db.rslt.getString(2));
               tf_person.setText(""+person.elementAt(c_member.getSelectedIndex()));
               tf_contact.setText(db.rslt.getString(3));
               ta_address.setText(db.rslt.getString(4));
            }
            int count=0;
            Object data[][]=null;
            Object header[]={"Select","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month,sn from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new Object[count][9];
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
            sn.clear();
            while(db.rslt.next())
            {
                data[count][0]=false;
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                sn.addElement(db.rslt.getString(10));
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                tfine+=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                tdebit+=Integer.valueOf(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                tcredit+=Integer.valueOf(db.rslt.getString(5));
                if(db.rslt.getString(6)!=null)
                tinterest+=Integer.valueOf(db.rslt.getString(6));
                ttotal+=Integer.valueOf(db.rslt.getString(7));
                tbalance=Integer.valueOf(db.rslt.getString(8));
                tmonth+=Integer.valueOf(db.rslt.getString(9));
                count++;
            }
            l_recurring.setText("Total Recurring : "+trecurring);
            l_fine.setText("Total Fine : "+tfine);
            l_debit.setText("Total Debit : "+tdebit);
            l_credit.setText("Total Credit : "+tcredit);
            l_interest.setText("Total Interest : "+tinterest);
            l_deposit.setText("Total Deposit : "+ttotal);
            l_balance.setText("Current Balance : "+tbalance);
            l_month.setText("Total Month : "+tmonth);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Member Does Not Exits","Error",JOptionPane.ERROR_MESSAGE);}
        db.disconnect();

    }//GEN-LAST:event_c_memberActionPerformed

    private void tf_availableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_availableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_availableActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_close;
    private javax.swing.JButton bt_save;
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JComboBox c_member;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel l_balance;
    private javax.swing.JLabel l_cdate;
    private javax.swing.JLabel l_credit;
    private javax.swing.JLabel l_debit;
    private javax.swing.JLabel l_deposit;
    private javax.swing.JLabel l_fine;
    private javax.swing.JLabel l_interest;
    private javax.swing.JLabel l_ldate;
    private javax.swing.JLabel l_month;
    private javax.swing.JLabel l_recurring;
    private javax.swing.JPanel p_table;
    private javax.swing.JTextArea ta_address;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_available;
    private javax.swing.JTextField tf_contact;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_name;
    private javax.swing.JTextField tf_person;
    // End of variables declaration//GEN-END:variables

}
