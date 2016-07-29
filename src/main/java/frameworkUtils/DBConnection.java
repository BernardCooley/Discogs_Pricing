package frameworkUtils;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
	
	Connection con = null;
	
	public static Connection dbConnector() {
		try {
			DriverManager.registerDriver(new org.sqlite.JDBC());
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:matches.sqlite");
//			System.out.println("DB Connection successful");
			return con;
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
