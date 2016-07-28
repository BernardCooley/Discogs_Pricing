package frameworkUtils;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
	
	Connection con = null;
	
	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			String d = null;
			String oS = System.getProperty("os.name");
//			System.out.println(oS);
			if (oS.contains("x")) {
				d = "/";
			} else if(oS.equalsIgnoreCase("Windows 10")) {
				d = "\\";
			}
			Connection con = DriverManager.getConnection("jdbc:sqlite:C:"+d+"Eclipse Workspace Git"+d+"Discogs_Pricing"+d+"matches.sqlite");
			System.out.println("DB Connection successful");
			return con;
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
