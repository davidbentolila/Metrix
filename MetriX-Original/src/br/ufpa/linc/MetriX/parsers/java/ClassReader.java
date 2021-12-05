package br.ufpa.linc.MetriX.parsers.java;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import br.ufpa.linc.MetriX.MetriX;
import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Attribute;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.api.model.Primitive;
import br.ufpa.linc.MetriX.api.model.Type;
import br.ufpa.linc.MetriX.configurations.Configurations;
import br.ufpa.linc.MetriX.configurations.Files;
import br.ufpa.linc.MetriX.dao.Database;
import br.ufpa.linc.MetriX.metricModel.MetricModel;
import br.ufpa.linc.MetriX.metrics.Metric;
import br.ufpa.linc.MetriX.parsers.Reader;
import br.ufpa.linc.MetriX.view.gui.Status;

import com.sun.org.apache.bcel.internal.classfile.AccessFlags;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;
import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

public class ClassReader implements Reader {
	private JarFile jarFile = null;

	private API api = null;
	private List<Type> tipos = null;
	private File apiFile = null;
	private String release = null;
	private String releaseDate = null;
	private String apiName = null;
	private String apiDownloadURL = null;
	private List<JavaClass> javaClasses = null;
	private List<Entity> entities = null;
	private List<Package> packages = null, superPackages = null;
	private long start ;
	private long end;
	private boolean folder = false;
	
	/**
	 * Constructor method
	 * 
	 * @param frame
	 *            - the main window that has treemap & treeview
	 * @param language
	 *            - Language used in app
	 */
	public ClassReader() {
		super();
		tipos = new ArrayList<Type>();
		javaClasses = new ArrayList<JavaClass>();
		// javaClasses = new ArrayList<JavaClass>();
		entities = new ArrayList<Entity>();
		// new FormAPIData(this);
	}

	public ClassReader(File jar) {
		tipos = new ArrayList<Type>();
		javaClasses = new ArrayList<JavaClass>();
		entities = new ArrayList<Entity>();
		folder = true;
		setApiFile(jar);
		setApiName(jar.getName());
		verify();
	}

	public void verify() {
		if (apiFile != null)
			try {
				jarFile = new JarFile(apiFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		if (jarFile != null)
			jarFileReader();
	}

	/**
	 * Read the jar file, copying the content to temp folder in user home
	 * 
	 * @throws URISyntaxException
	 */
	private void jarFileReader() {
		start = System.currentTimeMillis();
		
		packages = new ArrayList<Package>();
		superPackages = new ArrayList<Package>();
		api = new API();
		api.setNome(apiName);
		api.setAPIFileName(apiFile.getName());
		api.setDownloadURL(apiDownloadURL);
		api.setRelease(release);
		api.setReleaseDate(releaseDate);
		// System.out.println(api.getAPIFileName() + main.getConnect());
		// if ( main.getConnect() != null ) main.setCounter( new
		// EntityCounter(api.getAPIFileName(), main.getConnect()) );
		MetriX.tempDir = Configurations.getTempDir();
		MetriX.useFullPath = false;
			
		Enumeration<JarEntry> e = jarFile.entries();

		int count = 0, count2 = 0;
		for (MetricModel mm : MetriX.getInstance().getMetricModelsAvailable())
			count += mm.getMetrics().size();
		JarEntry je;
		while (e.hasMoreElements()) {
			je = e.nextElement();
			if (je.getName().endsWith("class") && !je.getName().contains("$")) count2++;
		}
		final int total = count2;
		
		 
		MetriX.getInstance().setStatus(
				new Status(Configurations.getString(
						"analysis.start"), total  * count));

		Thread t = new Thread(MetriX.getInstance().getStatus());
		t.start();
		
		Thread t1 = new Thread() {
			public void run() {
				File path = null;
				JarEntry je;
				ClassParser cp;
				JavaClass jc;
				URI uriClass = null;
				Entity ent;
				int count = 0;
				Enumeration<JarEntry> e = jarFile.entries();
				
				while (e.hasMoreElements()) {
					
					je = e.nextElement();
					count++;
					if (je.getName().endsWith("class") && !je.getName().contains("$")) {
						uriClass = new File(getPath(je)).toURI();
						// uriClass = new File( je.getName().replace('/',
						// System.getProperty("file.separator"))).toURI();
						path = new File(uriClass.getPath().substring(0,
								uriClass.getPath().lastIndexOf("/")));
						// System.out.println(uriClass);
		
						if (!path.exists() && !path.mkdirs()) {
							JOptionPane.showMessageDialog(null, Configurations
									.getString("message.error.classDir"));
							System.exit(0);
						}
						if (!Files.copyClass(jarFile,je, uriClass))
							return;
												
						try {
							cp = new ClassParser(uriClass.getPath());
							jc = cp.parse();
		
							javaClasses.add(jc);
							
							MetriX.getInstance()
							.getStatus()
							.updateProgressBar(  Configurations.getString("message.unzinping.file") + " " + count + "/" + total +  ": " + jc.getClassName() );
							
							Package package_ = existPackage(jc.getPackageName());
							if (package_ == null) {
								package_ = new Package(jc.getPackageName(), api);
								packages.add(package_);
//								relatePackage(package_);
							}

//							if ( e.getFullName().equals("br.ufpa.linc.MetriX.MetriX")) System.out.println(jc.getSuperclassName());
//							if ( jc.getSuperclassName().contains("Date")) System.out.println("super " + jc.getSuperclassName() + " : " + e.getFullName() );

							ent = jc.isInterface() ? new Interface(jc.getClassName().substring(jc.getClassName().lastIndexOf(".") + 1)) :
								new Class(jc.getClassName().substring(jc.getClassName().lastIndexOf(".") + 1));
							entities.add(ent);
							package_.addEntity(ent);
						} catch (IOException e1) {
							System.err.println(Configurations.getString("message.error.Readclass"));
		//					e1.printStackTrace();
						}
						
		
					}
				}
				insertApi();				
			}
		};
		t1.start();
	}
	
	private String getPath(JarEntry je){
		return MetriX.tempDir.getAbsolutePath()
		+ System.getProperty("file.separator")
		+ (MetriX.useFullPath ? je.getName().replace('/', System.getProperty("file.separator").charAt(0)) : je.getName().substring(je.getName().lastIndexOf("/")+1));
	}

	private void insertApi() {
//		int count = 0;
//		for (MetricModel mm : MetriX.getInstance().getMetricModelsAvailable())
//			count += mm.getMetrics().size();

//		MetriX.getInstance().setStatus(
//				new Status(Configurations.getString(
//						"analysis.start"), uris.size() * (1 + count)));
//
//		Thread t = new Thread(MetriX.getInstance().getStatus());
//		t.start();
		Thread t1 = new Thread() {
			public void run() {
				System.out.println(Configurations.getString(
						"analysis.start"));
				int total = javaClasses.size();
				int count = 0;
				for (JavaClass jc : javaClasses) {
					MetriX.getInstance()
								.getStatus()
								.updateProgressBar( Configurations.getString("message.Readclass") + " " + (count++) + "/" + total + " : " + jc.getClassName() );
					ReadJavaClass(jc);						
				}
	
				
//
//				for (JavaClass jc : javaclasses){
//					MetriX.getInstance().getStatus().updateProgressBar( jc.getClassName() + "("+count+++")");
//					
//				}

				// create package tree
				for (Package p : packages) {
					Collections.sort(p.getEntities());
					relatePackage(p);
				}
				
				System.out.println(Configurations.getString(
						"analysis.stop"));

				MetriX.getInstance()
						.getStatus()
						.updateProgressBar(
								Configurations.getString(
										"message.calculatingMetrics"));
				System.out.println(Configurations.getString(
						"message.calculatingMetrics"));

				/*
				 * Calculate all metrics
				 */
				Entity e;
				for (MetricModel mm : MetriX.getInstance()
						.getMetricModelsAvailable()) {
					for (Metric m_ : mm.getMetrics()) {
						for (Package p : api.getPackages()) {
							// main.getStatus().restart(api.getAllEntities().size());
							for (int i = 0 ; i < p.getEntities().size() ; i++ ) {
								
								e = p.getEntities().get(i);
								
								MetriX.getInstance()
										.getStatus()
										.updateProgressBar(
												Configurations
														
														.getString(
																"message.calculatingMetric")
														+ " "
														+ m_.getLabel()
														+ ": "
														+ e.getPackage()
																.getName()
														+ "." + e.getName());
								for (Method m : e.getMethods())
									m_.setValue(m, m_.getValue(m));
								m_.setValue(e, m_.getValue(e));
							}
							m_.setValue(p, m_.getValue(p));
						}
						m_.setValue(api, m_.getValue(api));
					}
				}

				MetriX.getInstance()
						.getStatus()
						.updateProgressBar(
								Configurations.getString(
										"message.dao.inserting") + " : " +api);

				System.out.println(api);

				System.out.println("total: " + total + " Classes analisadas");
				
				Collections.sort(api.getPackages());
				
//				List<Metric> metrics = new ArrayList<Metric>();
//				metrics.add(new InterfaceSize());
//				
//				MetriX.getInstance().setApi(api);
//				MetriX.getInstance().setDefineColor(0);
//				MetriX.getInstance().setDefineSize(0);
//				MetriX.getInstance().setRelativeInternal(false);
//					
//				new Analysis(Arrays.asList(api), new int[]{0}, metrics, new int[]{0});
//				
//				MetriX.getInstance().setApi(api);
//				MetriX.getInstance().setDefineColor(0);
//				MetriX.getInstance().setDefineSize(0);
//				MetriX.getInstance().setRelativeInternal(false);
//				
//				new MyTreeMap().getTreeMap(api);
//				
//				try {
//					System.out.println("sleeping");
//					sleep(30000);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//				
//				MetriX.getInstance().setApi(api);
//				MetriX.getInstance().setDefineColor(0);
//				MetriX.getInstance().setDefineSize(0);
//				MetriX.getInstance().setRelativeInternal(false);
//				
//				new MyTreeMap().getTreeMap(api,true);
//				
//				System.out.println("acabouuuuuuuuuuuuuuuu");
//				
				boolean ok = Database.getInstance().insert(api);
				
				MetriX.getInstance().getStatus().dispose();
				
				end = System.currentTimeMillis();
				
				MetriX.getInstance().setApi(null);
				
				if (!folder)
					if (ok)
						JOptionPane.showMessageDialog(null, Configurations
								
								.getString("message.dao.inserted"));
					else
						JOptionPane.showMessageDialog(
								null,
								Configurations.getString(
										"message.dao.errorinsert"));
				else MetriX.getInstance().nextJar();
				
				System.out.println("inserted in " + ((end-start)/1000) + " seconds");
			}
		};
		t1.start();
	}

	private Entity ReadJavaClass(JavaClass jc){
		
		JavaClass jcSC = null, jcI;
		Entity e = existEntity(jc.getClassName()), superE , interfacee;
		e.setAbstrakt(jc.isAbstract());
		e.setModifier(getModifier(jc));
		e.setAttributes(getFields(jc,e));
		e.setMetodos(getMethods(jc, e));
		
		String pathSuperClass, pathInterface;
		
		pathSuperClass = MetriX.tempDir + File.separator + jc.getSuperclassName().substring(jc.getSuperclassName().lastIndexOf(".") + 1) + ".class";
		
		try {
			jcSC = new ClassParser(pathSuperClass).parse();
		} catch (ClassFormatException e1) {
//			System.out.println("Error superclass format " +jc.getSuperclassName());
			jcSC = null;
		} catch (IOException e1) {
//			System.out.println("Error read superclass " +jc.getSuperclassName());
			jcSC = null;
		} catch (NullPointerException e2) {
//			System.out.println("Error load superclass " +jc.getSuperclassName());
			jcSC = null;
		}
		
		if (jcSC != null && ( superE = existEntity(jc.getSuperclassName())) != null ) {
				e.setSuperClass(superE);
				superE.addSubClass(e);
			}
//			else {
//				superE = ReadJavaClass(jcSC);
//				e.setSuperClass(superE);
//				superE.addSubClass(e);
//			}
//		
		for (String interfaceName : jc.getInterfaceNames()) {
			
//			System.out.println(interfaceName);
			pathInterface = MetriX.tempDir + File.separator + interfaceName.substring(interfaceName.lastIndexOf(".") + 1) + ".class";
			
			try {
				jcI = new ClassParser(pathInterface).parse();
			} catch (ClassFormatException e1) {
				System.out.println("Error interface format " + pathInterface );//;interfaceName);
				jcI = null;
			} catch (IOException e1) {
				System.out.println("Error read interface " + pathInterface );//;interfaceName);
				jcI = null;
			} catch (NullPointerException e2) {
				System.out.println("Error load interface " + pathInterface );//;interfaceName);
				jcI = null;
			}
			
			if (jcI != null && (interfacee = existEntity(interfaceName)) != null && interfacee instanceof Interface) e.addInterface((Interface)interfacee);
//				else if ( jcI .isInterface() ) e.addInterface((Interface)ReadJavaClass(jcI));
			
			
		}

		entities.add(e);
		tipos.add(e);
		return e;
	}

	/**
	 * Get all fields declared in class
	 * 
	 * @param javaClass
	 * @return all fields
	 */
	private ArrayList<Attribute> getFields(JavaClass jc, Entity e) {
		Attribute a;
		Type t = null;
		ArrayList<Attribute> attributesClass = new ArrayList<Attribute>();
		Field[] fields = jc.getFields();

		for (Field f : fields) {
			a = new Attribute();
			a.setEntity(e);
			a.setModifier(getModifier(f));

			
			t = isPrimitive(f.getType().toString()) ? t = new Primitive(f.getType().toString()) : null;
			if (t == null) t = existType(f.getType().toString());
//			if (t == null) t = existEntity(f.getType().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
			if (t == null) {
				String className = "", packageName = "";
				className = f.getType().toString();
				boolean array = false;
				if (className.endsWith("[]")) {
					className = className.replaceAll("\\[", "")
							.replaceAll("\\]", "");
					array = true;
				}
				a.setArray(array);
				
				if (className.contains("."))
					packageName = className.substring(0,
								className.lastIndexOf("."));
				
				if (packageName.startsWith("java.") || packageName.startsWith("javax.") || packageName.contains(".sun.")) 
					t = new Class(f.getType().toString());
				else {
						Package p = existPackage(packageName);
						if (p == null) {
							p = new Package(packageName, api);
							// api.addPackage( p );
							packages.add(p);
//							relatePackage(p);
						}
						className = className.substring(className.lastIndexOf(".") + 1);
						t = new Class(className, p);
						entities.add((Entity) t);
					}
					tipos.add(t);
			}
			a.setName(f.getName());

			if ( t instanceof Entity) a.setEntityType((Entity)t);
			else if (t instanceof Primitive) a.setPrimitiveType((Primitive)t);
			
			attributesClass.add(a);
		}
		return attributesClass;
	}

	/**
	 * Get all methods of class
	 * 
	 * @param javaClass
	 * @return all methods
	 */
	private ArrayList<Method> getMethods(JavaClass jc, Entity e) {
		ArrayList<Method> allMethods = new ArrayList<Method>();
		Method m = null;
		com.sun.org.apache.bcel.internal.classfile.Method[] methods = jc.getMethods();

		for (com.sun.org.apache.bcel.internal.classfile.Method me : methods) {
			m = new Method();
			m.setModifier(getModifier(me));
			m.setEntidade(e);
			m.setName(me.getName());
			m.setParametros(getAttributesOfMethod(me, m));
			// ExceptionTable exception = me.getExceptionTable();
			// if ( exception != null ){
			// String[] excess = exception.getExceptionNames();
			// for (String s : excess) {
			// System.out.println(entity.getPackage() + "." + entity + "  "+
			// m.getNome() + "  " + s);
			// }
			// }
			Attribute returnn = getReturnType(me);
			if (returnn != null) m.setReturn(returnn);
			allMethods.add(m);
		}
		return allMethods;
	}

	/**
	 * get return type of specific method
	 * 
	 * @param me
	 * @param m
	 * @return Tipo
	 */
	private Attribute getReturnType(
			com.sun.org.apache.bcel.internal.classfile.Method me) {
		Type t =  isPrimitive(me.getReturnType().toString()) ? new Primitive(me.getReturnType().toString()) : null; 
		Attribute att ;
		boolean array = false;
		
		if (t == null) t = existType(me.getReturnType().toString());
//		if (t == null) t = existEntity(me.getReturnType().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
		if (t == null) {
				String className = "", packageName = "";
				className = me.getReturnType().toString();
				if (className.contains("."))
					packageName = className.substring(0,
							className.lastIndexOf("."));
				className = className.substring(className.lastIndexOf(".") + 1);
				if (packageName.startsWith("java.")
						|| packageName.startsWith("javax.")
						|| packageName.contains(".sun.")) {
					t = new Class();
					t.setName(me.getReturnType().toString());
				} else {
					if (className.endsWith("[]")) {
						className = className.replaceAll("\\[", "").replaceAll(
								"\\]", "");
						array = true;
					}

					Package p = existPackage(packageName);
					if (p == null) {
						p = new Package(packageName, api);
						// api.addPackage( p );
						packages.add(p);
//						relatePackage(p);
					}
					t = new Class(className, p);
					entities.add(((Entity) t));
				}
				tipos.add(t);
		}
		att = t instanceof Entity ? new Attribute((Entity) t) : new Attribute((Primitive) t);
		att.setArray(array);
		return att;
	}

	/**
	 * get all attributes of specific method
	 * 
	 * @param me
	 *            - method javaparser
	 * @param m
	 *            - Metodo (this package)
	 * @return all attributes of specific method
	 */
	private ArrayList<Attribute> getAttributesOfMethod(
			com.sun.org.apache.bcel.internal.classfile.Method me, Method m) {
		Attribute a;
		Type t = null;
		com.sun.org.apache.bcel.internal.generic.Type argumentTypes[] = me
				.getArgumentTypes();
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();

		for (com.sun.org.apache.bcel.internal.generic.Type type : argumentTypes) {
			a = new Attribute();
			a.setName(type.toString());
			a.setMetodo(m);
			t = isPrimitive(a.getName()) ? t = new Primitive(a.getName()) : null;
			if (t == null) t = existType(type.toString());
//			if (t == null) t = existEntity(type.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
			if (t == null) {
				String className = "", packageName = "";
				className = type.toString();
				if (className.contains(".")) packageName = className.substring(0,className.lastIndexOf("."));
				className = className.substring(className.lastIndexOf(".") + 1);
				if (packageName.startsWith("java.") || packageName.startsWith("javax.") || packageName.contains(".sun.")) t = new Class(type.toString());
				
				else {
					boolean array = false;
					if (className.endsWith("[]")) {
						className = className.replaceAll("\\[", "")
								.replaceAll("\\]", "");
						array = true;
					}

					Package p = existPackage(packageName);
					if (p == null) {
						p = new Package(packageName, api);
						packages.add(p);
//						relatePackage(p);
					}
					t = new Class(className, p);
					a.setArray(array);
					entities.add(((Entity) t));
				}
			}
			
			tipos.add(t);
			
			if ( t instanceof Entity) a.setEntityType((Entity)t);
			else if (t instanceof Primitive) a.setPrimitiveType((Primitive)t);
			
			attributes.add(a);
		}
		return attributes;
	}

	/**
	 * Verify if type is a primitive type of language (java)
	 * 
	 * @param type
	 *            - type witch will be
	 * @return
	 */
	private boolean isPrimitive(String type) {
		Matcher m = null;
		Pattern[] p = { Pattern.compile("boolean(\\[\\])*?"),
				Pattern.compile("char(\\[\\])*?"),
				Pattern.compile("byte(\\[\\])*?"),
				Pattern.compile("short(\\[\\])*?"),
				Pattern.compile("int(\\[\\])*?"),
				Pattern.compile("long(\\[\\])*?"),
				Pattern.compile("float(\\[\\])*?"),
				Pattern.compile("double(\\[\\])*?"),
				Pattern.compile("void(\\[\\])*?"), };

		for (int i = 0; i < p.length; i++) {
			m = p[i].matcher(type);
			if (m.matches())
				return true;
		}

		return false;
	}

	private Package existPackage(String name) {
		for (Package p : packages)
			if (p.getName().equals(name))
				return p;
		for (Package p : superPackages)
			if (p.getName().equals(name))
				return p;
		return null;
	}

	private void relatePackage(Package p) {
		if ( !(p.getName().contains(".")) ) return;
		String nameSuperPackage = p.getName().substring(0,p.getName().lastIndexOf("."));
		
		Package superPackage = existPackage(nameSuperPackage);
		
		if ( superPackage == null){
			superPackage = new Package(nameSuperPackage, api);
//			packages.add(superPackage);
			
			superPackage.addSubPackage(p);
			p.setSuperPackage(superPackage);
			superPackages.add(superPackage);
			if ( superPackage.getName().contains(".") ) relatePackage(superPackage);
		}
		else {
			superPackage.addSubPackage(p);
			p.setSuperPackage(superPackage);
		}
	}

	private Entity existEntity(String fullName) {
		for (Entity e : entities)
			if (e.getFullName().equals(fullName)) return e;
		return null;
	}
	
	private String getModifier(AccessFlags ac) {
		String modifier = "public";
		if (ac.isPublic())
			modifier = "public";
		else if (ac.isPrivate())
			modifier = "private";
		else if (ac.isProtected())
			modifier = "protected";
		return modifier;
	}

	private Type existType(String typeName) {
		for (Type t : tipos)
			if (t.getName().equals(typeName)) return t;
		return existEntity(typeName);
	}

	public File getApiFile() {
		return apiFile;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiFile(File apiFile) {
		this.apiFile = apiFile;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getRelease() {
		return release;
	}

	public String getApiDownloadURL() {
		return apiDownloadURL;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public void setApiDownloadURL(String apiDownloadURL) {
		this.apiDownloadURL = apiDownloadURL;
	}
}