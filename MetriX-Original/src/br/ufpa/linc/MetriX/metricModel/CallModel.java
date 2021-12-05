package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.Calls;
import br.ufpa.linc.MetriX.metrics.Metric;

public class CallModel extends MetricModel {

	private List<Metric> metrics;

	public String getLabel() {
		return "Calls Count from sourcerer";
	}

	public List<Metric> getMetrics() {
		if ( metrics == null ){
			metrics = new ArrayList<Metric>();
			metrics.add( new Calls() );
		}
		return metrics;
	}
	

}
