package br.ufpa.linc.MetriX.analysis;

import java.util.ArrayList;

/**
 *@author david
 *Date: 08/04/2010
 */
@SuppressWarnings("serial")
public class MyList extends ArrayList<Double> {
	
	public double getSum() {
		double total = 0;
   
		for (Double d : this)
			total += d;

		return total;
	}

	public double getAverage() {
		return getSum() / this.size();
	}

	public double getMax() {
		double max = Double.MIN_VALUE;
		for (double d : this) max = d > max ? d : max;
		return max;
	}
	
	public double getMin() {
		double min = Double.MAX_VALUE;
		for (double d : this) min = d < min ? d : min;
		return min;
	}
	
	public double getMedian() {
		int n = 0;

		if (this.size() % 2 == 0) {
			n = this.size() / 2;
			return (this.get(n - 1) + this.get(n)) / 2;
		}

		n = (this.size() - 1) / 2 + 1;
		return this.get(n - 1);
	}

	public double getStandardDeviation() {
		double avg = getAverage();
		double totalp = 0;
		for (Double d : this) totalp += Math.pow((d - avg), 2);

		return Math.sqrt(totalp / (this.size() -1));
	}
}
