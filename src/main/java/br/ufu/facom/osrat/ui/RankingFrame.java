package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.Ranking;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.reader.ReadRanking;
import br.ufu.facom.osrat.util.OsratUtil;

public class RankingFrame extends JPanel {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnlFilter;
	private JComboBox<Node> cmbGroups;
	private JComboBox<String> cmbTypeLog;
	private JDatePickerImpl dtInit;
	private JDatePickerImpl dtEnd;

	private String group;
	private String typeLog;
	private String dateInit;
	private String dateEnd;

	private JPanel pnlTable;
	private JLabel lblTable;
	private JTable table;

	private JButton btnSearch;
	private JButton btnExport;

	public RankingFrame() {
		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		this.add(getPnlFilter(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		this.add(getPnlTable(), gbc);
	}

	public final JPanel getPnlFilter() {
		if (this.pnlFilter == null) {
			this.pnlFilter = new JPanel();
			pnlFilter.setLayout(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Filter");
			pnlFilter.setBorder(titled);

			// =============== combo grupos ==================
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Groups"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			gbc.gridy = 0;
			pnlFilter.add(getCmbGroups(), gbc);

			// =============== combo logs ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 2;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Logs"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 0;
			pnlFilter.add(getCmbTypeLog(), gbc);

			// =============== data inicio ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			gbc.gridy = 1;
			pnlFilter.add(new JLabel("Period Start"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			gbc.gridy = 1;
			pnlFilter.add(getDtInit(), gbc);

			// =============== data fim ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 2;
			gbc.gridy = 1;
			pnlFilter.add(new JLabel("Period End"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 1;
			pnlFilter.add(getDtEnd(), gbc);

			// =============== buttons ==================
			JPanel panelBtns = new JPanel();
			panelBtns.setLayout(new GridBagLayout());
			
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 4;
			gbc.gridy = 1;
			gbc.weightx = 1;
			pnlFilter.add(panelBtns, gbc);
			
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 0;
			gbc.gridy = 0;
			panelBtns.add(getBtnSearch(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelBtns.add(getBtnExport(), gbc);
		}
		return pnlFilter;
	}

	public final JButton getBtnSearch() {
		if (this.btnSearch == null) {
			ImageIcon img = OsratUtil.createImageIcon("search.png");
			btnSearch = new JButton(img);
			
			this.btnSearch.addActionListener(new ActionListener() {

				@SuppressWarnings("unchecked")
				@Override
				public void actionPerformed(final ActionEvent e) {

					List<Ranking> ranking = new ArrayList<Ranking>();

					Node root = (Node) getCmbGroups().getSelectedItem();

					String type = (String) getCmbTypeLog().getSelectedItem();
					lblTable.setText(type);

					Date periodStart = null;
					if (getDtInit().getModel().getValue() != null) {
						periodStart = (Date) getDtInit().getModel().getValue();
					}

					Date periodEnd = null;
					if (getDtEnd().getModel().getValue() != null) {
						periodEnd = (Date) getDtEnd().getModel().getValue();
					}

					// salva o filtro da pesquisa
					group = root.getName();
					typeLog = type;

					SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
					dateInit = null;
					if (periodStart != null) {
						dateInit = dtFormat.format(periodStart);
					}
					dateEnd = null;
					if (periodEnd != null) {
						dateEnd = dtFormat.format(periodEnd);
					}

					DefaultMutableTreeNode model = root.getModel();
					Enumeration<TreeNode> children = model
							.breadthFirstEnumeration();

					while (children.hasMoreElements()) {
						TreeNode child = children.nextElement();
						Node node = (Node) ((DefaultMutableTreeNode) child)
								.getUserObject();

						if (!TypeNode.MACHINE.equals(node.getTypeNode())) {
							continue;
						}

						if (TypeLog.KERNEL.name().equalsIgnoreCase(type)) {
							ranking.add(new Ranking(node.getName(), ReadRanking
									.getTotal(node.getKernels(), periodStart,
											periodEnd)));
						} else if (TypeLog.SERVICE.name()
								.equalsIgnoreCase(type)) {
							ranking.add(new Ranking(node.getName(), ReadRanking
									.getTotal(node.getServices(), periodStart,
											periodEnd)));
						} else if (TypeLog.OS_APPLICATION.name()
								.equalsIgnoreCase(type)) {
							ranking.add(new Ranking(node.getName(), ReadRanking
									.getTotal(node.getOsApps(), periodStart,
											periodEnd)));
						} else if (TypeLog.USER_APPLICATION.name()
								.equalsIgnoreCase(type)) {
							ranking.add(new Ranking(node.getName(), ReadRanking
									.getTotal(node.getUserApps(), periodStart,
											periodEnd)));
						} else {
							long total = 0;
							total += ReadRanking.getTotal(node.getKernels(),
									periodStart, periodEnd);
							total += ReadRanking.getTotal(node.getServices(),
									periodStart, periodEnd);
							total += ReadRanking.getTotal(node.getOsApps(),
									periodStart, periodEnd);
							total += ReadRanking.getTotal(node.getUserApps(),
									periodStart, periodEnd);
							ranking.add(new Ranking(node.getName(), total));
						}

					}

					Collections.sort(ranking,
							Collections.reverseOrder(new Comparator<Ranking>() {

								@Override
								public int compare(final Ranking r1,
										final Ranking r2) {
									int t1 = r1.getTotal().intValue();
									int t2 = r2.getTotal().intValue();

									if (t1 == t2) {
										return 0;
									} else if (t1 > t2) {
										return 1;
									} else {
										return -1;
									}
								}
							}));

					if(ranking == null || ranking.isEmpty()){
						JOptionPane.showMessageDialog(null, "No records found.");
					}
					
					putValuesTable(ranking);

				}
			});
		}
		return btnSearch;
	}

	public final JPanel getPnlTable() {
		if (this.pnlTable == null) {
			pnlTable = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Ranking");
			pnlTable.setBorder(titled);

			lblTable = new JLabel("Total");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlTable.add(lblTable, gbc);

			gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlTable.add(new JScrollPane(getTable()), gbc);
		}
		return pnlTable;
	}

	public final JTable getTable() {
		if (this.table == null) {

			DefaultTableModel dataModel = new DefaultTableModel();
			Object[] tableColumnNames = {"Sdf", "Total"};
			dataModel.setColumnIdentifiers(tableColumnNames);

			this.table = new JTable(dataModel);
		}
		return table;
	}

	public final JDatePickerImpl getDtInit() {
		if (dtInit == null) {
			UtilDateModel model = new UtilDateModel();
			JDatePanelImpl datePanel = new JDatePanelImpl(model);
			dtInit = new JDatePickerImpl(datePanel);

		}
		return dtInit;
	}

	public final JDatePickerImpl getDtEnd() {
		if (dtEnd == null) {
			UtilDateModel model = new UtilDateModel();
			JDatePanelImpl datePanel = new JDatePanelImpl(model);
			dtEnd = new JDatePickerImpl(datePanel);

			dtEnd.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					JDatePanelImpl datePanelImpl = (JDatePanelImpl) e
							.getSource();

					if (datePanelImpl.getModel().getValue() == null) {
						return;
					}

					Date periodStart = null;
					if (getDtInit().getModel().getValue() != null) {
						periodStart = (Date) getDtInit().getModel().getValue();
					}

					Date periodEnd = (Date) datePanelImpl.getModel().getValue();
					if (periodStart != null
							&& periodEnd.compareTo(periodStart) < 0) {
						datePanelImpl.getModel().setValue(null);
					}
				}
			});
		}
		return dtEnd;
	}

	@SuppressWarnings("unchecked")
	public final JComboBox<Node> getCmbGroups() {
		if (this.cmbGroups == null) {

			List<Node> groups = new ArrayList<Node>();

			DefaultMutableTreeNode root = (DefaultMutableTreeNode) MainFrame
					.getTree().getModel().getRoot();
			Node node = (Node) ((DefaultMutableTreeNode) root).getUserObject();
			groups.add(node);

			Enumeration<TreeNode> children = root.breadthFirstEnumeration();
			while (children.hasMoreElements()) {
				TreeNode child = children.nextElement();
				node = (Node) ((DefaultMutableTreeNode) child).getUserObject();
				if (!TypeNode.GROUP.equals(node.getTypeNode())) {
					continue;
				}
				groups.add(node);
			}

			cmbGroups = new JComboBox<Node>(groups.toArray(new Node[groups
					.size()]));
		}
		return cmbGroups;
	}

	public final JComboBox<String> getCmbTypeLog() {
		if (this.cmbTypeLog == null) {
			TypeLog[] types = TypeLog.values();

			List<String> names = new ArrayList<String>();
			names.add("ALL");

			for (int i = 0; i < types.length; i++) {
				String name = types[i].name();
				if (TypeLog.IGNORE.name().equals(name)) {
					continue;
				}
				names.add(name);
			}
			cmbTypeLog = new JComboBox<String>(names.toArray(new String[names
					.size()]));
		}
		return cmbTypeLog;
	}

	public final void putValuesTable(final List<Ranking> ranking) {
		DefaultTableModel dataModel = (DefaultTableModel) getTable().getModel();
		dataModel.getDataVector().removeAllElements();

		for (Ranking rank : ranking) {
			Object[] obj = new Object[table.getColumnCount()];
			obj[0] = rank.getName();
			obj[1] = rank.getTotal();
			dataModel.addRow(obj);
		}
	}

	private JButton getBtnExport() {
		if (btnExport == null) {
			ImageIcon imgXls = OsratUtil
					.createImageIcon("xls1.png");
			btnExport = new JButton(imgXls);
			btnExport.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent e) {
					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(new FileNameExtensionFilter(
							"Excel Workbook", "xls"));
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

					if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						File fileXls = new File(file.getAbsolutePath() + ".xls");
						try {
							WritableWorkbook workbook = Workbook
									.createWorkbook(fileXls);
							WritableSheet sheet = workbook.createSheet(
									lblTable.getText(), 0);

							// Filtro
							int row = 0;
							sheet.addCell(createLabelCellBold("Filter", 0, row));

							sheet.addCell(createLabelCell("Group", 0, ++row));
							sheet.addCell(createLabelCell(group, 1, row));

							sheet.addCell(createLabelCell("Type Log", 0, ++row));
							sheet.addCell(createLabelCell(typeLog, 1, row));

							sheet.addCell(createLabelCell("Init date", 0, ++row));
							sheet.addCell(createLabelCell(dateInit, 1, row));

							sheet.addCell(createLabelCell("Init end", 0, ++row));
							sheet.addCell(createLabelCell(dateEnd, 1, row));

							row++; // pula uma linha

							// Ranking
							sheet.addCell(createLabelCellBold("Sdf", 0, ++row));
							sheet.addCell(createLabelCellBold("Total", 1, row));

							DefaultTableModel model = (DefaultTableModel) getTable()
									.getModel();
							int nRow = model.getRowCount();
							for (int i = 0; i < nRow; i++) {
								row++;
								sheet.addCell(createLabelCell(
										(String) table.getValueAt(i, 0), 0, row));
								sheet.addCell(createIntCell(
										(long) table.getValueAt(i, 1), 1, row));
							}

							workbook.write();
							workbook.close();

							JOptionPane.showMessageDialog(null,
									"Ranking successfully saved.");

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			});
		}
		return btnExport;
	}

	private static Label createLabelCellBold(final String value, final int c,
			final int r) {
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

	private static Label createLabelCell(final String value, final int c,
			final int r) {
		return new Label(c, r, value);
	}

	private static Number createIntCell(final long value, final int c,
			final int r) {
		WritableCellFormat integerFormat = new WritableCellFormat(
				NumberFormats.INTEGER);
		return new Number(c, r, value, integerFormat);
	}

}
