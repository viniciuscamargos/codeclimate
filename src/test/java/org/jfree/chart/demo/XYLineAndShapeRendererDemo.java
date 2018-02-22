package org.jfree.chart.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import br.ufu.facom.osrat.distribution.CalcDistribution;

/**
 * A simple demonstration of the {@link XYLineAndShapeRenderer} class.
 */
public class XYLineAndShapeRendererDemo extends ApplicationFrame {

	static List<Double> tbfs = new ArrayList<>();
	static double[] array;
	
    /**
     * Constructs the demo application.
     *
     * @param title  the frame title.
     */
    public XYLineAndShapeRendererDemo(final String title, CalcDistribution calc) {

        super(title);
        XYDataset dataset = createSampleDataset(calc);
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);        
        plot.setRenderer(renderer);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
        setContentPane(chartPanel);

    }
    
    /**
     * Creates a sample dataset.
     * 
     * @return A dataset.
     */
    private XYDataset createSampleDataset(CalcDistribution calc) {
        XYSeries series1 = new XYSeries("Series 1");
        series1.add(1.0, 3.3);
        series1.add(2.0, 4.4);
        series1.add(3.0, 1.7);
        XYSeries series2 = new XYSeries("Series 2");
        for (double d : calc.getData()) {			
        	series2.add(1.0, 7.3);
        	series2.add(2.0, 6.8);
        	series2.add(3.0, 9.6);
        	series2.add(4.0, 5.6);
		}
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

    	before();
    	
    	CalcDistribution cal = new CalcDistribution(tbfs);
    	
    	
    	
        final XYLineAndShapeRendererDemo demo = new XYLineAndShapeRendererDemo(
            "XYLineAndShapeRenderer Demo", cal
        );
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
    
    public static void before(){
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

}

