package pack;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
class Login extends JDialog implements ActionListener
{
	JLabel username,password;
	JLabel label;
	JTextField usertext;
	JPasswordField passwordtext;
	JButton ok;	
	String usernames="";
        String passwords="";
	Login(JFrame parent,String name,Dimension screen)
	{
		super(parent,true);
        setLayout(null);
		setSize(255,170);
		
		setLocation((int)screen.getWidth()/2-125,(int)screen.getHeight()/2-150);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
      	public void windowClosing(WindowEvent e){UnloadWindow();}});
		
		label=new JLabel("  Enter Correct Username And Password");
		label.setBackground(Color.blue);
		label.setForeground(Color.white);
		label.setOpaque(true);		
		add(label);
		label.setBounds(0,0,750,40);
		username=new JLabel("UserName : ");
		add(username);
		username.setBounds(10,50,80,20);
		password=new JLabel("Password : ");
		password.setBounds(10,80,80,20);
		add(password);

		usertext=new JTextField();
		add(usertext);
		usertext.setBounds(100,50,130,20);

		passwordtext=new JPasswordField();
		add(passwordtext);
		passwordtext.setBounds(100,80,130,20);
		passwordtext.addActionListener(this);
		ok=new JButton("Login In",new ImageIcon(getClass().getResource("images/switch.gif")));
		add(ok);
		ok.setBounds(130,110,100,20);
		ok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(usertext.getText().equals(""))
                {
                            JOptionPane.showMessageDialog(this,"Enter UserName !","Login Failed" ,JOptionPane.ERROR_MESSAGE);
                            return;
                }

		if(passwordtext.getText().equals(""))
                {
                            JOptionPane.showMessageDialog(this,"Enter Password !","Login Failed" ,JOptionPane.ERROR_MESSAGE);
                            return;
                }
		try
		{
                    Dbcon db=new Dbcon();
                    db.connect();
                    db.rslt=db.stmt.executeQuery("select password from login where username='"+usertext.getText()+"'");
                    db.rslt.next();
                    if(passwordtext.getText().equals(db.rslt.getString(1)))
                    {
                        usernames=usertext.getText();
                        passwords=passwordtext.getText();
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"Username and Password did not matched","Login Failed" ,JOptionPane.ERROR_MESSAGE);
                    }
                    
                    db.disconnect();
                }
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this,"You Can Not Login !","Error" ,JOptionPane.ERROR_MESSAGE);
			return;
                }
	}
	protected void UnloadWindow()
	{
		try 
		{
	    	int reply = JOptionPane.showConfirmDialog(this,
	    	                                          "Are you sure you want to exit?",
	    	                                          "GSM 1.0" ,
	    	                                          JOptionPane.YES_NO_OPTION,
	    	                                          JOptionPane.WARNING_MESSAGE);
			// If the confirmation was affirmative, handle exiting.
			if (reply == JOptionPane.YES_OPTION) 
			{
		     ///		conn.close();
		    	setVisible(false); // hide the Frame
		    	dispose();         // free the system resources
		    	System.exit(0);    // close the application
			}
		}
		catch(Exception e) {}
	}
	
}
