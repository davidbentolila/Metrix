package br.ufpa.linc.MetriX.csv;

public class NumField implements Field {
	private Double num;
	
	
	
	public NumField(Double num) {
		this.num = num;
	}

	@Override
	public Double getValueN() {
		return num;
	}

	@Override
	public String getValueS() {
		return num.toString();
	}

}
