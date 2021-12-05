package br.ufpa.linc.MetriX.metrics;

import java.io.IOException;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;

public abstract class Metric {

	public abstract Double getValue(API api) throws IOException;
	
	public abstract Double getValue(Package p) throws IOException;
	
	public abstract Double getValue(Entity e) throws IOException;
	
	public abstract Double getValue(Method m);
	
	public abstract void setValue(API api, double d);
	
	public abstract void setValue(Package p, double d);
	
	public abstract void setValue(Entity e, double d);
	
	public abstract void setValue(Method m, double d);
	
//	public abstract void getMeanValue(API api, double d);
//	
//	public abstract void getMeanValue(Package p, double d);
//	
//	public abstract void getMeanValue(Entity e, double d);
//	
//	public abstract void getMeanValue(Method m, double d);
	
	public abstract String getLabel();
	
	public String toString(){
		return getLabel();
	}
}
