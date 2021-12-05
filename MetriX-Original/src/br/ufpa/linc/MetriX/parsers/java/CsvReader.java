package br.ufpa.linc.MetriX.parsers.java;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.MetricsValues;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.csv.CsvParser;
import br.ufpa.linc.MetriX.csv.Field;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.view.gui.Status;

public class CsvReader {
	private File file;
	private CsvParser csv;
	private String fileName;
	final public static int COLLUMN_NONE = -1;
	private int methodCollumn= COLLUMN_NONE;
	private int entityCollumn= COLLUMN_NONE;
	private int packageCollumn = COLLUMN_NONE;
	private Map<Metric,Integer> metricsCollumns;
	
	public CsvReader(String filename) {
		this.fileName = filename;
		file = new File(filename);
		try {
			System.out.println("Loading the file");
			csv = new CsvParser(file);
//			parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getTitles() {
		return csv.getTitles();
	}
	
	public void parse(final int max){
		MetriX.getInstance().setStatus( new Status(Configurations.getString("analysis.start"), csv.getNumLines() ));
		System.out.println(Configurations.getString("analysis.start"));
		Thread t = new Thread(MetriX.getInstance().getStatus());
		Thread t1;
		final int lower;
		if(methodCollumn!=COLLUMN_NONE){
			lower = 0;
			
		}
		else if(entityCollumn != COLLUMN_NONE){
			lower = 1;
		}
		else if(packageCollumn != COLLUMN_NONE){
			lower = 2;
		}
		else {
			//Erro!
			return;
		}

		t1 = new Thread(){
			public void run() {
				boolean ok = false;
				try {
					API a = new API();
					Map<String,Package>packages = new HashMap<String,Package>();
					Map<String,Entity>entities = new HashMap<String,Entity>();
					a.setNome( fileName.substring(fileName.lastIndexOf(System.getProperty("file.separator"))+1) );
					a.setMetricsValues(new MetricsValues());
					//Criando um default package
					Package defaultPackage = null;
					if(packageCollumn == COLLUMN_NONE){
						defaultPackage = new Package("default", a);
						defaultPackage.setMetricsValues(new MetricsValues());
						a.addPackage(defaultPackage);
					}
					
					int count = 0;
					
					switch(lower){
					case 1:
						for (int i = 0; i < csv.getNumLines()-1; i++) {
							List<Field> line = csv.getLine(i);
							String cname = line.get(entityCollumn).getValueS();
							Package currentPackage;
							if((packageCollumn != COLLUMN_NONE)){
								String s = line.get(packageCollumn).getValueS();
								if(!entities.containsKey(s) ){
									currentPackage = new Package(s, a);
									currentPackage.setMetricsValues(new MetricsValues());
									a.addPackage(defaultPackage);
									packages.put(s, currentPackage);
								}
								else currentPackage =  packages.get(s);
							}
							else {
								currentPackage = defaultPackage;
							}
							MetriX.getInstance().getStatus().updateProgressBar( currentPackage.getName() );
							Entity actualEntity = new Class(cname,currentPackage);
							currentPackage.addEntity(actualEntity);
							actualEntity.setMetricsValues(new MetricsValues());
							for (Entry<Metric, Integer> entry : getMetricsCollumns().entrySet()) {
								Metric metric  = entry.getKey();
								double value = entry.getValue();
								metric.setValue(actualEntity, value);
								System.out.println(actualEntity.getName()+", "+metric.getLabel()+", "+value);
								metric.setValue(currentPackage, value + metric.getValue(currentPackage));
							}
							count ++;
							if(count == max) break;
						}
						break;
						
					}
					
					System.out.println("Linhas inseridas: "+ (count));
					
					ok = Database.getInstance().insert(a);					
				}catch (Exception e) {
					ok = false;
					e.printStackTrace();
				}
				
				MetriX.getInstance().getStatus().dispose();
				
				if ( ok ) JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.inserted"));
				else JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.errorinsert"));
			}
		};
		t.start();
		t1.start();
	}
	
	
	
	public void parse() {

		MetriX.getInstance().setStatus( new Status(Configurations.getString("analysis.start"), csv.getNumLines() ));
		System.out.println(Configurations.getString("analysis.start"));
		Thread t = new Thread(MetriX.getInstance().getStatus());
		
		Thread t1 = new Thread(){
			public void run() {
				API a = new API();
				a.setNome( fileName.substring(fileName.lastIndexOf(System.getProperty("file.separator"))+1) );
				a.setMetricsValues(new MetricsValues());
				//Criando um default package
				Package defaultPackage = new Package("default", a);
				defaultPackage.setMetricsValues(new MetricsValues());
				a.addPackage(defaultPackage);
				
				int is = -1;// = Arrays.binarySearch(csv.getTitles(), "IS_SUM");
				int bugc = -1;// = Arrays.binarySearch(csv.getTitles(), "NBUG");
				int calls = -1;
				String[] titles = csv.getTitles();
				for (int i = 0; i < titles.length; i++) {
					if(titles[i].equals("IS2_SUM")) is = i;
					else if(titles[i].equals("NBUG")) bugc = i;
					else if(titles[i].equals("NCALLS")) calls = i;
				}
				System.out.println(is);
				System.out.println(bugc);
				System.out.println(Arrays.toString(titles));
				System.out.println("Linhas a inserir: "+ (csv.getNumLines()));
				int count = 0;
				
				for (int i = 0; i < csv.getNumLines()-1; i++) {
					List<Field> line = csv.getLine(i);
					String pname = line.get(0).getValueS();
					MetriX.getInstance().getStatus().updateProgressBar( pname );
					Entity actualFile = null;
		//			for (Entidade entidade : files) {
		//				if(entidade.getNome().equals(pname)){
		//					actualFile = entidade;
		//					break;
		//				}
		//			}
		//			if(actualFile == null){ Error: Wrong char
						actualFile = new Class();
						actualFile.setName(pname);
		//				files.add(actualFile);
						defaultPackage.addEntity(actualFile);
		//				a.addPackage(actualFunction);
						actualFile.setMetricsValues(new MetricsValues());
						System.out.println("New file: "+ pname);
		//			}
		//			Metodo function = new Metodo();
		//			function.setNome(line.get(1).getValueS());
		//			MetricsValues metricsValues = new MetricsValues();
		//			metricsValues.setIS(line.get(3).getValueN());
		//			metricsValues.setNP(line.get(2).getValueN());
		//			metricsValues.setNM(line.get(2).getValueN());
		//			metricsValues.setCalls(line.get(4).getValueN());
		//			function.setMetricsValues(metricsValues);
		
					MetricsValues packagesValues = actualFile.getMetricsValues();
					if ( is != -1 ) packagesValues.setIS(line.get(is).getValueN());
		//			packagesValues.setNP(line.get(2).getValueN() + packagesValues.getNP());
		//			packagesValues.setNM(line.get(2).getValueN() + packagesValues.getNM());
		//			packagesValues.setCalls(line.get(8).getValueN());
					if ( bugc != -1 ) packagesValues.setBugCount(line.get(bugc).getValueN());
					if ( calls != -1 ) packagesValues.setCalls(line.get(calls).getValueN());
					
					
		//			packagesValues.setIS(line.get(8).getValueN());
		//			packagesValues.setNP(line.get(2).getValueN() + packagesValues.getNP());
		//			packagesValues.setNM(line.get(2).getValueN() + packagesValues.getNM());
		//			packagesValues.setCalls(line.get(8).getValueN());
		//			packagesValues.setBugCount(line.get(13).getValueN());
					
		//			actualFile.addMethod(function);
		//			function.setEntidade(actualFile);
		//			System.out.println("New function: "+function.getNome());
					count ++;
		//			if(count > 2800)break;
				}
				System.out.println("Linhas inseridas: "+ (count));
				
				boolean ok = Database.getInstance().insert(a);
				MetriX.getInstance().getStatus().dispose();
				
				if ( ok ) JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.inserted"));
				else JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.errorinsert"));
			}
		};
		t.start();
		t1.start();
	}

	public void setMethodCollumn(int methodCollumn) {
		this.methodCollumn = methodCollumn;
	}

	public int getMethodCollumn() {
		return methodCollumn;
	}

	public void setEntityCollumn(int entityCollumn) {
		this.entityCollumn = entityCollumn;
	}

	public int getEntityCollumn() {
		return entityCollumn;
	}

	public void setPackageCollumn(int packageCollumn) {
		this.packageCollumn = packageCollumn;
	}

	public int getPackageCollumn() {
		return packageCollumn;
	}

	public void setMetricsCollumns(Map<Metric,Integer> metricsCollumns) {
		this.metricsCollumns = metricsCollumns;
	}

	public Map<Metric,Integer> getMetricsCollumns() {
		return metricsCollumns;
	}
}
