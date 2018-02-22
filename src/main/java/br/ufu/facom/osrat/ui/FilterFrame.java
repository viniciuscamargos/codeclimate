package br.ufu.facom.osrat.ui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import br.ufu.facom.hpcs.agent.bean.Failure;
import br.ufu.facom.osrat.component.MyComboBoxEditor;
import br.ufu.facom.osrat.component.MyComboBoxRenderer;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.reader.ReadFailures;
import br.ufu.facom.osrat.util.OsratUtil;

public class FilterFrame extends JPanel {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;

	private JTextField txtEvent;
	private JTextField txtSource;
	private JTextField txtProduct;
	private JButton btnSave;

	private JPanel pnlFilter;
	private JPanel pnlFailures;

	private JButton btnRanking;
	private JTabbedPane jTabPane;

	private JButton btnXls;

	private List<JTable> tables = new ArrayList<JTable>();

	public FilterFrame() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(getPnlFilter(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(getPnlFailures(), gbc);
	}

	public final JPanel getPnlFilter() {
		if (this.pnlFilter == null) {
			this.pnlFilter = new JPanel();
			pnlFilter.setLayout(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Filter");
			pnlFilter.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Event Identifier:"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 20);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			gbc.gridy = 0;
			pnlFilter.add(getTxtEvent(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 2;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Source Name:"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 20);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 0;
			pnlFilter.add(getTxtSource(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 4;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Product Name"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 5;
			gbc.gridy = 0;
			pnlFilter.add(getTxtProduct(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 20);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 6;
			gbc.gridy = 0;
			pnlFilter.add(getBtnSave(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 20);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 7;
			gbc.gridy = 0;
			pnlFilter.add(getBtnXls(), gbc);

		}
		return pnlFilter;
	}

	public final JTextField getTxtEvent() {
		if (txtEvent == null) {
			txtEvent = new JTextField();
			txtEvent.setEditable(false);
			txtEvent.setPreferredSize(new Dimension(50, 25));
		}
		return txtEvent;
	}

	public final JTextField getTxtSource() {
		if (txtSource == null) {
			txtSource = new JTextField();
			txtSource.setEditable(false);
			txtSource.setPreferredSize(new Dimension(250, 25));
		}
		return txtSource;
	}

	public final JTextField getTxtProduct() {
		if (txtProduct == null) {
			txtProduct = new JTextField();
			txtProduct.setEditable(false);
			txtProduct.setPreferredSize(new Dimension(250, 25));
		}
		return txtProduct;
	}

	public final JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");

			btnSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					try {
						int x = JOptionPane.showConfirmDialog(null,
								"Do you really want to save?", "Save",
								JOptionPane.YES_NO_OPTION);
						if (x == JOptionPane.NO_OPTION) {
							return;
						}

						File copyFile = new File("temp.xls");
						WritableWorkbook workbookCopy = Workbook
								.createWorkbook(copyFile);

						TypeLog[] types = TypeLog.values();
						int s = 0;
						for (TypeLog typeLog : types) {
							WritableSheet sheet = workbookCopy.createSheet(
									typeLog.name(), s);
							sheet.addCell(createLabelCell("Event Identifier",
									0, 0));
							sheet.addCell(createLabelCell("Source Name", 1, 0));
							sheet.addCell(createLabelCell("Product Name", 2, 0));
							s++;
						}

						int count = getjTabPane().getTabCount();
						for (int i = 0; i < count; i++) {

							JScrollPane jScrollPane = (JScrollPane) getjTabPane()
									.getComponentAt(i);
							JViewport viewport = (JViewport) jScrollPane
									.getViewport();
							JTable table = (JTable) viewport.getComponent(0);

							int nRow = table.getRowCount();
							for (int j = 0; j < nRow; j++) {
								TypeLog tp = (TypeLog) table.getValueAt(j, 0);
								WritableSheet sheet = workbookCopy.getSheet(tp
										.name());

								Integer eventIdentifier = (Integer) table
										.getValueAt(j, 1);
								String sourceName = (String) table.getValueAt(
										j, 2);
								String productName = (String) table.getValueAt(
										j, 3);

								int r = sheet.getRows();
								sheet.addCell(createIntCell(eventIdentifier, 0,
										r));
								sheet.addCell(createLabelCell(sourceName, 1, r));
								sheet.addCell(createLabelCell(productName, 2, r));

							}
						}

						workbookCopy.write();
						workbookCopy.close();

						JOptionPane.showMessageDialog(null,
								"Failures successfully saved.");

						File originalFile = new File("Failures.xls");
						originalFile.delete();

						copyFile.renameTo(new File("Failures.xls"));

						ReadFailures.read();
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "Save error log: "
								+ e2.getMessage());
					}

				}
			});
		}
		return btnSave;
	}

	public final JButton getBtnRanking() {
		if (this.btnRanking == null) {
			this.btnRanking = new JButton("Ranking");
			this.btnRanking.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
		return btnRanking;
	}

	public final JPanel getPnlFailures() {
		if (this.pnlFailures == null) {
			pnlFailures = new JPanel(new GridBagLayout());
			TitledBorder titled = new TitledBorder("Failures");
			pnlFailures.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			pnlFailures.add(getjTabPane(), gbc);
		}
		return pnlFailures;
	}

	public final JTabbedPane getjTabPane() {
		if (jTabPane == null) {
			jTabPane = new JTabbedPane();

			Set<String> keys = ReadFailures.getMapLogs().keySet();
			for (String key : keys) {
				JTable table = getTableFailure(ReadFailures.getMapFailures()
						.get(key), key);
				jTabPane.addTab(key, new JScrollPane(table));
				tables.add(table);
			}
		}
		return jTabPane;
	}

	@SuppressWarnings("serial")
	public final JTable getTableFailure(final List<Failure> failures, final String typeLog) {

		DefaultTableModel dataModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(final int row, final int column) {
				return column == 0;
			}
		};
		Object[] tableColumnNames = {"", "Event Identifier", "Source Name",
				"Product Name"};
		dataModel.setColumnIdentifiers(tableColumnNames);

		final JTable tableFailure = new JTable(dataModel);

		tableFailure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				int row = tableFailure.getSelectedRow();
				if (row < 0) {
					return;
				}
				txtEvent.setText((Integer) tableFailure.getValueAt(row, 1) + "");
				txtSource.setText((String) tableFailure.getValueAt(row, 2));
				txtProduct.setText((String) tableFailure.getValueAt(row, 3));
				super.mouseClicked(e);
			}
		});

		tableFailure.getColumnModel().getColumn(0)
				.setCellRenderer(new MyComboBoxRenderer(TypeLog.values()));
		tableFailure.getColumnModel().getColumn(0)
				.setCellEditor(new MyComboBoxEditor(TypeLog.values()));

		for (Failure failure : failures) {

			Object[] obj = new Object[tableFailure.getColumnCount()];
			obj[0] = TypeLog.valueOf(typeLog);
			obj[1] = failure.getEventIdentifier();
			obj[2] = failure.getSourceName();
			obj[3] = failure.getProductName();

			dataModel.addRow(obj);
		}

		return tableFailure;
	}

	private static Label createLabelCell(final String value, final int c, final int r) {
		return new Label(c, r, value);
	}

	private static Number createIntCell(final long value, final int c, final int r) {
		WritableCellFormat integerFormat = new WritableCellFormat(
				NumberFormats.INTEGER);
		return new Number(c, r, value, integerFormat);
	}

	public final JButton getBtnXls() {
		if (btnXls == null) {
			ImageIcon imgXls = OsratUtil.createImageIcon("xls1.png");
			btnXls = new JButton(imgXls);
			btnXls.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent e) {
					File file = new File("Failures.xls");
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			});
		}
		return btnXls;
	}
}
