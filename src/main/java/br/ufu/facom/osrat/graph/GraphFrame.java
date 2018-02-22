package br.ufu.facom.osrat.graph;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import br.ufu.facom.osrat.util.OsratUtil;
  
public class GraphFrame extends JFrame {  
  
	private static final long serialVersionUID = 1L;
	private List<Double> tbfs;

	public GraphFrame(String title, List<Double> tbfs) {  
      super(title);  
      setIconImage(OsratUtil.getMainIcon().getImage());
      this.tbfs = tbfs;
      
      final XYDataset dataset = createDataset();
      final JFreeChart chart = createChart(dataset);
      final ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
      
      //========================SEP
     /* JPanel chartPanel = createDemoPanel();  
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));  
      setContentPane(chartPanel);  */
   }  

	public GraphFrame(String title, List<Double> tbfs, List<Double> tbfsD) {  
		super(title);  
		setIconImage(OsratUtil.getMainIcon().getImage());
		this.tbfs = tbfs;
		
		overlaidBarChartDemo("teste");
		
		/*final XYDataset dataset = createDataset(tbfs, tbfsD);
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);*/
		
		//========================SEP
				/* JPanel chartPanel = createDemoPanel();  
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));  
      setContentPane(chartPanel);  */
	}  

	private XYDataset createDataset() {
	    
	
	    final XYSeries series2 = new XYSeries("Second");
	    series2.add(1.0, 5.0);
	    series2.add(2.0, 7.0);
	    series2.add(3.0, 6.0);
	    series2.add(4.0, 8.0);
	    series2.add(5.0, 4.0);
	    series2.add(6.0, 4.0);
	    series2.add(7.0, 2.0);
	    series2.add(8.0, 1.0);
	
	
	    final XYSeriesCollection dataset = new XYSeriesCollection();
	    dataset.addSeries(series2);
	            
	    return dataset;
	    
	}

	private XYDataset createDataset(List<Double> listF, List<Double> listS) {
		
		
		final XYSeries series2 = new XYSeries("Second");
		series2.add(listF.get(0), listS.get(0));
		series2.add(listF.get(1), listS.get(1));
		series2.add(listF.get(2), listS.get(2));
		series2.add(listF.get(3), listS.get(3));
		series2.add(listF.get(4), listS.get(4));
		series2.add(listF.get(5), listS.get(5));
		series2.add(listF.get(6), listS.get(6));
		series2.add(listF.get(7), listS.get(7));
		series2.add(listF.get(8), listS.get(8));
		series2.add(listF.get(9), listS.get(9));
		
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series2);
		
		return dataset;
		
	}
	
	private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6",      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        chart.setBackgroundPaint(Color.white);

        
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

                
        return chart;
        
    }
	
	//==================================================== SEPARAÇÂO
     
   private static XYDataset createDataset1() {  
      Function2D f1 = new Function2D() {  
         public double getValue(double x) {  
         return 3 * x * x * x + x * x + 4.0;  
         }   
      };  
      return DatasetUtilities.sampleFunction2D(f1,-4.0, 2.5, 100,"Exponencial");  
   }  
     
   private static XYDataset createDataset2() {  
      Function2D f2 = new Function2D() {  
         public double getValue(double x) {  
         return 50 - 50 * x * x;  
         }   
      };  
      return DatasetUtilities.sampleFunction2D(f2, -2.0, 2.0, 300, "Lognormal");  
   }  

   public void teste(){
	   HistogramDataset histogramdataset = new HistogramDataset();
	   //histogramdataset.addSeries(key, values, bins);
   }
   
   public void overlaidBarChartDemo(final String title) {


       // create the first dataset...
       DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
       dataset1.addValue(1.0, "S1", "Category 1");
       dataset1.addValue(4.0, "S1", "Category 2");
       dataset1.addValue(3.0, "S1", "Category 3");
       dataset1.addValue(5.0, "S1", "Category 4");
       dataset1.addValue(5.0, "S1", "Category 5");
       dataset1.addValue(7.0, "S1", "Category 6");
       dataset1.addValue(7.0, "S1", "Category 7");
       dataset1.addValue(8.0, "S1", "Category 8");

       dataset1.addValue(5.0, "S2", "Category 1");
       dataset1.addValue(7.0, "S2", "Category 2");
       dataset1.addValue(6.0, "S2", "Category 3");
       dataset1.addValue(8.0, "S2", "Category 4");
       dataset1.addValue(4.0, "S2", "Category 5");
       dataset1.addValue(4.0, "S2", "Category 6");
       dataset1.addValue(2.0, "S2", "Category 7");
       dataset1.addValue(1.0, "S2", "Category 8");


       // create the first renderer...
 //      final CategoryLabelGenerator generator = new StandardCategoryLabelGenerator();
       final CategoryItemRenderer renderer = new BarRenderer();
   //    renderer.setLabelGenerator(generator);
       renderer.setItemLabelsVisible(true);
       
       final CategoryPlot plot = new CategoryPlot();
       plot.setDataset(dataset1);
       plot.setRenderer(renderer);
       
       plot.setDomainAxis(new CategoryAxis("Category"));
       plot.setRangeAxis(new NumberAxis("Value"));

       plot.setOrientation(PlotOrientation.VERTICAL);
       plot.setRangeGridlinesVisible(true);
       plot.setDomainGridlinesVisible(true);

       // now create the second dataset and renderer...
       DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
       dataset2.addValue(9.0, "T1", "Category 1");
       dataset2.addValue(7.0, "T1", "Category 2");
       dataset2.addValue(2.0, "T1", "Category 3");
       dataset2.addValue(6.0, "T1", "Category 4");
       dataset2.addValue(6.0, "T1", "Category 5");
       dataset2.addValue(9.0, "T1", "Category 6");
       dataset2.addValue(5.0, "T1", "Category 7");
       dataset2.addValue(4.0, "T1", "Category 8");

       final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
       plot.setDataset(1, dataset2);
       plot.setRenderer(1, renderer2);

       // create the third dataset and renderer...
       final ValueAxis rangeAxis2 = new NumberAxis("Axis 2");
       plot.setRangeAxis(1, rangeAxis2);

       DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
       dataset3.addValue(94.0, "R1", "Category 1");
       dataset3.addValue(75.0, "R1", "Category 2");
       dataset3.addValue(22.0, "R1", "Category 3");
       dataset3.addValue(74.0, "R1", "Category 4");
       dataset3.addValue(83.0, "R1", "Category 5");
       dataset3.addValue(9.0, "R1", "Category 6");
       dataset3.addValue(23.0, "R1", "Category 7");
       dataset3.addValue(98.0, "R1", "Category 8");

       plot.setDataset(2, dataset3);
       final CategoryItemRenderer renderer3 = new LineAndShapeRenderer();
       plot.setRenderer(2, renderer3);
       plot.mapDatasetToRangeAxis(2, 1);

       // change the rendering order so the primary dataset appears "behind" the 
       // other datasets...
       plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
       
       plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
       final JFreeChart chart = new JFreeChart(plot);
       chart.setTitle("Overlaid Bar Chart");
     //  chart.setLegend(new StandardLegend());

       // add the chart to a panel...
       final ChartPanel chartPanel = new ChartPanel(chart);
       chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
       setContentPane(chartPanel);

   }
     
   private static JFreeChart createChart(XYDataset dataset1, XYDataset dataset2) {  
	   CategoryItemRenderer renderer1 = new BarRenderer();
	        renderer1.setItemLabelsVisible(true);
	   
	   // create the chart...  
      JFreeChart chart = ChartFactory.createXYLineChart(  
         "Distributions",      // chart title  
         "TBF(s)",                      // x axis label  
         "Frequencies",                      // y axis label  
         dataset1,                  // data  
         PlotOrientation.VERTICAL,  
         true,                     // include legend  
         true,                     // tooltips  
         false                     // urls  
      );  
        
      // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...  
      chart.setBackgroundPaint(Color.white);  
        
      // get a reference to the plot for further customisation...  
      XYPlot plot = (XYPlot) chart.getPlot();  
      plot.setDataset(1, dataset2);  
      plot.setBackgroundPaint(Color.lightGray);  
      plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));  
      plot.setDomainGridlinePaint(Color.white);  
      plot.setRangeGridlinePaint(Color.white);  
        
      XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();  
      renderer.setShapesVisible(false);  
        
      XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);  
      plot.setRenderer(1, renderer2);  
        
      // change the auto tick unit selection to integer units only...  
      NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
      rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
      // OPTIONAL CUSTOMISATION COMPLETED.  
        
      return chart;  
   }  
     
   public static JPanel createDemoPanel() {  
      JFreeChart chart = createChart(createDataset1(), createDataset2());  
      return new ChartPanel(chart);  
   }  
   
     
}  


