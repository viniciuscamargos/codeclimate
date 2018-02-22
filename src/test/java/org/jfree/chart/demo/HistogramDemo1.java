package org.jfree.chart.demo;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.random.EmpiricalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class HistogramDemo1 extends ApplicationFrame {

	static List<Double> tbfs = new ArrayList<Double>();
	static double[]  array;
	
	public HistogramDemo1(String s) {
		super(s);
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static void fillTbfs() {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			String txt = "";

			br = new BufferedReader(new FileReader("input.txt"));

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

			array = ArrayUtils.toPrimitive(tbfs.toArray(new Double[0]));

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

	
	private static IntervalXYDataset createDataset() {

		fillTbfs();
		
		int n = array.length;
		
		DescriptiveStatistics stats = new DescriptiveStatistics(array);
		
		//calcula a classe
		int k = (int)Math.round(1 + 3.3 * Math.log10(n));
		
		double min = stats.getMin();
		double max = stats.getMax();
		
		HistogramDataset histogramdataset = new HistogramDataset();
	    histogramdataset.setType(HistogramType.FREQUENCY);
	    histogramdataset.addSeries("Histogram",array, k, min, max);
		
		return histogramdataset;
	}

	private static JFreeChart createChart(IntervalXYDataset intervalxydataset) {
		JFreeChart jfreechart = ChartFactory.createHistogram(
				"Histograma", "TBF(s)", "Freq.", intervalxydataset,
				PlotOrientation.VERTICAL, true, true, true);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setForegroundAlpha(0.85F);
		XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
		xybarrenderer.setDrawBarOutline(false);
		return jfreechart;
	}

	public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[]) throws IOException {
		HistogramDemo1 histogramdemo1 = new HistogramDemo1(
				"JFreeChart : HistogramDemo1");
		histogramdemo1.pack();
		RefineryUtilities.centerFrameOnScreen(histogramdemo1);
		histogramdemo1.setVisible(true);
	}
}