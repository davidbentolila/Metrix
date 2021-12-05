package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Primitive implements Type, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2322170323019687537L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private type type_;
	
	public Primitive(String name) {
		setName(name);
	}
	
	public Primitive() {
	}
	
	public enum type{ BOOLEAN, CHAR, INT, BYTE, SHORT, LONG, FLOAT, DOUBLE, VOID };
		
	public int getPeso() {
		if ( getType().equals(type.BOOLEAN) ) return 8;
		if ( getType().equals(type.CHAR) ) return 16;
		if ( getType().equals(type.INT) ) return 32;
		if ( getType().equals(type.BYTE) ) return 8;
		if ( getType().equals(type.SHORT) ) return 16;
		if ( getType().equals(type.LONG) ) return 64;
		if ( getType().equals(type.FLOAT) ) return 32;
		if ( getType().equals(type.DOUBLE) ) return 64;
		if ( getType().equals(type.VOID)) return 0;
		return 8;
	}

	public Integer getId() {
		return id;
	}

	public type getType() {
		return type_;
	}

	public void setType(type type_) {
		this.type_ = type_;
	}

	@Override
	public String getName() {
		return getType().toString();
	}

	@Override
	public void setName(String name) {
		if ( name.equalsIgnoreCase( type.BOOLEAN.toString()) ) setType(type.BOOLEAN);
		if ( name.equalsIgnoreCase(type.CHAR.toString()) ) setType(type.CHAR);
		if ( name.equalsIgnoreCase(type.INT.toString()) ) setType(type.INT);
		if ( name.equalsIgnoreCase(type.BYTE.toString()) ) setType(type.BYTE);
		if ( name.equalsIgnoreCase(type.SHORT.toString()) ) setType(type.SHORT);
		if ( name.equalsIgnoreCase(type.LONG.toString()) ) setType(type.LONG);
		if ( name.equalsIgnoreCase(type.FLOAT.toString()) ) setType(type.FLOAT);
		if ( name.equalsIgnoreCase(type.DOUBLE.toString()) ) setType(type.DOUBLE);
		setType(type.VOID);
	}
}