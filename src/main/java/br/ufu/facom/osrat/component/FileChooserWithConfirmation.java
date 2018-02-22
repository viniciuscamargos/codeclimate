package br.ufu.facom.osrat.component;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileChooserWithConfirmation extends JFileChooser {

	public void approveSelection() {
		File f = getSelectedFile();
		if (f.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(this,
					"The file exists, overwrite?", "Existing file",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				super.approveSelection();
				return;
			case JOptionPane.CANCEL_OPTION:
				cancelSelection();
				return;
			default:
				return;
			}
		}
		super.approveSelection();
	}
}
