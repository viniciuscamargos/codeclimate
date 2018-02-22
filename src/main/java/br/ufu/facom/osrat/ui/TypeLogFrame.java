package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import br.ufu.facom.osrat.xls.XLSWriter;

public class TypeLogFrame extends JDialog {

	private JDialog dialog;
	private JComboBox<String> cmb;

	public TypeLogFrame() {
		this.dialog = this;

		getContentPane().setLayout(new GridBagLayout());

		JLabel label = new JLabel("Select type log:");
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(label, gbc2);

		String[] vals = {"Kernel", "App", "Service", "User"};
		cmb = new JComboBox<String>(vals);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(cmb, gbc);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					String vlr = (String) cmb.getSelectedItem();

					File inputWorkbook = new File(XLSWriter.class
							.getClassLoader()
							.getResource(
									"br/ufu/osrat/resource/Failures.xls")
							.toURI());
					Workbook w = Workbook.getWorkbook(inputWorkbook);
					WritableWorkbook workbook = Workbook.createWorkbook(
							new File("temp.xls"), w);

					WritableSheet sheet = workbook.getSheet(vlr);
					int r = sheet.getRows();
					// sheet.addCell(createLabelCell(sheet.getName(), 0, r));

				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane
							.showMessageDialog(null,
									"Erro saving the register. Contact the system admin.");
					dialog.dispose();
				}

			}
		});
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 2;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(btnSave, gbc1);
	}
}
