package ui;

import javax.swing.JComboBox;

@SuppressWarnings("rawtypes")
public class TabbedComboBox extends JComboBox {

	private static final long serialVersionUID = -4308679376448914209L;
	private String code;

	@SuppressWarnings("unchecked")
	public TabbedComboBox(String code, String[] data) {
		super(data);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
