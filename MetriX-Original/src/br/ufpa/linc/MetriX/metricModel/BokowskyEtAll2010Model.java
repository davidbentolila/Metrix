package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.LargeMethods;
import br.ufpa.linc.MetriX.metrics.Metric;

public class BokowskyEtAll2010Model extends MetricModel {

	private List<Metric> metrics = null;
	
	public String getLabel() {
		return "Bokowsky et al. (2010)";
	}

	public List<Metric> getMetrics() {
		if ( metrics == null ){
			metrics = new ArrayList<Metric>();
			metrics.add( new LargeMethods() );
		}
		return metrics;
	}

}
