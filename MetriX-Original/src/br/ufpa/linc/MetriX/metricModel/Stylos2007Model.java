package br.ufpa.linc.MetriX.metricModel;

import java.util.ArrayList;
import java.util.List;

import br.ufpa.linc.MetriX.metrics.ContructorDefault;
import br.ufpa.linc.MetriX.metrics.Metric;

public class Stylos2007Model extends MetricModel {

	@Override
	public String getLabel() {
		return "Stylos 2007 CreateSetCall";
	}

	@Override
	public List<Metric> getMetrics() {
		ArrayList<Metric> a = new ArrayList<Metric>();
		a.add(new ContructorDefault());
		return a;
	}

}
