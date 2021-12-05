package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.ufpa.linc.MetriX.configurations.Configurations;

@javax.persistence.Entity
@Table(name="api")
public class API implements Comparable<API>,  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5279375979733630727L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id ;
	/*
	 * A API have many (or none) packages
	 * 
	 * CascadeType.ALL indicates that if remove an {@link API} remove yours packages too.
	 * 
	 * mappedBy="api" indicates which attribute in {@link Package} make reference to {@link API}
	 */
	@OneToMany(mappedBy="api", cascade=CascadeType.ALL)
	private List<Package> packages = null;
	
	private String nome = null;
	
	private String APIFileName = null;
	
	private String apiRelease = null;
	
	private String releaseDate = null;
	
	private String analisysDate = null;
	
	private String downloadURL = null;

	/*
	 * An API have one set of metrics
	 * 
	 * CascadeType.ALL indicates that if remove an {@link API} remove yours metrics too.
	 * 
	 * JoinColumn(name = "metricsValues_id") indicates that the column "metricsValues_id" in {@link API} table have id of line in {@link MetricsValues} table 
	 */
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "metricsValues_id")
	private MetricsValues metricsValues = null;
	
	@Transient
	private static String metricData = null;
	
	@Transient
	List<Entity> entities = null;
	
	public API() {
		nome = "";
		APIFileName = "";
		apiRelease = "";
		releaseDate = Calendar.getInstance().get( Calendar.YEAR ) + "/" + Calendar.getInstance().get( Calendar.MONTH ) + "/" + Calendar.getInstance().get( Calendar.DAY_OF_YEAR );
		analisysDate = Calendar.getInstance().get( Calendar.YEAR ) + "/" + Calendar.getInstance().get( Calendar.MONTH ) + "/" + Calendar.getInstance().get( Calendar.DAY_OF_YEAR );
		packages = new ArrayList<Package>();
		metricsValues = new MetricsValues();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return o nome da API
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome o nome da API
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return as entidades(classes e interfaces) que compoem a API
	 */
	public List<Package> getPackages() {
		return packages;
	}

	/**
	 * @param packages that make API
	 */
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
	public void addPackage( Package package_) {
		packages.add( package_ );
	}
	
	public int numberOfEntities(){
		int n = 0;
		for (Package p : getPackages())
			n += p.getEntities().size();
		return n;
	}
	
	public List<Entity> getAllEntities(){
		if ( entities == null ){
			entities = new ArrayList<Entity>();
			for (Package p : getPackages())
				for (Entity e : p.getEntities()) entities.add(e);
		}
		return entities;
	}
	
	public String getMetricData( ){
		if ( metricData == null ){
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			
			double is = metricsValues.getIS();
			double np = metricsValues.getNP();
			double nm = metricsValues.getNM();
			double ppm = metricsValues.getPPM();
			double ahf = metricsValues.getAHF();
			double lm = metricsValues.getLM();
			
			metricData =
				"<html>" +
				"<center><table>" +
					"<tr><td colspan=\"2\" align=\"center\"><b>" + Configurations.getString("word.api") + " :</b>&nbsp;" + getNome() + "</td></tr>" +
					"<tr><td align=\"center\"><b>"+ Configurations.getString("word.metric") +"</b></td><td align=\"center\"><b>" + Configurations.getString("word.value") + "</b></td></tr>" +
					"<tr><td><b>" + Configurations.getString("metrics.IS") + ":</b></td><td>" + is + "</tr>" +
					"<tr><td><b>" + Configurations.getString("metrics.NP") + ":</b></td><td>" + np + "</tr>" +
					"<tr><td><b>" + Configurations.getString("metrics.NM") + ":</b></td><td>" + nm + "</tr>" +
					"<tr><td><b>" + Configurations.getString("metrics.PPM") + ":</b></td><td>" + ppm + "</tr>" +
					"<tr><td><b>" + Configurations.getString("metrics.AHF") + ":</b></td><td>" + nf.format(ahf) + "%</tr>" +
					"<tr><td><b>" + Configurations.getString("metrics.LM") + ":</b></td><td>" + nf.format(lm) + "%</tr>" +
				"</table></center>" +
				"</html>" ;
		}
		return metricData;
	}
	
	public String getApiRelease() {
		return apiRelease;
	}

	public void setRelease(String apiRelease) {
		this.apiRelease = apiRelease;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAnalisysDate() {
		return analisysDate;
	}

	public void setAnalisysDate(String analisysDate) {
		this.analisysDate = analisysDate;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}
	
	public String getAPIFileName() {
		return APIFileName;
	}

	public void setAPIFileName(String fileName) {
		APIFileName = fileName;
	}
	
	public String toString(){
		return getNome();
	}

	public int compareTo(API a) {
		return getNome().compareToIgnoreCase( a.getNome() );
	}

	public void setMetricsValues(MetricsValues metricsValues) {
		this.metricsValues = metricsValues;
	}

	public MetricsValues getMetricsValues() {
		return metricsValues;
	}
}