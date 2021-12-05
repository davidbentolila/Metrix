package br.ufpa.linc.MetriX.dao.prevayler;

import java.util.Date;

import org.prevayler.Transaction;

import br.ufpa.linc.MetriX.api.model.API;

/**
 *@author david
 *Date: 02/11/2010
 */
public class UpdateAPI  implements Transaction {  
     
	/**
	 * 
	 */
	private static final long serialVersionUID = -5922728133119869924L;
	private API api;  
  
    public UpdateAPI(API api) {  
        this.api = api;  
    }  
  
    public void executeOn(Object businessSystem, Date date) {  
        ((APIList) businessSystem).update(api);  
    }
}