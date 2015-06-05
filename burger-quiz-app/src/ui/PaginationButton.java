package ui;

import javax.swing.JButton;

public class PaginationButton extends JButton {

	/*
	 * Attributes
	 */
	private static final long serialVersionUID = 2968817413511611449L;
	private String code;

	public PaginationButton(String name, String code) {
		super(name);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
