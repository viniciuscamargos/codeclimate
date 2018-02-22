package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
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
import javax.swing.JTextField;
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
import br.ufu.facom.hpcs.entity.Aggregation;
import br.ufu.facom.osrat.component.AbstractComboBoxRenderer;
import br.ufu.facom.osrat.component.MyComboBoxEditorRecordField;
import br.ufu.facom.osrat.component.MyComboBoxEditorString;
import br.ufu.facom.osrat.component.MyComboBoxRendererRecordField;
import br.ufu.facom.osrat.component.MyComboBoxRendererString;
import br.ufu.facom.osrat.model.ConditionalOperator;
import br.ufu.facom.osrat.model.FieldRecord;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.util.OsratUtil;

public class SearchFrame extends JPanel {

	/**
	 * Tela Source e Product Name.
	 */
	private static final long serialVersionUID = 1L;

	private JPanel pnlFilter;
	private JComboBox<Node> cmbGroups;
	private JComboBox<Node> cmbSdfs;
	private JComboBox<TypeLog> cmbTypeLog;
	private JComboBox<Aggregation> cmbAggregation;
	private JDatePickerImpl dtInit;
	private JDatePickerImpl dtEnd;

	private JPanel pnlAgregation;
	private JTextField txtAggregation;
	private JButton btnAdd;
	private JButton btnSave;
	private JButton btnClean;
	private JTable tblAgreggation;

	private String group;
	private String sdf;
	private String typeLog;
	private String dateInit;
	private String dateEnd;

	private JPanel pnlTable;
	private JScrollPane scrollPaneTable;
	private JTable table;

	private JButton btnSearch;
	private JButton btnExport;

	/**
	 * Construtor da classe.
	 */
	public SearchFrame() {
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
		gbc.weightx = 1;
		this.add(getPnlAgregation(), gbc);

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 1;
		this.add(getPnlTable(), gbc);
	}

	/**
	 * Cria e retorna o botão para adicionar uma nova linha do agrupamento.
	 * @return JButton.
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			ImageIcon imgSave = OsratUtil
					.createImageIcon("add.png");
			btnAdd = new JButton(imgSave);

			this.btnAdd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					DefaultTableModel dataModel = (DefaultTableModel) getTblAgreggation()
							.getModel();
					int i = 0;
					Object[] obj = new Object[tblAgreggation.getColumnCount()];
					obj[i] = "AND";
					obj[++i] = FieldRecord.COMPUTER_NAME;
					obj[++i] = "=";
					obj[++i] = "";

					dataModel.addRow(obj);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * Cria e retorna o botão limpar.
	 * @return JButton
	 */
	private JButton getBtnClean() {
		if (btnClean == null) {
			ImageIcon imgSave = OsratUtil
					.createImageIcon("clear.png");
			btnClean = new JButton(imgSave);

			this.btnClean.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					DefaultTableModel dataModel = (DefaultTableModel) getTblAgreggation()
							.getModel();
					dataModel.getDataVector().removeAllElements();
					getTblAgreggation().updateUI();
				}
			});
		}
		return btnClean;
	}

	/**
	 * Cria o botão salvar.
	 * @return JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			ImageIcon imgSave = OsratUtil
					.createImageIcon("save.png");
			btnSave = new JButton(imgSave);

			this.btnSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					if (txtAggregation == null || txtAggregation.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Enter the name of the aggregation.");
						getTblAgreggation().clearSelection();
						txtAggregation.requestFocus();
					
					} else if (getTblAgreggation().getRowCount() == 0) {
						JOptionPane.showMessageDialog(null, "Inset a row in table aggregation.");
					} else {
						/*AggregationDao dao = new AggregationDaoJDBC();
						Aggregation agg = new Aggregation();
						agg.setName(txtAggregation.getText());
						agg = dao.save(agg);

						DefaultTableModel model = (DefaultTableModel) getTblAgreggation()
								.getModel();
						int nRow = model.getRowCount();
						for (int i = 0; i < nRow; i++) {
							int col = 0;
							AggregationField field = new AggregationField();
							field.setOpLogic((String) getTblAgreggation().getValueAt(i, col));
							
							FieldRecord fieldRecord = (FieldRecord) getTblAgreggation().getValueAt(i, ++col);
							field.setField(fieldRecord.getField());
							
							field.setOpCond((String) getTblAgreggation().getValueAt(i, ++col));
							
							getTblAgreggation().getCellEditor(i, 3).stopCellEditing();
							String vlField = (String) getTblAgreggation().getValueAt(i, ++col);
							if(vlField == null || vlField.trim().isEmpty()){
								JOptionPane.showMessageDialog(null, "Insert a value in row '" + (i + 1) + "'.");
								return;
							}
							field.setVlrField(vlField);
							
							//field.setIdAggregations(agg.getId());
							dao.saveField(field);
						}

						JOptionPane.showMessageDialog(null,
								"Aggregation saved successfully.");
						getCmbAggregations().addItem(agg);
*/					}
				}
			});
		}
		return btnSave;
	}

	public JTable getTblAgreggation() {
		if (this.tblAgreggation == null) {
			tblAgreggation = new JTable();

			DefaultTableModel dataModel = new DefaultTableModel();
			Object[] tableColumnNames = {"Operator", "Field", "Operator", "Value" };
			dataModel.setColumnIdentifiers(tableColumnNames);

			this.tblAgreggation = new JTable(dataModel);

			String[] logicOps = {"AND", "OR"};
			FieldRecord[] fields = FieldRecord.values();
			ConditionalOperator[] logicCond = ConditionalOperator.values();

			tblAgreggation.getColumnModel().getColumn(0)
					.setCellRenderer(new MyComboBoxRendererString(logicOps));
			tblAgreggation.getColumnModel().getColumn(0)
					.setCellEditor(new MyComboBoxEditorString(logicOps));

			tblAgreggation.getColumnModel().getColumn(1)
					.setCellRenderer(new MyComboBoxRendererRecordField(fields));
			tblAgreggation.getColumnModel().getColumn(1)
					.setCellEditor(new MyComboBoxEditorRecordField(fields));

			tblAgreggation.getColumnModel().getColumn(2).setCellRenderer(new AbstractComboBoxRenderer(ConditionalOperator.values()));
			/*tblAgreggation.getColumnModel().getColumn(2)
					.setCellEditor(new MyComboBoxEditorString(logicCond));*/
			
			//tblAgreggation.getColumnModel().getColumn(3)
			//		.setCellEditor(new MyTableCellEditor());
		}
		return tblAgreggation;
	}

	/**
	 * Cria o panel filtro.
	 * @return JPanel
	 */
	private JPanel getPnlFilter() {
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
			gbc.gridx = 0;
			gbc.gridy = 1;
			pnlFilter.add(new JLabel("Logs"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			gbc.gridy = 1;
			pnlFilter.add(getCmbTypeLog(), gbc);

			// =============== combo logs ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 2;
			gbc.gridy = 1;
			pnlFilter.add(new JLabel("Aggregations"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 1;
			pnlFilter.add(getCmbAggregations(), gbc);

			// =============== data inicio ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			gbc.gridy = 2;
			pnlFilter.add(new JLabel("Period Start"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 1;
			gbc.gridy = 2;
			pnlFilter.add(getDtInit(), gbc);

			// =============== data fim ==================
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 2;
			gbc.gridy = 2;
			pnlFilter.add(new JLabel("Period End"), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 3;
			gbc.gridy = 2;
			pnlFilter.add(getDtEnd(), gbc);

			// =============== buttons ==================
			JPanel panelBtns = new JPanel();
			panelBtns.setLayout(new GridBagLayout());
			
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 4;
			gbc.gridy = 2;
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

	/**
	 * Cria o botão de pesquisa.
	 * @return JButton
	 */
	public JButton getBtnSearch() {
		if (this.btnSearch == null) {
			ImageIcon img = OsratUtil.createImageIcon("search.png");
			btnSearch = new JButton(img);
		}
		return btnSearch;
	}

	private JPanel getPnlAgregation() {
		if (this.pnlAgregation == null) {
			this.pnlAgregation = new JPanel();
			pnlAgregation.setLayout(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Aggregation");
			pnlAgregation.setBorder(titled);

			// =============== txt aggregations ==================
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.EAST;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlAgregation.add(new JLabel("Name"), gbc);

			this.txtAggregation = new JTextField();
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			pnlAgregation.add(txtAggregation, gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 2;
			gbc.weightx = 1;
			pnlAgregation.add(new JScrollPane(getTblAgreggation()), gbc);

			JPanel pnlBtns = new JPanel(new GridBagLayout());

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 2;
			gbc.gridy = 1;
			pnlAgregation.add(pnlBtns, gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			pnlBtns.add(getBtnAdd(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 1;
			pnlBtns.add(getBtnClean(), gbc);

			gbc = new GridBagConstraints();
			gbc.insets = new Insets(2, 2, 2, 2);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 2;
			pnlBtns.add(getBtnSave(), gbc);
		}
		return pnlAgregation;
	}

	public final JPanel getPnlTable() {
		if (this.pnlTable == null) {
			pnlTable = new JPanel(new GridBagLayout());

			TitledBorder titled = new TitledBorder("Records");
			pnlTable.setBorder(titled);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			pnlTable.add(new JScrollPane(getTable()), gbc);
		}
		return pnlTable;
	}

	public JTable getTable() {
		if(this.table == null){			
			DefaultTableModel dataModel = new DefaultTableModel();
			Object[] tableColumnNames = {"Computer Name", "Event Identifier", "Product Name", "Source Name", "Time Generated", "Log FIle", "Message", "Insertin String", "Log Type"};
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
					if (periodStart != null && periodEnd.compareTo(periodStart) < 0) {
						datePanelImpl.getModel().setValue(null);
					}
				}
			});
		}
		return dtEnd;
	}

	/**
	 * Cria e retorna o combo grupos.
	 * @return JComboBox
	 */
	@SuppressWarnings("unchecked")
	public JComboBox<Node> getCmbGroups() {
		if (this.cmbGroups == null) {

			final List<Node> groups = new ArrayList<Node>();

			final DefaultMutableTreeNode root = (DefaultMutableTreeNode) MainFrame
					.getTree().getModel().getRoot();
			Node node = (Node) ((DefaultMutableTreeNode) root).getUserObject();
			groups.add(node);

			final Enumeration<TreeNode> children = root
					.breadthFirstEnumeration();
			while (children.hasMoreElements()) {
				final TreeNode child = children.nextElement();
				node = (Node) ((DefaultMutableTreeNode) child).getUserObject();
				if (!TypeNode.GROUP.equals(node.getTypeNode())) {
					continue;
				}
				groups.add(node);
			}

			cmbGroups = new JComboBox<Node>(groups.toArray(new Node[groups
					.size()]));

			cmbGroups.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					final JComboBox<Node> combo = (JComboBox<Node>) (e
							.getSource());
					putComboSdfs((Node) combo.getSelectedItem());
				}
			});
		}
		return cmbGroups;
	}

	public JComboBox<TypeLog> getCmbTypeLog() {
		if (this.cmbTypeLog == null) {
			TypeLog[] types = TypeLog.getFailureLogs();
			/*List<String> names = new ArrayList<String>();
			names.add("ALL");*/
			cmbTypeLog = new JComboBox<TypeLog>(types);
		}
		return cmbTypeLog;
	}

	public JComboBox<Aggregation> getCmbAggregations() {
		if (this.cmbAggregation == null) {
			
			cmbAggregation = new JComboBox<Aggregation>();

			/*Aggregation agg = new Aggregation();
			agg.setName("ANY");
			cmbAggregation.addItem(agg);

			AggregationDAO dao = new Aggregation();
			List<Aggregation> list = dao.getAll();
			Aggregation[] aggs = list.toArray(new Aggregation[list.size()]);
			for (Aggregation aggregation : aggs) {
				cmbAggregation.addItem(aggregation);
			}
			
			cmbAggregation.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					Aggregation agg = (Aggregation) getCmbAggregations()
							.getSelectedItem();
					if("ANY".equals(agg.getName())){
						DefaultTableModel dataModel = (DefaultTableModel) getTblAgreggation()
								.getModel();
						dataModel.getDataVector().removeAllElements();
						getTblAgreggation().updateUI();
						return;
					}
					AggregationDao dao = new AggregationDaoJDBC();
					List<FieldAggregation> fields = dao.getFields(agg.getId());

					DefaultTableModel dataModel = (DefaultTableModel) getTblAgreggation()
							.getModel();

					dataModel.getDataVector().removeAllElements();

					for (FieldAggregation field : fields) {
						int i = 0;
						Object[] obj = new Object[4];
						obj[i] = field.getOpLogic();
						obj[++i] = FieldRecord.valueOf(field.getField().toUpperCase());
						obj[++i] = field.getOpCond();
						obj[++i] = field.getVlrField();
						dataModel.addRow(obj);
					}
				}
			});
*/
		}
		return cmbAggregation;
	}

	public final void setCmbAggregation(final JComboBox<Aggregation> cmbAggregation) {
		this.cmbAggregation = cmbAggregation;
	}


	public final void putValuesTable(final List<Object[]> objs) {
		DefaultTableModel dataModel = (DefaultTableModel) getTable()
				.getModel();
		dataModel.getDataVector().removeAllElements();

		for (Object[] obj : objs) {
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
							WritableSheet sheet = workbook.createSheet("Records", 0);

							// Filtro
							int row = 0;
							sheet.addCell(createLabelCellBold("Filter", 0, row));

							sheet.addCell(createLabelCell("Group", 0, ++row));
							sheet.addCell(createLabelCell(group, 1, row));

							sheet.addCell(createLabelCell("Sdf", 0, ++row));
							sheet.addCell(createLabelCell(sdf, 1, row));

							sheet.addCell(createLabelCell("Type Log", 0, ++row));
							sheet.addCell(createLabelCell(typeLog, 1, row));

							sheet.addCell(createLabelCell("Init date", 0, ++row));
							sheet.addCell(createLabelCell(dateInit, 1, row));

							sheet.addCell(createLabelCell("Init end", 0, ++row));
							sheet.addCell(createLabelCell(dateEnd, 1, row));

							row++; // pula uma linha

							sheet.addCell(createLabelCellBold("Source name", 0,
									++row));
							sheet.addCell(createLabelCellBold("Product name",
									1, row));
							sheet.addCell(createLabelCellBold("Total", 2, row));

							DefaultTableModel model = (DefaultTableModel) table
									.getModel();
							int nRow = model.getRowCount();
							for (int i = 0; i < nRow; i++) {
								row++;
								sheet.addCell(createLabelCell(
										(String) table.getValueAt(i, 0), 0, row));
								sheet.addCell(createLabelCell(
										(String) table.getValueAt(i, 1), 1, row));
								sheet.addCell(createIntCell(
										(long) table.getValueAt(i, 2), 2, row));
							}

							workbook.write();
							workbook.close();

							JOptionPane.showMessageDialog(null,
									"Sheet successfully saved.");

						} catch (Exception e1) {
							e1.printStackTrace();
						}

					}
				}
			});
		}
		return btnExport;
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
		WritableCellFormat integerFormat = new WritableCellFormat(
				NumberFormats.INTEGER);
		return new Number(c, r, value, integerFormat);
	}

	public final JScrollPane getScrollPaneTable() {
		if (scrollPaneTable == null) {
			scrollPaneTable = new JScrollPane();
		}
		return scrollPaneTable;
	}

	public JComboBox<Node> getCmbSdfs() {
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

		Enumeration<TreeNode> children = node.getModel()
				.breadthFirstEnumeration();
		while (children.hasMoreElements()) {
			TreeNode child = children.nextElement();
			Node nd = (Node) ((DefaultMutableTreeNode) child).getUserObject();
			if (!TypeNode.MACHINE.equals(nd.getTypeNode())) {
				continue;
			}
			sdfs.add(nd);
		}

		getCmbSdfs().setModel(
				new JComboBox<Node>(sdfs.toArray(new Node[sdfs.size()]))
						.getModel());
		getCmbSdfs().insertItemAt(new Node("ALL", TypeNode.MACHINE, null), 0);
		getCmbSdfs().setSelectedIndex(0);
	}

}

