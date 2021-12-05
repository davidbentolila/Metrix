package br.ufpa.linc.MetriX.metrics;
//TODO: Persist it!
import java.util.List;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;

public class ContructorDefault extends Metric {

	@Override
	public String getLabel() {
		return "Stylos2007CreateSetCall";
	}

	@Override
	public Double getValue(API api) {
		double stylosCD = api.getMetricsValues().getStylosCD();
		if(stylosCD == 0){
			for(Package p: api.getPackages()) stylosCD += getValue(p);
		}
		return stylosCD;
	}

	@Override
	public Double getValue(Package p) {
		double stylosCD= p.getMetricsValues().getStylosCD();
		if(stylosCD==0){
			for(Entity e: p.getEntities())	stylosCD+= getValue(e);
		}
		return stylosCD;
	}

	@Override
	public Double getValue(Entity e) {
		double stylosCD= e.getMetricsValues().getStylosCD();
		if(stylosCD==0){
			List<Method> cons = e.getConstructors();
			if(cons.isEmpty()){
				stylosCD = 0.0;
			}
			else {
				int ret = Integer.MAX_VALUE;
				for(Method me: cons){
					int n;
					if( (n=me.getParameters().size()) < ret){
						ret = n;
					}
				}
				stylosCD = (double)ret;
			}
		}
		return stylosCD;
	}

	@Override
	public Double getValue(Method m) {
		return (double)0.0;
	}

	@Override
	public void setValue(API api, double d) {
		api.getMetricsValues().setStylosCD(d);
	}

	@Override
	public void setValue(Package p, double d) {
		p.getMetricsValues().setStylosCD(d);
	}

	@Override
	public void setValue(Entity e, double d) {
		e.getMetricsValues().setStylosCD(d);
	}

	@Override
	public void setValue(Method m, double d) {}

}
