package br.ufu.facom.osrat.distribution;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

public class Lognormal {

	private RealDistribution distribution;
	private Double mean;
	private Double sd;
	private Double ks;
	private Double chiSquare;
	private Double ad;
	
	public Lognormal(LogNormalDistribution distribution, Double ks, Double chiSquare, Double ad) {
		this.distribution = distribution;
		this.mean = distribution.getScale();
		this.sd = distribution.getShape();
		
		this.ks = ks;
		this.chiSquare = chiSquare;
		this.ad = ad;
	}
	
	public RealDistribution getDistribution() {
		return distribution;
	}
	
	public void setDistribution(RealDistribution distribution) {
		this.distribution = distribution;
	}
	
	public Double getMean() {
		return mean;
	}
	
	public void setMean(Double mean) {
		this.mean = mean;
	}
	
	public Double getSd() {
		return sd;
	}
	
	public void setSd(Double sd) {
		this.sd = sd;
	}
	
	public Double getKs() {
		return ks;
	}
	
	public void setKs(Double ks) {
		this.ks = ks;
	}
	
	public Double getChiSquare() {
		return chiSquare;
	}
	
	public void setChiSquare(Double chiSquare) {
		this.chiSquare = chiSquare;
	}
	
	public Double getAd() {
		return ad;
	}
	
	public void setAd(Double ad) {
		this.ad = ad;
	}
}
