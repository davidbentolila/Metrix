package br.ufpa.linc.MetriX.dao.prevayler;

import java.util.Date;

import org.prevayler.Transaction;

import br.ufpa.linc.MetriX.api.model.API;

/**
 * @author david Date: 12/11/2010
 */
public class RemoveAPI  implements Transaction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1537061386232100897L;
	private API api;
	
	public RemoveAPI(API api) {
		this.api = api;
	}
	
	public void executeOn(Object businessSystem, Date date) {
		((APIList) businessSystem).remove(api);
	}
}
