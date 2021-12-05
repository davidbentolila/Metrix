package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

/**
 *@author david
 *Date: 20/10/2010
 */
public class NumberPublicAttribute extends Metric {

	public Double getValue(API api) {
		double npa = api.getMetricsValues().getNPA();
		if ( npa == 0 )
			for (Package p : api.getPackages()) npa += getValue(p);
		return npa;
	}

	public Double getValue(Package p) {
		double npa = p.getMetricsValues().getNPA();
		if ( npa == 0 )
			for (Entity e : p.getEntities()) npa += getValue(e);
		return npa;
	}

	public Double getValue(Entity e) {
		double npa = e.getMetricsValues().getNPA();
		if ( npa == 0 )
			for (Attribute a : e.getAttributes()) if ( a.getModifier().equals("public") ) npa++;
		return npa;
	}

	public Double getValue(Method m) 			{	return 0d;	}

	public void setValue(API api, double d)		{	api.getMetricsValues().setNPA(d);	}
	public void setValue(Package p, double d)	{	p.getMetricsValues().setNPA(d);		}
	public void setValue(Entity e, double d)	{	e.getMetricsValues().setNPA(d);		}
	public void setValue(Method m, double d) 	{	return;								}

	public String getLabel() {
		return Configurations.getString("metrics.NPA");
	}
}
