package br.ufpa.linc.MetriX.view.graph;

import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;

/**
 * Using no more dependency graph visualization
 */
public class MyVertex {

	private Entity entity = null;
	private Method method = null;
	private int groupID ;
	private int status = 0; //0 - normal ; 1 - selected ; 2 - neighbor
	
	public MyVertex( Entity entidade){
		this.entity = entidade;
		groupID = 0;
		status = 0;
	}
	
	public MyVertex( Entity entidade , int groupID){
		this.entity = entidade;
		this.groupID = groupID;
		status = 0;
	}
	
	public MyVertex( Entity entidade, Method method){
		this.entity = entidade;
		this.method = method;
		groupID = 0;
		status = 0;
	}
	
	public MyVertex( Entity entidade , Method method, int groupID){
		this.entity = entidade;
		this.method = method;
		this.groupID = groupID;
		status = 0;
	}
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Object getObject(){
		if ( method == null ) return entity;
		return method;
	}
	
	public String getData(){
		if ( method == null ) return entity.getFullName();
//		return entity.getFullName() +":"+ method.getNome();
		return method.getHTMLStructure();//, main.getIlWeight(), main.getIsWeight());//main.getOacWeight(), 
	}
	
	public String getToolTip(){
		if ( method == null ) return entity.getMetricData();//, main.getIlWeight(), main.getIsWeight());//main.getOacWeight(), 
		return "<html>" + method.getHTMLStructure();//, main.getIlWeight(), main.getIsWeight()) + "</html>";//main.getOacWeight(), 
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String toString() {
		if ( method == null ) return entity.getName();
		return method.getName();
	}
}