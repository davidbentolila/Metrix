package br.ufpa.linc.MetriX.dao;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.jpa.DatabaseJPA;
import br.ufpa.linc.MetriX.dao.prevayler.DatabasePrevayler;


public class Database {

	private static DatabaseStrategy database;
 
    public synchronized static DatabaseStrategy getInstance() {
    	if ( database == null )
	    	database = Configurations.getDatabaseConfiguration()[0].equals(MetriX.VendorDB.Prevayler.toString()) ? DatabasePrevayler.getInstance() : DatabaseJPA.getInstance();
//	    	database = DatabaseJPA.getInstance();
    	
    	return database;
	}
    
	public static void closeDatabase() {
		System.out.println("Closing database");
		if ( database == null ) return;
		database.closeDatabase();
		database = null;
	}
}
