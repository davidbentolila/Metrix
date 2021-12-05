package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class InterfaceSize extends Metric {
	
	public Double getValue(API api){
		double is = api.getMetricsValues().getIS();
		if ( is == 0 )
			for (Package p : api.getPackages()) is += getValue(p);
		return is;
	}
	
	public Double getValue(Package p){
		double is = p.getMetricsValues().getIS();
		if ( is == 0 )
			for ( Entity e : p.getEntities() ) {
				for (Entity i : e.getInterfaces()) is -= getValue(i);
				is += getValue(e);
			}
		return is;
	}
	
	public Double getValue(Entity e){
		double is = e.getMetricsValues().getIS();
		if ( is == 0 )
			for ( Method m : e.getMethods() )
				if ( m.getModifier().equals("public") ) is += getValue(m);
		return is;
	}
	
	public Double getValue(Method m) {
		double k3 = 1, k4 = 1;
		double is = m.getMetricsValues().getIS();
		if ( is == 0 ){
			// if not return void, the return must be consider a parameter
			is = m.getReturnType().equals("void") ? k3 * m.getParameters().size() : k3 * (m.getParameters().size() + 1);
			
			double strength = 0;
			for (Attribute atributo : m.getParameters()) strength += atributo.getWeight("IS");
			if ( !m.getReturnType().equals("void") )
				if ( m.getReturn().getEntityType() != null ) strength += m.getReturn().getEntityType().getMetricsValues().getIS();
				else if ( m.getReturn().getPrimitiveType() != null ) strength += m.getReturn().getPrimitiveType().getPeso();
			
			is += k4 * strength;
		}
		return is;
	}

	public void setValue(API api, double is)	{	api.getMetricsValues().setIS( is );	}
	public void setValue(Package p, double is)	{	p.getMetricsValues().setIS( is );	}
	public void setValue(Entity e, double is)	{	e.getMetricsValues().setIS( is );	}
	public void setValue(Method m, double is)	{	m.getMetricsValues().setIS( is );	}

	public String getLabel() {
		return Configurations.getString("metrics.IS");
	}
}
