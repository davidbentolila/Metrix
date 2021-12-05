package br.ufpa.linc.MetriX.metrics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;

public class ParameterRearrangement extends Metric {

	@Override
	public String getLabel() {
		return "Parameter Rearrangement";
	}

	@Override
	public Double getValue(API api) {
		double pr = api.getMetricsValues().getPRearrangement();
		if(pr == 0){
			for(Package p: api.getPackages()) pr += getValue(p);
		}
		return pr;
	}

	@Override
	public Double getValue(Package p) {
		double pr = p.getMetricsValues().getPRearrangement();
		if(pr == 0){
			for(Entity e: p.getEntities()) pr += getValue(e);
		}
		return pr;
	}

	@Override
	public Double getValue(Entity e) {
		double pr = e.getMetricsValues().getPRearrangement();
		if(pr == 0){
			List<Method> methods = e.getMethods();
			Map<String, List<Method>> repeated = new HashMap<String,List<Method>>();
			for(Iterator<Method> it = methods.iterator();it.hasNext();){
				Method m = it.next();
				if(repeated.containsKey(m.getName())){
					String nome = m.getName();
					for(Method h: repeated.get(nome) ){
						List<Attribute> paramsA = m.getParameters();
						List<Attribute> paramsB = h.getParameters();
						if(paramsA.size() < paramsB.size()){
							for(int i = 0; i < paramsA.size(); ++i){
								if(!paramsA.get(i).getType().equals(paramsB.get(i))){
									pr++;
								}
							}
						}
					}
					repeated.get(m.getName()).add(m);
				}
				else {
					repeated.put(m.getName(), new LinkedList<Method>());
					repeated.get(m.getName()).add(m);
				}
			}
		}
		return pr;
	}

	@Override
	public Double getValue(Method m) {
		return 0.0;
	}

	@Override
	public void setValue(API api, double d) {
		api.getMetricsValues().setPRearrangement(d);
	}

	@Override
	public void setValue(Package p, double d) {
		p.getMetricsValues().setPRearrangement(d);
	}

	@Override
	public void setValue(Entity e, double d) {
		e.getMetricsValues().setPRearrangement(d);
	}

	@Override
	public void setValue(Method m, double d) {	}

}
