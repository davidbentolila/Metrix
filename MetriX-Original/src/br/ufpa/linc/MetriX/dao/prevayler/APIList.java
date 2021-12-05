package br.ufpa.linc.MetriX.dao.prevayler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufpa.linc.MetriX.api.model.API;

/**
 * @author david Date: 26/09/2010
 */
public class APIList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7277793629187318340L;
	private List<API> apiList = new ArrayList<API>();

	protected void insert(API api) {
		api.setId(System.currentTimeMillis());
		apiList.add(api);
	}

	protected void update(API api) {
//		apiList.set(api.getId(), api);
		for( int i=0; i < size() ; i++) 
			if (api.getId() == get(i).getId()) apiList.set(i, api);
	}

	protected void remove(API api) {
//		apiList.remove(api.getId());
		for (API a : apiList)
			if (api.getId() == a.getId())
				apiList.remove(a);
	}

	public API get(int i) {
		return apiList.get(i);
	}
	
	public List<API> getAPIList(){
		return apiList;
	}

	public int size() {
		return apiList.size();
	}

	public void sort() {
		Collections.sort(apiList);
	}
}
