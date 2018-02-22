package br.ufu.facom.osrat.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import br.ufu.facom.osrat.util.OsratUtil;

public class ImportFrameSdf extends JDialog {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;
	private JDialog frame;
	private JTextField txtOrigin;
	private JTextField txtDest;
	private JButton btnGenerateFiles;
	private JFileChooser chooserOrigin;
	private JFileChooser chooserDest;
	private File fOrg;
	private File fDest;

	private static final Logger LOG = Logger.getLogger(ImportFrameSdf.class);

	public ImportFrameSdf() {
		this.frame = this;
		this.setTitle("Import logs (" + OsratUtil.getTitleSystem() + ")");
		this.setSize(new Dimension(400, 200));
		setIconImage(OsratUtil.getMainIcon().getImage());
		getContentPane().setLayout(new GridBagLayout());

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(final java.awt.event.WindowEvent windowEvent) {
				setfOrg(null);
				setfDest(null);
			}
		});

		chooserOrigin = new JFileChooser();
		chooserDest = new JFileChooser();

		JLabel lblOrigem = new JLabel("Source Directory");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(lblOrigem, gbc);

		txtOrigin = new JTextField();
		txtOrigin.getCaret().setVisible(true);
		txtOrigin.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		getContentPane().add(txtOrigin, gbc);

		final JButton btnOrigem = new JButton();
		btnOrigem
				.setIcon(new ImageIcon(
						MainFrame.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		btnOrigem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				// chooserOrigin.setFileFilter(new
				// FileNameExtensionFilter("SQL Server Compact Database File",
				// "sdf"));
				chooserOrigin
						.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				// chooserOrigin.setMultiSelectionEnabled(true);
				if (chooserOrigin.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					fOrg = chooserOrigin.getSelectedFile();
					txtOrigin.setText(fOrg.getName());
				}
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(btnOrigem, gbc);

		JLabel lblDestino = new JLabel("Destination Directory");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.WEST;
		getContentPane().add(lblDestino, gbc);

		txtDest = new JTextField();
		txtDest.getCaret().setVisible(true);
		txtDest.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		getContentPane().add(txtDest, gbc);

		final JButton btnDestino = new JButton();
		btnDestino
				.setIcon(new ImageIcon(
						MainFrame.class
								.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		btnDestino.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				chooserDest.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooserDest.setFileFilter(new FileNameExtensionFilter(
						"Excel Workbook", "xls"));
				if (chooserDest.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					fDest = chooserDest.getSelectedFile();
					txtDest.setText(fDest.getName());
				}
			}
		});
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(btnDestino, gbc);

		btnGenerateFiles = new JButton("Generate files");
		btnGenerateFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					if (fOrg == null || fDest == null) {
						JOptionPane
								.showMessageDialog(frame,
										"Enter the directory 'Source' and 'Destination'.");
						return;
					}
					frame.dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
					LOG.error(e2);
				}
			}
		});

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(btnGenerateFiles, gbc);
	}

	public final File getfOrg() {
		return fOrg;
	}

	public final void setfOrg(final File fOrg) {
		this.fOrg = fOrg;
	}

	public final File getfDest() {
		return fDest;
	}

	public final void setfDest(final File fDest) {
		this.fDest = fDest;
	}

}
