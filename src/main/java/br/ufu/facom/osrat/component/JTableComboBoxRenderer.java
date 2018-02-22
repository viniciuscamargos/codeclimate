package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class JTableComboBoxRenderer implements TableCellRenderer {
	@Override
	@SuppressWarnings("rawtypes")
	public final Component getTableCellRendererComponent(final JTable table, final Object value,
			final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		JComboBox cmb = (JComboBox) value;
		if (value != null) {
			if (isSelected) {
				cmb.setForeground(table.getSelectionForeground());
				cmb.setBackground(table.getSelectionBackground());
			} else {
				cmb.setForeground(table.getForeground());
				// cmb.setBackground(UIManager.getColor("Button.background"));
			}
		}
		return cmb;
	}
}
