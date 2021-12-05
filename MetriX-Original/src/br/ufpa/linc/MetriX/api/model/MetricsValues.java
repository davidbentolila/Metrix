package br.ufpa.linc.MetriX.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MetricsValues implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 819719111818976814L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private double IS_, NM, NP, PPM, calls, StylosCD, PRearrangement, classLinks, bugCount, cbo, npa, classCoupling, laa, noav, ahf, dit, lm;
	
	public double getStylosCD() {
		return StylosCD;
	}

	public void setStylosCD(double stylosCD) {
		StylosCD = stylosCD;
	}

	public MetricsValues() {
		IS_ = NM = NP = PPM = calls = StylosCD = PRearrangement = classLinks= bugCount = cbo = npa = classCoupling = laa = noav = ahf = dit = lm =0;
	}

	public Integer getId()								{	return id;		}
	public void setId(Integer id)						{	this.id = id;	}
	
	/**
	 * @return amount of bugs 
	 */
	public double getBugCount()							{	return bugCount;			}
	/**
	 * @param bugCount - amount of bugs 
	 */
	public void setBugCount(double bugCount)			{	this.bugCount = bugCount;	}
	
	public double getClassLinks()						{	return classLinks;				}
	public void setClassLinks(double classLinks)		{	this.classLinks = classLinks;	}
	
	public double getIS()								{	return IS_;	}
	public void setIS(double is)						{	IS_ = is;	}
	
	public double getNM()								{	return NM;	}
	public void setNM(double nm)						{	NM = nm;	}
	
	public double getNP() 								{	return NP;	}
	public void setNP(double np) 						{	NP = np;	}
	
	public double getPPM()								{	return PPM;	}
	public void setPPM(double ppm)						{	PPM = ppm;	}
	
	public double getCalls()							{	return calls;		}
	public void setCalls(double calls)					{	this.calls = calls;	}
	
	public double getPRearrangement()					{	return PRearrangement;			}
	public void setPRearrangement(double rearrangement) {	PRearrangement = rearrangement;	}
	
	/**
	 * @return Coupling Between Object Classes
	 */
	public double getCBO()								{	return cbo;		}
	/**
	 * 
	 * @param cbo - Coupling Between Object Classes
	 */
	public void setCBO(double cbo)						{	this.cbo = cbo;	}
	
	/**
	 * 
	 * @return Number of Public Attribute
	 */
	public double getNPA()								{	return npa;		}
	/**
	 * 
	 * @param npa - Number of Public Attribute
	 */
	public void setNPA(double npa)						{	this.npa = npa;	}
	
	/**
	 * 
	 * @return Class Coupling
	 */
	public double getClassCoupling()					{	return classCoupling;				}
	/**
	 * 
	 * @param classCoupling - ClassCoupling
	 */
	public void setClassCoupling(double classCoupling)	{	this.classCoupling = classCoupling;	}
	
	/**
	 * 
	 * @return Locality Attribute Accesses
	 */
	public double getClassLAA()							{	return laa;				}
	/**
	 * 
	 * @param laa - Locality Attribute Accesses
	 */
	public void setClassLAA(double laa)					{	this.laa = laa;	}
	
	/**
	 * 
	 * @return Number of Accessed Variables
	 */
	public double getClassNOAV()						{	return noav;				}
	/**
	 * 
	 * @param laa - Number of Accessed Variables
	 */
	public void setClassNOAV(double noav)				{	this.noav = noav;	}
	
	/**
	 * 
	 * @return Attribute Hiding Factor
	 */
	public double getAHF()							{	return ahf;				}
	/**
	 * 
	 * @param laa - Attribute Hiding Factor
	 */
	public void setAHF(double ahf)					{	this.ahf = ahf;	}
	
	/**
	 * 
	 * @return Depth of Inheritance Tree
	 */
	public double getDIT()							{	return dit;				}
	/**
	 * 
	 * @param dit - Depth of Inheritance Tree
	 */
	public void setDIT(double dit)					{	this.dit = dit;	}
	
	/**
	 * 
	 * @return lm - Large Methods (factor)
	 */
	public double getLM()							{	return lm;				}
	/**
	 * 
	 * @param lm - Large Methods (factor)
	 */
	public void setLM(double lm)					{	this.lm = lm;	}
}
