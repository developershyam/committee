package pack;

import java.util.prefs.Preferences;
import java.sql.*;
import javax.swing.*;
public class RegistryDemo {
    public static final String PREF_KEY = "";
    RegistryDemo(){
		try
		{
			

	   Preferences userPref = Preferences.userRoot();
	   boolean flag=userPref.nodeExists("xieon");
            java.util.Date date=new java.util.Date();
	   String strdate=(date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+date.getDate();
	   Date date1=null;
	   Date date2=Date.valueOf(strdate);
	   if (flag)
	   {
		   userPref=userPref.node("xieon");
		   userPref=userPref.node("gsm-1.0");
		   String s1=userPref.get("date", "date");		   
		   System.out.println("yes"+s1);
		   date1=Date.valueOf(s1);
		   int results = date1.compareTo(date2);
  
		   if(results > 0)
		   {
				System.out.println("First Date is after second");
	   
		   }
		   else {
			   if(results < 0)
				{     
			    System.out.println("First Date is before second");
				JOptionPane.showMessageDialog(null,"This software Trial Version Of One Month For Full Version contact at \nXieon Technologies(xieontech@gmail.com) \n ph. 9928712342, 0141-5112342, 9772220532","Warning",JOptionPane.PLAIN_MESSAGE);
				//JOptionPane.showMessageDialog(null,"This software needs some updation \n so  please immediate contact at \nXieon Technologies(xieontech@gmail.com) \n ph. 9928712342, 0141-5112342, 9772220532","Warning",JOptionPane.PLAIN_MESSAGE);
				System.out.println("First Date is before second");
				System.exit(0);
				}
		   }


	   }
	   else
	   {
		   java.util.Date date11=new java.util.Date();
		   String strdate11=(date.getYear()+1900)+"-"+(date.getMonth()+1+1)+"-"+(date.getDate());
		   userPref=userPref.node("xieon");
		   userPref=userPref.node("gsm-1.0");
		   userPref.put("installed", "1");
		   userPref.put("software", "gsm");
		   userPref.put("version", "1.0");
		   userPref.put("date",strdate11);
		   System.out.println("No");
		}
		
			
	   }catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,""+e);
		}	   
	  
	}
	public static void main(String[] args) {
		new RegistryDemo();
	}
}