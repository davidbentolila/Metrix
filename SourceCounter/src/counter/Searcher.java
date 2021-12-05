package counter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Searcher {
	
	public static Map<String, Long> getJars(String s, Connect connect){
		Map<String, Long> jars = new HashMap<String, Long>();
		ResultSet rs;
		Statement stm = connect.getStatement();
		try {
			rs = stm.executeQuery("SELECT jar_id, name FROM jars WHERE name LIKE '%"+s+"%'");
			while (rs.next()) {  
			    String name = rs.getString("name");  
			    Long id = rs.getLong("jar_id");
			    jars.put(name, id);
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return jars;
	}
}
