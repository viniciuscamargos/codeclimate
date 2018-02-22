package org.jfree.chart.demo;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import br.ufu.facom.osrat.distribution.CalcDistribution;
import br.ufu.facom.osrat.distribution.Exponential;
import br.ufu.facom.osrat.distribution.Gamma;
import br.ufu.facom.osrat.distribution.Lognormal;

public class TestGraph extends ApplicationFrame {

	public TestGraph(String s) {
		super(s);
		double[] tbfs = getData();
		JPanel jpanel = createDemoPanel(tbfs);
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private double[] getData() {
		BufferedReader br = null;
		List<Double> tbfs = new ArrayList<>();

		try {

			String sCurrentLine;

			String txt = "";

			br = new BufferedReader(new FileReader("input6.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				txt += sCurrentLine + ";";
			}

			String values[] = txt.split(";");

			for (String string : values) {
				tbfs.add(new Double(string));
			}

			Collections.sort(tbfs, new Comparator<Double>() {
				@Override
				public int compare(Double d1, Double d2) {

					return d1.compareTo(d2);
				}
			});

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

		return ArrayUtils.toPrimitive(tbfs.toArray(new Double[0]));
	}

	private static IntervalXYDataset createHistogram(double[] tbfs) {
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);

		int n = tbfs.length;

		// calcula a classe
		int k = (int) Math.round(1 + 3.3 * Math.log10(n));

		double min = stats.getMin();
		double max = stats.getMax();

		HistogramDataset histogramdataset = new HistogramDataset();
		histogramdataset.setType(HistogramType.RELATIVE_FREQUENCY);
		histogramdataset.addSeries("Histogram", tbfs, k, min, max);

		return histogramdataset;
	}

	private static XYDataset createExponential(CalcDistribution calc) {
		XYSeries seriesExp = calcDistExp(calc);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesExp);

		return dataset;

	}

	private static XYDataset createLognormal(CalcDistribution calc) {
		XYSeries seriesLog = calcDistLog(calc);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesLog);

		return dataset;

	}

	private static XYDataset createGamma(CalcDistribution calc) {
		XYSeries seriesLog = calcDistGamma(calc);
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesLog);

		return dataset;

	}

	private static XYSeries calcDistExp(CalcDistribution calc) {
		XYSeries seriesExp = new XYSeries("Exponential");

		Exponential exponential = calc.calcExponential();
		ExponentialDistribution exp = (ExponentialDistribution) exponential.getDistribution();
		
		double[] tbfs = calc.getData();
		
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		for (int i = 0; i < stats.getMax(); i+=1000) {
			seriesExp.add(i, exp.density(i));
		}
		return seriesExp;
	}

	private static XYSeries calcDistLog(CalcDistribution calc) {
		XYSeries seriesLog = new XYSeries("Lognormal");

		Lognormal lognormal = calc.calcLognormal();
		LogNormalDistribution log = (LogNormalDistribution) lognormal.getDistribution();
		
		double[] tbfs = calc.getData();
		
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		for (int i = 0; i < stats.getMax(); i+=1000) {
			seriesLog.add(i, log.density(i));
		}
		return seriesLog;
	}

	private static XYSeries calcDistGamma(CalcDistribution calc) {
		XYSeries seriesGamma = new XYSeries("Gamma");
		DescriptiveStatistics stats = new DescriptiveStatistics(calc.getData());

		Gamma gamma = calc.calcGamma();
		GammaDistribution gammaDist = (GammaDistribution)gamma.getDistribution();
		for (int i = 0; i < stats.getMax(); i+=1000) {
			seriesGamma.add(i, Double.isInfinite(gammaDist.density(i)) ? 1 : gammaDist.density(i));
		}
		return seriesGamma;
	}

	private List<Double> calcDistWeibull(double[] tbfs) {
		double alpha = 0.00001;
		double beta = 100;
		List<Double> values = new ArrayList<Double>();
		WeibullDistribution weibull = new WeibullDistribution(alpha, beta);
		for (int i = 0; i < tbfs.length; i++) {
			values.add(weibull.density(tbfs[i]));
		}
		return values;
	}

	private static JFreeChart createChartHistogram(IntervalXYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createHistogram("Histograma", "TBF(s)", "Freq.", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
		xybarrenderer.setSeriesPaint(0, Color.BLUE);
		xybarrenderer.setDrawBarOutline(true);
		return jfreechart;
	}

	private static JFreeChart createChartLine(XYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart("Linha", "TBF(s)", "Freq.", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, true);
		return jfreechart;
	}

	public static JPanel createDemoPanel(double[] tbfs) {
		CalcDistribution calc = new CalcDistribution(tbfs);
		JFreeChart histChart = createChartLine(createLognormal(calc));
		
		
		/*NumberAxis domainAxis = new NumberAxis("TBF");
		//domainAxis.setRange(-100000, Math.pow(10, 7));
		domainAxis.setTickUnit( new NumberTickUnit(1));  
		domainAxis.setNumberFormatOverride(new DecimalFormat("0.0E0")); 
		
		xy.setDomainAxis(domainAxis);*/
		
		return new ChartPanel(histChart);
	}

	public static void main(String args[]) throws IOException {
		TestGraph histogramdemo1 = new TestGraph("JFreeChart : HistogramDemo1");
		histogramdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(histogramdemo1);
		histogramdemo1.setVisible(true);
	}
}