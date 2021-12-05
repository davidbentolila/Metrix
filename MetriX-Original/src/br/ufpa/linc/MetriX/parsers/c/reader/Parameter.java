package br.ufpa.linc.MetriX.parsers.c.reader;

public class Parameter {
	private String type;
	private String name;
	private int pointerLevel;
	
	public int getPointerLevel() {
		return pointerLevel;
	}
	public void setPointerLevel(int pointerLevel) {
		this.pointerLevel = pointerLevel;
	}
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
	@Override
	public String toString() {
		return "Attribute [name=" + name + ", pointerLevel=" + pointerLevel
				+ ", type=" + type + "]";
	}
	
}
