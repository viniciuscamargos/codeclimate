package br.ufu.facom.osrat.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.StatUtils;

import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.util.OsratUtil;

public class SdfGroupNodeFrame extends JPanel {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;
	private Node node;

	private JPanel pnlCT;
	private JTable tableCt;

	private JPanel pnlNF;
	private JTable tableNf;

	private JPanel pnlBtns;
	private JButton btnGraph;
	private JButton btnMetrics;

	public SdfGroupNodeFrame(final Node node) {
		this.node = node;

		this.setLayout(new GridBagLayout());
		this.setBackground(Color.white);

		readAllChildNodes(node.getModel());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		this.add(getPnlNF(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		this.add(getPnlCT(), gbc);
	}

	@SuppressWarnings("unchecked")
	private void readAllChildNodes(final DefaultMutableTreeNode dNode) {
		Enumeration<TreeNode> children = ((DefaultMutableTreeNode) dNode)
				.breadthFirstEnumeration();
		while (children.hasMoreElements()) {
			TreeNode child = children.nextElement();
			Node nn = (Node) ((DefaultMutableTreeNode) child).getUserObject();
			if (child.getParent() == null
					|| TypeNode.GROUP.equals(nn.getTypeNode())) {
				continue;
			}

			Double totalNF = new Double(nn.getKernels().size()
					+ nn.getServices().size() + nn.getOsApps().size()
					+ nn.getUserApps().size());
			node.getListNF().add(totalNF);

			node.getListCT().add((nn.getNumberDays().doubleValue()));
		}
	}

	@SuppressWarnings("serial")
	public final JButton getBtnMetrics() {
		if (btnMetrics == null) {
			final JPopupMenu popup = new JPopupMenu();
			popup.add(new JMenuItem(new AbstractAction("MTTF") {
				public void actionPerformed(final ActionEvent e) {
					// showMTTF();
				}
			}));
			popup.add(new JMenuItem(new AbstractAction("TBF") {
				public void actionPerformed(final ActionEvent e) {
					// showTBF();
				}
			}));

			popup.add(new JMenuItem(new AbstractAction("Useful Life") {
				public void actionPerformed(final ActionEvent e) {
					// showUseful();
				}
			}));

			ImageIcon imgBtnGraph = OsratUtil
					.createImageIcon("br/ufu/facom/osrat/image/metric.png");
			btnMetrics = new JButton(imgBtnGraph);
			btnMetrics.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent e) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			});
		}
		return btnMetrics;
	}

	public final JPanel getPnlCT() {
		if (this.pnlCT == null) {
			pnlCT = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Collection Time");
			pnlCT.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlCT.add(new JScrollPane(getTableCt()), gbc);
		}
		return pnlCT;
	}

	public final JPanel getPnlNF() {
		if (this.pnlNF == null) {
			pnlNF = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Number of Fails");
			pnlNF.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlNF.add(new JScrollPane(getTableNf()), gbc);
		}
		return pnlNF;
	}

	public final JPanel getPnlBtns() {
		if (this.pnlBtns == null) {
			pnlBtns = new JPanel();
			TitledBorder titled = new TitledBorder("");
			pnlBtns.setBorder(titled);
			pnlBtns.setBackground(Color.WHITE);

			// btn graph
			GridBagConstraints gbcBtnGraph = new GridBagConstraints();
			gbcBtnGraph.insets = new Insets(2, 2, 2, 2);
			gbcBtnGraph.gridx = 1;
			gbcBtnGraph.gridy = 0;
			pnlBtns.add(getBtnGraph(), gbcBtnGraph);

			// btn metrics
			GridBagConstraints gbcBtnMetric = new GridBagConstraints();
			gbcBtnMetric.insets = new Insets(2, 2, 2, 2);
			gbcBtnMetric.gridx = 2;
			gbcBtnMetric.gridy = 0;
			pnlBtns.add(getBtnMetrics(), gbcBtnMetric);
		}
		return pnlBtns;
	}

	@SuppressWarnings("serial")
	public final JButton getBtnGraph() {
		if (btnGraph == null) {
			final JPopupMenu popup = new JPopupMenu();
			popup.add(new JMenuItem(new AbstractAction("Bar Line Plot") {
				public void actionPerformed(final ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Option 1 selected");
				}
			}));
			popup.add(new JMenuItem(new AbstractAction("Bar Plot") {
				public void actionPerformed(final ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Option 2 selected");
				}
			}));

			popup.add(new JMenuItem(new AbstractAction("Pie") {
				public void actionPerformed(final ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Option 2 selected");
				}
			}));

			popup.add(new JMenuItem(new AbstractAction("Point Chart") {
				public void actionPerformed(final ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Option 2 selected");
				}
			}));

			ImageIcon imgBtnGraph = OsratUtil
					.createImageIcon("br/ufu/facom/osrat/image/graph.png");
			btnGraph = new JButton(imgBtnGraph);
			btnGraph.addMouseListener(new MouseAdapter() {
				public void mousePressed(final MouseEvent e) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			});

		}
		return btnGraph;
	}

	public final JTable getTableCt() {
		if (tableCt == null) {
			DefaultTableModel dataModel = new DefaultTableModel();

			Object[] tableColumnNames = {"Info", "Value"};
			dataModel.setColumnIdentifiers(tableColumnNames);

			tableCt = new JTable(dataModel);
			tableCt.getTableHeader().setReorderingAllowed(false);

			Vector<Object> row = new Vector<Object>();
			row.addElement("Min");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Max");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Mean");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Median");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Standard Deviation");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Mode");
			row.addElement(null);
			dataModel.addRow(row);
		}
		return tableCt;
	}

	public final JTable getTableNf() {
		if (tableNf == null) {
			DefaultTableModel dataModel = new DefaultTableModel();

			Object[] tableColumnNames = {"Info", "Value"};
			dataModel.setColumnIdentifiers(tableColumnNames);

			tableNf = new JTable(dataModel);
			tableNf.getTableHeader().setReorderingAllowed(false);


			Vector<Object> row = new Vector<Object>();
			row.addElement("Min");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Max");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Mean");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Median");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Standard Deviation");
			row.addElement(null);
			dataModel.addRow(row);

			row = new Vector<Object>();
			row.addElement("Mode");
			row.addElement(null);
			dataModel.addRow(row);
		}
		return tableNf;
	}


}
