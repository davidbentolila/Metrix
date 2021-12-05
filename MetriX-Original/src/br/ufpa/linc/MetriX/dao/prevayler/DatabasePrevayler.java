package br.ufpa.linc.MetriX.dao.prevayler;

import java.io.IOException;
import java.util.List;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.DatabaseStrategy;

/**
 * @author david Date: 26/09/2010
 */
public class DatabasePrevayler implements DatabaseStrategy {

	private Prevayler prevayler;
	private SnapshotTimer snapshot;
	private static DatabasePrevayler database;

	public static synchronized DatabasePrevayler getInstance() {
		if (database == null)
			database = new DatabasePrevayler();
		return database;
	}

	public DatabasePrevayler() {
		PrevaylerFactory factory = new PrevaylerFactory();
		factory.configurePrevalenceDirectory(Configurations.getDBDir());
		factory.configurePrevalentSystem(new APIList());
		try {
			prevayler = factory.create();
		} catch (IOException e) {
			System.out.println("Can't read database.");
			System.err.println("Please delete the folder: " + Configurations.getDBDir());
			System.exit(1);
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Database inconsistence. The Tool database version is diferente of your.");
			System.err.println("Please delete: " + Configurations.getDBDir() + "folder");
			System.exit(1);
			// e.printStackTrace();
		} 
//		catch (Exception e) {
//			new FormChooseDatabase();
//		}
		snapshot = new SnapshotTimer(prevayler);
		snapshot.start();
	}

	public boolean insert(Object o) {
		if ( o instanceof API ) {
			prevayler.execute(new InsertAPI((API) o));
			return true;
		}
		return false;
	}

	public boolean update(Object o) {
		if ( o instanceof API ) {
			prevayler.execute(new UpdateAPI((API) o));
			return true;
		}
		return false;
	}

	public boolean remove(Object o) {
		if ( o instanceof API ) {
			prevayler.execute(new RemoveAPI((API) o));
			return true;
		}
		return false;
	}

	public List<API> getAllAPI() {
		APIList APIlist = ((APIList) prevayler.prevalentSystem());
		APIlist.sort();
		return APIlist.getAPIList();
	}

	public void closeDatabase() {
		snapshot.stopTimer();
		database = null;
	}

	public Object get(Object o, int id) {
		if ( o instanceof API ) getAPI(id);
		else if ( o instanceof Package ) getPackage(id);
		else if ( o instanceof Class || o instanceof Interface ) getEntity(id);
		return null;
	}
	
	private API getAPI(int id){
		APIList APIlist = ((APIList) prevayler.prevalentSystem());
		return APIlist.get(id);
	}

	private Package getPackage(int id){
		APIList APIlist = ((APIList) prevayler.prevalentSystem());
		API a;
		for (int i = 0 ; i < APIlist.size() ; i++) {
			a = APIlist.get(i);
			for (Package p : a.getPackages()) if ( p.getId() == id ) return p;
		}
		return null;
	}
	
	private Entity getEntity(int id){
		APIList APIlist = ((APIList) prevayler.prevalentSystem());
		API a;
		for (int i = 0 ; i < APIlist.size() ; i++) {
			a = APIlist.get(i);
			for (Entity e : a.getAllEntities()) if ( e.getId() == id ) return e;
		}
		return null;
	}
}
