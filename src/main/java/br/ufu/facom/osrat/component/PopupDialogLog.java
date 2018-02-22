package br.ufu.facom.osrat.component;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import br.ufu.facom.osrat.util.OsratUtil;

public class PopupDialogLog extends JDialog {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;

	public PopupDialogLog() {
		super((Frame) null, "Message (" + OsratUtil.getTitleSystem() + ")",
				true);
		setIconImage(OsratUtil.getMainIcon().getImage());

		textArea = new JTextArea(5, 50);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		KeyStroke keyStroke = KeyStroke.getKeyStroke("ENTER");
		textArea.getInputMap().put(keyStroke, "none");
		JScrollPane scrollPane = new JScrollPane(textArea);
		getContentPane().add(scrollPane);

		JPanel buttons = new JPanel();
		getContentPane().add(buttons, BorderLayout.SOUTH);
		pack();
	}

	public final void setText(final String text) {
		textArea.setText(text);
	}

}
