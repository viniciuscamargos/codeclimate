package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class AbstractComboBoxRenderer<T> extends JComboBox<T> implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	public AbstractComboBoxRenderer(T[] items) {
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

		setSelectedItem(value);
		return this;
	}

}
