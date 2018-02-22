package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import br.ufu.facom.osrat.model.Node;

public class MyRenderer extends DefaultTreeCellRenderer {

	public final Component getTreeCellRendererComponent(final JTree tree,
			final Object value, final boolean sel, final boolean expanded,
			final boolean leaf, final int row, final boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		DefaultMutableTreeNode df = (DefaultMutableTreeNode) value;
		Node node = (Node) df.getUserObject();
		if (node != null) {
			Icon icon = createImageIcon(node.getTypeNode().getPath());
			setIcon(icon);
			setToolTipText(node.getTypeNode().getToolTip());
			setText(node.getName());
		}
		return this;
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * @param path
	 * @return ImageIcon
	 */
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