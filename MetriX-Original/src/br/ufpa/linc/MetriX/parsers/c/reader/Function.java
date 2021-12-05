package br.ufpa.linc.MetriX.parsers.c.reader;

import java.util.List;

public class Function {
	private String type;
	private String name;
	private int pointerLevel;
	/**
	 * @return the pointerLevel
	 */
	public int getPointerLevel() {
		return pointerLevel;
	}

	/**
	 * @param pointerLevel the pointerLevel to set
	 */
	public void setPointerLevel(int pointerLevel) {
		this.pointerLevel = pointerLevel;
	}

	private List<Parameter>atributes;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getParameters() {
		return atributes;
	}

	public void setAtributes(List<Parameter> atributes) {
		this.atributes = atributes;
	}

	@Override
	public String toString() {
		return "Function [name=" + name
				+ ", type=" + type + " atributes=" + atributes + "]";
	}
	
}
