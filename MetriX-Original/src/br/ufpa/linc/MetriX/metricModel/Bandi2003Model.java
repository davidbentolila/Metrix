package br.ufpa.linc.MetriX.metricModel;

import java.util.Arrays;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.InterfaceSize;
import br.ufpa.linc.MetriX.metrics.Metric;

public class Bandi2003Model extends MetricModel {

	private final List<Metric> metrics;

	public Bandi2003Model() {
		metrics = Arrays.asList((Metric) new InterfaceSize() );
	}
	public String getLabel() {
		return Messages.getString("Bandi2003Model.modelName");
	}

	public List<Metric> getMetrics() {
		return metrics;
	}	
}
