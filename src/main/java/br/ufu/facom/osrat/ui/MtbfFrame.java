package br.ufu.facom.osrat.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MtbfFrame extends JPanel {
	private JTextField txtResults;

	public MtbfFrame() {

		GridBagLayout gblPanel = new GridBagLayout();
		gblPanel.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gblPanel.columnWeights = new double[] {0.0, 0.0};
		this.setLayout(gblPanel);

		JButton btnService = new JButton("Service");
		GridBagConstraints gbcService = new GridBagConstraints();
		gbcService.insets = new Insets(2, 2, 2, 2);
		gbcService.anchor = GridBagConstraints.WEST;
		gbcService.gridx = 0;
		gbcService.gridy = 0;
		this.add(btnService, gbcService);

		JButton btnKernel = new JButton("Kernel");
		GridBagConstraints gbcKernel = new GridBagConstraints();
		gbcKernel.insets = new Insets(2, 2, 2, 2);
		gbcKernel.anchor = GridBagConstraints.WEST;
		gbcKernel.gridx = 1;
		gbcKernel.gridy = 0;
		this.add(btnKernel, gbcKernel);

		JButton btnApplication = new JButton("Application");
		GridBagConstraints gbcApp = new GridBagConstraints();
		gbcApp.insets = new Insets(2, 2, 2, 2);
		gbcApp.anchor = GridBagConstraints.WEST;
		gbcApp.gridx = 2;
		gbcApp.gridy = 0;
		this.add(btnApplication, gbcApp);

		JButton btnAllErrors = new JButton("All Errors");
		GridBagConstraints gbcAll = new GridBagConstraints();
		gbcAll.insets = new Insets(2, 2, 2, 2);
		gbcAll.anchor = GridBagConstraints.WEST;
		gbcAll.gridx = 3;
		gbcAll.gridy = 0;
		this.add(btnAllErrors, gbcAll);

		JLabel lblResults = new JLabel("Results");
		GridBagConstraints gbcResults = new GridBagConstraints();
		gbcResults.insets = new Insets(2, 2, 2, 2);
		gbcResults.anchor = GridBagConstraints.EAST;
		gbcResults.gridx = 0;
		gbcResults.gridy = 1;
		this.add(lblResults, gbcResults);

		txtResults = new JTextField();
		GridBagConstraints gbcTxtResults = new GridBagConstraints();
		gbcTxtResults.insets = new Insets(2, 2, 2, 2);
		gbcTxtResults.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtResults.anchor = GridBagConstraints.WEST;
		gbcTxtResults.gridwidth = 2;
		// gbcTxtResults.weighty = 1.0;
		gbcTxtResults.gridx = 1;
		gbcTxtResults.gridy = 1;
		this.add(txtResults, gbcTxtResults);

	}

}
