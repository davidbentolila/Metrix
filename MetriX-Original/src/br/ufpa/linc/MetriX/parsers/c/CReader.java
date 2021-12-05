package br.ufpa.linc.MetriX.parsers.c;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.api.model.Primitive;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.metricModel.MetricModel;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.parsers.Reader;
import br.ufpa.linc.MetriX.parsers.c.reader.Function;
import br.ufpa.linc.MetriX.parsers.c.reader.Parameter;
import br.ufpa.linc.MetriX.parsers.c.reader.Parser;
import br.ufpa.linc.MetriX.view.gui.Status;



public class CReader implements Reader{

	private File apiFile;
	private String apiName;
	private Parser parser;
	private String apiDownloadURL;
	private String release;
	private String releaseDate;
	private API api;
	@Override
	public void setApiDownloadURL(String text) {
		this.apiDownloadURL = text;
	}

	@Override
	public void setApiFile(File apiFile) {
		this.apiFile =  apiFile;
		
	}

	@Override
	public void setApiName(String text) {
		this.apiName = text;
	}

	@Override
	public void setRelease(String text) {
		this.release = text;
	}

	@Override
	public void setReleaseDate(String text) {
		this.releaseDate = text;
	}

	@Override
	public void verify() {
		if(apiFile != null){
			try {
				parser =  new Parser(apiFile.getParent()+"/", apiFile.getName());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(parser!= null) cFileReader();
	}

	private void cFileReader() {
		api = new API();
		new ArrayList<Package>();
		
		api.setNome( apiName );
		api.setAPIFileName(apiFile.getName());
		api.setDownloadURL(apiDownloadURL);
		api.setRelease( release );
		api.setReleaseDate(releaseDate);
		
		try {
			parser.preRun();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		for (Parser parser : files) {
//			
//		}
		insertApi();
	}

	private void insertApi() {
		int count = 0;
		final List<Parser> files = parser.getIncludes();
		for (MetricModel mm : MetriX.getInstance().getMetricModelsAvailable()) count+=mm.getMetrics().size();
		
		MetriX.getInstance().setStatus( new Status(Configurations.getString("analysis.start"), files.size() * (1 + count)));
		
		Thread t = new Thread(MetriX.getInstance().getStatus());
		t.start();
		
		Thread t1 = new Thread(){
			public void run() {
				boolean ok = false;
				try {
					System.out.println(Configurations.getString("analysis.start"));
					int total  = files.size();
					for (Parser file : files){
						Package p = new Package(file.getFileName(), api);
//						api.addPackage(p);
						//Interface i =  new Interface("<NO CLASS>", p);
						Interface i =  new Interface(file.getFileName(), p);
						file.parse();
						p.addEntity(i);
						Set<Function> functions = file.getFunctions();
						for (Function function : functions) {
							insertFunction(function, i);
						}
					}
					
					MetriX.getInstance().getStatus().updateProgressBar(Configurations.getString("message.calculatingMetrics"));
					System.out.println(Configurations.getString("message.calculatingMetrics"));
					

					/*
					 * Calculate all metrics
					 */
					for (MetricModel mm : MetriX.getInstance().getMetricModelsAvailable()){
						for (Metric m_ : mm.getMetrics()){
							for (Package p : api.getPackages()){
//								main.getStatus().restart(api.getAllEntities().size());
								for (Entity e : p.getEntities()){
									MetriX.getInstance().getStatus().updateProgressBar(Configurations.getString("message.calculatingMetric")+" "+m_.getLabel()+": "+e.getPackage().getName() + "." + e.getName());
									for (Method m : e.getMethods()) 
										m_.setValue(m, m_.getValue(m) );
									m_.setValue(e, m_.getValue(e) );
								}
								m_.setValue(p, m_.getValue(p) );
							}
						m_.setValue(api, m_.getValue(api) );
						}
					}

					MetriX.getInstance().getStatus().updateProgressBar(Configurations.getString("message.dao.inserting"));
					
					System.out.println(api );

					System.out.println("total: " + total + " Classes analisadas");
					ok = Database.getInstance().insert(api);
	
				}catch (Exception e) {
					ok = false;
					e.printStackTrace();
				}
								
				MetriX.getInstance().getStatus().dispose();
				if ( ok ) JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.inserted"));
				else JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.errorinsert"));
//				JOptionPane.showMessageDialog(null, Configurations.getString("message.dao.inserted"));
//				MetriX.getInstance().nextJar();
			}


		};
		t1.start();
	}
	
	private void insertFunction(Function function, Entity i) {
		Method m = new Method();
		m.setEntidade(i);
		i.addMethod(m);
		m.setName(function.getName());
		Attribute returnn = new Attribute();
		returnn.setName(function.getType());
		returnn.setPrimitiveType(new Primitive(function.getType()));
		m.setReturn(returnn);
		List<Attribute> parameters = new ArrayList<Attribute>();
		m.setParametros(parameters);
		for (Parameter attrib : function.getParameters()) {
			insertAttrib(attrib, parameters,m);
		}
	}

	private void insertAttrib(Parameter attrib, List<Attribute> parameters, Method function) {
		Attribute a = new Attribute();
		a.setMetodo(function);
		a.setName(attrib.getName());
		a.setPrimitiveType(new Primitive(attrib.getType()));
		parameters.add(a);
	}

//	private boolean isPrimitive(String type) {
//		Matcher m = null;
//		Pattern[] p = { 
//				Pattern.compile("bool(\\[\\])*?") ,
//				Pattern.compile("char(\\[\\])*?"),
//				Pattern.compile("byte(\\[\\])*?"),
//				Pattern.compile("short(\\[\\])*?"),
//				Pattern.compile("int(\\[\\])*?"),
//				Pattern.compile("long(\\[\\])*?"),
//				Pattern.compile("float(\\[\\])*?"),
//				Pattern.compile("double(\\[\\])*?"),
//				Pattern.compile("void(\\[\\])*?"), 
//				};
//		
//		for ( int i = 0 ; i < p.length ; i++){
//			m = p[i].matcher( type );
//			if ( m.matches() ) return true;
//		}
//		return false;
//	}
	
}