package ui;

import javax.swing.JTextField;

public class TabbedTextField extends JTextField {

	/*
	 * Attributes
	 */
	private static final long serialVersionUID = -3386719136009685386L;
	private String code;

	public TabbedTextField() {};

	/**
	 * TabbedTextField Constructor
	 * @param name Name of the component
	 * @param code Code of the component (must match columns names)
	 */
	public TabbedTextField(String name, String code, int columns) {

		super(name, columns);
		this.code = code;

	}

	/*
	 * Accessors
	 */
	public String getCode() {
		return this.code;
	}

}
