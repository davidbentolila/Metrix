package br.ufpa.linc.MetriX.metrics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;

class BCount {
	private String api;
	private Map<String,Long> entidades;
	private Connection conn;
	private Statement stm;
	public BCount(String api) {
		super();
		this.api = api;
		this.entidades = new HashMap<String, Long>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/ossnetwork", "root", "123456");
			stm = conn.createStatement();  
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(2);
		}
		ResultSet rs;
		try {
			if(api.endsWith(".jar")) api= api.substring(0, api.length()-4);
			rs = stm.executeQuery("SELECT p.PAR_ID FROM ossnetwork.projetos p WHERE p.PRJ_NOME like '%"+api+"%'");
			List<Long> projects = new LinkedList<Long>();
			while (rs.next()) {  
			    long count = rs.getLong(1);
			    projects.add(count);
			}
			for(long prj_id:projects){
				rs = stm.executeQuery("SELECT sf.file, count(*) FROM svnfiles sf,  svnchangedfiles sc WHERE sf.FILE like '%.java' AND sc.SVN_BUG is not null AND sc.svn_file = sf.id AND sf.PRJ_ID = "+prj_id+" GROUP BY sf.ID ORDER BY sf.ID");
				while (rs.next()) {  
					String file = rs.getString(1);
				    long count = rs.getLong(2);
				    entidades.put(file, count);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public Map<String, Long> getEntidades() {
		return entidades;
	}
	public void setEntidades(Map<String, Long> entidades) {
		this.entidades = entidades;
	}
}

public class BugCount extends Metric {

	private BCount cache=null;

	
	@Override
	public String getLabel() {
		return "BugCount";
	}

	@Override
	public Double getValue(API api) {
		double count = api.getMetricsValues().getBugCount();
		if ( count == 0 )
			for (Package p : api.getPackages()) count += getValue(p);
		return count;
	}

	@Override
	public Double getValue(Package p) {
		double count = p.getMetricsValues().getBugCount();
		if ( count == 0 )
			for (Entity e : p.getEntities()) count += getValue(e);
		return count;
	}

	@Override
	public Double getValue(Entity e) {
		double count = e.getMetricsValues().getBugCount();
		if ( count == 0 ){
//			long num = 0;
//			ResultSet rs;
			if(cache==null){
				cache = new BCount(e.getPackage().getAPI().getNome());
			}
			else if(cache.getApi() != e.getPackage().getAPI().getNome()){
				cache = new BCount(e.getPackage().getAPI().getNome());
			}
			String theName = e.getFullName().replace('.', '/');
			for(Entry<String, Long> s:cache.getEntidades().entrySet()){
				if(s.getKey().indexOf(theName)>=0) {
					count = (double)s.getValue();
					break;
				}
			}
			if(count<0)count = 0;
//			try {
//				rs = stm.executeQuery("SELECT sf.id, sf.file, count(*) FROM svnfiles sf,  svnchangedfiles sc WHERE sf.FILE like '%"+theName+".java' AND sc.SVN_BUG is not null AND sc.svn_file = sf.id GROUP BY sf.ID ORDER BY sf.ID  LIMIT 0,1000");
//				while (rs.next()) {  
//				    num = rs.getLong(3);
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			count = (double)num;
		}
		return count;
	}

	@Override
	public Double getValue(Method m) {
		return 0.0;
	}

	@Override
	public void setValue(API api, double d) {
		api.getMetricsValues().setBugCount(d);
	}

	@Override
	public void setValue(Package p, double d) {
		p.getMetricsValues().setBugCount(d);
	}

	@Override
	public void setValue(Entity e, double d) {
		e.getMetricsValues().setBugCount(d);
	}

	@Override
	public void setValue(Method m, double d) {
		m.getMetricsValues().setBugCount(d);
	}

}
