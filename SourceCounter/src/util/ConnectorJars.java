package util;

import java.sql.*;

public class ConnectorJars {
	public static void main(String[] args) {
		System.out.println("Hi moon do!");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://tagus.ics.uci.edu:3306/sourcerer?user=sourcerer-public");  
			Statement stm = conn.createStatement();  
			ResultSet rs = stm.executeQuery("SELECT jar_id, name FROM jars WHERE name LIKE '%xerces%'");
//			ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM jars");
//			System.out.println(rs);
//			rs.next();    
		    while (rs.next()) {  
//		    	System.out.println("Count: " + rs.getInt(1) );
			    String name = rs.getString("name");  
			    int id = rs.getInt("jar_id");
			    System.out.println("id: "+id+" name :"+name);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
}
