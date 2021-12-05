package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class NumberMethods extends Metric {

	public Double getValue(API api){
		double nm = api.getMetricsValues().getNM();
		if ( nm == 0 )
			for (Package p : api.getPackages()) nm += getValue(p);
		return nm;
	}
	
	public Double getValue(Package p){
		double nm = p.getMetricsValues().getNM();
		if ( nm == 0 )
			for (Entity e : p.getEntities()) nm += getValue(e);
		return nm;
	}
	
	public Double getValue(Entity e){
		return (double) e.getMethods().size();
	}
	
	public Double getValue(Method m) {
		return (double)0.0;
	}

	public void setValue(API api, double nm){
		api.getMetricsValues().setNM( nm );
	}
	
	public void setValue(Package p, double nm){
		p.getMetricsValues().setNM( nm );
	}
	
	public void setValue(Entity e, double nm){
		e.getMetricsValues().setNM( nm );
	}
	
	public void setValue(Method m, double nm){}
	
	public String getLabel() {
		return Configurations.getString("metrics.NM");
	}
}