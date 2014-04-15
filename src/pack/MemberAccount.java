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
public class MemberAccount extends javax.swing.JInternalFrame {

    /** Creates new form MemberAccount */
    JFrame mainframe;
    Vector recurring,nonrecurring,date,member_id,person,lmonth,_memberid,_membername;
    int tmonth=0,trecurring,tfine,tdebit,tcredit,tinterest,ttotal,tbalance,rinterest;
    long totalavailable=0;
    int arr[]=null;
    ButtonGroup pbgp=new ButtonGroup();
    static JWindow jw;
    static JList l;
    public MemberAccount(JFrame mainframe) {
        super("Member Account",true,true,true,true);
        this.mainframe=mainframe;
        recurring=new Vector();
        nonrecurring=new Vector();
        date=new Vector();
        member_id=new Vector();
        person=new Vector();
        lmonth =new Vector();
        _membername=new Vector();
        _memberid=new Vector();
        jw=new JWindow();
        l=new JList();
        jw.add("Center",new JScrollPane(l));
        l.setBackground(Color.LIGHT_GRAY);
        l.setOpaque(true);
        
        initComponents();
        
        l_cdate.setText(""+(new Date().toLocaleString()));
        l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex()))
);
        Dbcon db=new Dbcon();
        db.connect();
        try {

            db.rslt=db.stmt.executeQuery("select member_id,member_no,person from memberaccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' && status=1");
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
            String data[][]=null;
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][9];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            rinterest=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            String lastdeposite="0";
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                lastdeposite=db.rslt.getString(7);
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));

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
            data[count][0]="Total";
            data[count][2]=""+trecurring;
            data[count][3]=""+tfine;
            data[count][4]=""+tdebit;
            data[count][5]=""+tcredit;
            data[count][6]=""+tinterest;
            data[count][7]=""+ttotal;
            data[count][8]=""+tbalance;
            l_recurring.setText("  Recurring : "+trecurring);
            l_fine.setText("  Fine : "+tfine);
            l_debit.setText("  Debit : "+tdebit);
            l_credit.setText("  Credit : "+tcredit);
            l_interest.setText("  Interest : "+tinterest);
            l_deposit.setText("  Deposit : "+ttotal);
            l_balance.setText(" Balance : "+tbalance);
            l_month.setText("  Month : "+tmonth);
            //l_recurringinterest.setText("Recurring Interest : "+rinterest);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            tf_depositeupdate.setText(lastdeposite);
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Error"+e);}
        db.disconnect();
        pbgp.add(c_last);
        pbgp.add(c_complete);

        l.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                jw.setVisible(false);
                System.out.println("mouse click");
                tf_search.setText(_membername.elementAt(l.getSelectedIndex())+"");
                c_member.setSelectedItem(_memberid.elementAt(l.getSelectedIndex()));

                //jw.dispose();
            }

        })
        ;
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
            db.rslt=db.stmt.executeQuery("select lottery_no,recurring,nonrecurring,date,month from lottery where status=1");
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
        c_member = new javax.swing.JComboBox();
        l_ldate = new javax.swing.JLabel();
        l_cdate = new javax.swing.JLabel();
        tf_search = new javax.swing.JTextField();
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tf_recurring = new javax.swing.JTextField();
        tf_cdebit = new javax.swing.JTextField();
        tf_current = new javax.swing.JTextField();
        tf_deposite = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tf_fine = new javax.swing.JTextField();
        tf_interest = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tf_debit = new javax.swing.JTextField();
        tf_date = new javax.swing.JTextField();
        bt_date = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        bt_close = new javax.swing.JButton();
        bt_save = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        tf_available = new javax.swing.JTextField();
        tf_depositeupdate = new javax.swing.JTextField();
        tf_debitupdate = new javax.swing.JTextField();
        r_update = new javax.swing.JRadioButton();
        bt_update = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        c_last = new javax.swing.JRadioButton();
        c_complete = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
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

        tf_search.setFont(new java.awt.Font("Tahoma", 1, 18));
        tf_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_searchActionPerformed(evt);
            }
        });
        tf_search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tf_searchFocusLost(evt);
            }
        });
        tf_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(c_lottery, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(c_member, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(tf_search, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(l_cdate, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tf_search, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(l_cdate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_member, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(l_ldate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c_lottery, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Member Detail")));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel2.setText(" Name :");

        tf_name.setEditable(false);
        tf_name.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_name.setHorizontalAlignment(javax.swing.JTextField.LEFT);
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
        jLabel3.setText("Father:");

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
        jLabel5.setText("Contact:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel6.setText("Add. :");

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tf_contact, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tf_person, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tf_fname, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tf_name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Current Payment Detail"));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Total Deposite :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Total Current :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Current Debit :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Recurring:");

        tf_recurring.setEditable(false);
        tf_recurring.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_recurring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_recurringActionPerformed(evt);
            }
        });

        tf_cdebit.setEditable(false);
        tf_cdebit.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_cdebit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cdebitActionPerformed(evt);
            }
        });

        tf_current.setEditable(false);
        tf_current.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_current.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_currentActionPerformed(evt);
            }
        });

        tf_deposite.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_deposite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_depositeActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Fine :");

        tf_fine.setEditable(false);
        tf_fine.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_fine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_fineActionPerformed(evt);
            }
        });

        tf_interest.setEditable(false);
        tf_interest.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_interest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_interestActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Interest :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Debit Amount :");

        tf_debit.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_debit.setText("0");
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

        tf_depositeupdate.setEditable(false);
        tf_depositeupdate.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_depositeupdate.setText("0");
        tf_depositeupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_depositeupdateActionPerformed(evt);
            }
        });

        tf_debitupdate.setEditable(false);
        tf_debitupdate.setFont(new java.awt.Font("Tahoma", 0, 16));
        tf_debitupdate.setText("0");
        tf_debitupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_debitupdateActionPerformed(evt);
            }
        });

        r_update.setText("Update");
        r_update.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                r_updateItemStateChanged(evt);
            }
        });

        bt_update.setText("Update");
        bt_update.setEnabled(false);
        bt_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_updateActionPerformed(evt);
            }
        });

        jLabel1.setText("Deposite");

        jLabel16.setText("Debit");

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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(c_last)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c_complete)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_debit, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tf_available)
                            .addComponent(tf_deposite)
                            .addComponent(tf_current)
                            .addComponent(tf_cdebit)
                            .addComponent(tf_recurring, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tf_fine, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tf_interest, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(33, 33, 33))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tf_debitupdate)
                            .addComponent(tf_depositeupdate, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(r_update)
                            .addComponent(bt_update))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_recurring, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_fine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_cdebit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_interest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_current, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_deposite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_available, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_depositeupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(r_update))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_debitupdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_update, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_debit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_save, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_close, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(c_last)
                        .addComponent(c_complete))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Report"));

        l_recurring.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_recurring.setText("Recurring :");

        l_fine.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_fine.setText("Fine :");

        l_debit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_debit.setText(" Debit :");

        l_credit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_credit.setText("Credit");

        l_interest.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_interest.setText("Interest :");

        l_deposit.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_deposit.setText("Deposit :");

        l_balance.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_balance.setText("Balance :");

        l_month.setFont(new java.awt.Font("Tahoma", 0, 16));
        l_month.setText("Month :");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(l_recurring, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_debit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_credit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_interest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_deposit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_balance, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_month, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(l_fine, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)))
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
                .addComponent(l_month, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            l_ldate.setText(""+DateConverter.toDDMMYYYY((String)date.elementAt(c_lottery.getSelectedIndex()))
);
            db.rslt=db.stmt.executeQuery("select member_id,member_no,person from memberaccount where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' && status=1");
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
            String data[][]=null;
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][9];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            rinterest=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            String lastdeposite="0";
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                lastdeposite=db.rslt.getString(7);
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));
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
            data[count][0]="Total";
            data[count][2]=""+trecurring;
            data[count][3]=""+tfine;
            data[count][4]=""+tdebit;
            data[count][5]=""+tcredit;
            data[count][6]=""+tinterest;
            data[count][7]=""+ttotal;
            data[count][8]=""+tbalance;


            l_recurring.setText("  Recurring : "+trecurring);
            l_fine.setText("  Fine : "+tfine);
            l_debit.setText("  Debit : "+tdebit);
            l_credit.setText("  Credit : "+tcredit);
            l_interest.setText("  Interest : "+tinterest);
            l_deposit.setText("  Deposit : "+ttotal);
            l_balance.setText("  Balance: "+tbalance);
            l_month.setText("  Month : "+tmonth);
//            l_recurringinterest.setText("Recurring interest : "+rinterest);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();

            tf_recurring.setText("");
            tf_fine.setText("");
            tf_cdebit.setText("");
            tf_interest.setText("");
            tf_current.setText("");
            tf_deposite.setText("");
            tf_date.setText("");
            tf_debit.setText("0");
            tf_depositeupdate.setText(lastdeposite);
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

    private void tf_recurringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_recurringActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_recurringActionPerformed

    private void tf_currentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_currentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_currentActionPerformed

    private void tf_depositeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_depositeActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if(tmonth>=Integer.parseInt(""+lmonth.elementAt(c_lottery.getSelectedIndex())))
        {
            JOptionPane.showMessageDialog(mainframe,"Total Month Completed !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(arr==null)
        {
            JOptionPane.showMessageDialog(mainframe,"One  Month Is Not Completed !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(tf_date.getText().equals(""))
        {
            JOptionPane.showMessageDialog(mainframe,"Select Date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int recurring=arr[1];
        int fine=arr[2];
        int currentbalance=tbalance;
        int interest=arr[3];
        int month=arr[4];
        int deposit=0;
        int debit=0;
        int balance=0;
        int credit=0;
        try
        {
            deposit=Integer.valueOf(tf_deposite.getText());
            debit=Integer.valueOf(tf_debit.getText());

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Enter Amount In Numeric !","Error",JOptionPane.ERROR_MESSAGE);
            return;

        }
        if(deposit<(recurring+fine+interest))
        {
            JOptionPane.showMessageDialog(mainframe,"Deposit Amount < Recurring+Fine+Interest ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(deposit>(recurring+fine+currentbalance+interest))
        {
            JOptionPane.showMessageDialog(mainframe,"Deposit Amount > Recurring+Fine+Current Debit+Interest ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(debit>(totalavailable+deposit))
        {
            JOptionPane.showMessageDialog(mainframe,"Debit Amount > Available + Deposit ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            int order=1;
            db.rslt=db.stmt.executeQuery("select max(order1) from sheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' and date='"+DateConverter.toYYYYMMDD(tf_date.getText())
+"'");
            if(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                    order=Integer.parseInt(db.rslt.getString(1))+1;
            }
            credit=deposit-fine-interest-recurring;
            balance=currentbalance-credit+debit;
            System.out.println("deposit="+deposit+"    credit="+credit+"  balnce="+balance+"   debit="+debit);
            db.stmt.executeUpdate("insert into sheet(lottery_no,member_id,date,recurring,fine,debit,credit,interest,total,balance,month,order1) "+

             "values('"+lottery.elementAt(c_lottery.getSelectedIndex())+"',"+member_id.elementAt(c_member.getSelectedIndex())+",'"+DateConverter.toYYYYMMDD(tf_date.getText())
+"','"+

             recurring+"','"+fine+"','"+debit+"','"+credit+"','"+interest+"','"+deposit+"','"+balance+"',"+month+","+order+")");
            db.conn.commit();


        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Can Not Be Inserted","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
        //db.disconnect();
        tf_recurring.setText("");
        tf_fine.setText("");
        tf_cdebit.setText("");
        tf_interest.setText("");
        tf_current.setText("");
        tf_deposite.setText("");
        //tf_date.setText("");
        tf_debit.setText("0");
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
                String data[][]=null;
                String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
                db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
                db.rslt.afterLast();
                if(db.rslt.previous())count=db.rslt.getRow();
                data=new String[count+1][9];
                db.rslt.beforeFirst();
                count=0;
                tmonth=0;
                trecurring=0;
                rinterest=0;
                tfine=0;
                tdebit=0;
                tcredit=0;
                tinterest=0;
                ttotal=0;
                tbalance=0;
                String lastdeposite="0";
                while(db.rslt.next())
                {
                    data[count][0]=(count+1)+"";
                    data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                    data[count][2]=db.rslt.getString(2);
                    data[count][3]=db.rslt.getString(3);
                    data[count][4]=db.rslt.getString(4);
                    data[count][5]=db.rslt.getString(5);
                    data[count][6]=db.rslt.getString(6);
                    data[count][7]=db.rslt.getString(7);
                    data[count][8]=db.rslt.getString(8);
                    lastdeposite=db.rslt.getString(7);
                    trecurring+=Integer.valueOf(db.rslt.getString(2));
                   rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));
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
                data[count][0]="Total";
                data[count][2]=""+trecurring;
                data[count][3]=""+tfine;
                data[count][4]=""+tdebit;
                data[count][5]=""+tcredit;
                data[count][6]=""+tinterest;
                data[count][7]=""+ttotal;
                data[count][8]=""+tbalance;
                l_recurring.setText("  Recurring : "+trecurring);
                l_fine.setText("  Fine : "+tfine);
                l_debit.setText("  Debit : "+tdebit);
                l_credit.setText("  Credit : "+tcredit);
                l_interest.setText("  Interest : "+tinterest);
                l_deposit.setText("  Deposit : "+ttotal);
                l_balance.setText(" Balance : "+tbalance);
                l_month.setText("  Month : "+tmonth);
//                l_recurringinterest.setText("Recurring Interest : "+rinterest);
                table.setModel(new DefaultTableModel(data,header));
                table.setRowHeight(25);
                table.updateUI();
                tf_depositeupdate.setText(lastdeposite);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        db.disconnect();

    }//GEN-LAST:event_tf_depositeActionPerformed

    private void tf_fineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_fineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_fineActionPerformed

    private void tf_interestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_interestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_interestActionPerformed

    private void tf_debitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_debitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_debitActionPerformed

    private void tf_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dateActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tf_dateActionPerformed

    private void bt_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_dateActionPerformed
        // TODO add your handling code here:
        tf_date.setText(DateConverter.toDDMMYYYY(new DatePicker(mainframe).setPickedDate()));
        try {
            int i=Integer.valueOf(tf_person.getText());
        }catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Enter Person In Numeric !","Error",JOptionPane.ERROR_MESSAGE);return;}
        if(tf_date.getText().equals("")) {
            JOptionPane.showMessageDialog(mainframe,"Select date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(tmonth>=Integer.parseInt(""+lmonth.elementAt(c_lottery.getSelectedIndex())))
        {
            JOptionPane.showMessageDialog(mainframe,"Total Month Completed !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int a[]=Result.monthlydesposite(""+date.elementAt(c_lottery.getSelectedIndex()),DateConverter.toYYYYMMDD(tf_date.getText())
, tmonth,Integer.parseInt(""+recurring.elementAt(c_lottery.getSelectedIndex())), tbalance, Integer.parseInt(""+person.elementAt(c_member.getSelectedIndex())));
        arr=a;
        if(a==null)
        {
            if(r_update.isSelected())
            {
                JOptionPane.showMessageDialog(mainframe,"First Enter Last Total Deposite  !","Enter",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(mainframe,"One  Month Is Not Completed !","Error",JOptionPane.ERROR_MESSAGE);
            tf_recurring.setText("");
            tf_fine.setText("");
            tf_cdebit.setText("");
            tf_interest.setText("");
            tf_current.setText("");
            tf_deposite.setText("");
            tf_debit.setText("0");
            return;
        }
        tf_recurring.setText(""+a[1]);
        tf_fine.setText(""+a[2]);
        tf_cdebit.setText(""+tbalance);
        tf_interest.setText(""+a[3]);
        tf_current.setText(""+a[0]);
        tf_debit.setText("0");
        
    }//GEN-LAST:event_bt_dateActionPerformed

    private void bt_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_closeActionPerformed
        // TODO add your handling code here:
        dispose();
}//GEN-LAST:event_bt_closeActionPerformed

    private void bt_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_saveActionPerformed
        // TODO add your handling code here:
        if(tmonth>=Integer.parseInt(""+lmonth.elementAt(c_lottery.getSelectedIndex())))
        {
            JOptionPane.showMessageDialog(mainframe,"Total Month Completed !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(arr==null)
        {
            JOptionPane.showMessageDialog(mainframe,"One  Month Is Not Completed !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(tf_date.getText().equals(""))
        {
            JOptionPane.showMessageDialog(mainframe,"Select Date !","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        int recurring=arr[1];
        int fine=arr[2];
        int currentbalance=tbalance;
        int interest=arr[3];
        int month=arr[4];
        int deposit=0;
        int debit=0;
        int balance=0;
        int credit=0;
        try
        {
            deposit=Integer.valueOf(tf_deposite.getText());
            debit=Integer.valueOf(tf_debit.getText());            
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Enter Amount In Numeric !","Error",JOptionPane.ERROR_MESSAGE);
            return;

        }
        if(deposit<(recurring+fine+interest))
        {
            JOptionPane.showMessageDialog(mainframe,"Deposit Amount < Recurring+Fine+Interest ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(deposit>(recurring+fine+currentbalance+interest))
        {
            JOptionPane.showMessageDialog(mainframe,"Deposit Amount > Recurring+Fine+Current Debit+Interest ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(debit>(totalavailable+deposit))
        {
            JOptionPane.showMessageDialog(mainframe,"Debit Amount > Available + Deposit ","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            int order=1;
            db.rslt=db.stmt.executeQuery("select max(order1) from sheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' and date='"+DateConverter.toYYYYMMDD(tf_date.getText())
+"'");
            if(db.rslt.next())
            {
                if(db.rslt.getString(1)!=null)
                    order=Integer.parseInt(db.rslt.getString(1))+1;
            }
            credit=deposit-fine-interest-recurring;
            balance=currentbalance-credit+debit;
            System.out.println("deposit="+deposit+"    credit="+credit+"  balnce="+balance+"   debit="+debit);
            db.stmt.executeUpdate("insert into sheet(lottery_no,member_id,date,recurring,fine,debit,credit,interest,total,balance,month,order1) "+

             "values('"+lottery.elementAt(c_lottery.getSelectedIndex())+"',"+member_id.elementAt(c_member.getSelectedIndex())+",'"+DateConverter.toYYYYMMDD(tf_date.getText())
+"','"+

             recurring+"','"+fine+"','"+debit+"','"+credit+"','"+interest+"','"+deposit+"','"+balance+"',"+month+","+order+")");
            db.conn.commit();
              
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Can Not Be Inserted","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
        //db.disconnect();
        tf_recurring.setText("");
        tf_fine.setText("");
        tf_cdebit.setText("");
        tf_interest.setText("");
        tf_current.setText("");
        tf_deposite.setText("");
        //tf_date.setText("");
        tf_debit.setText("0");
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
                String data[][]=null;
                String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
                db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
                db.rslt.afterLast();
                if(db.rslt.previous())count=db.rslt.getRow();
                data=new String[count+1][9];
                db.rslt.beforeFirst();
                count=0;
                tmonth=0;
                trecurring=0;
                rinterest=0;
                tfine=0;
                tdebit=0;
                tcredit=0;
                tinterest=0;
                ttotal=0;
                tbalance=0;
                String lastdeposite="0";
                while(db.rslt.next())
                {
                    data[count][0]=(count+1)+"";
                    data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                    data[count][2]=db.rslt.getString(2);
                    data[count][3]=db.rslt.getString(3);
                    data[count][4]=db.rslt.getString(4);
                    data[count][5]=db.rslt.getString(5);
                    data[count][6]=db.rslt.getString(6);
                    data[count][7]=db.rslt.getString(7);
                    data[count][8]=db.rslt.getString(8);
                    lastdeposite=db.rslt.getString(7);
                    trecurring+=Integer.valueOf(db.rslt.getString(2));
                   rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));
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
                data[count][0]="Total";
                data[count][2]=""+trecurring;
                data[count][3]=""+tfine;
                data[count][4]=""+tdebit;
                data[count][5]=""+tcredit;
                data[count][6]=""+tinterest;
                data[count][7]=""+ttotal;
                data[count][8]=""+tbalance;
                l_recurring.setText("  Recurring : "+trecurring);
                l_fine.setText("  Fine : "+tfine);
                l_debit.setText("  Debit : "+tdebit);
                l_credit.setText("  Credit : "+tcredit);
                l_interest.setText("  Interest : "+tinterest);
                l_deposit.setText("  Deposit : "+ttotal);
                l_balance.setText(" Balance : "+tbalance);
                l_month.setText("  Month : "+tmonth);
//                l_recurringinterest.setText("Recurring Interest : "+rinterest);
                table.setModel(new DefaultTableModel(data,header));
                table.setRowHeight(25);
                table.updateUI();
                tf_depositeupdate.setText(lastdeposite);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        db.disconnect();
    }//GEN-LAST:event_bt_saveActionPerformed

    private void c_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_memberActionPerformed
        // TODO add your handling code here:
        System.out.println("action");
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
            String data[][]=null;
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","  Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][9];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            rinterest=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            String lastdeposite="0";
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                lastdeposite=db.rslt.getString(7);
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));
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
            data[count][0]="Total";
            data[count][2]=""+trecurring;
            data[count][3]=""+tfine;
            data[count][4]=""+tdebit;
            data[count][5]=""+tcredit;
            data[count][6]=""+tinterest;
            data[count][7]=""+ttotal;
            data[count][8]=""+tbalance;

            l_recurring.setText("  Recurring : "+trecurring);
            l_fine.setText("  Fine : "+tfine);
            l_debit.setText("  Debit : "+tdebit);
            l_credit.setText("  Credit : "+tcredit);
            l_interest.setText("  Interest : "+tinterest);
            l_deposit.setText("  Deposit : "+ttotal);
            l_balance.setText(" Balance : "+tbalance);
            l_month.setText("  Month : "+tmonth);
//            l_recurringinterest.setText("Recurring Interest :"+rinterest);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            tf_recurring.setText("");
            tf_fine.setText("");
            tf_cdebit.setText("");
            tf_interest.setText("");
            tf_current.setText("");
            tf_deposite.setText("");
            tf_depositeupdate.setText(lastdeposite);
           // tf_date.setText("");
         
        } catch(Exception e){System.out.println(e);JOptionPane.showMessageDialog(mainframe,"Member Does Not Exits","Error",JOptionPane.ERROR_MESSAGE);}
        if(!tf_date.getText().equals(""))
        {
            try {
                int i=Integer.valueOf(tf_person.getText());
            }catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Enter Person In Numeric !","Error",JOptionPane.ERROR_MESSAGE);return;}
            if(tf_date.getText().equals("")) {
                JOptionPane.showMessageDialog(mainframe,"Select date !","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tmonth>=Integer.parseInt(""+lmonth.elementAt(c_lottery.getSelectedIndex())))
            {
                JOptionPane.showMessageDialog(mainframe,"  Month Completed !","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int a[]=Result.monthlydesposite(""+date.elementAt(c_lottery.getSelectedIndex()),DateConverter.toYYYYMMDD(tf_date.getText())
, tmonth,Integer.parseInt(""+recurring.elementAt(c_lottery.getSelectedIndex())), tbalance, Integer.parseInt(""+person.elementAt(c_member.getSelectedIndex())));
            arr=a;
            if(a==null)
            {
                if(r_update.isSelected())
                {
                    JOptionPane.showMessageDialog(mainframe,"First Enter Last   Deposite  !","Enter",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                
                tf_recurring.setText("");
                tf_fine.setText("");
                tf_cdebit.setText("");
                tf_interest.setText("");
                tf_current.setText("");
                tf_deposite.setText("");
                tf_debit.setText("0");

                JOptionPane.showMessageDialog(mainframe,"One  Month Is Not Completed !","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            tf_recurring.setText(""+a[1]);
            tf_fine.setText(""+a[2]);
            tf_cdebit.setText(""+tbalance);
            tf_interest.setText(""+a[3]);
            tf_current.setText(""+a[0]);
            tf_debit.setText("0");
        }
        db.disconnect();

    }//GEN-LAST:event_c_memberActionPerformed

    private void tf_availableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_availableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_availableActionPerformed

    private void tf_cdebitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cdebitActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_tf_cdebitActionPerformed

    private void tf_depositeupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_depositeupdateActionPerformed
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            int deposite=Integer.valueOf(tf_depositeupdate.getText());
            int debit=Integer.valueOf(tf_debitupdate.getText());
            int sn=0,rec=0,fn=0,dr=0,cr=0,intr=0,tot=0,bal=0;
            db.rslt=db.stmt.executeQuery("select sn,recurring,fine,debit,credit,interest,total,balance from sheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' && member_id="+member_id.elementAt(c_member.getSelectedIndex()));
            int c=0;
            db.rslt.afterLast();
            if(db.rslt.previous())c=db.rslt.getRow();
            int t1=c;
            db.rslt.beforeFirst();
            c=0;
            while(db.rslt.next())
            {
                sn=Integer.valueOf(db.rslt.getString(1));
                rec=Integer.valueOf(db.rslt.getString(2));
                fn=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                dr=Integer.valueOf(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                cr=Integer.valueOf(db.rslt.getString(5));
                if(db.rslt.getString(6)!=null)
                intr=Integer.valueOf(db.rslt.getString(6));
                tot=Integer.valueOf(db.rslt.getString(7));
                if(c<t1-1)
                {
                    bal=Integer.valueOf(db.rslt.getString(8));
                    System.out.println(bal);
                }
                c++;

            }
            if(t1<1)
            {
                JOptionPane.showMessageDialog(mainframe,"This can be updated","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(deposite<(rec+fn+intr))
            {
                JOptionPane.showMessageDialog(mainframe,"Amount Is Less","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            long avbal=totalavailable-tot+dr+deposite;
            tf_available.setText(avbal+"");
        }
        catch(Exception e){System.out.println(e);
        JOptionPane.showMessageDialog(mainframe,"You Can Not Be Update","Error",JOptionPane.ERROR_MESSAGE);
        }
        db.disconnect();

    }//GEN-LAST:event_tf_depositeupdateActionPerformed

    private void tf_debitupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_debitupdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_debitupdateActionPerformed

    private void bt_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_updateActionPerformed
        // TODO add your handling code here:

        Dbcon db=new Dbcon();
        db.connect();
        try
        {
            int deposite=Integer.valueOf(tf_depositeupdate.getText());
            int debit=Integer.valueOf(tf_debitupdate.getText());
            int sn=0,rec=0,fn=0,dr=0,cr=0,intr=0,tot=0,bal=0;
            db.rslt=db.stmt.executeQuery("select sn,recurring,fine,debit,credit,interest,total,balance from sheet where lottery_no='"+lottery.elementAt(c_lottery.getSelectedIndex())+"' && member_id="+member_id.elementAt(c_member.getSelectedIndex()));
            int c=0;
            db.rslt.afterLast();
            if(db.rslt.previous())c=db.rslt.getRow();
            int t1=c;
            db.rslt.beforeFirst();
            c=0;
            while(db.rslt.next())
            {
                sn=Integer.valueOf(db.rslt.getString(1));
                rec=Integer.valueOf(db.rslt.getString(2));
                fn=Integer.valueOf(db.rslt.getString(3));
                if(db.rslt.getString(4)!=null)
                dr=Integer.valueOf(db.rslt.getString(4));
                if(db.rslt.getString(5)!=null)
                cr=Integer.valueOf(db.rslt.getString(5));
                if(db.rslt.getString(6)!=null)
                intr=Integer.valueOf(db.rslt.getString(6));
                tot=Integer.valueOf(db.rslt.getString(7));
                if(c<t1-1)
                {
                    bal=Integer.valueOf(db.rslt.getString(8));
                    System.out.println(bal);
                }
                c++;
                
            }
            if(t1<1)
            {
                JOptionPane.showMessageDialog(mainframe,"This can be updated","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(deposite<(rec+fn+intr))
            {
                System.out.println(deposite+" "+(rec+fn+intr)+" rec="+rec+" fn="+fn+" intr="+intr);
                JOptionPane.showMessageDialog(mainframe,"Amount Is Less","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            long avbal=totalavailable-tot+dr;
            if(debit>(avbal+deposite))
            {
                JOptionPane.showMessageDialog(mainframe,"Available Balance Is Less","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int netcr=deposite-(rec+fn+intr);
            int tbal=bal;
            int netbal=tbal-netcr+debit;
            System.out.println(" bal="+bal+" cr="+cr+" db="+db+" netcr="+netcr+" tbal="+tbal+"  netbal="+netbal);
            db.stmt.executeUpdate("update sheet set recurring="+rec+",fine="+fn+",debit="+debit+",credit="+netcr+",interest="+intr+",total="+deposite+",balance="+netbal+" where sn="+sn);

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
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","  Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count+1][9];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            rinterest=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            String lastdeposite="0";
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=DateConverter.toDDMMYYYY(db.rslt.getString(1));
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);
                lastdeposite=db.rslt.getString(7);
                trecurring+=Integer.valueOf(db.rslt.getString(2));
                rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));
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
            data[count][0]="Total";
            data[count][2]=""+trecurring;
            data[count][3]=""+tfine;
            data[count][4]=""+tdebit;
            data[count][5]=""+tcredit;
            data[count][6]=""+tinterest;
            data[count][7]=""+ttotal;
            data[count][8]=""+tbalance;
            l_recurring.setText("  Recurring : "+trecurring);
            l_fine.setText("  Fine : "+tfine);
            l_debit.setText("  Debit : "+tdebit);
            l_credit.setText("  Credit : "+tcredit);
            l_interest.setText("  Interest : "+tinterest);
            l_deposit.setText("  Deposit : "+ttotal);
            l_balance.setText(" Balance : "+tbalance);
            l_month.setText("  Month : "+tmonth);
//            l_recurringinterest.setText("Recurring Interest : "+rinterest);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            tf_recurring.setText("");
            tf_fine.setText("");
            tf_cdebit.setText("");
            tf_interest.setText("");
            tf_current.setText("");
            tf_deposite.setText("");
           // tf_date.setText("");
            tf_deposite.setText("");
            tf_debit.setText("");
            tf_depositeupdate.setText(lastdeposite);
            tf_debitupdate.setText("0");

            JOptionPane.showMessageDialog(mainframe,"Successfully updated","Error",JOptionPane.PLAIN_MESSAGE);
            db.conn.commit();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(mainframe,"Can Not Be Inserted","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        }
    }//GEN-LAST:event_bt_updateActionPerformed

    private void r_updateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_r_updateItemStateChanged
        // TODO add your handling code here:
        if(r_update.isSelected())
        {
            tf_depositeupdate.setEditable(true);
            tf_debitupdate.setEditable(true);
            bt_update.setEnabled(true);
            bt_save.setEnabled(false);
            tf_deposite.setEditable(false);
            tf_debit.setEditable(false);
        }
        if(!r_update.isSelected())
        {
            tf_depositeupdate.setEditable(false);
            tf_debitupdate.setEditable(false);
            bt_update.setEnabled(false);
            bt_save.setEnabled(true);
            tf_deposite.setEditable(true);
            tf_debit.setEditable(true);
        }
        
        
    }//GEN-LAST:event_r_updateItemStateChanged

    private void c_memberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_c_memberItemStateChanged
        // TODO add your handling code here:
        /*Dbcon db=new Dbcon();
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
            String data[][]=null;
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            db.rslt=db.stmt.executeQuery("select date,recurring,fine,debit,credit,interest,total,balance,month from sheet where member_id='"+member_id.elementAt(c_member.getSelectedIndex())+"'");
            db.rslt.afterLast();
            if(db.rslt.previous())count=db.rslt.getRow();
            data=new String[count][9];
            db.rslt.beforeFirst();
            count=0;
            tmonth=0;
            trecurring=0;
            rinterest=0;
            tfine=0;
            tdebit=0;
            tcredit=0;
            tinterest=0;
            ttotal=0;
            tbalance=0;
            while(db.rslt.next())
            {
                data[count][0]=(count+1)+"";
                data[count][1]=db.rslt.getString(1);
                data[count][2]=db.rslt.getString(2);
                data[count][3]=db.rslt.getString(3);
                data[count][4]=db.rslt.getString(4);
                data[count][5]=db.rslt.getString(5);
                data[count][6]=db.rslt.getString(6);
                data[count][7]=db.rslt.getString(7);
                data[count][8]=db.rslt.getString(8);

                trecurring+=Integer.valueOf(db.rslt.getString(2));
                rinterest+=Math.round(2.0/100*Integer.valueOf(db.rslt.getString(2)));
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
            l_balance.setText("  : "+tbalance);
            l_month.setText("Total Month : "+tmonth);
//            l_recurringinterest.setText("Recurring Interest :"+rinterest);
            table.setModel(new DefaultTableModel(data,header));
            table.setRowHeight(25);
            table.updateUI();
            tf_recurring.setText("");
            tf_fine.setText("");
            tf_cdebit.setText("");
            tf_interest.setText("");
            tf_current.setText("");
            tf_deposite.setText("");
           // tf_date.setText("");
           
        } catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Member Does Not Exits","Error",JOptionPane.ERROR_MESSAGE);}
        if(!tf_date.getText().equals(""))
        {
            try {
                int i=Integer.valueOf(tf_person.getText());
            }catch(Exception e){JOptionPane.showMessageDialog(mainframe,"Enter Person In Numeric !","Error",JOptionPane.ERROR_MESSAGE);return;}
            if(tf_date.getText().equals("")) {
                JOptionPane.showMessageDialog(mainframe,"Select date !","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(tmonth>=Integer.parseInt(""+lmonth.elementAt(c_lottery.getSelectedIndex())))
            {
                JOptionPane.showMessageDialog(mainframe,"Total Month Completed !","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int a[]=Result.monthlydesposite(""+date.elementAt(c_lottery.getSelectedIndex()),tf_date.getText(), tmonth,Integer.parseInt(""+recurring.elementAt(c_lottery.getSelectedIndex())), tbalance, Integer.parseInt(""+person.elementAt(c_member.getSelectedIndex())));
            arr=a;
            if(a==null)
            {
                if(r_update.isSelected())
                {
                    JOptionPane.showMessageDialog(mainframe,"First Enter Last Total Deposite  !","Enter",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(mainframe,"One cmS Month Is Not Completed !","Error",JOptionPane.ERROR_MESSAGE);
                tf_recurring.setText("");
                tf_fine.setText("");
                tf_cdebit.setText("");
                tf_interest.setText("");
                tf_current.setText("");
                tf_deposite.setText("");
                tf_debit.setText("0");
                return;
            }
            tf_recurring.setText(""+a[1]);
            tf_fine.setText(""+a[2]);
            tf_cdebit.setText(""+tbalance);
            tf_interest.setText(""+a[3]);
            tf_current.setText(""+a[0]);
            tf_debit.setText("0");
        }
        db.disconnect();
        */
    }//GEN-LAST:event_c_memberItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         System.out.print("\n kk");
        if(c_last.isSelected())
        {
            System.out.print("\n kk");
            DefaultTableModel model=(DefaultTableModel)table.getModel();
            Vector d=new Vector();
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            String s2[][]=new String[1][9];
            System.out.print("\n kk");
            for (int i=0;i<table.getRowCount();i++)
            {
                    if(i==table.getRowCount()-1)
                    {
                        s2[0][0]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(0));
                        s2[0][1]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(1));
                        s2[0][2]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(2));
                        s2[0][3]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(3));
                        s2[0][4]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(4));
                        s2[0][5]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(5));
                        s2[0][6]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(6));
                        s2[0][7]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(7));
                        s2[0][8]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(8));
                    }
            }
            table t=new table("Last Tran.: "+c_lottery.getSelectedItem()+"-"+c_member.getSelectedItem()+", "+tf_name.getText(),header,s2);
            t.setVisible(true);
        }
        else if(c_complete.isSelected())
        {
            DefaultTableModel model=(DefaultTableModel)table.getModel();
            Vector d=new Vector();
            String header[]={"S.No.","Date","Recurring","Fine","Debit","Credit","Interest","Total Deposite","Balance"};
            String s2[][]=new String[table.getRowCount()][9];
            for (int i=0;i<table.getRowCount();i++)
            {
                    s2[i][0]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(0));
                    s2[i][1]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(1));
                    s2[i][2]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(2));
                    s2[i][3]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(3));
                    s2[i][4]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(4));
                    s2[i][5]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(5));
                    s2[i][6]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(6));
                    s2[i][7]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(7));
                    s2[i][8]=(String)(((Vector)model.getDataVector().elementAt(i)).elementAt(8));
            }
            // String s1[]=null;//(Sttable.getTableHeader().getComponents();
            table t=new table("Monthly Tran.: "+c_lottery.getSelectedItem()+"-"+c_member.getSelectedItem()+", "+tf_name.getText(),header,s2);
            //t.printGradesTable();
            t.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(mainframe, "Select Last or Complete","Error",JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_searchKeyReleased
        // TODO add your handling code here:
        Dbcon db=new Dbcon();
        db.connect();
        //String data[]=null;
        try
        {
                _memberid.clear();
                _membername.clear();
                db.rslt=db.stmt.executeQuery("select memberaccount.member_no,memberinfo.name from memberaccount,memberinfo where memberinfo.member_id=memberaccount.member_id && memberaccount.lottery_no='"+c_lottery.getSelectedItem()+"' && memberinfo.name like '"+tf_search.getText()+"%' && memberaccount.status=1");
                while (db.rslt.next())
                {
                    _memberid.addElement(db.rslt.getString(1));
                    _membername.addElement(db.rslt.getString(2));
                    System.out.println(db.rslt.getString(1)+"     "+db.rslt.getString(2) +"  "+c_lottery.getSelectedItem());
                }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        Dimension d=tf_search.getSize();
        Point p=tf_search.getLocationOnScreen();
        //jw=new JWindow();
        l.setModel(new javax.swing.AbstractListModel() {
            Object[] strings = _membername.toArray();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
            });

        //l.setModel(new javax.swing..ListModel(_membername.toArray()));

        jw.setLocation(p.x,p.y+d.height);
        jw.setSize(d.width,200);
        jw.setVisible(true);
        //jw.show();
        
    }//GEN-LAST:event_tf_searchKeyReleased

    private void tf_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_searchActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_tf_searchActionPerformed

    private void tf_searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tf_searchFocusLost
        // TODO add your handling code here:
        //jw.dispose();
        jw.setVisible(false);
    }//GEN-LAST:event_tf_searchFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_close;
    private javax.swing.JButton bt_date;
    private javax.swing.JButton bt_save;
    private javax.swing.JButton bt_update;
    private javax.swing.JRadioButton c_complete;
    private javax.swing.JRadioButton c_last;
    private javax.swing.JComboBox c_lottery;
    java.util.Vector lottery=new java.util.Vector();
    private javax.swing.JComboBox c_member;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JRadioButton r_update;
    private javax.swing.JTextArea ta_address;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_available;
    private javax.swing.JTextField tf_cdebit;
    private javax.swing.JTextField tf_contact;
    private javax.swing.JTextField tf_current;
    private javax.swing.JTextField tf_date;
    private javax.swing.JTextField tf_debit;
    private javax.swing.JTextField tf_debitupdate;
    private javax.swing.JTextField tf_deposite;
    private javax.swing.JTextField tf_depositeupdate;
    private javax.swing.JTextField tf_fine;
    private javax.swing.JTextField tf_fname;
    private javax.swing.JTextField tf_interest;
    private javax.swing.JTextField tf_name;
    private javax.swing.JTextField tf_person;
    private javax.swing.JTextField tf_recurring;
    private javax.swing.JTextField tf_search;
    // End of variables declaration//GEN-END:variables

}
