package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.util.OsratUtil;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class DistributionsFrame extends JPanel {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnlFilter;
	private JComboBox<Node> cmbGroups;
	private JComboBox<Node> cmbSdfs;
	private JComboBox<TypeLog> cmbTypeLog;
	private JDatePickerImpl dtInit;
	private JDatePickerImpl dtEnd;

	private JPanel pnlDistributions;
	private JTable tableDistributions;

	private JPanel pnlMetrics;
	private JTable tableMetrics;

	private JButton btnSearch;
	private JButton btnGraph;

	private JPanel pnlTbf;
	private JTable tableTbf;
	private List<Double> tbfs;

	public DistributionsFrame() {
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
		gbc.weightx = 1;
		this.add(getPnlTbf(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 1;
		gbc.weightx = 1;
		this.add(getPnlDistributions(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weighty = 1;
		gbc.weightx = 1;
		this.add(getPnlMetrics(), gbc);
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

			// =============== combo sdfs ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 2;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Sdfs"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 0;
			pnlFilter.add(getCmbSdfs(), gbc);

			// =============== combo logs ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 4;
			gbc.gridy = 0;
			pnlFilter.add(new JLabel("Logs"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 5;
			gbc.gridy = 0;
			gbc.weightx = 1;
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
			gbc.gridwidth = 2;
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
			panelBtns.add(getBtnGraph(), gbc);
		}
		return pnlFilter;
	}

	public final JPanel getPnlDistributions() {
		if (this.pnlDistributions == null) {
			pnlDistributions = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Distributions");
			pnlDistributions.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlDistributions.add(new JScrollPane(getTableDistributions()), gbc);
		}
		return pnlDistributions;
	}

	@SuppressWarnings("serial")
	public final JTable getTableDistributions() {
		if (this.tableDistributions == null) {

			Object[] tableColumnNames = { "", "Distributions", "Kolmogorov Smirnov", "RK", "Anderson Darling", "RK",
					"Chi-Squared", "RK" };
			Object[][] data = { { false, "Exponencial", "0", "0", "0", "0", "0", "0" },
					{ false, "Lognormal", "0", "0", "0", "0", "0", "0" },
					{ false, "Gamma", "0", "0", "0", "0", "0", "0" },
					{ false, "Weibull", "0", "0", "0", "0", "0", "0" }, };
			DefaultTableModel dataModel = new DefaultTableModel(data, tableColumnNames) {
				public boolean isCellEditable(int row, int column) {
					return column != 0;
				};
			};

			tableDistributions = new JTable(dataModel) {

				private static final long serialVersionUID = 1L;

				@Override
				public Class getColumnClass(final int column) {
					switch (column) {
					case 0:
						return Boolean.class;
					default:
						return String.class;
					}
				}
			};

			tableDistributions.getColumnModel().getColumn(0).setMaxWidth(50);
			tableDistributions.setEnabled(false);
		}
		return tableDistributions;
	}

	public final JPanel getPnlMetrics() {
		if (this.pnlMetrics == null) {
			pnlMetrics = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Metrics");
			pnlMetrics.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlMetrics.add(new JScrollPane(getTableMetrics()), gbc);
		}
		return pnlMetrics;
	}

	public final JTable getTableMetrics() {
		if (this.tableMetrics == null) {

			Object[] tableColumnNames = { "Metric", "Value" };

			Object[][] data = { { "Mean Time Between Failure", "" }, { "Reability", "" }, { "Unreability", "" },
					{ "Hazard rate", "" }, { "Calculate", "" } };
			DefaultTableModel dataModel = new DefaultTableModel(data, tableColumnNames);
			this.tableMetrics = new JTable(dataModel);

		}
		return tableMetrics;
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
					JDatePanelImpl datePanelImpl = (JDatePanelImpl) e.getSource();

					if (datePanelImpl.getModel().getValue() == null) {
						return;
					}

					Date periodStart = null;
					if (getDtInit().getModel().getValue() != null) {
						periodStart = (Date) getDtInit().getModel().getValue();
					}
					Date periodEnd = (Date) datePanelImpl.getModel().getValue();
					if (periodStart != null && periodEnd.compareTo(periodStart) < 0) {
						datePanelImpl.getModel().setValue(null);
					}
				}
			});
		}
		return dtEnd;
	}

	public JButton getBtnGraph() {
		if (btnGraph == null) {
			ImageIcon img = OsratUtil.createImageIcon("chart_bar.png");
			btnGraph = new JButton(img);
			btnGraph.setEnabled(false);
		}
		return btnGraph;
	}

	public final JButton getBtnSearch() {
		if (btnSearch == null) {
			ImageIcon img = OsratUtil.createImageIcon("search.png");
			btnSearch = new JButton(img);
		}
		return btnSearch;
	}

	public final void putValuesTableTbf(final List<Long> tbfs) {
		DefaultTableModel dataModel = (DefaultTableModel) getTableTbf().getModel();
		dataModel.getDataVector().removeAllElements();

		for (Long tbf : tbfs) {
			Object[] obj = new Object[getTableTbf().getColumnCount()];
			obj[0] = tbf;
			dataModel.addRow(obj);
		}
	}

	@SuppressWarnings("unchecked")
	public final JComboBox<Node> getCmbGroups() {
		if (this.cmbGroups == null) {

			List<Node> groups = new ArrayList<Node>();

			DefaultMutableTreeNode root = (DefaultMutableTreeNode) MainFrame.getTree().getModel().getRoot();
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

			cmbGroups = new JComboBox<Node>(groups.toArray(new Node[groups.size()]));

			cmbGroups.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					JComboBox<Node> combo = (JComboBox<Node>) (e.getSource());
					putComboSdfs((Node) combo.getSelectedItem());
				}
			});
		}
		return cmbGroups;
	}

	public final JComboBox<Node> getCmbSdfs() {
		if (this.cmbSdfs == null) {
			cmbSdfs = new JComboBox<Node>();
			cmbSdfs.insertItemAt(new Node("ALL", TypeNode.MACHINE, null), 0);
			cmbSdfs.setSelectedIndex(0);
		}
		return cmbSdfs;
	}

	@SuppressWarnings("unchecked")
	private void putComboSdfs(final Node node) {
		List<Node> sdfs = new ArrayList<Node>();

		Enumeration<TreeNode> children = node.getModel().breadthFirstEnumeration();
		while (children.hasMoreElements()) {
			TreeNode child = children.nextElement();
			Node nd = (Node) ((DefaultMutableTreeNode) child).getUserObject();
			if (!TypeNode.MACHINE.equals(nd.getTypeNode())) {
				continue;
			}
			sdfs.add(nd);
		}

		getCmbSdfs().setModel(new JComboBox<>(sdfs.toArray(new Node[sdfs.size()])).getModel());
	}

	public final JComboBox<TypeLog> getCmbTypeLog() {
		if (this.cmbTypeLog == null) {
			TypeLog[] types = TypeLog.getFailureLogs();
			cmbTypeLog = new JComboBox<TypeLog>(types);
		}
		return cmbTypeLog;
	}

	public List<Double> getTbfs() {
		return tbfs;
	}

	public void setTbfs(List<Double> tbfs) {
		this.tbfs = tbfs;
	}

	public JPanel getPnlTbf() {
		if (this.pnlTbf == null) {
			pnlTbf = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Tbf(s)");
			pnlTbf.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlTbf.add(new JScrollPane(getTableTbf()), gbc);
		}
		return pnlTbf;
	}

	public JTable getTableTbf() {
		if (this.tableTbf == null) {

			DefaultTableModel dataModel = new DefaultTableModel();
			Object[] tableColumnNames = { "Time Generated", "TBF" };
			dataModel.setColumnIdentifiers(tableColumnNames);

			this.tableTbf = new JTable(dataModel);
		}
		return tableTbf;
	}

}
