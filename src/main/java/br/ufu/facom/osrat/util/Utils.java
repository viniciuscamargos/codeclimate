package br.ufu.facom.osrat.util;

import java.util.List;

/**
 * Classe utils.
 *
 */
public final class Utils {

	private Utils() { }

	/**
	 * Retorna média.
	 * @param array
	 * @return double.
	 */
	public static double avarage(final List<Double> array) {
		double sum = 0;
		int size = array.size();

		for (double value : array) {
			sum += value;
		}

		return sum / size;
	}

}