package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.LocalityAttributeAccesses;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.metrics.NumberAccessedVariables;

/**
 *@author david
 *Date: 20/10/2010
 */
public class Lanza2006 extends MetricModel {

	public String getLabel() {
		return "Lanza & Marinescu (2006)";
	}

	public List<Metric> getMetrics() {
		List<Metric> a = new ArrayList<Metric>();
		a.add(new LocalityAttributeAccesses());
		a.add(new NumberAccessedVariables());
		return a;
	}

}
