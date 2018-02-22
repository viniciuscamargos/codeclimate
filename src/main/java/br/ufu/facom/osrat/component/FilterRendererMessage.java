package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FilterRendererMessage extends DefaultTableCellRenderer {

	@Override
	public final Component getTableCellRendererComponent(final JTable table,
			final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		String message = (String) table.getValueAt(row, 5);
		setToolTipText(message);
		return this;
	}

}
