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
 *Date: 08/02/2011
 */
public class DepthOfInheritanceTree extends Metric{

	public Double getValue(API api) {
		double dit = api.getMetricsValues().getDIT();
		if ( dit == 0 )
			for (Package p : api.getPackages()) dit += getValue(p);
		return dit;
	}

	public Double getValue(Package p) {
		double dit = p.getMetricsValues().getDIT();
		if ( dit == 0 )
			for ( Entity e : p.getEntities() ) dit += getValue(e);
		return dit;
	}

	public Double getValue(Entity e) {
		double dit = e.getMetricsValues().getDIT();
		if ( dit == 0 ){
			if ( MetriX.tempDir == null ) return 1d;
			String path = MetriX.tempDir.getAbsolutePath() + System.getProperty("file.separator") + (MetriX.useFullPath ? e.getFullName().replace(".", System.getProperty("file.separator")) : e.getName() ) + ".class";
			CKMetrics.calculateCK(path, true, true);
			dit = CKMetrics.getCMC().getMetrics(e.getFullName()).getDit();
		}	
		return dit;
	}

	public Double getValue(Method m)			{	return 0d;	}
	public void setValue(API api, double d)		{	api.getMetricsValues().setDIT( d );	}
	public void setValue(Package p, double d)	{	p.getMetricsValues().setDIT( d );	}
	public void setValue(Entity e, double d)	{	e.getMetricsValues().setDIT( d );	}
	public void setValue(Method m, double d)	{	return;	}

	public String getLabel() {
		return Configurations.getString("metrics.DIT");
	}

}
