package br.ufpa.linc.MetriX.metrics;

import java.util.Map;

import counter.Connect;
import counter.EntityCounter;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;

public class Calls extends Metric{

	private CallCount count = null;
	
	public Double getValue(API api){
		double calls = api.getMetricsValues().getCalls();
		if ( calls == 0 )
			for (Package p : api.getPackages()) calls += getValue(p);
		return calls;
	}
	
	public Double getValue(Package p){
		double calls = p.getMetricsValues().getCalls();
		if ( calls == 0 )
			for (Entity e : p.getEntities()) calls += getValue(e);
		return calls;
	}

	public Double getValue(Entity e){
		double calls = e.getMetricsValues().getCalls();
		if ( calls == 0 ){
			if(count == null){
				count = new CallCount(e.getPackage().getAPI().getNome());
			}
			else if(count.getJar()!=e.getPackage().getAPI().getNome()){
				count.setJar(e.getPackage().getAPI().getNome());
			}
			Map<String,Long> m = count.getMap();
			Long a = m.get(e.getFullName());
			calls = (a==null)?(0):(a);
			if(calls < 0) calls = 0;
		}
		return calls;
	}
	
	public Double getValue(Method m){
		return 0.0; //TODO comofas
	}
	
	public void setValue(API api, double calls){
		api.getMetricsValues().setCalls( calls );
	}
	
	public void setValue(Package p, double calls){
		p.getMetricsValues().setCalls( calls );
	}
	
	public void setValue(Entity e, double calls){
		e.getMetricsValues().setCalls( calls );
	}
	
	public void setValue(Method m, double calls){
		m.getMetricsValues().setCalls( calls );
	}
	
	public String getLabel() {
		return Configurations.getString("metrics.Call");
	}

}

class CallCount{
	private Connect connect;
	private EntityCounter counter;
	private Map<String, Long> map;
	private String jar;
	
	public CallCount(String jar) {
		connect = new Connect();
		this.jar = jar;
		if(!connect.connect()){
			System.err.println("/--------------\\");
			System.err.println("|Not Connected!|");
			System.err.println("\\--------------/");
		}
		counter = new EntityCounter(jar, connect);
		map = counter.countAll();
	}

	public void setJar(String nome) {
		jar =nome;
		counter = new EntityCounter(jar, connect);
	}

	public String getJar() {
		return jar;
	}

	public Connect getConnect() {
		return connect;
	}

	public EntityCounter getCounter() {
		return counter;
	}

	public Map<String, Long> getMap() {
		return map;
	}
	
	
	
}