package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.AttributeHidingFactor;
import br.ufpa.linc.MetriX.metrics.Metric;

/**
 *@author david
 *Date: 21/10/2010
 */
public class BritoEAbreuAndMelo1996Model extends MetricModel {

	public String getLabel() { return "Morris 1988"; }
	
	public List<Metric> getMetrics() {
		List<Metric> a = new ArrayList<Metric>();
		a.add(new AttributeHidingFactor());
		return a;
	}	
}
