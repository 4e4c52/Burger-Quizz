package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AddWindow extends JFrame implements ActionListener {

	/*
	 * Attributes 
	 */
	private static final long serialVersionUID = -3689973553359903246L;
	private TabbedSection section;
	private Tab<?> object;

	/**
	 * AddWindow Constructor
	 */
	public AddWindow(String title, TabbedSection section, Tab<?> object) {

		this.section = section;
		this.object = object;

		this.setTitle(title);
		this.setSize(this.object.getAddWindowWidth(), this.object.getAddWindowHeigh());
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.setContentPane(this.setupPanel());

		this.validate();		
		this.setVisible(true);

	}

	/**
	 * Return the section this window belongs to
	 * @return section
	 */
	public TabbedSection getSection() {
		return this.section;
	}

	/**
	 * Setup the panel of the window
	 * @return panel The panel of the window
	 */
	private JPanel setupPanel() {		
		return this.object.renderForm(this);		
	}

	/**
	 * Handle the clicks on the two buttons
	 * Save or Cancel
	 */
	@Override
	public void actionPerformed(ActionEvent event) {

		TabbedButton button = (TabbedButton) event.getSource();

		if (button.getCode() == "cancel") this.dispose();
		else {
			this.object.addRecord(this);
			this.dispose();
		}

	}

}
