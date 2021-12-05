package br.ufpa.linc.MetriX.parsers.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpa.linc.MetriX.api.model.API;
import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Method;
import br.ufpa.linc.MetriX.api.model.Package;
import br.ufpa.linc.MetriX.configurations.Files;

@SuppressWarnings("unused")
public class CodeReader {

	private List<Entity> APIEntities;
	List<String> implementsList, callMethods, importsList;
	List<Instance> instaceList;
	private Pattern p;
	private Matcher m ;
	
	public CodeReader(String projectPath, List<Entity> APIEntities) {
		File path = new File(projectPath);
		this.APIEntities = APIEntities;
		findJava( path.listFiles() );
	}
	
	private void findJava(File[] files){
		List<File> dir = new ArrayList<File>();
		for (File f : files)
			if ( f.getName().toLowerCase().endsWith(".java")) findMethods( f );
			else if ( f.isDirectory()  ) dir.add( f );

		while ( dir.size() > 0 ){
			findJava( dir.get(0).listFiles() );
			dir.remove(0);
		}
	}
	
	private void findMethods(File classFile){
		String packageName = "não encontrado";
		String className = "não encontrado";
		
		packageName = getPackage( classFile );
		
		importsList = getImportList( classFile );
		
		className = getClassName( classFile );
		
		instaceList = getInstances( classFile );
		
		callMethods = getCallMethods(classFile);
		
		for (Instance ie : instaceList) System.out.println(ie);
				
		for (String cm : callMethods) System.out.println(cm);
		
//		System.out.println( "  pacote  " + packageName);
//		
//		System.out.println( "  classe  " + className);
		
//		for (String s : importsList) {
			for (Entity e : APIEntities) {
				for (Method me : e.getMethods()) {
//					System.out.println(e.getFullName() + "." + me );
//					if ( me.getNome().contains("addVer")) System.out.println("existe");
				}
				
//				if (s.equals(e.getFullName() )) System.out.println("ele importa " + e.getFullName());	
//			}
		}
	}
	
	private String getPackage(File classFile){
		p = Pattern.compile( "package\\s(.*?);" );
		m = p.matcher( Files.fileToString(classFile) );
		if ( m.find() ) return m.group(1).trim();
		System.out.println("Can't find package name in " + classFile );
		return "";
	}
	
	private String getClassName(File classFile){
		p = Pattern.compile( "[(class)(interface)]\\s+(.*?)[\\s+\\{]" );
		m = p.matcher( Files.fileToString(classFile) );
		if ( m.find()) return m.group(1);
		System.out.println("Can't find class name in " + classFile);
		return "";
	}
	
	private List<String> getImportList(File classFile){
		List<String> importsList = new ArrayList<String>();
		p = Pattern.compile( "import\\s(.*?);" );
		m = p.matcher( Files.fileToString(classFile) );
		while ( m.find() ) importsList.add( m.group(1) );
		return importsList;
	}
	
	private List<Instance> getInstances(File classFile){
		List<Instance> instancesList = new ArrayList<Instance>();
		String classContent = Files.fileToString(classFile);
		for (Entity e : APIEntities)
			if ( importsList.contains( e.getFullName() )){
				p = Pattern.compile( e.getName() + "\\s+(\\w[\\d\\w]*)[\\s=;\n]");
				m = p.matcher( classContent.replaceAll("<.*>", " ").replaceAll("\\s+", " ") );
				while ( m.find() ) instancesList.add( new Instance(m.group(1), e) );
			}
			else{
				p = Pattern.compile( e.getFullName() + "\\s+(\\w[\\d\\w]*)[\\s=;\n]");
				m = p.matcher( classContent.replaceAll("<.*>", " ").replaceAll("\\s+", " ") );
				while ( m.find() ) instancesList.add( new Instance(m.group(1), e) );
			}
		
//		p = Pattern.compile( "<(.*?)> circleLayout");
//		m = p.matcher( classContent );
//		if ( m.find() ) {
//			System.out.println("nomdxxxxxxxxxxxxxxxxxxxxxe: " + m.group(1));
//		}
		return instancesList;
	}
	
	private List<String> getCallMethods(File classFile){
		List<String> callMethods = new ArrayList<String>();
		for (Instance i : instaceList) 
			for (Method me : i.getEntity().getMethods()) {
				p = Pattern.compile( i.getEntity().getName() + "\\s*\\.\\s*" + me.getName());
				m = p.matcher( Files.fileToString(classFile) );
				if ( m.find() ) callMethods.add( me.getEntity().getFullName() + "." + me.getName() );
			}
		return callMethods;
	}
	
	public static API getFakeAPI(){
		API api = new API();
		api.setNome("Jung");
		Package p = new Package("edu.uci.ics.jung.algorithms.layout", api);
		Entity e = new Class("CircleLayout");
		p.addEntity(e);
		

		p = new Package("edu.uci.ics.jung.graph", api);
		e = new Class("Graph");
		
		Method m = new Method();
		m.setName("addVertex");
		m.setEntidade(e);
		
		p.addEntity(e);

		
		e = new Class("UndirectedSparseGraph");
		p.addEntity(e);

		//-----------------		
		
		p = new Package("edu.uci.ics.jung.visualization", api);
		e = new Class("DefaultVisualizationModel");
		p.addEntity(e);
		
		e = new Class("GraphZoomScrollPane");
		p.addEntity(e);
		
		e = new Class("RenderContext");
		p.addEntity(e);
		
		e = new Class("VisualizationModel");
		p.addEntity(e);
		
		e = new Class("VisualizationViewer");
		p.addEntity(e);
		
		p = new Package("edu.uci.ics.jung.visualization.control", api);
		e = new Class("DefaultModalGraphMouse");
		p.addEntity(e);
		
		e = new Class("ModalGraphMouse");
		p.addEntity(e);
		
		p = new Package("edu.uci.ics.jung.visualization.decorators", api);
		e = new Class("ToStringLabeller");
		p.addEntity(e);
		
		p = new Package("edu.uci.ics.jung.visualization.renderers", api);
		e = new Class("Renderer");
		p.addEntity(e);
		
		return api;
	}
	
	private class Instance{
		private String name = null;
		private Entity entity = null;
		
		public Instance(String name, Entity entity) {
			this.name = name;
			this.entity = entity;
		}
		
		public String getName() {
			return name;
		}

		public Entity getEntity() {
			return entity;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setEntity(Entity entity) {
			this.entity = entity;
		}
		
		@Override
		public String toString() {
			return entity.getName() + " " + name;
		}
	}
}
