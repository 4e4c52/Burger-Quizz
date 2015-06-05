package ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class CellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 6411430318655447487L;
	private Color whiteColor = new Color(254, 254, 254);
	private Color alternateColor = new Color(243, 246, 250);
	private Color selectedColor = new Color(56, 117, 215);

	@Override
	public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {

		super.getTableCellRendererComponent(table, value, selected, focused, row, column);

		// Set the background color
		Color background;

		if (!selected) background = (row % 2 == 0 ? alternateColor : whiteColor);
		else background = selectedColor;

		setBackground(background);

		// Set the foreground to white when selected
		Color foreground;

		if (selected) foreground = Color.white;
		else foreground = Color.black;

		setForeground(foreground);

		return this;

	}

}
