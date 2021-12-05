package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.BugCount;
import br.ufpa.linc.MetriX.metrics.Metric;

public class BugCountModel extends MetricModel {

	private List<Metric> metrics = null;

	public String getLabel() {
		return "Bug Count from ossnetwork";
	}

	public List<Metric> getMetrics() {
		if ( metrics == null ){
			metrics = new ArrayList<Metric>();
			metrics.add( new BugCount() );
		}
		return metrics;
	}
	
}
