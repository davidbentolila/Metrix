package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class NumberParameters extends Metric {

	public Double getValue(API api) {
		double np = api.getMetricsValues().getNP();
		if ( np == 0 )
			for (Package p : api.getPackages()) np += getValue(p);
		return np;
	}

	public Double getValue(Package p) {
		double np = p.getMetricsValues().getNP();
		if ( np == 0 )
			for (Entity e : p.getEntities()) np += getValue(e);
		return np;
	}

	public Double getValue(Entity e) {
		double np = e.getMetricsValues().getNP();
		if ( np == 0 )
			for (Method m : e.getMethods()) np += getValue(m);
		return np;
	}
	
	public Double getValue(Method m) {
		return (double) m.getParameters().size() ;
	}
	
	public void setValue(API api, double np){
		api.getMetricsValues().setNP( np );
	}
	
	public void setValue(Package p, double np){
		p.getMetricsValues().setNP( np );
	}
	
	public void setValue(Entity e, double np){
		e.getMetricsValues().setNP( np );
	}
	
	public void setValue(Method m, double np){
		m.getMetricsValues().setNP( np );
	}
	
	public String getLabel() {
		return Configurations.getString("metrics.NP");
	}
}