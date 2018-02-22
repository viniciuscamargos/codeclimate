package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class MyTableCellEditor extends AbstractCellEditor implements
		TableCellEditor {

	private JComponent component = new JTextField();

	public final Component getTableCellEditorComponent(final JTable table, final Object value,
			final boolean isSelected, final int rowIndex, final int vColIndex) {

		((JTextField) component).setText((String) value);

		return component;
	}

	public final Object getCellEditorValue() {
		return ((JTextField) component).getText();
	}
}
