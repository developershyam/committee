package pack;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
class Dbcon 
{
	String url;
	String dbName ;
	String driver ;
	String userName;
	String password;
        //String password ="xieon@2012";
	Connection conn=null;
	Statement stmt=null;
	ResultSet rslt=null;

	Dbcon()
	{
            try {
                Properties prop = new Properties();
                prop.load(new FileInputStream("src/config.properties"));
                url=prop.getProperty("db.url");
                dbName=prop.getProperty("db.name");
                driver=prop.getProperty("db.driver");
                userName=prop.getProperty("db.user.name");
                password=prop.getProperty("db.user.password");
                System.out.println("Properties successfully loaded");

            } catch (Exception e) {
                e.printStackTrace();
            }
	}
	public void connect()
	{
		try
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
			stmt=conn.createStatement();
                        conn.setAutoCommit(false);

                          //STEP 5: Execute a query to create statment with
                          // required arguments for RS example.
                          System.out.println("Creating statement...");
                          stmt = conn.createStatement(
                                               ResultSet.TYPE_SCROLL_INSENSITIVE,
                                               ResultSet.CONCUR_UPDATABLE);
			System.out.println("Database Connected...");
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public void disconnect()
	{
		try
		{
			stmt.close();
			conn.close();
			System.out.println("Database Dis-Connected...");
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

	}
}
