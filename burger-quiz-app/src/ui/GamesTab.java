package ui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.dao.Games;
import business.models.Game;
import business.models.Model;

public class GamesTab extends Tab<GamesTab> {

	private final static String[] columnNames = { "#", "Nom", "Creation", "Modification" };
	private ArrayList<JComponent> components;
	private JPanel panel;
	private JLabel nameLabel;
	private TabbedTextField nameInput;

	/*
	 * GamesTab Constructor
	 * @param name Display name
	 */
	public GamesTab(String name, String buttonText) {
		this.name = name;
		this.addNewButtonText = buttonText;
		this.components = new ArrayList<JComponent>();
	}

	/*
	 * Accessors
	 */
	@Override
	public void setName(String name) {
		this.name = name;	
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setAddNewButtonText(String addNewButtonText) {
		this.addNewButtonText = addNewButtonText;
	}

	@Override
	public String getAddNewButtonText() {
		return this.addNewButtonText;
	}

	@Override
	public String[] getColumnNames() {
		return GamesTab.columnNames;
	}

	@Override
	public ArrayList<JComponent> getComponents() {
		if (this.components.size() > 0) return this.components;
		else return null;
	}

	/**
	 * Fetch a range of records in the database
	 * @param limit The number of records to fetch
	 * @param offset The offset when the range starts
	 * @result An array container the object as an array
	 */
	@Override
	protected Object[][] fetchAll(int limit, int offset) {
		ArrayList<Game> items = new Games().findAll(limit, offset);

		Iterator<Game> it = items.iterator();
		Object[][] results = new Object[items.size()][];
		int i = 0;

		while (it.hasNext()) {
			Game game = it.next();
			results[i] = new Object[] { game.getId(), game.getName(), game.getCreatedAt(), game.getUpdatedAt() };
			i++;
		}

		return results;
	}

	/**
	 * Fetch one record from the database
	 * @param id The id of the record
	 * @return The record object
	 */
	@Override
	protected Model fetchOne(int id) {
		return null;
	}

	/**
	 * Fetch the number of records in the database
	 */
	@Override
	protected int fetchRecordsNumber() {
		return new Games().count();
	}

	/**
	 * Add a record in the database
	 * @param window The window containing the form
	 */
	@Override
	protected void addRecord(AddWindow window) {
		Game game = new Game();
		game.setName(this.nameInput.getText());
		new Games().create(game);
		window.getSection().refreshTab();	
	}

	/**
	 * Update a record in the database
	 * @param data An array containing the raw values
	 */
	@Override
	protected void updateRecord(String[] data) {
		Game game = new Game();
		game.setId(new Integer(data[0]));
		game.setName(data[1]);
		new Games().update(game);
	}

	/**
	 * Destroy a record in the database
	 * @param id The id of the record
	 */
	@Override
	protected void destroyRecord(int id) {
		Game game = new Game();
		game.setId(id);
		new Games().destroy(game);	
	}

	/**
	 * Render the form for adding a record
	 */
	@Override
	protected JPanel renderForm(AddWindow window) {

		this.panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		this.nameLabel = new JLabel("Nom du jeu");
		this.nameInput = new TabbedTextField(null, "name", 16);
		this.components.add(this.nameInput);

		TabbedButton cancel = new TabbedButton("Annuler", "cancel");
		cancel.addActionListener(window);

		TabbedButton submit = new TabbedButton("Enregistrer", "submit");
		submit.addActionListener(window);

		this.panel.add(this.nameLabel);
		this.panel.add(this.nameInput);
		this.panel.add(cancel);
		this.panel.add(submit);

		this.panel.validate();

		return panel;

	}

	/**
	 * Return the height the window containing
	 * the form to add a record should have
	 */
	@Override
	protected int getAddWindowHeigh() {
		return 120;
	}

	/**
	 * Return the width the window containing
	 * the form to add a record should have
	 */
	@Override
	protected int getAddWindowWidth() {
		return 250;
	}

}
