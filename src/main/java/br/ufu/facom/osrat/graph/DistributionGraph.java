package br.ufu.facom.osrat.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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

import br.ufu.facom.osrat.distribution.MLEGamma;

public class DistributionGraph extends ApplicationFrame {

	public DistributionGraph(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static IntervalXYDataset createHistogram(double[] tbfs){
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		//calcula a classe
		int k =  new BigDecimal(1 + 3.3 * Math.log(tbfs.length) / Math.log(10)).setScale(2, BigDecimal.ROUND_HALF_EVEN).intValue();
		/*int k = (int)Math.floor(1 + 3.3 * Math.log(tbfs.length) / Math.log(10));*/
		
		//calcula a amplitude do conjunto
		double L = stats.getMax() - stats.getMin(); 
		
		//calcula a amplitude
		double h = new BigDecimal(L / k).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		
		double rest = new BigDecimal(stats.getMax()).setScale(1, BigDecimal.ROUND_HALF_EVEN).doubleValue() - stats.getMax();
		double min = stats.getMin() - new BigDecimal(rest/2).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		double max = stats.getMax() + rest/2;
		
		HistogramDataset histogramdataset = new HistogramDataset();
	    histogramdataset.setType(HistogramType.FREQUENCY);
	    histogramdataset.addSeries("Histogram",tbfs, k, min, max);
		
		return histogramdataset;
	}

	private static XYDataset createExponential(double[] tbfs){
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		//calcula a classe
		int k =  new BigDecimal(1 + 3.3 * Math.log(tbfs.length) / Math.log(10)).setScale(2, BigDecimal.ROUND_HALF_EVEN).intValue();
		
		//calcula a amplitude do conjunto
		double L = stats.getMax() - stats.getMin(); 
		
		//calcula a amplitude
		double h = new BigDecimal(L / k).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		
		XYSeries seriesExp = calcDistExp(tbfs, k, h);
		
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesExp);
                
        return dataset;
		
	}
	
	private static XYDataset createLognormal(double[] tbfs){
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		//calcula a classe
		int k =  new BigDecimal(1 + 3.3 * Math.log(tbfs.length) / Math.log(10)).setScale(2, BigDecimal.ROUND_HALF_EVEN).intValue();
		
		//calcula a amplitude do conjunto
		double L = stats.getMax() - stats.getMin(); 
		
		//calcula a amplitude
		double h = new BigDecimal(L / k).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		
		XYSeries seriesLog = calcDistLog(tbfs, k, h);
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesLog);
		
		return dataset;
		
	}

	private static XYDataset createGamma(double[] tbfs){
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		
		//calcula a classe
		int k =  new BigDecimal(1 + 3.3 * Math.log(tbfs.length) / Math.log(10)).setScale(2, BigDecimal.ROUND_HALF_EVEN).intValue();
		
		//calcula a amplitude do conjunto
		double L = stats.getMax() - stats.getMin(); 
		
		//calcula a amplitude
		double h = new BigDecimal(L / k).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		
		XYSeries seriesLog = calcDistGamma(tbfs, k, h);
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesLog);
		
		return dataset;
		
	}
	
	private static XYSeries calcDistExp(double[] tbfs, int k, double h){
		XYSeries seriesExp = new XYSeries("Exponential");
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		double mean = stats.getMean();
		ExponentialDistribution exp = new ExponentialDistribution(mean);
		double inc = 0;
		for (int i = 0; i < (k + 1) + 1; i++) {
			seriesExp.add(inc, exp.density(inc));
			inc+=h;
		}
		return seriesExp;
	}
	
	private static XYSeries calcDistLog(double[] tbfs, int k, double h){
		XYSeries seriesLog = new XYSeries("Lognormal");
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);
		double mean = stats.getMean();
		double standardDeviation = stats.getStandardDeviation();
		LogNormalDistribution log = new LogNormalDistribution(mean, standardDeviation);
		double inc = 0;
		for (int i = 0; i < (k + 1) + 1; i++) {
			seriesLog.add(inc, log.density(inc));
			inc+=h;
		}
		return seriesLog;
	}

	
	private static XYSeries calcDistGamma(double[] tbfs, int k, double h){
		XYSeries seriesGamma = new XYSeries("Gamma");
		
		MLEGamma mle = new MLEGamma(tbfs,  0.00001, 100);
		mle.calcule();
		
		GammaDistribution gamma = new GammaDistribution(mle.getShape(), mle.getScala());
		double inc = 0;
		for (int i = 0; i < (k + 1) + 1; i++) {
			seriesGamma.add(inc, gamma.density(inc));
			inc+=h;
		}
		return seriesGamma;
	}
	
	private List<Double> calcDistWeibull(double[] tbfs){
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
		JFreeChart jfreechart = ChartFactory.createHistogram(
				"Histograma", "TBF(s)", "Freq.", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, true);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
		xybarrenderer.setSeriesPaint(0, Color.BLUE);
		xybarrenderer.setDrawBarOutline(true);
		return jfreechart;
	}

	private static JFreeChart createChartLine(XYDataset  intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart(
				"Linha", "TBF(s)", "Freq.", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, true);
		return jfreechart;
	}

	public static JPanel createDemoPanel() {
		double[] tbfs = new double[10];
		int i = 0;
		
		tbfs[i] = 1;
		tbfs[++i] = 2;
		tbfs[++i] = 3;
		tbfs[++i] = 4;
		tbfs[++i] = 5;
		tbfs[++i] = 6;
		tbfs[++i] = 7;
		tbfs[++i] = 8;
		tbfs[++i] = 9;
		tbfs[++i] = 10;
		
		
		MLEGamma gamma = new MLEGamma(tbfs, 0.00001, 100);
		System.out.println(gamma.calcule());

		/*JFreeChart histChart = createChartHistogram(createHistogram(tbfs));*/
		JFreeChart histChart = createChartLine(createExponential(tbfs));
		XYPlot plot = histChart.getXYPlot();
		
		//exponential
		/*StandardXYItemRenderer rendererExp = new StandardXYItemRenderer();
		plot.setDataset(1, createExponential(tbfs));
		plot.setRenderer(1, rendererExp);*/
		
		//lognormal
		StandardXYItemRenderer rendererLog = new StandardXYItemRenderer();
		plot.setDataset(1, createLognormal(tbfs));
		plot.setRenderer(1, rendererLog);

		//gamma
		StandardXYItemRenderer rendererGamma = new StandardXYItemRenderer();
		plot.setDataset(2, createGamma(tbfs));
		plot.setRenderer(2, rendererGamma);
		
		
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		
		return new ChartPanel(histChart);
	}

	public static void main(String args[]) throws IOException {
		DistributionGraph histogramdemo1 = new DistributionGraph(
				"JFreeChart : HistogramDemo1");
		histogramdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(histogramdemo1);
		histogramdemo1.setVisible(true);
	}
}