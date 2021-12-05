package br.ufpa.linc.MetriX.dao.prevayler;

import java.util.Date;

import org.prevayler.Transaction;

import br.ufpa.linc.MetriX.api.model.API;

/**
 *@author david
 *Date: 26/09/2010
 */
public class InsertAPI implements Transaction {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -512111202944375850L;
	private API api;  
  
	public InsertAPI(API api) {
        this.api = api;  
    }  
  
    public void executeOn(Object businessSystem, Date date) {  
        ((APIList) businessSystem).insert(api);  
    }
}