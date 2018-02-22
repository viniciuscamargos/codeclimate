package br.ufu.facom.hpcs.controller;

import java.io.File;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import br.ufu.facom.hpcs.action.AbstractAction;
import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.persist.dao.RecordDAO;
import br.ufu.facom.osrat.persist.dao.RecordDAOJPA;
import br.ufu.facom.osrat.reader.ReadFailures;
import br.ufu.facom.osrat.ui.LogUnknowFrame;

public class LogUnknowController extends PersistenceController {

	private static final int COLUMN_EVENT_IDENTIFIER = 1;
	private static final int COLUMN_PRODUCT_NAME = 2;
	private static final int COLUMN_SOURCE_NAME = 3;
	private static final int COLUMN_TYPE_LOG = 8;
	
	private RecordDAO recordDAO;
	private LogUnknowFrame frame;
	
	public LogUnknowController(final LogUnknowFrame frame) {
		loadPersistenceContext();
		recordDAO = new RecordDAOJPA(getPersistenceContext());
		this.frame = frame;
		
		registerAction(frame.getBtnSave(), new AbstractAction() {
			
			@Override
			protected void action() {
				save();
			}
		});

		registerAction(frame.getBtnExport(), new AbstractAction() {
			
			@Override
			protected void action() {
				export();
			}
		});
	}
	
	public LogUnknowFrame getFrame() {
		return frame;
	}
	
	private void save(){
		try {
			JTable table = frame.getTable();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int nRow = model.getRowCount();

			int x = JOptionPane.showConfirmDialog(null, "Want to continue?", "Save", JOptionPane.YES_NO_OPTION);
			if (x == JOptionPane.NO_OPTION) {
				return;
			}

			File originalFile = new File("Failures.xls");
			File copyFile = new File("temp.xls");
			Workbook wOriginal = Workbook.getWorkbook(originalFile);
			WritableWorkbook workbookCopy = Workbook.createWorkbook(copyFile, wOriginal);

			for (int i = 0; i < nRow; i++) {

				TypeLog typeLog = (TypeLog) table.getValueAt(i, COLUMN_TYPE_LOG);
				WritableSheet sheet = workbookCopy.getSheet(typeLog.getPlanilha());

				Integer eventIdentifier = (Integer) table.getValueAt(i, COLUMN_EVENT_IDENTIFIER);
				String sourceName = (String) table.getValueAt(i, COLUMN_SOURCE_NAME);
				String productName = (String) table.getValueAt(i, COLUMN_PRODUCT_NAME);

				String failure = (eventIdentifier + sourceName + productName).replaceAll("\\s", "");

				int r = sheet.getRows();
				sheet.addCell(createIntCell(eventIdentifier, 0, r));
				sheet.addCell(createLabelCell(sourceName, 1, r));
				sheet.addCell(createLabelCell(productName, 2, r));

				ReadFailures.getMapLogs().get(typeLog.getPlanilha()).add(failure);
				
				int ei = eventIdentifier;
				String sn = sourceName;
				String pn = productName;
				
				String s1 = (ei + sn).replaceAll("\\s", "");
				String s2 = (ei + sn + pn).replaceAll("\\s", "");
				
				Iterator<RecordBean> iterator = frame.getUnknows().iterator();
				while(iterator.hasNext()){
					RecordBean bean = iterator.next();
					
					int eiR = bean.getEventIdentifier();
					String snR = bean.getSourceName();
					String pnR = bean.getProductName();
					
					String s1R = (eiR + snR).replaceAll("\\s", "");
					String s2R = (eiR + snR + pnR).replaceAll("\\s", "");
					
					if(s1.equals(s1R) || s2.equals(s2R)){
						bean.setTypeLog(typeLog);
						recordDAO.saveOrUpdate(bean);
						iterator.remove();
					}
				}
				
			}

			workbookCopy.write();
			workbookCopy.close();
			wOriginal.close();

			JOptionPane.showMessageDialog(null, "Record successfully saved.");

			originalFile.delete();
			copyFile.renameTo(new File("Failures.xls"));

			/*if (frame.isImportLocal()) {
				Thread th = new Thread(new ThreadImportLocalSdf());
				th.start();
			} else {
				Thread th = new Thread(new ThreadImportSdf(MainFrame.getDirSdfs()));
				th.start();
			}*/

			frame.dispose();
		} catch (Exception e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Save error log: " + e2.getMessage());
		}
	}
	
	public void export(){
		JTable table = frame.getTable();
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Excel Workbook", "xls"));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			File fileXls = new File(file.getAbsolutePath() + ".xls");
			try {
				WritableWorkbook workbook = Workbook.createWorkbook(fileXls);
				WritableSheet sheet = workbook.createSheet("Unknows", 0);

				int row = 0;
				// Cabeçalho
				sheet.addCell(createLabelCellBold("Computer Name", 0, row));
				sheet.addCell(createLabelCellBold("Event Identifier",1, row));
				sheet.addCell(createLabelCellBold("Product Name", 2, row));
				sheet.addCell(createLabelCellBold("Source Name", 3, row));
				sheet.addCell(createLabelCellBold("Time Generated", 4, row));
				sheet.addCell(createLabelCellBold("Log file", 5, row));
				sheet.addCell(createLabelCellBold("Message", 6, row));
				sheet.addCell(createLabelCellBold("Insertion Strings", 7, row));

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int nRow = model.getRowCount();
				for (int i = 0; i < nRow; i++) {
					row++;
					int col = 0;
					sheet.addCell(createLabelCell((String) table.getValueAt(i, col), col, row));
					sheet.addCell(createIntCell((int) table.getValueAt(i, ++col), col, row));
					sheet.addCell(createLabelCell((String) table.getValueAt(i, ++col), col, row));
					sheet.addCell(createLabelCell((String) table.getValueAt(i, ++col), col, row));
					sheet.addCell(createLabelCell((String) table.getValueAt(i, ++col), col, row));
					sheet.addCell(createLabelCell((String) table.getValueAt(i, ++col), col, row));
					sheet.addCell(createLabelCell((String) table.getValueAt(i, ++col), col, row));
					sheet.addCell(createLabelCell((String) table.getValueAt(i, ++col), col, row));
				}

				workbook.write();
				workbook.close();

				JOptionPane.showMessageDialog(null,	"Unknows successfully exported.");

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
	
	private static Label createLabelCellBold(final String value, final int c, final int r) {
		try {
			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setBoldStyle(WritableFont.BOLD);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			return new Label(c, r, value, cellFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Label createLabelCell(final String value, final int c, final int r) {
		return new Label(c, r, value);
	}

	private static Number createIntCell(final long value, final int c, final int r) {
		WritableCellFormat integerFormat = new WritableCellFormat(NumberFormats.INTEGER);
		return new Number(c, r, value, integerFormat);
	}
	
	
	
}
