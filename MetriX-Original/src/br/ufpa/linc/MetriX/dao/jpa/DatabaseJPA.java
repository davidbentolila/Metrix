package br.ufpa.linc.MetriX.dao.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.MetricsValues;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.api.model.Primitive;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.DatabaseStrategy;


public class DatabaseJPA implements DatabaseStrategy {

	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private static DatabaseJPA database = null;
	
	private DatabaseJPA() {
		java.lang.Class<?>[] classes = new java.lang.Class<?>[]{API.class,Attribute.class,Class.class,Entity.class,Interface.class,Method.class,MetricsValues.class,Package.class,Primitive.class};
//		emf = Persistence.createEntityManagerFactory("metrixPU");
		emf = DynamicPersistenceUnits.createEMF(classes);
		em = emf.createEntityManager();
	}
	
	public synchronized static DatabaseJPA getInstance() {
		if (database == null) database = new DatabaseJPA();
		return database;
	}	
	
	public boolean insert(Object o) {
		System.out.println(Configurations.getString("message.dao.inserting"));
		boolean ok = false;
		try {
			em.getTransaction().begin();
			em.persist(o);
			em.getTransaction().commit();
			ok = true;
			System.out.println(Configurations.getString("message.dao.inserted"));
		} catch (Exception e) {
			System.out.println(e);
			ok = false;
			Logger.getLogger(getClass().getName()).log(Level.ALL, "exception caught", e);
//			em.getTransaction().rollback();
			/////////////////////////////AudioDevice.device.openChannel( getClass().getResourceAsStream("/sounds/error.wav"));
		}
		return ok;
	}
	
	public boolean remove(Object o) {
		if ( o instanceof API ) return removeAPI(((API)o).getId());
		return false;
	}
	
	public boolean removeAPI(long id) {
		System.out.println(Configurations.getString("message.dao.remove"));

		boolean ok = false;
		try {
			em.getTransaction().begin();
			em.remove(getPessoa(id));
			em.getTransaction().commit();
			System.out.println(Configurations.getString("message.dao.removed"));
			ok = true;
		} catch (Exception e) {
			ok = false;
			Logger.getLogger(getClass().getName()).log(Level.ALL,"exception caught", e);
			em.getTransaction().rollback();
			Configurations.playErrorSound();
		}
		return ok;
	}

	public boolean update(Object o) {
		System.out.println(Configurations.getString("message.dao.update"));

		boolean ok = false;
		try {
			em.getTransaction().begin();
			em.merge(o);
			em.getTransaction().commit();
			System.out.println(Configurations.getString("message.dao.updated"));
			ok = true;
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ALL, "exception caught", e);
			em.getTransaction().rollback();
			Configurations.playErrorSound();
		}
		return ok;
	}

//	@SuppressWarnings("unchecked")
//	private List<Object> getAll(String table) {
//		System.out.println(Configurations.getString("message.dao.list") + " " + table);
//		return em.createQuery("select a from " + table + " a").getResultList();
//	}
	
	public List<API> getAllAPI() {
		return em.createQuery("SELECT a FROM API a order by a.nome", API.class).getResultList();
//		return em.createQuery("SELECT a FROM API a order by a.nome", API.class).setLockMode(LockModeType.READ).getResultList();
	}
	
	public Object get(Object o, int id) {
		if ( o instanceof API ) return getAPI(((API)o).getId());
		return null;
	}
	
	private API getAPI(long id) {
		return em.find(API.class, id);
	}
	
	public API getPessoa(long id) {
		return em.find(API.class, id);
	}
	
	public void closeDatabase() {
		if ( em.isOpen() ) em.close();
		if ( emf.isOpen() ) emf.close();
		database = null;
	}
}
