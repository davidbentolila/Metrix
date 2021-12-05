package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.CouplingBetweenObjectClasses;
import br.ufpa.linc.MetriX.metrics.DepthOfInheritanceTree;
import br.ufpa.linc.MetriX.metrics.Metric;

/**
 *@author david
 *Date: 20/10/2010
 */
public class CK1994Model extends MetricModel {

	public String getLabel() {
		return "Chidamber and Kemerer 1994";
	}

	public List<Metric> getMetrics() {
		List<Metric> a = new ArrayList<Metric>();
		a.add(new CouplingBetweenObjectClasses());
		a.add(new DepthOfInheritanceTree());
		return a;
	}

}
