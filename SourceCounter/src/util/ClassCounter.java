package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ClassCounter {
	private static Connection conn;
	private static Statement stm;

	public static void main(String[] args) {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));  
		System.out.println("Iniciando!");
		conect();
		String jarQuery =null;
		try {
			System.out.print("Qual biblioteca? ");
			jarQuery = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,Integer> jars = getJars(jarQuery);
		showJars(jars);
		String jarSelected = null;
		try {
			System.out.print("Qual jar? ");
			jarSelected = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<Integer,String> classes = countClasses(jarSelected, jars);
//	    System.out.println(classes);
		int jarId = jars.get(jarSelected);
		long total =0;
		long total2 =0;
	    for (Integer c : classes.keySet()) {
//			System.out.println(c);
//			System.out.println(classes.get(c));
//			System.out.println();
	    	long num = countEntity(c,jarId);
//	    	long num2 = countEntity2(c,jarId);
//	    	long num3 = countEntity3(c,jarId);
	    	System.out.println("classe: "+classes.get(c)+":\t"+num);
	    	total2 += num;
	    	Map<Integer,String> methods = countMethods(c);
	    	for (Integer m: methods.keySet()) {
	    		long qtd = countEntity(m,jarId);
	    		total += qtd;
	    		if(qtd==0)
	    			System.out.println(">>método: "+methods.get(m)+":\t"+qtd);
	    		else 
	    			System.err.println(">>método: "+methods.get(m)+":\t"+qtd);
				
			}
	    	System.out.println();
			System.out.println("Parcial: "+total+"; "+ total2);
		}
		System.out.println("Total: "+total+ "; "+total2);
		close();
	}


	private static Map<Integer, String> countMethods(Integer c) {
		Map<Integer,String> classes = new HashMap<Integer,String>();
		ResultSet rs;
		try {
			rs = stm.executeQuery("SELECT entity_id, fqn FROM jar_entities, jar_relations WHERE lhs_jeid=entity_id AND relation_type='INSIDE'AND rhs_jeid="+c+" AND entity_type='METHOD'");
			while (rs.next()) {  
			    String name = rs.getString("fqn");  
			    int id = rs.getInt("entity_id");
			    classes.put(id, name);
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	    return classes;
	}

	private static Map<Integer,String> countClasses(String jarSelected, Map<String, Integer> jars) {
		int jarId = jars.get(jarSelected);
		Map<Integer,String> classes = new HashMap<Integer,String>();
		ResultSet rs;
		try {
			rs = stm.executeQuery("SELECT entity_id, fqn FROM jar_entities WHERE jar_id="+jarId+" AND (entity_type='CLASS' OR entity_type='INTERFACE')");
			while (rs.next()) {  
			    String name = rs.getString("fqn");  
			    int id = rs.getInt("entity_id");
			    classes.put(id, name);
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	    return classes;
	}
	
	
	
	private static long countEntity(Integer c, int jarId) {
		ResultSet rs;
		long num = -1;
		try {
			rs = stm.executeQuery("SELECT count(*) FROM relations WHERE rhs_jeid="+c);
			while (rs.next()) {  
			    num = rs.getLong(1);
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

//	private static long countEntity2(Integer c, int jarId) {
//		ResultSet rs;
//		long num = -1;
//		try {
//		rs = stm.executeQuery("SELECT count(*) FROM jar_relations WHERE rhs_jeid="+c);
//			while (rs.next()) {  
//			    num = rs.getLong(1);
//			}
//	    } catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return num;
//	}
//	
//	private static long countEntity3(Integer c, int jarId) {
//		ResultSet rs;
//		long num = -1;
//		try {
//			rs = stm.executeQuery("SELECT count(*) FROM jar_relations WHERE rhs_jeid="+c+" AND jar_id !="+jarId);
//			while (rs.next()) {  
//			    num = rs.getLong(1);
//			}
//	    } catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return num;
//	}

	private static void showJars(Map<String, Integer> jars) {
		Iterator<Entry<String, Integer>> jarsIt = jars.entrySet().iterator();
		int i = 1;
		while (jarsIt.hasNext()){
			Entry<String, Integer> e= jarsIt.next();
			System.out.println("#" + i +": " +e);
			++i;
		}
	}

	private static void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	private static Map<String, Integer> getJars(String jarQuery) {
		Map<String, Integer> jars = new HashMap<String, Integer>();
		ResultSet rs;
		try {
			rs = stm.executeQuery("SELECT jar_id, name FROM jars WHERE name LIKE '%"+jarQuery+"%'");
			while (rs.next()) {  
			    String name = rs.getString("name");  
			    int id = rs.getInt("jar_id");
			    jars.put(name, id);
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return jars;
	}

	private static void conect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		try {
			conn = DriverManager.getConnection("jdbc:mysql://tagus.ics.uci.edu:3306/sourcerer?user=sourcerer-public");  
			stm = conn.createStatement();  
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
