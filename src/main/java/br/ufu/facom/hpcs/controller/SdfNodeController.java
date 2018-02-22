package br.ufu.facom.hpcs.controller;

import java.util.List;

import javax.swing.JScrollPane;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.SearchFilter;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.persist.dao.RecordDAO;
import br.ufu.facom.osrat.persist.dao.RecordDAOJPA;
import br.ufu.facom.osrat.ui.SdfNodeFrame;

public class SdfNodeController extends PersistenceController {

	private RecordDAO recordDAO;
	private SdfNodeFrame frame;
	private Node node;
	
	public SdfNodeController(Node node) {
		loadPersistenceContext();
		recordDAO = new RecordDAOJPA(getPersistenceContext());
		
		frame = new SdfNodeFrame(node);
		this.node = node;
		
		getRecords();
		
	}
	
	public SdfNodeFrame getFrame() {
		return frame;
	}
	
	private void getRecords(){
		int row = 0;
		int total = 0;
		
		SearchFilter filter = new SearchFilter();
		filter.setSdf(node.getSdf());

		//KERNEL
		filter.setTypeLog(TypeLog.KERNEL);
		List<RecordBean> records = recordDAO.findRecordBeans(filter);
		frame.getjTabPane().addTab("Kernel", new JScrollPane(frame.getTableRecords(records)));
		frame.getTableInfo().setValueAt(records.size(), row, 1);
		total += records.size();

		//SERVICE
		filter.setTypeLog(TypeLog.SERVICE);
		records = recordDAO.findRecordBeans(filter);
		frame.getjTabPane().addTab("Service", new JScrollPane(frame.getTableRecords(records)));
		frame.getTableInfo().setValueAt(records.size(), ++row, 1);
		total += records.size();
		
		//OS_APPLICATION
		filter.setTypeLog(TypeLog.OS_APPLICATION);
		records = recordDAO.findRecordBeans(filter);
		frame.getjTabPane().addTab("OS App", new JScrollPane(frame.getTableRecords(records)));
		frame.getTableInfo().setValueAt(records.size(), ++row, 1);
		total += records.size();
		
		//USER_APPLICATION
		filter.setTypeLog(TypeLog.USER_APPLICATION);
		records = recordDAO.findRecordBeans(filter);
		frame.getjTabPane().addTab("User App", new JScrollPane(frame.getTableRecords(records)));
		frame.getTableInfo().setValueAt(records.size(), ++row, 1);
		total += records.size();

		frame.getTableInfo().setValueAt(total, ++row, 1);
	}
}
