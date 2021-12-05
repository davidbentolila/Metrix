package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ConnectorCounter {
	public static void main(String[] args) {    		
		System.out.println("Hi moon do!");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://tagus.ics.uci.edu:3306/sourcerer?user=sourcerer-public");  
			Statement stm = conn.createStatement();  
//			ResultSet rs = stm.executeQuery("SELECT name, COUNT(*) FROM jars INNER JOIN jar_uses ON jars.jar_id = jar_uses.jar_id WHERE name LIKE '%saxon%' GROUP BY jar_uses.jar_id ORDER BY COUNT(*) DESC");
			ResultSet rs = stm.executeQuery("SELECT fqn, COUNT(*) FROM jar_entities INNER JOIN relations ON entity_id=rhs_jeid WHERE jar_id=4093 GROUP BY entity_id ORDER BY COUNT(*) DESC;");
//			System.out.println(rs);
//			rs.next();
		    while (rs.next()) {  
//		    	System.out.println("Count: " + rs.getInt(1) );
			    String col1 = rs.getString(1);
			    String col2 = rs.getString(2);  
			    /*String col3 = rs.getString(3);  
			    /*String col4 = rs.getString(4);  
			    String col5 = rs.getString(5);  */
			    System.out.println("fnq: "+col1+"\tCount:"+col2 /*+"\tCount:"+col3/*+"\tfile_id :"+col4+"\tproject_id :"+col5*/);
			}
		    conn.close(); 
		    System.out.println("ended");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
