package br.ufpa.linc.MetriX.csv;

public class StringField implements Field {
	private String string;
	
	

	public StringField(String string) {
		this.string = string;
	}

	@Override
	public Double getValueN() {
		return Double.valueOf(string);
	}

	@Override
	public String getValueS() {
		return string;
	}

}
