package ui;

import javax.swing.JButton;

public class TabbedButton extends JButton {

	/**
	 * Attributes
	 */
	private static final long serialVersionUID = 8357633792577546485L;
	private String code;

	public TabbedButton(String name, String code) {
		super(name);
		this.code = code;
	}

	/**
	 * Accessors
	 */
	public String getCode() {
		return this.code;
	}

}
