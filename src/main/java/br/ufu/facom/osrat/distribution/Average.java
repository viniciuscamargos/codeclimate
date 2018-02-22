package br.ufu.facom.osrat.distribution;

import java.util.List;

public class Average {

	private List<Double> tbf;

	public Average(final List<Double> tbf) {
		this.tbf = tbf;
	}

	public final double calcule() {
		return algorithm();
	}

	private double algorithm() {
		int size = tbf.size();
		double sum = 0;

		for (Double value : tbf) {
			sum += value;
		}
		return (sum / size);
	}

	@Override
	public final String toString() {
		return "Algorithm average result: " + calcule();
	}
}