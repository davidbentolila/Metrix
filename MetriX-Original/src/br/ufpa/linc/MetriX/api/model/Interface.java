package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ManyToMany;

@javax.persistence.Entity
public class Interface extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3420418803558517025L;

	@ManyToMany(mappedBy="interfaces")
	private List<Entity> implementedBy = null;
	
	public Interface() {
		super();
	}
	
	public Interface(String name) {
		super();
		setName(name);
	}
	
	public Interface(String name, Package package_) {
		super();
		setName(name);
		setPackage(package_);
	}
	
	public List<Entity> getImplementedBy() {
		return implementedBy;
	}

	public void setImplementedBy(List<Entity> implementedBy) {
		this.implementedBy = implementedBy;
	}
}