package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import br.ufu.facom.osrat.model.FieldRecord;

public class MyComboBoxRendererRecordField extends JComboBox<FieldRecord> implements
		TableCellRenderer {

	public MyComboBoxRendererRecordField(final FieldRecord[] items) {
		super(items);
	}

	public final Component getTableCellRendererComponent(final JTable table,
			final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		/*
		 * if(row == 0 && column == 0){ return null; }
		 */
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}

		// Select the current value
		setSelectedItem(value);
		return this;
	}

}
