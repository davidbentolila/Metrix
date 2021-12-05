package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class AttributeHidingFactor extends Metric{

	public Double getValue(API api) {
		double ahf = api.getMetricsValues().getAHF();
		if ( ahf == 0 )
			for (Package p : api.getPackages()) ahf += getValue(p);
		return ahf;
	}

	public Double getValue(Package p) {
		double ahf = p.getMetricsValues().getAHF();
		if ( ahf == 0 )
			for ( Entity e : p.getEntities() ) ahf += getValue(e);
		return ahf;
	}

	@SuppressWarnings("unused")
	public Double getValue(Entity e) {
		double ahf = e.getMetricsValues().getAHF();
		if ( ahf == 0 ){
			//TODO corrigir
			if ( true ) return 0d;
			double allClasses = e.getPackage().getAPI().getAllEntities().size();
			double allDependentClasses = e.getDependentEntities().size();
			
			int av = 0;
			for ( Attribute a : e.getAttributes() ) 
				if ( a.getModifier().equals("private") ) av++;
				else if ( a.getModifier().equals("protected") ) av += e.getPackage().getEntities().size();
				else av += allDependentClasses ;
			
			ahf = av / (allClasses -1 ) / e.getAttributes().size();
		}
		return ahf;
	}

	public Double getValue(Method m) {	return 0d;	}
	public void setValue(API api, double d) 	{	api.getMetricsValues().setAHF( d );	}
	public void setValue(Package p, double d)	{	p.getMetricsValues().setAHF( d );	}
	public void setValue(Entity e, double d)	{	e.getMetricsValues().setAHF( d );	}
	public void setValue(Method m, double d)	{	return;								}
	public String getLabel() {
		return Configurations.getString("metrics.AHF");
	}
}
