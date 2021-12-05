package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;

@javax.persistence.Entity
public class Class extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7874983962273746078L;

	public Class() {
		super();
	}
	
	public Class(String name) {
		super();
		setName(name);
	}
	
	public Class(String name, Package package_) {
		super();
		setName(name);
		setPackage(package_);
	}
}