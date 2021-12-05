package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class LargeMethods extends Metric {

	public Double getValue(API api) {
		double lm = api.getMetricsValues().getLM();
		int largeMethods = 0, allMethods = 0;
		if ( lm == 0 ) {
			for (Package p : api.getPackages()) 
				for (Entity e : p.getEntities()) {
					for (Method m : e.getMethods()) largeMethods += m.getMetricsValues().getLM();
					allMethods += e.getMethods().size();
				}
			lm = allMethods > 0 ? (largeMethods*100)/allMethods : 0;
		}
		return lm;
	}

	public Double getValue(Package p) {
		double lm = p.getMetricsValues().getLM();
		int largeMethods = 0, allMethods = 0;
		if ( lm == 0 ) {
			for (Entity e : p.getEntities()) {
				for (Method m : e.getMethods()) largeMethods += getValue(m);
				allMethods += e.getMethods().size();
			}
			lm = allMethods > 0 ? (largeMethods*100)/allMethods : 0;
		}
		return lm;
	}

	public Double getValue(Entity e) {
		double lm = e.getMetricsValues().getLM();
		if ( lm == 0 ) {
			int largeMethods = 0;
			for (Method m : e.getMethods()) largeMethods += getValue(m);
			lm = e.getMethods().size() > 0 ? (largeMethods*100)/e.getMethods().size() : 0;
		}
		return lm;
	}

	public Double getValue(Method m) {
		return m.getMetricsValues().getLM() == 0 ? m.getParameters().size() > 4 ? 1d : 0 : 1;
	}

	public void setValue(API api, double d) {	api.getMetricsValues().setLM(d);	}

	public void setValue(Package p, double d) {	p.getMetricsValues().setLM(d);	}

	public void setValue(Entity e, double d) {	e.getMetricsValues().setLM(d);	}

	public void setValue(Method m, double d) {	m.getMetricsValues().setLM(d);	}

	public String getLabel() {
		return Configurations.getString("metrics.LM");
	}

}
