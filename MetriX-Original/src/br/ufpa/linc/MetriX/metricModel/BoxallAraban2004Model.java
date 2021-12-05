package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.metrics.NumberMethods;
import br.ufpa.linc.MetriX.metrics.NumberParameters;
import br.ufpa.linc.MetriX.metrics.ParametersPerMethod;

public class BoxallAraban2004Model extends MetricModel {

	private List<Metric> metrics = null;
	
	public String getLabel() {
		return "Boxall & Araban (2004)";
	}

	public List<Metric> getMetrics() {
		if ( metrics == null ){
			metrics = new ArrayList<Metric>();
			metrics.add( new NumberMethods() );
			metrics.add( new NumberParameters() );
			metrics.add( new ParametersPerMethod() );
		}
		return metrics;
	}
	
}
