package counter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
	private Connection conn;
	private Statement stm;
	public boolean connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		try {
			conn = DriverManager.getConnection("jdbc:mysql://tagus.ics.uci.edu:3306/sourcerer?user=sourcerer-public");
			stm = conn.createStatement();  
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} 
		return true;
	}
	
	public Statement getStatement() {
		return stm;
	}

	public Connection getConnection(){
		return conn;
	}
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
