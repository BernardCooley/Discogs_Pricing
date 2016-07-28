package Discogs_Price.Discogs_Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import frameworkUtils.DBConnection;

public class WriteToDatabase {

	private static Connection con = DBConnection.dbConnector();
	private static PreparedStatement pst = null;
	
	public static boolean doesUrlExist(String url) {
		String sqlSelect = "SELECT * FROM matches";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				if (url.equals(rs.getString(1))) {
					return true;
				}
			}
		} catch (SQLException e) {
		}
		return false;
	}
	
	public static void writeToDatabase(String columnName, String value) {
		String sqlInsert = "INSERT INTO matches (?) VALUES (?)";
		
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setString(1, columnName);
			pst.setString(2, value);
			pst.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	
	
	
	
}
