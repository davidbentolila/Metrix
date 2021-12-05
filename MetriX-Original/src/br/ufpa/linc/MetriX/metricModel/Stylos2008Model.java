package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.ClassLinks;
import br.ufpa.linc.MetriX.metrics.Metric;

public class Stylos2008Model extends MetricModel {

	@Override
	public String getLabel() {
		return "Stylos2008";
	}

	@Override
	public List<Metric> getMetrics() {
		ArrayList<Metric> a = new ArrayList<Metric>();
		a.add(new ClassLinks());
		return a;
	}

}
