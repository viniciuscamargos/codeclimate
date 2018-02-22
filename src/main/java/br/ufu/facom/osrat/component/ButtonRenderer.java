package br.ufu.facom.osrat.component;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {

	  public ButtonRenderer() {
	    setOpaque(true);
	  }

	  public final Component getTableCellRendererComponent(final JTable table, final Object value,
	      final boolean isSelected, final boolean hasFocus, final int row, final int column) {
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }

	    Object vlr = value;
		if (vlr == null) {
	    	vlr = "";
	    }
	    setText(vlr.toString());
	    return this;
	  }
	}
