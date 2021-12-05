package counter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class EntityCounter {
	private long jarId;
	private Set<String> filterType, filterRealtion;
	private Map<String,Long> entitiesCache;
	private Map<Long,Long> countCache;
	private Connect connect;
	public EntityCounter(long jarId, Connect connect){
		this.jarId = jarId;
		this.filterType = new HashSet<String>();
		this.filterRealtion = new HashSet<String>();
		entitiesCache = null;
		countCache = new HashMap<Long,Long>();
		this.connect = connect;
	}
	public EntityCounter(String jar, Connect connect){
		this(0,connect);
		ResultSet rs;
		Statement stm = connect.getStatement();
		try {
			rs = stm.executeQuery("SELECT jar_id FROM jars WHERE name LIKE '%"+jar+"%'");
			if (rs.next()) {  
			    jarId = rs.getLong("jar_id");
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public long count(long entityId){
		long num=0;
		if(countCache.containsKey(entityId)){
			num=countCache.get(entityId);
		}
		else{
			ResultSet rs;
			try {
				Statement stm=connect.getStatement();
				//TODO: Suports filter of relations types
				rs = stm.executeQuery("SELECT count(*) FROM relations WHERE rhs_jeid="+entityId);
				while (rs.next()) {  
				    num = rs.getLong(1);
				}
		    } catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return num;
	}
	
	public long count(String entity){
		if(entitiesCache== null){
			makeEntitiesCache();
		}
		if(entitiesCache.containsKey(entity)){
			Long c = entitiesCache.get(entity);
			return count(c);
		}
		else {
			return -1;
		}
	}
	
	private void makeEntitiesCache() {
		entitiesCache = new HashMap<String, Long>();
		ResultSet rs;
		Statement stm = connect.getStatement();
		try {
			//TODO: suports filter of entities;
			rs = stm.executeQuery("SELECT entity_id, fqn FROM jar_entities WHERE jar_id="+jarId+" AND (entity_type='CLASS' OR entity_type='INTERFACE')");
			while (rs.next()) {  
			    String name = rs.getString("fqn");  
			    Long id = rs.getLong("entity_id");
			    entitiesCache.put(name, id);
			}
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,Long> countAll(){
		if(entitiesCache== null){
			makeEntitiesCache();
		}
		HashMap<String, Long> counts = new HashMap<String,Long>();
		Iterator<Entry<String, Long>> it = entitiesCache.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Long> e = it.next();
			System.out.println("#lendo"+e.getKey());
			counts.put(e.getKey(), count(e.getValue()));
		}
		return counts;
	}
	
	public Map<String, Long> getEntities() {
		if(entitiesCache ==null){
			makeEntitiesCache();
		}
		return new HashMap<String, Long>(entitiesCache);
	}
	
	public boolean hasEntity(String entity){
		if(entitiesCache ==null){
			makeEntitiesCache();
		}
		return entitiesCache.containsKey(entity);
	}
	
	public Set<String> getFilterType() {
		return filterType;
	}
	
	public void setFilterType(Set<String> filterType) {
		clearCaches();
		this.filterType = filterType;
	}
	
	public Set<String> getFilterRealtion() {
		return filterRealtion;
	}
	
	public void setFilterRealtion(Set<String> filterRealtion) {
		clearCaches();
		this.filterRealtion = filterRealtion;
	}
	
	public long getJarId() {
		return jarId;
	}
	
	public void clearCaches() {
		entitiesCache = null;
		countCache = new HashMap<Long, Long>();
	}

	public Map<String, Long> getJars(String s){
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
