package ui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.dao.Categories;
import business.dao.Games;
import business.models.Category;
import business.models.Game;
import business.models.Model;

public class CategoriesTab extends Tab<CategoriesTab> {

	private final static String[] columnNames = { "#", "Name", "Jeu", "Création", "Modification" };
	private ArrayList<JComponent> components;
	private JPanel panel;
	private JLabel nameLabel;
	private TabbedTextField nameInput;
	private JLabel gameLabel;
	private TabbedComboBox gamesList;

	/*
	 * CategoryTab Constructor
	 * @param name Display name
	 */
	public CategoriesTab(String name, String buttonText) {
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
		return CategoriesTab.columnNames;
	}

	@Override
	protected ArrayList<JComponent> getComponents() {
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
		ArrayList<Category> items = new Categories().findAll(limit, offset);

		Iterator<Category> it = items.iterator();
		Object[][] results = new Object[items.size()][];
		int i = 0;

		while (it.hasNext()) {
			Category category = it.next();
			results[i] = new Object[] { category.getId(), category.getName(), category.getGameId(), category.getCreatedAt(), category.getUpdatedAt() };
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
		return new Categories().count();
	}

	/**
	 * Add a record in the database
	 * @param window The window containing the form
	 */
	@Override
	protected void addRecord(AddWindow window) {
		Category category = new Category();
		category.setName(this.nameInput.getText());

		String selectedGame = (String) this.gamesList.getSelectedItem();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\] (.)+");
		Matcher matcher = pattern.matcher(selectedGame);
		matcher.find();
		int gameId = new Integer(matcher.group(1));

		category.setGameId(gameId);

		new Categories().create(category);
		window.getSection().refreshTab();

	}

	/**
	 * Update a record in the database
	 * @param data An array containing the raw values
	 */
	@Override
	protected void updateRecord(String[] data) {
		Category category = new Category();
		category.setId(new Integer(data[0]));
		category.setName(data[1]);
		category.setGameId(new Integer(data[2]));
		new Categories().update(category);
	}

	/**
	 * Destroy a record in the database
	 * @param id The id of the record
	 */
	@Override
	protected void destroyRecord(int id) {
		Category category = new Category();
		category.setId(id);
		new Categories().destroy(category);	
	}

	/**
	 * Render the form for adding a record
	 */
	@Override
	protected JPanel renderForm(AddWindow window) {

		this.panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		this.nameLabel = new JLabel("Nom de la catégorie");
		this.nameInput = new TabbedTextField(null, "name", 19);
		this.components.add(this.nameInput);

		this.gameLabel = new JLabel("Jeu parent");
		this.gamesList = new TabbedComboBox("game", this.getGamesList());
		this.components.add(this.gamesList);

		TabbedButton cancel = new TabbedButton("Annuler", "cancel");
		cancel.addActionListener(window);

		TabbedButton submit = new TabbedButton("Enregistrer", "submit");
		submit.addActionListener(window);

		this.panel.add(this.nameLabel);
		this.panel.add(this.nameInput);
		this.panel.add(this.gameLabel);
		this.panel.add(this.gamesList);
		this.panel.add(cancel);
		this.panel.add(submit);

		this.panel.validate();

		return panel;

	}

	/**
	 * Return the list of games formatted
	 * for a ComboBox
	 */
	private String[] getGamesList() {

		Games games = new Games();

		int maxRecords = games.count();
		ArrayList<Game> records = games.findAll(maxRecords, 0);
		Iterator<Game> it = records.iterator();

		String[] output = new String[maxRecords];
		int i = 0;

		while (it.hasNext()) {

			Game game = it.next();
			output[i] = "[" + game.getId() + "] " + game.getName();
			i++;

		}

		return output;

	}

	/**
	 * Return the height the window containing
	 * the form to add a record should have
	 */
	@Override
	protected int getAddWindowHeigh() {
		return 170;
	}

	/**
	 * Return the width the window containing
	 * the form to add a record should have
	 */
	@Override
	protected int getAddWindowWidth() {
		return 230;
	}

}
