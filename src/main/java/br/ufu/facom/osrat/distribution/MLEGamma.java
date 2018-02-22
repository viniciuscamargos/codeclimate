package br.ufu.facom.osrat.distribution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import br.ufu.facom.osrat.util.Utils;

/**
 * Calcula o MLEGamma.
 *
 */
public class MLEGamma {

	private double[] tbfs;
	private double precision;
	private double error;
	private double shape;
	private double scala;

	public MLEGamma(final double[] array, final double precision, final double error) {
		tbfs = array;
		this.precision = precision;
		this.error = error;
	}

	/**
	 * Realiza o cálculo.
	 * @return String
	 */
	public final String calcule() {
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		double y = stats.getMean();
		double lnY = Math.log(y);

		ArrayList<Double> x = new ArrayList<Double>();

		for (Double value : tbfs) {
			x.add(Math.log(value));
		}
		double avarageX = Utils.avarage(x);

		double a0 = y;
		double a1 = 0;

		while (error > precision) {
			a1 = (a0 * (Math.log(a0) - Gamma.digamma(a0)) / (lnY - avarageX));
			error = Math.abs(a1 - a0);
			if (error < precision) {
				break;
			}
			a0 = a1;
		}

		double alpha = a1;
		double beta = y / a1;
		
		this.shape = alpha;
		this.scala = beta;

		return "Alpha: " + alpha + " Beta: " + beta;
	}

	@Override
	public final String toString() {
		return "Algorithm MLE Gamma\n" + calcule();
	}
	
	public double getScala() {
		return scala;
	}
	
	public double getShape() {
		return shape;
	}
}