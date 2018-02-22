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
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
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
import br.ufu.facom.osrat.distribution.Weibull;

public class DistributionCumulatyAcumulity extends ApplicationFrame {

	private static final long serialVersionUID = 9097047963618614545L;

	public DistributionCumulatyAcumulity(final String title, final CalcDistribution calc) {
		super(title);
		ChartPanel jpanel = createPanel(calc);
		jpanel.setMouseWheelEnabled(true);
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static double[] getData() {
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

		//double min = stats.getMin();
		double max = stats.getMax();
		
		int size = (int)(Math.log10(max)+1);
		
		HistogramDataset histogramdataset = new HistogramDataset();
		histogramdataset.setType(HistogramType.FREQUENCY);
		histogramdataset.addSeries("Histogram", tbfs, k, 0, Math.pow(10, size));

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

	private static XYDataset createWeibull(CalcDistribution calc) {
		XYSeries seriesLog = calcDistWeibull(calc);
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
		
		double max = stats.getMax();
		
		int size = (int)(Math.log10(max)+1);
		
		for (int i = 0; i < Math.pow(10, size); i+=1000) {
			seriesExp.add(i , exp.cumulativeProbability(i));
		}
		return seriesExp;
	}

	private static XYSeries calcDistLog(CalcDistribution calc) {
		XYSeries seriesLog = new XYSeries("Lognormal");

		Lognormal lognormal = calc.calcLognormal();
		LogNormalDistribution log = (LogNormalDistribution) lognormal.getDistribution();
		
		double[] tbfs = calc.getData();
		
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		double max = stats.getMax();
		
		int size = (int)(Math.log10(max)+1);
		
		for (int i = 0; i < Math.pow(10, size); i+=1000) {
			seriesLog.add(i, log.cumulativeProbability(i));
		}
		return seriesLog;
	}

	private static XYSeries calcDistGamma(CalcDistribution calc) {
		XYSeries seriesGamma = new XYSeries("Gamma");

		Gamma gamma = calc.calcGamma();
		GammaDistribution gammaDist = (GammaDistribution)gamma.getDistribution();
		
		double[] tbfs = calc.getData();
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		double max = stats.getMax();
		
		int size = (int)(Math.log10(max)+1);
		
		for (int i = 0; i < Math.pow(10, size); i+=1000) {
			seriesGamma.add(i, gammaDist.cumulativeProbability(i));
		}
		return seriesGamma;
	}

	private static XYSeries calcDistWeibull(CalcDistribution calc) {
		XYSeries seriesWeibull = new XYSeries("Weibull");

		Weibull weibull = calc.calcWeibull();
		WeibullDistribution weibullDist = (WeibullDistribution)weibull.getDistribution();

		double[] tbfs = calc.getData();
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		double max = stats.getMax();
		
		int size = (int)(Math.log10(max)+1);
			
		
		for (int i = 0; i < Math.pow(10, size); i+=1000) {
			seriesWeibull.add(i, weibullDist.cumulativeProbability(i));
		}
		return seriesWeibull;
	}

	private static JFreeChart createChartHistogram(IntervalXYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createHistogram("Cumulative Distribuction Function", "TBF(s)", "Frequency", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, false);

		jfreechart.setBackgroundPaint(Color.WHITE);
		
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        
        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setOutlineVisible(false);
        
        XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
		xybarrenderer.setSeriesPaint(0, Color.WHITE);
		xybarrenderer.setDrawBarOutline(true);
		return jfreechart;
	}
/*
	private static JFreeChart createChartLine(XYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart("Linha", "TBF(s)", "Freq.", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, true);
		return jfreechart;
	}*/

	public static ChartPanel createPanel(CalcDistribution calc) {
		JFreeChart histChart = createChartHistogram(createHistogram(calc.getData()));
		
		
		XYPlot localXYPlot = histChart.getXYPlot();
		
		/*localXYPlot.getDomainAxis().setAxisLinePaint(Color.WHITE);
		localXYPlot.getRangeAxis().setAxisLinePaint(Color.WHITE);
		*/

		//NumberAxis rangeAxis = (NumberAxis) localXYPlot.getDomainAxis();
		//rangeAxis.setRange (1.2, 1.3);
		//rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits ());
		//rangeAxis.setTickUnit (new NumberTickUnit(0.005));
		
		/*DecimalFormat newFormat = new DecimalFormat("0.0E0");
		rangeAxis.setNumberFormatOverride(newFormat);*/
		
		//localXYPlot.setDomainPannable(true);
	    //localXYPlot.setRangePannable(true);
		
	    NumberAxis numberExp = new NumberAxis("Exponential");
	    numberExp.setVisible(false);
	    //numberExp.setAutoRange(true);
		localXYPlot.setDataset(1, createExponential(calc)); 
		localXYPlot.mapDatasetToRangeAxis(1, 1);
		StandardXYItemRenderer rendererExp = new StandardXYItemRenderer();
		rendererExp.setDrawSeriesLineAsPath(false);
		localXYPlot.setRenderer(1,rendererExp);
		localXYPlot.setRangeAxis(1, numberExp);
		
		//localXYPlot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);
		
		
		NumberAxis numberLog = new NumberAxis("Lognormal");
	   // numberLog.setAutoRange(true);
		numberLog.setVisible(false);
		localXYPlot.setDataset(2, createLognormal(calc)); 
		localXYPlot.mapDatasetToRangeAxis(2, 2);
		StandardXYItemRenderer rendererLog = new StandardXYItemRenderer();
		localXYPlot.setRenderer(2,rendererLog);
		localXYPlot.setRangeAxis(2, numberLog);
		
		NumberAxis numberGamma = new NumberAxis("Gamma");
		//numberGamma.setAutoRange(true);
		numberGamma.setVisible(false);
		localXYPlot.setDataset(3, createGamma(calc)); 
		localXYPlot.mapDatasetToRangeAxis(3, 3);
		StandardXYItemRenderer rendererGamma = new StandardXYItemRenderer();
		localXYPlot.setRenderer(3,rendererGamma);
		localXYPlot.setRangeAxis(3, numberGamma);
		
		NumberAxis numberWeibull = new NumberAxis("Weibull");
		//numberWeibull.setAutoRange(true);
		numberWeibull.setVisible(false);
		localXYPlot.setDataset(4, createWeibull(calc)); 
		localXYPlot.mapDatasetToRangeAxis(4, 4);
		StandardXYItemRenderer rendererWeibull = new StandardXYItemRenderer();
		localXYPlot.setRenderer(4,rendererWeibull);
		localXYPlot.setRangeAxis(4, numberWeibull);
		
		localXYPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
		return new ChartPanel(histChart);
	}

	public static void main(String args[]) throws IOException {
		double[] tbfs = getData();
		CalcDistribution calc = new CalcDistribution(tbfs);
		DistributionCumulatyAcumulity histogramdemo1 = new DistributionCumulatyAcumulity("Graph", calc);
		histogramdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(histogramdemo1);
		histogramdemo1.setVisible(true);
	}
}