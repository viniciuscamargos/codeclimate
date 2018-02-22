package br.ufu.facom.hpcs.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import br.ufu.facom.hpcs.action.AbstractAction;
import br.ufu.facom.osrat.distribution.CalcDistribution;
import br.ufu.facom.osrat.distribution.Exponential;
import br.ufu.facom.osrat.distribution.Gamma;
import br.ufu.facom.osrat.distribution.Lognormal;
import br.ufu.facom.osrat.distribution.Weibull;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.SearchFilter;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.persist.dao.RecordDAO;
import br.ufu.facom.osrat.persist.dao.RecordDAOJPA;
import br.ufu.facom.osrat.ui.DistributionsFrame;

public class DistributionController extends PersistenceController {

	private RecordDAO recordDAO;
	private DistributionsFrame frame;
	private CalcDistribution calc;
	
	public DistributionController() {
		loadPersistenceContext();
		recordDAO = new RecordDAOJPA(getPersistenceContext());
		
		frame = new DistributionsFrame();
		
		registerAction(frame.getBtnSearch(), new AbstractAction() {
			
			@Override
			protected void action() {
				search();
			}
		});

		registerAction(frame.getBtnGraph(), new AbstractAction() {
			
			@Override
			protected void action() {
				generateGraph();
			}
		});
	}
	
	public DistributionsFrame getFrame() {
		return frame;
	}
	
	private void search() {
		SearchFilter searchFilter = new SearchFilter();
		
		Node nodeGrp = (Node) frame.getCmbGroups().getSelectedItem();
		searchFilter.setGroup(nodeGrp.getGroup());
		
		Node nodeSdf = (Node) frame.getCmbSdfs().getSelectedItem();
		searchFilter.setSdf(nodeSdf.getSdf());

		TypeLog typeLog = (TypeLog) frame.getCmbTypeLog().getSelectedItem();
		typeLog = TypeLog.ALL.equals(typeLog) ? null : typeLog;
		searchFilter.setTypeLog(typeLog);

		if (frame.getDtInit().getModel().getValue() != null) {
			Date periodStart = (Date) frame.getDtInit().getModel().getValue();
			searchFilter.setPeriodStart(periodStart);
		}

		if (frame.getDtEnd().getModel().getValue() != null) {
			Date periodEnd = (Date) frame.getDtEnd().getModel().getValue();
			searchFilter.setPeriodEnd(periodEnd);
		}
		
		List<Long> times = recordDAO.getTbfs(searchFilter);
		List<Double> tbfs = new ArrayList<Double>();
		
		DefaultTableModel dataModel = (DefaultTableModel) frame.getTableTbf().getModel();
		dataModel.getDataVector().removeAllElements();

		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		Long last = null;
		for (Long time : times) {

			String tbfStr = "-";
			if(last == null){
				last = time;
			}else{				
				Long tbf = time - last;
				if(tbf == 0){
					continue;
				}
				
				tbf = tbf / 1000;
				tbfStr = tbf.toString();
				tbfs.add(new Double(tbf));
			}

			Object[] obj = new Object[dataModel.getColumnCount()];
			obj[0] = dtFormat.format(new Date(time)) ;
			obj[1] = tbfStr;
			dataModel.addRow(obj);

			last = time;
		}
		
		CalcDistribution calcDistribution = new CalcDistribution(tbfs);
		
		DefaultTableModel dtModelDist = (DefaultTableModel) frame.getTableDistributions().getModel();

		Exponential exponential = calcDistribution.calcExponential();
		Lognormal lognormal = calcDistribution.calcLognormal();
		Gamma gamma = calcDistribution.calcGamma();
		Weibull weibull = calcDistribution.calcWeibull();

		double[] ks = {exponential.getKs(), lognormal.getKs(), gamma.getKs(), weibull.getKs()};
		Arrays.sort(ks);

		double[] ad = {exponential.getAd(), lognormal.getAd(), gamma.getAd(), weibull.getAd()};
		Arrays.sort(ad);

		double[] ch = {exponential.getChiSquare(), lognormal.getChiSquare(), gamma.getChiSquare(), weibull.getChiSquare()};
		Arrays.sort(ch);
		
		dtModelDist.setValueAt(exponential.getKs(), 0, 2);
		dtModelDist.setValueAt(Arrays.binarySearch(ks, exponential.getKs()) + 1, 0, 3);
		dtModelDist.setValueAt(exponential.getAd(), 0, 4);
		dtModelDist.setValueAt(Arrays.binarySearch(ad, exponential.getAd()) + 1, 0, 5);
		dtModelDist.setValueAt(exponential.getChiSquare(), 0, 6);
		dtModelDist.setValueAt(Arrays.binarySearch(ch, exponential.getChiSquare()) + 1, 0, 7);

		dtModelDist.setValueAt(lognormal.getKs(), 1, 2);
		dtModelDist.setValueAt(Arrays.binarySearch(ks, lognormal.getKs()) + 1, 1, 3);
		dtModelDist.setValueAt(lognormal.getAd(), 1, 4);
		dtModelDist.setValueAt(Arrays.binarySearch(ad, lognormal.getAd()) + 1, 1, 5);
		dtModelDist.setValueAt(lognormal.getChiSquare(), 1, 6);
		dtModelDist.setValueAt(Arrays.binarySearch(ch, lognormal.getChiSquare()) + 1, 1, 7);

		dtModelDist.setValueAt(gamma.getKs(), 2, 2);
		dtModelDist.setValueAt(Arrays.binarySearch(ks, gamma.getKs()) + 1, 2, 3);
		dtModelDist.setValueAt(gamma.getAd(), 2, 4);
		dtModelDist.setValueAt(Arrays.binarySearch(ad, gamma.getAd()) + 1, 2, 5);
		dtModelDist.setValueAt(gamma.getChiSquare(), 2, 6);
		dtModelDist.setValueAt(Arrays.binarySearch(ch, gamma.getChiSquare()) + 1, 2, 7);

		dtModelDist.setValueAt(weibull.getKs(), 3, 2);
		dtModelDist.setValueAt(Arrays.binarySearch(ks, weibull.getKs()) + 1, 3, 3);
		dtModelDist.setValueAt(weibull.getAd(), 3, 4);
		dtModelDist.setValueAt(Arrays.binarySearch(ad, weibull.getAd()) + 1, 3, 5);
		dtModelDist.setValueAt(weibull.getChiSquare(), 3, 6);
		dtModelDist.setValueAt(Arrays.binarySearch(ch, weibull.getChiSquare()) + 1, 3, 7);
		
		calcMetrics(times, tbfs);
		
		this.calc = calcDistribution;
		
		frame.getBtnGraph().setEnabled(true);
	}
	
	public void generateGraph(){
		/*DistributionGraphDensity graph = new DistributionGraphDensity("Density Graph", calc);
		graph.pack();
		RefineryUtilities.centerFrameOnScreen(graph);*/
		//graph.setVisible(true);
	}
	
	private void calcMetrics(List<Long> times, List<Double> tbfs){
		DefaultTableModel defaultTableModel = (DefaultTableModel) frame.getTableMetrics().getModel();
		
		DescriptiveStatistics statics = new DescriptiveStatistics();
		for (double time : times) {
			statics.addValue(time);
		}
		
		double total = statics.getMax() - statics.getMin();
		total = total / 1000D;
		
		statics = new DescriptiveStatistics();
		for (double tbf : tbfs) {
			statics.addValue(tbf);
		}
		
		double mtbf = statics.getMean();
		mtbf = new BigDecimal(mtbf).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		defaultTableModel.setValueAt(mtbf, 0, 1);
		
		double reability = Math.exp(-total/mtbf);
		reability = new BigDecimal(reability).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		defaultTableModel.setValueAt(reability, 1, 1);

		double unreability = 1 - reability;
		unreability = new BigDecimal(unreability).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue();
		defaultTableModel.setValueAt(unreability, 2, 1);
		
	}
	
	
/*	public static void main(String[] args) {
		List<Double> list = new ArrayList<Double>();
		
		DistributionController con = new DistributionController();
		double[] tbfs = new double[10];
		int i = 0;
		tbfs[i] = 0;
		tbfs[++i] = 1;
		tbfs[++i] = 2;
		tbfs[++i] = 3;
		tbfs[++i] = 4;
		tbfs[++i] = 5;
		tbfs[++i] = 6;
		tbfs[++i] = 7;
		tbfs[++i] = 8;
		tbfs[++i] = 9;
		
		double d = 0D;
		list.add(d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
		list.add(++d);
				

		//con.calcKS(tbfs);
		List<Double> dbs = con.calcDistExp(tbfs);
		for (Double double1 : dbs) {
			System.out.println(double1);
		}
		
		
		GraphFrame frame = new GraphFrame("teste", list, dbs);
		frame.setSize(new Dimension(400, 400));
		frame.setVisible(true);
	}*/
}
