package br.ufu.facom.osrat.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Classe OsratUtil.
 *
 */
public final class OsratUtil {

	/**
	 * Título da aplicação.
	 */
	private static final String TITLE_SYSTEM = "OSRat";

	/**
	 * Construtor privado.
	 */
	private OsratUtil() { }

	/**
	 * Retorna o ícone da aplicação.
	 * @return ImageIcon
	 */
	public static ImageIcon getMainIcon() {
		ImageIcon icon = createImageIcon("favicon.png");
		return icon;
	}

	/**
	 * Retorna o logo da aplicação com opacidade alterada.
	 * @return ImageIcon
	 */
	public static ImageIcon getLogoOpaque() {
		ImageIcon icon = createImageIcon("logo.gif");
		return icon;
	}

	/**
	 * Retorna o logo da aplicação.
	 * @return ImageIcon
	 */
	public static ImageIcon getLogo() {
		ImageIcon icon = createImageIcon("OSRAT6.png");
		return icon;
	}

	/**
	 * Retorna o título da aplicação.
	 * @return String
	 */
	public static String getTitleSystem() {
		return TITLE_SYSTEM;
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * @param path
	 * @return ImageIcon
	 */
	public static ImageIcon createImageIcon(final String path) {
		java.net.URL imgURL = OsratUtil.class.getClassLoader().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Seta a posição da janela.
	 * @param window
	 */
	public static void setCenterLocation(final JFrame window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		window.setLocation(x, y);
	}

	/**
	 * Seta a posição da janela.
	 * @param window
	 */
	public static void setCenterLocation(final JDialog window) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = window.getSize().width;
		int h = window.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		// Move the window
		window.setLocation(x, y);
	}
}
