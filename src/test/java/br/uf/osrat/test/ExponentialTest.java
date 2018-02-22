package br.uf.osrat.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.KolmogorovSmirnovTest;
import org.junit.Before;
import org.junit.Test;

public class ExponentialTest {

	List<Double> list = new ArrayList<Double>();
	double[] tbfs;
	
	@Before
	public void fillTbfs() {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			String txt = "";

			br = new BufferedReader(new FileReader("input6.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				txt += sCurrentLine + ";";
			}

			String values[] = txt.split(";");

			for (String string : values) {
				list.add(new Double(string));
			}

			Collections.sort(list, new Comparator<Double>() {
				@Override
				public int compare(Double d1, Double d2) {

					return d1.compareTo(d2);
				}
			});

			tbfs = ArrayUtils.toPrimitive(list.toArray(new Double[0]));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}
	
	@Test
	public void exponencialTest() {

		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		double mean = stats.getMean();
		System.out.println("Exponencial - mean:" + stats.getMean());

		ExponentialDistribution exponential = new ExponentialDistribution(mean);
		int count = 1;
		
		System.out.println("\n **** TABELA *** \n");
		for (Double tbf : tbfs) {
			System.out.println(count + " \t" + tbf + " \t\t" + exponential.density(tbf));
			count ++;
		}
	}
	
}
