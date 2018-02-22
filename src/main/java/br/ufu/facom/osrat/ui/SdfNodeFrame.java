package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.component.PopupDialogLog;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.util.OsratUtil;

public class SdfNodeFrame extends JPanel {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;

	private static final int COLUMN_INSERTION_STRINGS = 7;

	private Node node;

	private JPanel pnlInfo;
	private JTable tableInfo;

	private JPanel pnlRecords;
	private JTabbedPane jTabPane;

	public SdfNodeFrame(final Node node) {
		this.node = node;

		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(getPnlInfo(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(getPnlRecords(), gbc);
	}

	public final JPanel getPnlInfo() {
		if (this.pnlInfo == null) {
			pnlInfo = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Informations");
			pnlInfo.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlInfo.add(new JScrollPane(getTableInfo()), gbc);
		}
		return pnlInfo;
	}

	public final JTable getTableInfo() {
		if (tableInfo == null) {
			DefaultTableModel dataModel = new DefaultTableModel();

			Object[] tableColumnNames = {"Info", "Value"};
			dataModel.setColumnIdentifiers(tableColumnNames);

			tableInfo = new JTable(dataModel);

			Vector<Object> row = new Vector<Object>();
			row.addElement("Total Kernel");
			row.addElement(node.getKernels().size());
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Total Service");
			row.addElement(node.getServices().size());
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Total OS App");
			row.addElement(node.getOsApps().size());
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Total User App");
			row.addElement(node.getUserApps().size());
			dataModel.addRow(row);

			row = new Vector<Object>();
			long totalFails = node.getKernels().size()
					+ node.getServices().size() + node.getOsApps().size()
					+ node.getUserApps().size();
			row.addElement("Total Fails");
			row.addElement(totalFails);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Init Date");
			row.addElement(node.getInitDate());
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Final Date");
			row.addElement(node.getFinalDate());
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Number of Days");
			row.addElement(node.getNumberDays());
			dataModel.addRow(row);
		}
		return tableInfo;
	}

	public final JPanel getPnlRecords() {
		if (this.pnlRecords == null) {
			pnlRecords = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Records");
			pnlRecords.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlRecords.add(getjTabPane(), gbc);

		}
		return pnlRecords;
	}

	public final JTabbedPane getjTabPane() {
		if (jTabPane == null) {
			jTabPane = new JTabbedPane();
		}
		return jTabPane;
	}

	public final JTable getTableRecords(final List<RecordBean> records) {
		DefaultTableModel dataModel = new DefaultTableModel();

		Object[] tableColumnNames = {"Computer Name", "Event Identifier",
				"Product Name", "Source Name", "Time Generated", "Log file",
				"Message", "Insertion Strings"};
		dataModel.setColumnIdentifiers(tableColumnNames);

		final JTable tableRecords = new JTable(dataModel);
		tableRecords.getTableHeader().setReorderingAllowed(false);

		tableRecords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				int col = tableRecords.getSelectedColumn();
				int row = tableRecords.getSelectedRow();
				if (col == 6 || col == COLUMN_INSERTION_STRINGS) {
					String message = (String) tableRecords.getValueAt(row, col);
					PopupDialogLog dialog = new PopupDialogLog();
					dialog.setText(message);
					OsratUtil.setCenterLocation(dialog);
					dialog.setVisible(true);
				}
				super.mouseClicked(e);
			}
		});

		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		for (RecordBean bean : records) {

			Object[] obj = new Object[tableRecords.getColumnCount()];
			obj[0] = bean.getComputerName();
			obj[1] = bean.getEventIdentifier();
			obj[2] = bean.getProductName();
			obj[3] = bean.getSourceName();
			obj[4] = dtFormat.format(new Date(bean.getTimeGenerated()));
			obj[5] = bean.getLogfile();
			obj[6] = bean.getMessage();

			/*String listString = "";
			for (String str : bean.getInsertionStrings()) {
				listString += str + "\n";
			}*/
			obj[COLUMN_INSERTION_STRINGS] = bean.getInsertion();

			dataModel.addRow(obj);
		}
		return tableRecords;
	}

}
