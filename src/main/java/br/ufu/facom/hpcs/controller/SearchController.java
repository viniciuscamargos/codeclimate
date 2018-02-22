package br.ufu.facom.hpcs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import br.ufu.facom.hpcs.action.AbstractAction;
import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.entity.Aggregation;
import br.ufu.facom.hpcs.entity.AggregationField;
import br.ufu.facom.osrat.model.FieldRecord;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.Ranking;
import br.ufu.facom.osrat.model.SearchFilter;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.persist.dao.AggregationDAO;
import br.ufu.facom.osrat.persist.dao.AggregationDAOJPA;
import br.ufu.facom.osrat.persist.dao.RecordDAO;
import br.ufu.facom.osrat.persist.dao.RecordDAOJPA;
import br.ufu.facom.osrat.ui.SearchFrame;
import br.ufu.facom.osrat.util.JPAUtil;

public class SearchController extends PersistenceController {

	private SearchFrame frame;
	private RecordDAO recordDAO;
	private AggregationDAO aggregationDAO;
	
	public SearchController() {
		frame = new SearchFrame();
		//frame.setVisible(true);

		loadPersistenceContext();
		recordDAO = new RecordDAOJPA(getPersistenceContext());
		aggregationDAO = new AggregationDAOJPA(getPersistenceContext());
		
		registerActionCmbxAggregation();
		
		registerAction(frame.getBtnSearch(), new AbstractAction() {
			
			@Override
			protected void action() {
				search();
			}
		});
	}
	
	public SearchFrame getFrame() {
		return frame;
	}
	
	@Override
	protected void cleanUp() {
		super.cleanUp();
		
		JPAUtil.closeEntityManagerFactory();
	}
	
	public void registerActionCmbxAggregation(){
		AbstractAction actionCmbx =  new AbstractAction() {
			@Override
			protected void action() {
				Aggregation agg = (Aggregation) frame.getCmbAggregations().getSelectedItem();
				if("ANY".equals(agg.getName())){
					DefaultTableModel dataModel = (DefaultTableModel) frame.getTblAgreggation().getModel();
					dataModel.getDataVector().removeAllElements();
					frame.getTblAgreggation().updateUI();
					return;
				}
				
				List<AggregationField> fields = aggregationDAO.getFields(agg);

				DefaultTableModel dataModel = (DefaultTableModel) frame.getTblAgreggation().getModel();

				dataModel.getDataVector().removeAllElements();

				for (AggregationField field : fields) {
					int i = 0;
					Object[] obj = new Object[4];
					obj[i] = field.getOpLogic();
					obj[++i] = FieldRecord.valueOf(field.getField().toUpperCase());
					obj[++i] = field.getOpCond();
					obj[++i] = field.getVlrField();
					dataModel.addRow(obj);
				}
			}
		};
		
		registerAction(frame.getCmbAggregations(), actionCmbx);
	}
	
	private void search() {
		SearchFilter searchFilter = new SearchFilter();
		
		Node nodeGrp = (Node) frame.getCmbGroups().getSelectedItem();
		searchFilter.setGroup(nodeGrp.getGroup());
		
		Node nodeSdf = (Node) frame.getCmbSdfs().getSelectedItem();
		searchFilter.setSdf(nodeSdf.getSdf());

		TypeLog typeLog = (TypeLog) frame.getCmbTypeLog().getSelectedItem();
		searchFilter.setTypeLog(typeLog);

		if (frame.getDtInit().getModel().getValue() != null) {
			Date periodStart = (Date) frame.getDtInit().getModel().getValue();
			searchFilter.setPeriodStart(periodStart);
		}

		if (frame.getDtEnd().getModel().getValue() != null) {
			Date periodEnd = (Date) frame.getDtEnd().getModel().getValue();
			searchFilter.setPeriodEnd(periodEnd);
		}
		
		List<RecordBean> records = recordDAO.findRecordBeans(searchFilter);
		
		putValuesTable(records);

		/*int nrow = frame.getTblAgreggation().getRowCount();
		List<AggregationField> list = new ArrayList<AggregationField>();
		for (int i = 0; i < nrow; i++) {
			AggregationField fAggr = new AggregationField();
			fAggr.setOpLogic((String)  frame.getTblAgreggation().getValueAt(i, 0));
			
			FieldRecord fieldRecord = (FieldRecord) frame.getTblAgreggation().getValueAt(i, 1);
			fAggr.setField(fieldRecord.getField());
			
			fAggr.setOpCond((String) frame.getTblAgreggation().getValueAt(i, 2));
			
			frame.getTblAgreggation().getCellEditor(i, 3).stopCellEditing();
			String vlField = (String) frame.getTblAgreggation().getValueAt(i, 3);
			if(vlField == null || vlField.trim().isEmpty()){
				JOptionPane.showMessageDialog(null, "Insert a value in row '" + (i + 1) + "'.");
				return;
			}
			fAggr.setVlrField(vlField);
			
			list.add(fAggr);
		}*/

		//RecordDAO dao = new RecordDaoJDBC();
		/*List<Object[]> objs = dao.searchRecordsSP(group, sdf,
				typeLog, periodStart, periodEnd, list);
		
		if(objs == null || objs.isEmpty()){
			JOptionPane.showMessageDialog(null, "No records found.");
		}*/
		
		//putValuesTable(objs);

	}

	private final void putValuesTable(final List<RecordBean> records) {
		DefaultTableModel dataModel = (DefaultTableModel) frame.getTable().getModel();
		dataModel.getDataVector().removeAllElements();

		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		for (RecordBean record : records) {
			Object[] obj = new Object[dataModel.getColumnCount()];
			obj[0] = record.getComputerName();
			obj[1] = record.getEventIdentifier();
			obj[2] = record.getProductName();
			obj[3] = record.getSourceName();
			obj[4] = dtFormat.format(new Date(record.getTimeGenerated()));
			obj[5] = record.getLogfile();
			obj[6] = record.getMessage();
			obj[7] = record.getInsertion();
			obj[8] = record.getTypeLog();
			dataModel.addRow(obj);
		}
	}
	
	
}
