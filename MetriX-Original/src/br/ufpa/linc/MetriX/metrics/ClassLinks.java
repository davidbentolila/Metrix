package br.ufpa.linc.MetriX.metrics;

import java.util.LinkedList;
import java.util.List;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;

public class ClassLinks extends Metric {
	
	@Override
	public String getLabel() {
		return "Class Links";
	}

	public Double getValue(API api){
		double cl = api.getMetricsValues().getClassLinks();
		if ( cl == 0 )
			for (Package p : api.getPackages()) cl += getValue(p);
		return cl;
	}
	
	public Double getValue(Package p){
		double cl = p.getMetricsValues().getClassLinks();
		if ( cl == 0 )
			for ( Entity e : p.getEntities() ) cl += getValue(e);
		return cl;
	}
	
	public Double getValue(Entity e){
		double cl = e.getMetricsValues().getClassLinks();
		if ( cl == 0 )
			for ( Method m : e.getMethods() ) cl += getValue(m);
		return cl;
	}
	
	public Double getValue(Method m) {
		double cl = m.getMetricsValues().getClassLinks();
		if(cl==0){
			List<Entity> entidades = new LinkedList<Entity>();
			for(Attribute a: m.getParameters()){
				if(a.getEntityType()!=null ){
					cl+=1;
					entidades.add(a.getEntityType());
//					setValue(a.getEntityType(),getValue(a.getEntityType())+1);
				}
			}
			setValue(m, cl);
			for(Entity e: entidades){
				setValue(e,getValue(e)+1);
			}
		}
		return cl;
	}

	@Override
	public void setValue(API api, double d) {
		api.getMetricsValues().setClassLinks(d);
	}

	@Override
	public void setValue(Package p, double d) {
		p.getMetricsValues().setClassLinks(d);
	}

	@Override
	public void setValue(Entity e, double d) {
		e.getMetricsValues().setClassLinks(d);
	}

	@Override
	public void setValue(Method m, double d) {
		m.getMetricsValues().setClassLinks(d);
	}

}
