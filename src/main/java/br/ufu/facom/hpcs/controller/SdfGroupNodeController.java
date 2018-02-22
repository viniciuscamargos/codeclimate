package br.ufu.facom.hpcs.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.persist.dao.RecordDAO;
import br.ufu.facom.osrat.persist.dao.RecordDAOJPA;
import br.ufu.facom.osrat.ui.SdfGroupNodeFrame;

public class SdfGroupNodeController extends PersistenceController {

	private RecordDAO recordDAO;
	private SdfGroupNodeFrame frame;
	private Node node;
	
	public SdfGroupNodeController(Node node) {
		loadPersistenceContext();
		recordDAO = new RecordDAOJPA(getPersistenceContext());
		
		frame = new SdfGroupNodeFrame(node);
		this.node = node;
		
		getInfoCT();
		getInfoNF();
	}
	
	public SdfGroupNodeFrame getFrame() {
		return frame;
	}
	
	private void getInfoCT(){
		List<Double> list = recordDAO.getInfoCT(node.getGroup().getId());

		Double[] ds = list.toArray(new Double[list.size()]);
		double[] values = ArrayUtils.toPrimitive(ds);
		
		DescriptiveStatistics stats = new DescriptiveStatistics(values);
		
		int row = 0;
		
		//MIN
		frame.getTableNf().setValueAt(new Double(stats.getMin()).intValue(), row, 1);
		
		//MAX
		frame.getTableNf().setValueAt(new Double(stats.getMax()).intValue(), ++row, 1);
		
		//Média
		frame.getTableNf().setValueAt(new BigDecimal(stats.getMean()).setScale(2, BigDecimal.ROUND_HALF_EVEN), ++row, 1); //TODO (double) Math.round(StatUtils.mean(values) * 100) / 100
		
		//Mediana
		frame.getTableNf().setValueAt(new BigDecimal(stats.getPercentile(50)).setScale(2, BigDecimal.ROUND_HALF_EVEN), ++row, 1);

		//Desvio Padrão
		frame.getTableNf().setValueAt(new BigDecimal(stats.getStandardDeviation()).setScale(2, BigDecimal.ROUND_HALF_EVEN), ++row, 1);

		Frequency frequency = new Frequency();
		for (int i = 0; i < values.length; i++) {
			frequency.addValue(values[i]);
		}
		
		
		//Modo
		frame.getTableNf().setValueAt(frequency.getMode().toString(), ++row, 1);

	}
	
	private void getInfoNF(){
		List<Double> list = recordDAO.getInfoNF(node.getGroup().getId());
		
		Double[] ds = list.toArray(new Double[list.size()]);
		double[] values = ArrayUtils.toPrimitive(ds);
		
		DescriptiveStatistics stats = new DescriptiveStatistics(values);
		
		int row = 0;
		
		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		//MIN
		Long min = new Double(stats.getMin()).longValue();
		frame.getTableCt().setValueAt(dtFormat.format(new Date(min)), row, 1);
		
		//MAX
		Long max = new Double(stats.getMax()).longValue();
		frame.getTableCt().setValueAt(dtFormat.format(new Date(max)), ++row, 1);
		
		//Média
		frame.getTableCt().setValueAt(new BigDecimal(stats.getMean()).setScale(2, BigDecimal.ROUND_HALF_EVEN), ++row, 1);
		
		//Mediana
		frame.getTableCt().setValueAt(new BigDecimal(stats.getPercentile(50)).setScale(2, BigDecimal.ROUND_HALF_EVEN), ++row, 1);
		
		//Desvio Padrão
		frame.getTableCt().setValueAt(new BigDecimal(stats.getStandardDeviation()).setScale(2, BigDecimal.ROUND_HALF_EVEN), ++row, 1);
		
		Frequency frequency = new Frequency();
		for (int i = 0; i < values.length; i++) {
			frequency.addValue(values[i]);
		}
		
		//Modo
		frame.getTableCt().setValueAt(frequency.getMode().toString(), ++row, 1);
		
	}
	
}
