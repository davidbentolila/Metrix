package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import br.ufpa.linc.MetriX.configurations.Configurations;

@javax.persistence.Entity
public class Method implements Comparable<Method>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6666052179726571923L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	/*
	 * A {@link Method} is associate to one {@link Entity}
	 * 
	 * mappedBy="entity_id" indicates which attribute in {@link Entity} make
	 * reference to {@link Method}
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "entity_id")
	private Entity entity;

	/*
	 * A {@link Method} can throw exceptions (that are {@link Entity}s)
	 */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Entity> exceptions;

	/*
	 * A {@link Method} have (or not) parameters (that are the same as {@link
	 * Attribute})
	 */
	@OneToMany(cascade=CascadeType.ALL)
	private List<Attribute> parameters;

	private String modifier = null;

//	/*
//	 * A {@link Method} have a return type that can be {@link Primitive}
//	 */
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name = "primitive_return_id")
//	private Primitive primitiveReturn;
//
//	/*
//	 * A {@link Method} have a return type that can be {@link Entity}
//	 */
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "entity_return_id")
//	private Entity entityReturn;

	/*
	 * A {@link Method} have a return type that can be {@link Entity}
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "return_id")
	private Attribute returnn;

	/*
	 * A {@link Method} have a set of {@link MetricsValues}s
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Method} remove yours
	 * {@link MetricsValues}s too.
	 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "metricsValues_id")
	private MetricsValues metricsValues = null;

	@Transient
	private Set<Entity> dependencies;
	public Method() {
		parameters = new ArrayList<Attribute>();
//		primitiveReturn = null;
//		entityReturn = null;
		returnn = null;
		modifier = "public";
		setMetricsValues(new MetricsValues());
	}

	public String getHTMLStructure() {

		List<Attribute> parameters = getParameters();
		int count = 0;
		String structure = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>" + getModifier()
				+ "</b> " + getReturnType();

		if (getName().equals("<init>"))
			structure += " " + getEntity().getName() + " (";
		else
			structure += " " + getName() + " (";

		for (Attribute a : parameters) {
			if (a.getName() != null) {
				structure += a.getName();
				if (++count < parameters.size())
					structure += ", ";
			}
		}
		structure += ");<br>";
		return structure;
	}
	
	public String getStringStructure() {

		List<Attribute> parameters = getParameters();
		int count = 0;
		String structure = getModifier() + " " + getReturnType();

		if (getName().equals("<init>"))
			structure += " " + getEntity().getName() + " (";
		else
			structure += " " + getName() + " (";

		for (Attribute a : parameters) {
			if (a.getName() != null) {
				structure += a.getName();
				if (++count < parameters.size())
					structure += ", ";
			}
		}
		structure += ");";
		return structure;
	}

	public String getMetricData() {
		double is = getMetricsValues().getIS();
		double np = getMetricsValues().getNP();

		String metricData = "<html>"
				+ "<tr><td colspan=\"2\" align=\"center\"><b>"
				+ Configurations.getString("word.method")
				+ " :</b>&nbsp;" + getName() + "</td></tr>"
				+ "<tr><td colspan=\"2\" align=\"left\"><b>"
				+ Configurations.getString("word.modifier")
				+ " :</b>&nbsp;" + getModifier() + "</td></tr>";

		if (getParameters().size() > 0)
			metricData += "<tr><td colspan=\"2\" align=\"left\"><b>"
					+ Configurations.getString("word.parameters")
					+ " :</b>&nbsp;";

		for (Attribute a : getParameters())
			metricData += a.getName() + ", ";

		if (metricData.endsWith(", "))
			metricData = metricData.substring(0, metricData.length() - 2);

		metricData += "</td></tr>" + "<tr><td align=\"center\"><b>"
				+ Configurations.getString("word.metric")
				+ "</b></td><td align=\"center\"><b>"
				+ Configurations.getString("word.value")
				+ "</b></td></tr>" + "<tr><td><b>"
				+ Configurations.getString("metrics.IS")
				+ ":</b></td><td>" + is + "</tr>" + "<tr><td><b>"
				+ Configurations.getString("metrics.NP")
				+ ":</b></td><td>" + np + "</tr>" + "</table></center>"
				+ "</html>";
		return metricData;
	}

	/**
	 * @return method name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            - Method name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return a entidade do m�todo
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * @param entidade
	 *            - method's {@link Entity}
	 */
	public void setEntidade(Entity entidade) {
		this.entity = entidade;
	}

	/**
	 * @return os parametros do metodo
	 */
	public List<Attribute> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            - method's parameters
	 */
	public void setParametros(List<Attribute> parameters) {
		this.parameters = parameters;
	}

	public Integer getId() {
		return id;
	}

//	public Primitive getPrimitiveReturn() {
//		return primitiveReturn;
//	}
//
//	public void setPrimitiveReturn(Primitive primitiveReturn) {
//		this.primitiveReturn = primitiveReturn;
//	}
//
//	public Entity getEntityReturn() {
//		return entityReturn;
//	}
//
//	public void setEntityReturn(Entity entityReturn) {
//		this.entityReturn = entityReturn;
//	}

	public Attribute getReturn() {
		return returnn;
	}

	public void setReturn(Attribute returnn) {
		this.returnn = returnn;
	}
	
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getReturnType() {
		if (getReturn() != null) return getReturn().getType();
		return "void";
	}

	public List<Entity> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<Entity> exceptions) {
		this.exceptions = exceptions;
	}

	public Set<Entity> getDependencies() {
		if ( dependencies == null ){
			dependencies = new HashSet<Entity>();
			for (Attribute a : getParameters())
				if (a.getEntityType() != null && a.getEntityType().getPackage() != null)
						dependencies.add(a.getEntityType());
	
			if (getReturn() != null && getReturn().getEntityType() != null && getReturn().getEntityType().getPackage() != null)
					dependencies.add(getReturn().getEntityType());
		}
		return dependencies;
	}

	public int compareTo(Method o) {
		return getName().compareTo(o.getName());
	}

	public String toString() {
		if (getName().equals("<init>"))
			return getEntity().getName();
		else
			return getName();
	}

	public void setMetricsValues(MetricsValues metricsValues) {
		this.metricsValues = metricsValues;
	}

	public MetricsValues getMetricsValues() {
		return metricsValues;
	}
	
	public String getFullName(){
		return getEntity().getFullName() + "." + getName();
	}
}