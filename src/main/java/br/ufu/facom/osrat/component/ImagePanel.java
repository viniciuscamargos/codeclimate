package br.ufu.facom.osrat.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(final String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(final Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public final void paintComponent(final Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
