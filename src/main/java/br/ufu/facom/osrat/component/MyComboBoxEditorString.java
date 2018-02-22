package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class MyComboBoxEditorString extends DefaultCellEditor {

	public MyComboBoxEditorString(final String[] items) {
		super(new JComboBox<String>(items));
	}

	@Override
	public final Component getTableCellEditorComponent(final JTable table,
			final Object value, final boolean isSelected, final int row,
			final int column) {
		return super.getTableCellEditorComponent(table, value, isSelected, row,
				column);
	}

}
