package br.ufpa.linc.MetriX.analysis;

import java.util.HashSet;
import java.util.Set;

import br.ufpa.linc.MetriX.api.model.Class;
import br.ufpa.linc.MetriX.api.model.Entity;
import br.ufpa.linc.MetriX.api.model.Interface;
import br.ufpa.linc.MetriX.api.model.Method;

/**
 *@author david
 *Date: 27/03/2011
 */
public class UseCase {

	private String label;
	private Set<String> packages;
	private Set<String> classes;
	private Set<String> methods;
	private Set<String> entitiesToRelateName;
	private Set<Entity> relateEntities;
	private Set<Method> relateMethods;
	private MyList statistics;
	private int countClass;
	private int countInterfaces;
	

	public UseCase(int id) {
		this(id, null);
	}
	
	public UseCase(int id, String label) {
		this.label = label;
		this.methods = new HashSet<String>();
		this.classes = new HashSet<String>();
		this.packages = new HashSet<String>();
		entitiesToRelateName = new HashSet<String>();
		relateEntities = new HashSet<Entity>();
		relateMethods = new HashSet<Method>();
		statistics = new MyList();
	}
	
	public UseCase(int id, String label, Set<String> packages, Set<String> classes, Set<String> methods) {
		this.label = label;
		this.methods = methods;
		this.classes = classes;
		this.packages = packages;
		entitiesToRelateName = new HashSet<String>();
		for (String s : classes) entitiesToRelateName.add(s);
		relateEntities = new HashSet<Entity>();
		relateMethods = new HashSet<Method>();
		statistics = new MyList();
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Set<String> getPackages() {
		return packages;
	}
	
	public void addPackage(String packagee) {
		packages.add(packagee);
	}

	public void setPackages(Set<String> packages) {
		this.packages = packages;
	}

	public Set<String> getClasses() {
		return classes;
	}

	public void addClass(String clazz) {
		classes.add(clazz);
		addEntitiesToRelateName(clazz);
	}
	
	public void setClasses(Set<String> classes) {
		this.classes = classes;
	}
	
	public Set<String> getMethods() {
		return methods;
	}

	public void setMethods(Set<String> methods) {
		this.methods = methods;
	}
	
	public void addMethod(String methods) {
		this.methods.add(methods);
	}
	
	public void setEntitiesToRelateName(Set<String> entitiesToRelate) {
		this.entitiesToRelateName = entitiesToRelate;
	}
	
	public Set<String> getEntitiesToRelateName() {
		return entitiesToRelateName;
	}

	public void addEntitiesToRelateName(String entitiyName) {
		entitiesToRelateName.add(entitiyName);
	}

	public MyList getStatistics() {
		return statistics;
	}

	public void setStatistics(MyList analises) {
		this.statistics = analises;
	}
	
	public Set<Entity> getRelatedEntities() {
		return relateEntities;
	}

	public void setRelatedEntities(Set<Entity> relatedEntities) {
		this.relateEntities = relatedEntities;
	}
	
	public void addRelatedEntities(Entity entity) {
		if (entity instanceof Class) countClass++;
		else if (entity instanceof Interface) countInterfaces++;
		this.relateEntities.add(entity);
	}
	
	public Set<Method> getRelateMethods() {
		return relateMethods;
	}

	public void setRelateMethods(Set<Method> relateMethods) {
		this.relateMethods = relateMethods;
	}
	
	public void addRelateMethods(Method method) {
		this.relateMethods.add(method);
	}
	
	public int getCountClass() {
		return countClass;
	}

	public void setCountClass(int countClass) {
		this.countClass = countClass;
	}

	public int getCountInterfaces() {
		return countInterfaces;
	}

	public void setCountInterfaces(int countInterfaces) {
		this.countInterfaces = countInterfaces;
	}
	
	@Override
	public String toString() {
		String tostring = "name=" + getLabel() + "\n";
		if (getMethods() != null && getMethods().size() > 0 )
			for (String s : getMethods() ) 
				tostring += "\tmethod="+s+"\n";
		if (getClasses() != null && getClasses().size() > 0 )
			for (String s : getClasses() )
				tostring += "\tclass="+s+"\n";;
		if (getPackages() != null && getPackages().size() > 0 )
			for (String s : getPackages() )
				tostring += "\tpackage="+s+"\n";;
		return tostring;
	}
}
