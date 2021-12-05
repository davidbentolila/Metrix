package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.bentolila.metricasprogramasalheios.CKMetrics;

/**
 *@author david
 *Date: 20/10/2010
 *
 * based on http://sourceforge.net/projects/ckjm/
 */
public class CouplingBetweenObjectClasses extends Metric {

	public Double getValue(API api) {
		double cbo = api.getMetricsValues().getCBO();
		if ( cbo == 0 )
			for (Package p : api.getPackages()) cbo += getValue(p);
		return cbo;
	}

	public Double getValue(Package p) {
		double cbo = p.getMetricsValues().getCBO();
		if ( cbo == 0 )
			for ( Entity e : p.getEntities() ) cbo += getValue(e);
		return cbo;
	}

	public Double getValue(Entity e) {
		double cbo = e.getMetricsValues().getCBO();
		if ( cbo == 0 ){
			if ( MetriX.tempDir == null ) return 0d;
			String path = MetriX.tempDir.getAbsolutePath() + System.getProperty("file.separator") + (MetriX.useFullPath ? e.getFullName().replace(".", System.getProperty("file.separator")) : e.getName() ) + ".class";
			CKMetrics.calculateCK(path, true, true);
			cbo = CKMetrics.getCMC().getMetrics(e.getFullName()).getCbo();
		}
		return cbo;
	}

	public Double getValue(Method m) 			{	return 0d;							}
	public void setValue(API api, double d)		{	api.getMetricsValues().setCBO( d );	}
	public void setValue(Package p, double d) 	{	p.getMetricsValues().setCBO( d );	}
	public void setValue(Entity e, double d) 	{	e.getMetricsValues().setCBO( d );	}
	public void setValue(Method m, double d) 	{	return; 							}

	public String getLabel() {
		return Configurations.getString("metrics.CBO");
	}
}
