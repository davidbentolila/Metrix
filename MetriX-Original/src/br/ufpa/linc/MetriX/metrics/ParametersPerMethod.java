package br.ufpa.linc.MetriX.metrics;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class ParametersPerMethod extends Metric{

	public Double getValue(API api) {
		double ppm = api.getMetricsValues().getPPM();
		if ( ppm == 0 )
			for (Package p : api.getPackages()) ppm += getValue(p);
		return ppm;
	}

	public Double getValue(Package p) {
		double ppm = p.getMetricsValues().getPPM();
		if ( ppm == 0 )
			for (Entity e : p.getEntities()) ppm += getValue(e);
		return ppm;
	}

	public Double getValue(Entity e) {
		double ppm = e.getMetricsValues().getPPM();
		if ( ppm == 0 ){
			int totalNP = 0;
			int nm = 0;
			NumberParameters np = new NumberParameters();
			for (Method m : e.getMethods())
				if ( m.getModifier().equals("public") ){
					totalNP += np.getValue(m);
					nm++;
				}
			ppm = nm != 0 ? totalNP / nm : 0;
		}
		return ppm;
	}
		
	public String getLabel() {
		return Configurations.getString("metrics.PPM");
	}

	public Double getValue(Method m) {
		return (double)0.0;
	}

	public void setValue(API api, double ppm){
		api.getMetricsValues().setPPM( ppm );
	}
	
	public void setValue(Package p, double ppm){
		p.getMetricsValues().setPPM( ppm );
	}
	
	public void setValue(Entity e, double ppm){
		e.getMetricsValues().setPPM( ppm );
	}
	
	public void setValue(Method m, double ppm){}
}
