package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

import br.ufu.facom.osrat.component.BackgroundPanel;
import br.ufu.facom.osrat.util.OsratUtil;

public class InitDialog extends JDialog {

	private static final long serialVersionUID = -5672974574424703493L;

	public InitDialog(final JFrame frame, final ModalityType modalityType) {
		super(frame, modalityType);
		initialize();
	}

	public InitDialog(final JFrame frame, final boolean modal) {
		super(frame, modal);
		initialize();
	}

	public final void initialize() {
		this.setUndecorated(true);
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		this.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		BackgroundPanel bkPanel = new BackgroundPanel(OsratUtil.getLogo()
				.getImage());
		this.getContentPane().add(bkPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		this.getContentPane().add(progressBar, gbc);
	}

}
