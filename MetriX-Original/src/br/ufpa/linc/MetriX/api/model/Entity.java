package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.ufpa.linc.MetriX.configurations.Configurations;

/**
 * Can be {@link Class} or {@link Interface}
 * 
 * @author david
 * 
 */
/*
 * @Inheritance(strategy=InheritanceType.JOINED) indicates that that data model
 * have tables {@link Class} and {@link Interface} that are referred in {@link
 * Entity}
 */
@javax.persistence.Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Entity implements Type, Comparable<Entity>, Serializable {// ,
																		// Metrics
																		// {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6885590278066446573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	/*
	 * A {@link Package} have many {@link Entidade}
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Entidade} remove your
	 * {@link Package} too.
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "package_id", nullable = true)  
	private Package packagea;

	private String modifier = null;

	/*
	 * A {@link Entity} have many {@link Method}s
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Entity} remove yours
	 * {@link Method}s too.
	 */
	@Basic
	@OneToMany(cascade=CascadeType.ALL, mappedBy="entity")
	private List<Method> methods;

	/*
	 * A {@link Entity} have many {@link Attribute}s (named fields)
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Entity} remove yours
	 * {@link Attribute}s too.
	 */
	@Basic
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "entity")
	private List<Attribute> atttributes;

	/*
	 * A {@link Entity} have a set of {@link MetricsValues}s
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Entity} remove yours
	 * {@link MetricsValues}s too.
	 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "metricsValues_id")
	private MetricsValues metricsValues = null;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "superClass_id", referencedColumnName = "id")
	private Entity superClass = null;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "superClass")  
	private List<Entity> subClasses = null;
	
	@ManyToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="Entity_Interfaces", joinColumns={@JoinColumn(name="Entity_id")}, inverseJoinColumns={@JoinColumn(name="Interface_id")})    
	private List<Interface> interfaces = null;
	
	private boolean abstrakt;

	@Transient
	private Set<Entity> dependentEntities, dependencies;
	
	public Entity() {
		name = "";
		methods = new ArrayList<Method>();
		atttributes = new ArrayList<Attribute>();
		subClasses = new ArrayList<Entity>();
		interfaces = new ArrayList<Interface>();
		modifier = "public";
		// array = false;
		metricsValues = new MetricsValues();
	}

	public List<Attribute> getPublicAttributes() {
		List<Attribute> attributes = new ArrayList<Attribute>();
		for (Attribute a : getAttributes())
			if (a.getModifier().equals("public"))
				attributes.add(a);
		return attributes;
	}

	/**
	 * @return os construtores da classe
	 */
	public List<Method> getConstructors() {
		List<Method> methods = getMethods();
		List<Method> contrutctors = new ArrayList<Method>();
		for (Method m : methods) {
			if (m.getName().equals("<init>"))
				contrutctors.add(m);
		}
		return contrutctors;
	}

	public String getStructure() {
		double is = metricsValues.getIS();

		String structure = "<html>" + "<b>" + "IS" + "</b>&nbsp; " + is
				+ "<br>" + "<b>" + "Calls" + "</b>&nbsp; "
				+ metricsValues.getCalls() + "<br>" + "<b>" + "Package"
				+ "</b>&nbsp; " + getPackage().getName() + "<br>" + "<b>"
				+ getModifier() + "</b> " + getName() + " {<br><br>";
		for (Attribute a : getAttributes())
			if (a.getEntityType() != null )
				structure += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>"
						+ a.getModifier() + "</b> "
						+ a.getEntityType().getName() + " " + a.getName()
						+ ";<br>";

		structure += "<br>";
		for (Method m : getMethods())
			if (getModifier().equals("public"))
				structure += m.getHTMLStructure();

		structure += "}</html>";
		return structure;
	}

	public String getMetricData() {
		double is = metricsValues.getIS();
		double np = metricsValues.getNP();
		double nm = metricsValues.getNM();
		double ppm = metricsValues.getPPM();
		double cbo = metricsValues.getCBO();
		double dit = metricsValues.getDIT();
		double npa = metricsValues.getNPA();
		double ahf = metricsValues.getAHF();
		double lm = metricsValues.getLM();
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		String type = this instanceof Class ? Configurations
				.getString("word.class") : Configurations
				.getString("word.interface");
		String structure = "<html>" + "<center><table>"
				+ "<tr><td colspan=\"2\" align=\"center\"><b>"
				+ type
				+ " :</b>&nbsp;"
				+ getName()
				+ "</td></tr>"
				+ "<tr><td colspan=\"2\" align=\"left\"><b>"
				+ Configurations.getString("word.package")
				+ " :</b>&nbsp;"
				+ getPackage().getName()
				+ "</td></tr>"
				+ "<tr><td align=\"center\"><b>"
				+ Configurations.getString("word.metric")
				+ "</b></td><td align=\"center\"><b>"
				+ Configurations.getString("word.value")
				+ "</b></td></tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.IS")
				+ ":</b></td><td>"
				+ is
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.NP")
				+ ":</b></td><td>"
				+ np
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.NM")
				+ ":</b></td><td>"
				+ nm
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.PPM")
				+ ":</b></td><td>"
				+ ppm
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.CBO")
				+ ":</b></td><td>"
				+ cbo
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.DIT")
				+ ":</b></td><td>"
				+ dit
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.NPA")
				+ ":</b></td><td>"
				+ npa
				+ "</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.AHF")
				+ ":</b></td><td>"
				+ nf.format(ahf)
				+ "%</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.LM")
				+ ":</b></td><td>"
				+ nf.format(lm)
				+ "%</tr>"
				+ "<tr><td><b>"
				+ Configurations.getString("metrics.Call")
				+ ":</b></td><td>"
				+ metricsValues.getCalls()
				+ "</tr>"
				+ "</table></center>" + "</html>";
		return structure;
	}

	/**
	 * @return the Entity {@link Package}
	 */
	public Package getPackage() {
		return packagea;
	}

	/**
	 * @param packagea
	 *            - Entity package
	 */
	public void setPackage(Package packagea) {
		this.packagea = packagea;
	}

	/**
	 * @return Entity {@link Method}s
	 */
	public List<Method> getMethods() {
		return methods;
	}

	/**
	 * @param methods
	 *            - Entity {@link Method}s
	 */
	public void setMetodos(List<Method> methods) {
		this.methods = methods;
	}

	/**
	 * @return Entity {@link Attribute}s
	 */
	public List<Attribute> getAttributes() {
		return atttributes;
	}

	/**
	 * @param atttributes
	 *            - Entity {@link Attribute}s
	 */
	public void setAttributes(List<Attribute> atttributes) {
		this.atttributes = atttributes;
	}

	public Integer getId() {
		return id;
	}

	public String getModifier() {
		return modifier;
	}

	/**
	 * 
	 * @param modifier
	 *            must be public - all can access ; private ; and protected ;
	 * 
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 
	 * @param usePrivateMethods
	 * @return the Entity list that are related whith that one
	 */
	public Set<Entity> getDependencies(String modifier) {
		if ( dependencies == null ) {
			dependencies = new HashSet<Entity>();
			
			for (Attribute a : getAttributes())
			if (a.getEntityType() != null ) dependencies.add(a.getEntityType());

			for (Method m : getMethods())
				if (modifier == null  || m.getModifier().equals("public")) {
					for (Entity entity : m.getDependencies())
						dependencies.add(entity);
				}
		}
		return dependencies;
	}
	/**
	 * 
	 * @return Entities that have instances / parameters / returns of type of this Entity
	 */
	public Set<Entity> getDependentEntities() {
		if ( dependentEntities == null){
			dependentEntities = new HashSet<Entity>();
			
			dependentEntities.add(this);
			for (Entity e : getPackage().getAPI().getAllEntities()) {
				if ( dependentEntities.contains(e)) continue; 
				analisaSuperClass(e, dependentEntities);
				
				if ( dependentEntities.contains(e)) continue;
				analisaAtributos(e, dependentEntities);
				
				if ( dependentEntities.contains(e)) continue;
				analisaMetodos(e, dependentEntities);
			}
		}
		return dependentEntities;
	}

	private void analisaSuperClass(Entity e, Set<Entity> dependentEntities) {
		if (e.getSuperClass() != null && e.getSuperClass().getFullName().equals(this.getFullName()))
			dependentEntities.add(e);
	}

	private void analisaAtributos(Entity e, Set<Entity> dependentEntities) {
		for (Attribute a : e.getAttributes())
			if (a.getModifier().equals("public"))
				if (a.getEntityType() != null && a.getEntityType().getFullName().equals(this.getFullName()))
					dependentEntities.add(e);
	}

	private void analisaMetodos(Entity e, Set<Entity> dependentEntities) {
		for (Method m : e.getMethods())
			if (m.getModifier().equals("public"))
				for (Entity clazz : m.getDependencies())
					if (clazz.getFullName().equals(this.getFullName()))
						dependentEntities.add(e);
	}

	public MetricsValues getMetricsValues() {
		return metricsValues;
	}

	public void setMetricsValues(MetricsValues metricsValues) {
		this.metricsValues = metricsValues;
	}

	public Entity getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Entity superClass) {
		this.superClass = superClass;
	}

	public List<Entity> getSubClasses() {
		return subClasses;
	}

	public void setSubClasses(List<Entity> subClasses) {
		this.subClasses = subClasses;
	}
	
	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}
	
	public boolean isAbstrakt() {
		return abstrakt;
	}

	public void setAbstrakt(boolean abstrakt) {
		this.abstrakt = abstrakt;
	}
	
	public int compareTo(Entity o) {
		return getFullName().compareTo(o.getFullName());
	}

	public String toString() {
		return getName();
	}

	public void addMethod(Method function) {
		methods.add(function);
	}

	public void addSubClass(Entity e){
		subClasses.add(e);
	}
	
	public void addInterface(Interface i){
		interfaces.add(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return Entity name with {@link Package} name. like package.entity
	 */
	public String getFullName() {
		if (getPackage() != null && getPackage().getName().length() > 0)
			return getPackage().getName() + "." + getName();
		else
			return getName();
	}
}