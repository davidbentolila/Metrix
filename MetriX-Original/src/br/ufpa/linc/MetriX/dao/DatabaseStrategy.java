package br.ufpa.linc.MetriX.dao;

import java.util.List;

import br.ufpa.linc.MetriX.api.model.API;

public interface DatabaseStrategy {

	public boolean insert(Object o);
	public boolean remove(Object o);
	public boolean update(Object o);
	public Object get(Object o, int id);
	public void closeDatabase();
	public List<API> getAllAPI();	
}
