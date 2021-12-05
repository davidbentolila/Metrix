package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.ufpa.linc.MetriX.configurations.Configurations;
@javax.persistence.Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Package implements Comparable<Package>, Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8188466377188532268L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	
	/*
	 * A {@link Package} have many {@link Entity}s
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Package} remove yours {@link Entity}s too.
	 * 
	 *  mappedBy="packagea" indicates which attribute in {@link Entity} make reference to {@link Package}
	 */
	@OneToMany(mappedBy="packagea",cascade=CascadeType.ALL)
	private List<Entity> entidades = null;
	
	private String name = null;

	/**
	 * A {@link Package} can be a super package, so can have many sub packages
	 */	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "superPackage_id", referencedColumnName = "id")
	private Package superPackage = null;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "superPackage")  
	private List<Package> subPackages = null; 
	/*
	 * A {@link Package} is associate to an {@link API}
	 */
	@ManyToOne
    @JoinColumn(name="api_id", nullable=true)
	private API api = null;

	public static boolean update = false; 

	boolean useInView = true;
	
	/*
	 * A {@link Package} have a set of {@link MetricsValues}s
	 * 
	 * CascadeType.ALL indicates that if remove an {@link Package} remove yours {@link MetricsValues}s too.
	 *  
	 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "metricsValues_id")
	private MetricsValues metricsValues = null;

	private static NumberFormat nf;
	
	public Package(){
		entidades = new ArrayList<Entity>();
		subPackages = new ArrayList<Package>();
		metricsValues = new MetricsValues();
		useInView = true;
		nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(2);
	}

	public Package(String name, API api){
		this();
		this.name = name;
		this.api = api;
		if( !api.getPackages().contains(this) ) api.addPackage( this );
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}
	
	public void setAPI(API api){
		this.api = api;
	}
	
	public API getAPI(){
		return api;
	}

	public Package getSuperPackage() {
		return superPackage;
	}

	public void setSuperPackage(Package superPackage) {
		this.superPackage = superPackage;
	}

	public List<Package> getSubPackages() {
		return subPackages;
	}

	public void setSubPackage(List<Package> subPackages) {
		this.subPackages = subPackages;
	}
	
	public void addSubPackage(Package subPackage) {
		if ( !subPackages.contains(subPackage) ) subPackages.add(subPackage);
	}

	/**
	 * @return List {@link Entity}s from package
	 */
	public List<Entity> getEntities(){
		return entidades;
	}
	
	public void setEntities(List<Entity> entidades){
		this.entidades = entidades;
	}
	
	
	public void addEntity(Entity e){
		e.setPackage(this);
		entidades.add( e );
	}
	
	public void removeEntity(Entity e){
		entidades.remove( e );
	}
	
//	public Status getStatus() {
//		return status;
//	}

//	public void setStatus(Status status) {
//		this.status = status;
//	}

	public boolean isShow() {
		return useInView;
	}

	public void setShow(boolean show) {
		this.useInView = show;
	}

	public String getMetricData(){
		double is = metricsValues.getIS();
		double nm = metricsValues.getNM();
		double np = metricsValues.getNP();
		double ppm = metricsValues.getPPM();
		double ahf = metricsValues.getAHF();
		double lm = metricsValues.getLM();
		
		String metricData = 
			"<html>" +
			"<tr><td colspan=\"2\" align=\"center\"><b>" + Configurations.getString("word.package") + " :</b>&nbsp;" + getName() + "</td></tr>" +
			"<tr><td align=\"center\"><b>"+ Configurations.getString("word.metric") +"</b></td><td align=\"center\"><b>" + Configurations.getString("word.value") + "</b></td></tr>" +
			"<tr><td><b>" + Configurations.getString("metrics.IS") + ":</b></td><td>" + is + "</tr>" +
			"<tr><td><b>" + Configurations.getString("metrics.NP") + ":</b></td><td>" + np + "</tr>" +
			"<tr><td><b>" + Configurations.getString("metrics.NM") + ":</b></td><td>" + nm + "</tr>" +
			"<tr><td><b>" + Configurations.getString("metrics.PPM") + ":</b></td><td>" + ppm + "</tr>" +
			"<tr><td><b>" + Configurations.getString("metrics.AHF") + ":</b></td><td>" + nf.format(ahf) + "%</tr>" +
			"<tr><td><b>" + Configurations.getString("metrics.LM") + ":</b></td><td>" + nf.format(lm) + "%</tr>" +
			"</table></center>" +
			"</html>" ;
		return metricData;
	}
	
	public String getShortName(){
		return getSuperPackage() == null ? getName() : getName().replace(getSuperPackage().getName()+".", "");
	}
	
	public int compareTo(Package p) {
		return getName().compareToIgnoreCase( p.getName() );
	}
	
	public String toString() {
		return getShortName();
	}

	public void setMetricsValues(MetricsValues metricsValues) {
		this.metricsValues = metricsValues;
	}

	public MetricsValues getMetricsValues() {
		return metricsValues;
	}
}