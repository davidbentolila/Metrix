package br.ufpa.linc.MetriX.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.configurations.Files;

/**
 *@author david
 *Date: 09/02/2011
 */
public class AnaliseVersions {

	List<Classe> classes;
	StringBuffer csvFull, csv ;
	
	public AnaliseVersions(List<API> apis) {
		classes = new ArrayList<AnaliseVersions.Classe>();
		Classe c;
		API api;
		csvFull = new StringBuffer();
		
		csvFull.append("Entity").append(";");
		
		for (int i = 0 ; i < apis.size() ; i++) 
			csvFull.append(apis.get(i)).append(";");

		csvFull.append("\n");
		
		for (int i = 0 ; i < apis.size() ; i++) {
			csv = new StringBuffer();
			api = apis.get(i);
			for (Entity e : api.getAllEntities()) {
				c = addClasse(e, apis.size());
				c.adicionaMetrica((int) e.getMetricsValues().getIS(), i);
				csv.append(e.getFullName()).append(";").append((int) e.getMetricsValues().getIS()).append("\n");
				csvFull.append("\n");
			}
			Files.saveCSV(csv, api.toString());
		}
		criarCSV(classes);
	}
	
	void criarCSV (List<Classe> classes){
		Collections.sort(classes);
		
		for (Classe c : classes) {
			csvFull.append(c.e.getFullName()).append(";");
			for (Integer is : c.is) addMetricVersion(is, csvFull);
			csvFull.append("\n");	
		}
		
		Files.saveCSV(csvFull, "analise");
	}
	
	void addMetricVersion(Integer d, StringBuffer csv){
		if ( d == null ) csv.append("-;");
		else csv.append( d ).append(";");
	}
	
	Classe addClasse(Entity e, int size){
		for (Classe c : classes) 
			if (c.e.getFullName().equals(e.getFullName())) return c;
		
		Classe c2 = new Classe(e, size);
		classes.add( c2 );
		return c2; 
	}
	
	class Classe implements Comparable<Classe>{
		Integer[] is ;
		Entity e;
		
		public Classe(Entity e, int metricsSize) {
			is = new Integer[metricsSize];
			Arrays.fill(is, null);
			this.e = e;
		}
		
		void adicionaMetrica(Integer d, int pos){
			is[pos] = (int)d;
		}

		public int compareTo(Classe o) {
			return e.getFullName().compareTo(o.e.getFullName());
		}
	}
}
