package br.ufu.facom.osrat.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import br.ufu.facom.osrat.model.Node;

public class PanelTBF extends JPanel {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea txtResult;

	public PanelTBF(final Node node) {
		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());

		JButton btnKernel = new JButton("Kernel");
		btnKernel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				//calc(node.getTimesKernel());
			}
		});
		GridBagConstraints gbcBtnKernel = new GridBagConstraints();
		gbcBtnKernel.insets = new Insets(2, 2, 2, 2);
		gbcBtnKernel.anchor = GridBagConstraints.WEST;
		gbcBtnKernel.gridx = 0;
		gbcBtnKernel.gridy = 0;
		this.add(btnKernel, gbcBtnKernel);

		JButton btnService = new JButton("Service");
		btnService.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				//calc(node.getTimesService());

			}
		});
		GridBagConstraints gbcService = new GridBagConstraints();
		gbcService.insets = new Insets(2, 2, 2, 2);
		gbcService.anchor = GridBagConstraints.WEST;
		gbcService.gridx = 1;
		gbcService.gridy = 0;
		this.add(btnService, gbcService);

		JButton btnApp = new JButton("Application");
		btnApp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				//calc(node.getTimesApp());

			}
		});
		GridBagConstraints gbcApp = new GridBagConstraints();
		gbcApp.insets = new Insets(2, 2, 2, 2);
		gbcApp.anchor = GridBagConstraints.WEST;
		gbcApp.gridx = 2;
		gbcApp.gridy = 0;
		this.add(btnApp, gbcApp);

		JButton btnTotal = new JButton("Total");
		btnTotal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				List<Double> all = new ArrayList<Double>();
				all.addAll(node.getTimesKernel());
				all.addAll(node.getTimesApp());
				all.addAll(node.getTimesService());

				//calc(all);
			}
		});
		GridBagConstraints gbcTotal = new GridBagConstraints();
		gbcTotal.insets = new Insets(2, 2, 2, 2);
		gbcTotal.anchor = GridBagConstraints.WEST;
		gbcTotal.gridx = 3;
		gbcTotal.gridy = 0;
		this.add(btnTotal, gbcTotal);

		txtResult = new JTextArea();
		GridBagConstraints gbcResult = new GridBagConstraints();
		gbcResult.fill = GridBagConstraints.HORIZONTAL;
		gbcResult.anchor = GridBagConstraints.WEST;
		gbcResult.gridwidth = 2;
		gbcResult.insets = new Insets(2, 2, 2, 2);
		gbcResult.gridx = 0;
		gbcResult.gridy = 1;
		gbcResult.gridwidth = 4;
		this.add(txtResult, gbcResult);
	}

/*	private void calc(final List<Double> array) {
		try {
			TBF tbf = new TBF(array);

			StringBuilder builder = new StringBuilder();
			builder.append(tbf.getAverage().toString() + "\n\n");
			builder.append(tbf.getLognormal().toString() + "\n\n");
			builder.append(tbf.getMleGamma().toString());

			txtResult.setText(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
