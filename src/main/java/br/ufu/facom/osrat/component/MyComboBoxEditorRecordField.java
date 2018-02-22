package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;

import br.ufu.facom.osrat.model.FieldRecord;

public class MyComboBoxEditorRecordField extends DefaultCellEditor {

	public MyComboBoxEditorRecordField(final FieldRecord[] items) {
		super(new JComboBox<FieldRecord>(items));
	}

	@Override
	public final Component getTableCellEditorComponent(final JTable table,
			final Object value, final boolean isSelected, final int row,
			final int column) {
		return super.getTableCellEditorComponent(table, value, isSelected, row,
				column);
	}

}
