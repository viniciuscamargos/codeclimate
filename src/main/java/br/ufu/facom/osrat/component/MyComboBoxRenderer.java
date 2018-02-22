package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import br.ufu.facom.osrat.model.TypeLog;

public class MyComboBoxRenderer extends JComboBox<TypeLog> implements
		TableCellRenderer {
	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;

	public MyComboBoxRenderer(final TypeLog[] items) {
		super(items);
	}

	public final Component getTableCellRendererComponent(final JTable table,
			final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
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
