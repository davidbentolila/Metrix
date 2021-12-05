package br.ufpa.linc.MetriX.metricModel;

import java.util.List;

import br.ufpa.linc.MetriX.metrics.Metric;

public abstract class MetricModel {

	public abstract String getLabel();
	
	public abstract List<Metric> getMetrics();
	
}
