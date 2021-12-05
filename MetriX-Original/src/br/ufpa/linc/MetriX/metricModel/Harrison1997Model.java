package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.metrics.NumberPublicAttribute;

/**
 *@author david
 *Date: 20/10/2010
 */
public class Harrison1997Model extends MetricModel {

	public String getLabel() {
		return "Harrison et al 1997";
	}

	public List<Metric> getMetrics() {
		List<Metric> a = new ArrayList<Metric>();
		a.add(new NumberPublicAttribute());
		return a;
	}

}
