package Discogs_Price.Discogs_Price;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static void writeNewRecordToDatabase(String value) {
		String sqlInsert = "INSERT INTO matches VALUES (?,'N/A','No','No')";
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setString(1, value);
			pst.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public static void updateDatabase(String value) {
		String sqlUpdate = "UPDATE matches SET matched='Yes' WHERE url=?";
		
		try {
			pst = con.prepareStatement(sqlUpdate);
			pst.setString(1, value);
			pst.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	public static ArrayList<String> getOneColumnFromDatabase(String columnName, ArrayList<String> conditionsList) {
		ArrayList<String> columnContents = new ArrayList<String>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ? from matches WHERE ");
		
		
		
		
		String sqlSelect = "SELECT ? from matches";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				columnContents.add(rs.getString(1));
			}
		} catch (SQLException e) {
		}
		return columnContents;
	}
	
	
	
	
	
	
	
}
