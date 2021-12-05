package br.ufpa.linc.MetriX.dao.jpa;

import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.HibernatePersistence;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.configurations.Configurations;


public class DynamicPersistenceUnits {

	public static EntityManagerFactory createEMF(Class<?>[] entityClasses) {
		
		EntityManagerFactory emf = null;
		String[] database = Configurations.getDatabaseConfiguration();
		
		Ejb3Configuration ejb3conf = new Ejb3Configuration();

		ejb3conf.setProperty("hibernate.hbm2ddl.auto", "update");
	    ejb3conf.setProperty("hibernate.format_sql", "true");
	    ejb3conf.setProperty("hibernate.show_sql", "false");
	    ejb3conf.setProperty("org.hibernate.SQL","DEBUG");
	    ejb3conf.setProperty(HibernatePersistence.TRANSACTION_TYPE, "RESOURCE_LOCAL");
		
		if ( database[0].equals(MetriX.VendorDB.Derby.toString()) ) {
			ejb3conf.setProperty("hibernate.connection.url","jdbc:derby:"+ Configurations.getDBDir() + System.getProperty("file.separator") + database[3]+";create=true");
			ejb3conf.setProperty("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
			ejb3conf.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
		}
		else if (database[0].equals(MetriX.VendorDB.MySQL.toString())) {
			ejb3conf.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			ejb3conf.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			ejb3conf.setProperty("hibernate.connection.url","jdbc:mysql://"+ database[1] +":" + database[2] + "/" + database[3]);
			ejb3conf.setProperty("hibernate.connection.username",database[4]);
			ejb3conf.setProperty("hibernate.connection.password", database[5]);
		}
//		else if ( database[0].equals(MetriX.VendorDB.PostgreeSQL.toString())){
//			ejb3conf.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//			ejb3conf.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
//			ejb3conf.setProperty("hibernate.connection.url", "jdbc:postgresql://"+ database[1] +":" + database[2] + "/" + database[3]);
//			ejb3conf.setProperty("hibernate.connection.username",database[4]);
//			ejb3conf.setProperty("hibernate.connection.password", database[5]);
//		}
		else if ( database[0].equals(MetriX.VendorDB.HSQLDB.toString())){
			ejb3conf.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
			ejb3conf.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
			ejb3conf.setProperty("hibernate.connection.url", "jdbc:hsqldb:" + Configurations.getDBDir() + System.getProperty("file.separator") + database[3]);
			ejb3conf.setProperty("hibernate.connection.username","sa");
		}

		for (int i = 0; i < entityClasses.length; i++) {
			assert entityClasses[i] != null;
			ejb3conf.addAnnotatedClass(entityClasses[i]);
		}
		
		try{
			emf = ejb3conf.buildEntityManagerFactory();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("try again. if error persist, please delete " + Configurations.getAppDir() + System.getProperty("file.separator") + "config.properties" + " file");
		}
		
		return emf;
	}
}
