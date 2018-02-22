package br.ufu.facom.osrat.distribution;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;

public class CalcDistribution {

	double[] data;
	double[] lUpps;
	double[] lInfs;
	
	public CalcDistribution(List<Double> tbfs) {
		Collections.sort(tbfs, new Comparator<Double>() {
			@Override
			public int compare(Double d1, Double d2) {

				return d1.compareTo(d2);
			}
		});
		data = ArrayUtils.toPrimitive(tbfs.toArray(new Double[0]));
	}
	
	public CalcDistribution(double[] tbfs) {
		data = tbfs;
	}

	public Exponential calcExponential() {

		Double sum = 0D;
		for (Double tbf : data) {
			sum += tbf;
		}

		int qte = data.length;

		Double mean = sum / qte;

		mean = new BigDecimal(mean).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();

		System.out.println("Exponencial - mean:" + mean);

		// KS
		ExponentialDistribution exponentialDist = new ExponentialDistribution(mean);
		KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();
		Double ksTest = ks.kolmogorovSmirnovStatistic(exponentialDist, data);
//		if (Double.isFinite(ksTest)) {
//			ksTest = new BigDecimal(ksTest).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("KS EXPONENTIAL:" + ksTest);

		// ChiSquare
		Double chi = calcChiSquare(data, exponentialDist);
//		if (Double.isFinite(chi)) {
//			chi = new BigDecimal(chi).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("ChiSquare EXPO:" + chi);

		// AD
		Double ad = calcAndersonDarling(data, exponentialDist);
//		if (Double.isFinite(ad)) {
//			ad = new BigDecimal(ad).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("AD EXPONENTIAL:" + ad);

		Exponential exponential = new Exponential(exponentialDist, ksTest, chi, ad);
		return exponential;
	}

	public Lognormal calcLognormal() {
		List<Double> inLogs = new ArrayList<Double>();

		int n = data.length;

		Double sum = 0D;
		for (Double tbf : data) {
			Double log = Math.log(tbf);
			sum += log;
			inLogs.add(log);
		}

		Double mean = sum / n;

		Double sc = 0D;
		for (Double log : inLogs) {
			Double quad = Math.pow((log - mean), 2);
			sc += quad;
		}

		Double var = sc / n;
		Double dp = Math.sqrt(var);

		mean = new BigDecimal(mean).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		dp = new BigDecimal(dp).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();

		System.out.println("Lognormal - mean:" + mean + ", dp: " + dp);

		LogNormalDistribution logNormalDist = new LogNormalDistribution(mean, dp);

		// KS
		KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();
		Double ksTest = ks.kolmogorovSmirnovStatistic(logNormalDist, data);
//		if (Double.isFinite(ksTest.doubleValue())) {
//			ksTest = new BigDecimal(ksTest).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("KS LOGNORMAL:" + ksTest);

		// ChiSquare
		Double chi = calcChiSquare(data, logNormalDist);
//		if (Double.isFinite(chi)) {
//			chi = new BigDecimal(chi).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("ChiSquare LOGNORMAL:" + chi);

		// AD
		Double ad = calcAndersonDarling(data, logNormalDist);
//		if (Double.isFinite(ad)) {
//			ad = new BigDecimal(ad).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("AD LOGNORMAL:" + ad);

		Lognormal lognormal = new Lognormal(logNormalDist, ksTest, chi, ad);
		return lognormal;
	}

	public Gamma calcGamma() {

		int n = data.length;

		Double s = 0D;
		for (Double tbf : data) {
			s += tbf;
		}

		Double m = s / n;

		Double s2 = 0D;
		List<Double> indados = new ArrayList<Double>();
		for (Double tbf : data) {
			Double log = Math.log(tbf);
			indados.add(log);
			s2 += log;
		}

		Double m2 = s2 / n;
		Double lnmedia = Math.log(m);
		Double A0 = m;

		Double erro = null;
		while (erro == null || erro > (Math.pow(10, -6))) {
			Double A1 = (A0 * (Math.log(A0) - Mathematics.digamma(A0))) / (lnmedia - m2);
			erro = Math.abs(A1 - A0);
			A0 = A1;

		}
		Double alfa = A0;
		Double beta = m / A0;

		alfa = new BigDecimal(alfa).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		beta = new BigDecimal(beta).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();

		System.out.println("Gamma - alfa:" + alfa + ", beta: " + beta);

		GammaDistribution gammaDist = new GammaDistribution(alfa, beta);

		// KS
		KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();
		Double ksTest = ks.kolmogorovSmirnovStatistic(gammaDist, data);
//		if (Double.isFinite(ksTest)) {
//			ksTest = new BigDecimal(ksTest).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("KS GAMMA:" + ksTest);

		// ChiSquare
		Double chi = calcChiSquare(data, gammaDist);
//		if (Double.isFinite(chi)) {
//			chi = new BigDecimal(chi).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("ChiSquare GAMMA:" + chi);

		Double ad = calcAndersonDarling(data, gammaDist);
//		if (Double.isFinite(ad)) {
//			ad = new BigDecimal(ad).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("AD GAMMA:" + ad);

		Gamma gamma = new Gamma(gammaDist, ksTest, chi, ad);
		return gamma;
	}

	public Weibull calcWeibull() {

		int n = data.length;

		Double s = 0D;
		List<Double> indados = new ArrayList<Double>();
		for (Double tbf : data) {
			Double log = Math.log(tbf);
			indados.add(log);
			s += log;
		}

		Double m = s / n;

		Double maxw = null;
		boolean first = true;
		List<Double> w = new ArrayList<Double>();

		for (Double log : indados) {
			Double vlr = log - m;
			w.add(vlr);

			if (first) {
				maxw = vlr;
				first = false;
			} else if (vlr > maxw) {
				maxw = vlr;
			}
		}

		Double C0 = 1 / maxw;

		Double erro = null;
		while (erro == null || erro > Math.pow(10, -6)) {

			List<Double> cw = new ArrayList<Double>();
			for (Double wu : w) {
				cw.add(C0 * wu);
			}

			List<Double> cw2 = new ArrayList<Double>();
			for (Double wu : w) {
				cw2.add(C0 * (wu * wu));
			}

			Double sumG1 = 0D;
			Double sumG2 = 0D;

			int i = 0;
			for (Double cwu : cw) {
				Double ecw = Math.exp(cwu);

				Double g1 = (1 - cwu) * ecw;
				sumG1 += g1;

				Double g2 = cw2.get(i) * ecw;
				sumG2 += g2;

				i++;
			}
			Double C1 = C0 - (sumG1 / -sumG2);
			erro = Math.abs(C1 - C0);
			C0 = C1;
		}

		Double c = C0;

		s = 0D;
		for (Double tbf : data) {
			s += (Math.pow(tbf, c));
		}

		m = s / n;
		Double b = Math.pow(m, (1 / c));

		c = new BigDecimal(c).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		b = new BigDecimal(b).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();

		System.out.println("Weibull - c:" + c + ", b: " + b);

		WeibullDistribution weibullDist = new WeibullDistribution(c, b);

		// KS
		KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();
		Double ksTest = ks.kolmogorovSmirnovStatistic(weibullDist, data);
//		if (Double.isFinite(ksTest)) {
//			ksTest = new BigDecimal(ksTest).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("KS WEIBULL:" + ksTest);

		// ChiSquare
		Double chi = calcChiSquare(data, weibullDist);
//		if (Double.isFinite(chi)) {
//			chi = new BigDecimal(chi).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("ChiSquare WEIBULL:" + chi);

		// Anderson Darling
		Double ad = calcAndersonDarling(data, weibullDist);
//		if (Double.isFinite(ad)) {
//			ad = new BigDecimal(ad).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
//		}
		System.out.println("AD WEIBULL:" + ad);

		Weibull weibull = new Weibull(weibullDist, ksTest, chi, ad);
		return weibull;

	}

	private double calcAndersonDarling(double[] tbfs, RealDistribution distribution) {
		DescriptiveStatistics statics = new DescriptiveStatistics();
		for (double d : tbfs) {
			statics.addValue(d);
		}

		int n = tbfs.length;

		// 2 coluna
		double[] FZ = new double[n];
		for (int i = 0; i < n; i++) {
			FZ[i] = distribution.cumulativeProbability(tbfs[i]);
		}

		// 3 coluna
		double[] lnFZ = new double[n];
		for (int i = 0; i < n; i++) {
			lnFZ[i] = Math.log(FZ[i]);
		}

		// 4 coluna
		int[] n1i = new int[n];
		int y = n;
		for (int i = 0; i < n; i++) {
			n1i[i] = y;
			y--;
		}

		// 5 coluna
		double[] FZi = new double[n];
		for (int i = 0; i < n; i++) {
			FZi[i] = FZ[i];
		}

		// 6 coluna
		double[] Fnli1 = new double[n];
		for (int i = 0; i < n; i++) {
			Fnli1[i] = 1 - FZi[i];
		}

		// 7 coluna
		double[] ln1F = new double[n];
		for (int i = 0; i < n; i++) {
			ln1F[i] = Math.log(Fnli1[i]);
		}

		double D = 0;
		y = 1;
		for (int i = 0; i < n; i++) {
			D += (2 * y - 1) * lnFZ[i] + (2 * (n - y) + 1) * (ln1F[i]);
			y++;
		}

		double A = -n - (D / n);

		return A;

	}

	private double calcChiSquare(double[] tbfs, RealDistribution distribution) {

		DescriptiveStatistics stats = new DescriptiveStatistics();

		for (Double d : tbfs) {
			stats.addValue(d);
		}

		long[] obs = getObs(tbfs, stats);

		boolean valid = true;
		for (long ob : obs) {
			if (ob < 5) {
				valid = false;
				break;
			}
		}

		if (!valid) {
			NormalDistribution normal = new NormalDistribution(stats.getMean(), stats.getStandardDeviation());
			KolmogorovSmirnovTest ks = new KolmogorovSmirnovTest();
			double alfa = ks.kolmogorovSmirnovStatistic(normal, tbfs);
			alfa = 1 - alfa;

			if (alfa > 0.5) {
				double[] rank = getRank(tbfs);
				stats = new DescriptiveStatistics();
				for (Double d : rank) {
					stats.addValue(d);
				}
				obs = getObs(rank, stats);
			} else {
				double msd = stats.getStandardDeviation() / 2;
				int k = 3;

				lInfs = new double[3];
				lUpps = new double[3];

				lInfs[0] = 0;
				lUpps[0] = stats.getMean() - msd;

				lInfs[1] = stats.getMean() - msd;
				;
				lUpps[1] = stats.getMean() + msd;

				lInfs[2] = stats.getMean() + msd;
				lUpps[2] = stats.getMax() + 1;

				obs = new long[k];

				for (double d : tbfs) {
					for (int i = 0; i < k; i++) {
						if (d >= lInfs[i] && d < lUpps[i]) {
							obs[i] += 1;
						}
					}
				}
			}
		}

		valid = true;
		for (long ob : obs) {
			if (ob < 5) {
				valid = false;
				break;
			}
		}

		if (!valid) {
			obs = complete(obs);
		}

		int k = obs.length;
		int n = tbfs.length;

		double[] cumProb = new double[k];
		for (int i = 0; i < k; i++) {
			cumProb[i] = distribution.cumulativeProbability(lUpps[i]);
		}

		double[] cellProb = new double[k];
		cellProb[0] = cumProb[0];
		for (int i = 1; i < k; i++) {
			cellProb[i] = cumProb[i] - cumProb[i - 1];
		}

		double[] expected = new double[k];
		for (int i = 0; i < k; i++) {
			expected[i] = cellProb[i] * n;
		}

		ChiSquareTest test = new ChiSquareTest();
		return test.chiSquare(expected, obs);
	}

	private long[] getObs(double[] tbfs, DescriptiveStatistics stats) {
		int n = tbfs.length;

		int k = (int) Math.ceil((Math.log(n) / Math.log(2)) + 1);

		double L = stats.getMax() - stats.getMin();
		double h = Math.ceil(L / k);

		lInfs = new double[k];
		lInfs[0] = stats.getMin();
		for (int i = 1; i < k; i++) {
			lInfs[i] = lInfs[i - 1] + h;
		}

		lUpps = new double[k];
		for (int i = 0; i < k - 1; i++) {
			lUpps[i] = lInfs[i + 1];
		}
		lUpps[k - 1] = Math.ceil(stats.getMax()) + 1;

		long[] obs = new long[k];

		for (double d : tbfs) {
			for (int i = 0; i < k; i++) {
				if (d >= lInfs[i] && d < lUpps[i]) {
					obs[i] += 1;
				}
			}
		}

		return obs;
	}

	private long[] complete(long[] obs) {
		if (obs.length == 1) {
			return obs;
		}

		for (int i = 0; i < obs.length; i++) {
			if (obs[i] < 5) {
				if (i == 0) {
					obs[i + 1] = obs[i + 1] + obs[i];
					obs = ArrayUtils.remove(obs, i);
					lUpps = ArrayUtils.remove(lUpps, i);
					return complete(obs);
				} else if (i == (obs.length - 1)) {
					obs[i - 1] = obs[i - 1] + obs[i];
					obs = ArrayUtils.remove(obs, i);
					lUpps = ArrayUtils.remove(lUpps, i);
					return complete(obs);
				} else {
					long before = obs[i - 1];
					long after = obs[i + 1];
					if (before < after) {
						obs[i - 1] = obs[i - 1] + obs[i];
						obs = ArrayUtils.remove(obs, i);
						lUpps = ArrayUtils.remove(lUpps, i);
						return complete(obs);
					} else {
						obs[i + 1] = obs[i + 1] + obs[i];
						obs = ArrayUtils.remove(obs, i);
						lUpps = ArrayUtils.remove(lUpps, i);
						return complete(obs);
					}
				}
			}
		}

		return obs;
	}

	private double[] getRank(double[] tbfs){
		Map<Double, Double> map = new HashMap<Double, Double>();
		
		int n = tbfs.length;
		
		double last = tbfs[0];
		int begin = 1;
		for (int i = 0; i < n; i++) {
			if(tbfs[i] != last){
				double sum = i + begin;
				double rk = sum / 2D; 
				map.put(last, rk);
				begin = i + 1;
			}
			last = tbfs[i];
		}
		
		double sum = n + begin;
		double rk = sum / 2D; 
		map.put(last, rk);

		double[] rank = new double[n];
		for (int i = 0; i < n; i++) {
			rank[i] = map.get(tbfs[i]);
		}
		
		return rank;
	}
	
	public double[] getData() {
		return data;
	}
}
