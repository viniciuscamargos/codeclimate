package br.ufu.facom.osrat.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class ButtonEditor extends DefaultCellEditor {

	private JButton button;

	private String label;

	private boolean isPushed;

	public ButtonEditor(final JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				fireEditingStopped();
			}
		});
	}
}
