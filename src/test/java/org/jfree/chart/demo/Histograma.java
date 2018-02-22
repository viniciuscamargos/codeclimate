package org.jfree.chart.demo;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.time.Day;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.date.SerialDate;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import br.ufu.facom.osrat.distribution.CalcDistribution;
import br.ufu.facom.osrat.distribution.Exponential;

public class Histograma extends ApplicationFrame {

	public Histograma(final String title, double[] tbfs) {

        super(title);
        final JFreeChart chart = createOverlaidChart(tbfs);
        final ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);

    }
	
	private JFreeChart createOverlaidChart(double[] tbfs) {

        // create plot ...
        final IntervalXYDataset data1 = createDataset1(tbfs);
        final XYItemRenderer renderer1 = new XYBarRenderer(0.20);
        renderer1.setToolTipGenerator(
        		new StandardXYToolTipGenerator()
        		/*new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")
            )*/
        );
        final DateAxis domainAxis = new DateAxis("Seconds");
        domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        final ValueAxis rangeAxis = new NumberAxis("Value");
        final XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);

        // add a second dataset and renderer...
        CalcDistribution calc = new CalcDistribution(tbfs);
        final XYDataset data2 = createDataset2(calc);
        
        StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        
        /*final XYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer2.setToolTipGenerator(
            new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("ss"), new DecimalFormat("0.00")
            )
        );*/
        
/*        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(new NumberTickUnit(100));
        plot.setDomainAxis(xAxis);
*/        

        NumberAxis localNumberAxis2 = new NumberAxis("Percent");
        localNumberAxis2.setNumberFormatOverride(NumberFormat.getNumberInstance());
        localNumberAxis2.setAutoRange(true);
        plot.setRangeAxis(1, localNumberAxis2);
        plot.setDataset(1, data2);
        plot.setRenderer(1, renderer2);
        
        
        
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // return a new chart containing the overlaid plot...
        return new JFreeChart("Overlaid Plot Example", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

    }
	
	private IntervalXYDataset createDataset1(double[] tbfs) {
		DescriptiveStatistics stats = new DescriptiveStatistics(tbfs);

		int n = tbfs.length;

		// calcula a classe
		int k = (int) Math.round(1 + 3.3 * Math.log10(n));

		double min = stats.getMin();
		double max = stats.getMax();

		HistogramDataset histogramdataset = new HistogramDataset();
		histogramdataset.setType(HistogramType.FREQUENCY);
		histogramdataset.addSeries("Histogram", tbfs, k, min, max);

		return histogramdataset;
	}
	
	private XYDataset createDataset2(CalcDistribution calc) {

		XYSeries seriesExp = calcDistExp(calc);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesExp);

		return dataset;

    }
	
	private static XYSeries calcDistExp(CalcDistribution calc) {
		XYSeries seriesExp = new XYSeries("Exponential");
		DescriptiveStatistics stats = new DescriptiveStatistics(calc.getData());

		Exponential exponential = calc.calcExponential();
		ExponentialDistribution exp = (ExponentialDistribution) exponential.getDistribution();
		for (int i = 0; i < stats.getMax(); i += 1000) {
			seriesExp.add(i, exp.density(i));
		}
		return seriesExp;
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
	
	public static void main(String[] args) {
		double[] tbfs = getData();
		final Histograma demo = new Histograma("Overlaid XYPlot Demo", tbfs);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
	}

}
