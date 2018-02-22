package br.ufu.facom.osrat.component;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class LoadFrame extends JInternalFrame {

	public LoadFrame(final Dimension dm) {
		JFrame frame = new JFrame("Test");

		ImageIcon loading = createImageIcon("br/ufu/osrat/imagens/ajax-loader.gif");
		frame.add(new JLabel("loading... ", loading, JLabel.CENTER));

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(dm);
		frame.setVisible(true);
	}

	private static ImageIcon createImageIcon(final String path) {
		java.net.URL imgURL = MyRenderer.class.getClassLoader().getResource(
				path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
