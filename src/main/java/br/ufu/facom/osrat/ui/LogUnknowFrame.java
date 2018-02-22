package br.ufu.facom.osrat.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



import org.apache.commons.lang3.ArrayUtils;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.component.JTableButtonMouseListener;
import br.ufu.facom.osrat.component.MyComboBoxEditor;
import br.ufu.facom.osrat.component.MyComboBoxRenderer;
import br.ufu.facom.osrat.component.PopupDialogLog;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.util.OsratUtil;

public class LogUnknowFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private List<RecordBean> unknows;

	private JScrollPane pane;
	private JTable table;
	private JButton btnSave;
	private JButton btnExport;
	
	private boolean importLocal;

	private static final int COLUMN_MSG = 6;
	private static final int COLUMN_INSERTION_STRINGS = 7;
	private static final int COLUMN_TYPE_LOG = 8;

	public LogUnknowFrame(final List<RecordBean> unknows, final boolean importLocal) {
		this.unknows = unknows;
		this.importLocal = importLocal;

		setTitle("Logs Unknows (" + OsratUtil.getTitleSystem() + ")");
		setIconImage(OsratUtil.getMainIcon().getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(1000, 600));

		getContentPane().setLayout(new GridBagLayout());

		pane = new JScrollPane(getTable());
		GridBagConstraints gbcPane = new GridBagConstraints();
		gbcPane.weightx = 1.0;
		gbcPane.gridx = 0;
		gbcPane.gridy = 0;
		gbcPane.fill = GridBagConstraints.BOTH;
		gbcPane.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(pane, gbcPane);

		
		JPanel pnlButtons = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(pnlButtons, gbc);
		
		GridBagConstraints gbcBtn = new GridBagConstraints();
		gbcBtn.gridx = 0;
		gbcBtn.gridy = 0;
		gbcBtn.insets = new Insets(2, 2, 2, 2);
		pnlButtons.add(getBtnSave(), gbcBtn);

		gbcBtn = new GridBagConstraints();
		gbcBtn.gridx = 1;
		gbcBtn.gridy = 0;
		gbcBtn.insets = new Insets(2, 2, 2, 2);
		pnlButtons.add(getBtnExport(), gbcBtn);
	}

	public final JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
		}
		return btnSave;
	}

	@SuppressWarnings({"unused", "rawtypes" })
	public JTable getTable() {
		if (table == null) {

			DefaultTableModel dataModel = new DefaultTableModel();

			Object[] tableColumnNames = {"Computer Name", "Event Identifier",
					"Product Name", "Source Name", "Time Generated",
					"Log file", "Message", "Insertion Strings", "Type Log" };
			dataModel.setColumnIdentifiers(tableColumnNames);

			table = new JTable(dataModel);
			table.getTableHeader().setReorderingAllowed(false);

			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					int col = table.getSelectedColumn();
					int row = table.getSelectedRow();
					if (col == COLUMN_MSG || col == COLUMN_INSERTION_STRINGS) {
						String message = (String) table.getValueAt(row, col);
						PopupDialogLog dialog = new PopupDialogLog();
						dialog.setText(message);
						OsratUtil.setCenterLocation(dialog);
						dialog.setVisible(true);
					}
					super.mouseClicked(e);
				}
			});

			//remove o elemento ALL
			TypeLog[] types = TypeLog.values();
			types = ArrayUtils.removeElement(types, TypeLog.ALL);
			table.getColumnModel().getColumn(COLUMN_TYPE_LOG).setCellRenderer(new MyComboBoxRenderer(types));
			table.getColumnModel().getColumn(COLUMN_TYPE_LOG).setCellEditor(new MyComboBoxEditor(types));

			table.addMouseListener(new JTableButtonMouseListener(table));

			Set<RecordBean> s = new TreeSet<RecordBean>(
					new Comparator<RecordBean>() {
						@Override
						public int compare(final RecordBean r1, final RecordBean r2) {
							int eq = r1.getEventIdentifier() - r2.getEventIdentifier();
							if (eq == 0) {
								eq = r1.getSourceName().compareTo(r2.getSourceName());
							}
							if (eq == 0) {
								eq = r1.getProductName().compareTo(r2.getProductName());
							}
							return eq;
						}
					});

			s.addAll(unknows);

			/*unknows.clear();
			unknows.addAll(s);*/
			
			SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Iterator<RecordBean> iterator = s.iterator();
			for (Iterator iterator2 = s.iterator(); iterator2.hasNext();) {
				RecordBean bean = (RecordBean) iterator2.next();

				Object[] obj = new Object[table.getColumnCount() + 1];
				obj[0] = bean.getComputerName();
				obj[1] = bean.getEventIdentifier();
				obj[2] = bean.getProductName();
				obj[3] = bean.getSourceName();
				obj[4] = dtFormat.format(new Date(bean.getTimeGenerated()));
				obj[5] = bean.getLogfile();

				obj[COLUMN_MSG] = bean.getMessage();

				String listString = "";
				for (String str : bean.getInsertionStrings()) {
					listString += str + "\n";
				}
				obj[COLUMN_INSERTION_STRINGS] = listString;

				obj[COLUMN_TYPE_LOG] = TypeLog.IGNORE;
				
				obj[9] = bean.getId();

				dataModel.addRow(obj);
			}
		}
		return table;
	}

	public JButton getBtnExport() {
		if (btnExport == null) {
			ImageIcon imgXls = OsratUtil.createImageIcon("xls1.png");
			btnExport = new JButton(imgXls);
			btnExport.setToolTipText("Export unknows logs.");
		}
		return btnExport;
	}

	public boolean isImportLocal() {
		return importLocal;
	}
	
	public List<RecordBean> getUnknows() {
		return unknows;
	}
	
	public void setUnknows(List<RecordBean> unknows) {
		this.unknows = unknows;
	}

	
}
