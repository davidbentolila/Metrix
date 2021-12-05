package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@javax.persistence.Entity
public class Attribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5588634878003219729L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String modifier = null;
	private boolean array;
	
	/*
	 * An Attribute can be associate with many {@link Method}
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Attribute} remove yours {@link Method}s too.
	 *  
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "method_id") 
	private Method method;
	
	/*
	 * An {@link Entity} have many {@link Attribute}
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Attribute} the {@link Entity} which attribute is related.
	 *  
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name = "entity_id") 
	private Entity entity;

	/*
	 * An {@link Attribute} can be a {@link Primitivo}, so this attribute indicates which is {@link Attribute}'s primitive type.
	 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "primitiveType_id")
	private Primitive primitiveType;
	
	/*
	 * An {@link Attribute} can be a {@link Entity}, so this attribute indicates which is {@link Attribute}'s {@link Entity} type.
	 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="entityType_id")
	private Entity entityType;

	@Transient
	private double peso = 0;
	
	public Attribute() {
		primitiveType = null;
		entityType = null;
		modifier = "public";
	}
	
	public Attribute(Entity e) {
		setName(e.getName());
		primitiveType = null;
		entityType = e;
		modifier = "public";
	}
	
	public Attribute(Primitive p) {
		setName(p.getName());
		primitiveType = p;
		entityType = null;
		modifier = "public";
	}

	public double getWeight(String metric) {
		if ( peso == 0){
			if ( getPrimitiveType() != null) peso = getPrimitiveType().getPeso();
	//		else if ( getType().endsWith("[]") || getType().equalsIgnoreCase("java.lang.String") ) peso = 3;
	//		else if ( getType().endsWith("java.lang.Object") ) peso = 6;
	//		else if ( getType().equalsIgnoreCase("java.io.File") ) peso = 10;
			if ( getEntityType() != null ) peso = getEntityType().getMetricsValues().getIS();
			if ( getType().equals("void")) peso = 0;
		}
		return peso;
	}
	

	/**
	 * @return o nome do atributo
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name attribute name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return o {@link Method} que o {@link Attribute} faz parte
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method
	 */
	public void setMetodo(Method method) {
		this.method = method;
	}

	public Integer getId() {
		return id;
	}

	public Primitive getPrimitiveType() {
		return primitiveType;
	}

	public void setPrimitiveType(Primitive primitive) {
		this.primitiveType = primitive;
	}

	public Entity getEntityType() {
		return entityType;
	}

	public void setEntityType(Entity entity) {
		this.entityType = entity;
	}
	
	public String getModifier() {
		return modifier;
	}
	
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public boolean isArray(){
		return array;
	}
	
	public void setArray(boolean array){
		this.array = array;
	}
	
	public String toString() {
		return getType();
	}
	
	public String getType(){
		if ( getEntityType() != null ) return getEntityType().getFullName();
		if ( getPrimitiveType() != null ) return getPrimitiveType().getName();
		return "void";
	}
	
}