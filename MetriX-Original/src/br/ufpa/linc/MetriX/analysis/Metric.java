package br.ufpa.linc.MetriX.analysis;

//
public class Metric implements Comparable<Metric> {

	private int frequency;
	private double value;
	
	public Metric( double value ) {
		frequency = 1;
		this.value = value;
	}
	
	public Metric() {
		frequency = 0;
		this.value = 0;
	}

	public int getFrequency() {
		return frequency;
	}

	public double getValue() {
		return value;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public int compareTo(Metric m) {
		if ( value < m.getValue() )	return -1;
		if ( value > m.getValue() ) return 1;
		return 0;
	}
}