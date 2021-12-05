package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectorProjects {
	public static void main(String[] args) {
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
			ResultSet rs = stm.executeQuery("SELECT entity_id, entity_type, fqn, file_id, name FROM entities, projects WHERE entity_id <= 10000 AND projects.project_id = 1 AND projects.project_id = entities.project_id");
//			ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM entities WHERE entity_id <= 10000");
//			System.out.println(rs);
//			rs.next();
		    while (rs.next()) {  
//		    	System.out.println("Count: " + rs.getInt(1) );
			    String col1 = rs.getString(1);
			    String col2 = rs.getString(2);  
			    String col3 = rs.getString(3);  
			    String col4 = rs.getString(4);  
			    String col5 = rs.getString(5);  
			    System.out.println("entity_id: "+col1+"\tentity_type :"+col2 +"\tfnq :"+col3+"\tfile_id :"+col4+"\tproject_id :"+col5);
			}
		    conn.close(); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}    		
		System.out.println("Hi moon do!");
	}
}
