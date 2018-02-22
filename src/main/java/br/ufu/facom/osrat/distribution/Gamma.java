package br.ufu.facom.osrat.distribution;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

public class Gamma {

	private RealDistribution distribution;
	private Double scale;
	private Double shape;
	private Double ks;
	private Double chiSquare;
	private Double ad;
	
	public Gamma(GammaDistribution distribution, Double ks, Double chiSquare, Double ad) {
		this.distribution = distribution;
		this.scale = distribution.getScale();
		this.shape = distribution.getShape();
		
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
	
	public Double getScale() {
		return scale;
	}
	
	public void setScale(Double scale) {
		this.scale = scale;
	}
	
	public Double getShape() {
		return shape;
	}
	
	public void setShape(Double shape) {
		this.shape = shape;
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
