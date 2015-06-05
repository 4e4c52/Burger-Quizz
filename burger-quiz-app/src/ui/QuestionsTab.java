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
import business.dao.Questions;
import business.models.Category;
import business.models.Model;
import business.models.Question;

public class QuestionsTab extends Tab<QuestionsTab> {

	private final static String[] columnNames = { "#", "Text", "Categorie", "Temps","Good Answer", "Création", "Modification" };
	private ArrayList<JComponent> components;
	private JPanel panel;
	private JLabel nameLabel;
	private TabbedTextField nameInput;
	private JLabel timerLabel;
	private TabbedTextField timerInput;
	private JLabel categoryLabel;
	private TabbedComboBox categoriesList;

	/**
	 * CategoryTab Constructor
	 * @param name Display name
	 */
	public QuestionsTab(String name, String buttonText) {
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
		return QuestionsTab.columnNames;
	}

	@Override
	protected ArrayList<JComponent> getComponents() {
		return this.components;
	}

	/**
	 * Fetch a range of records in the database
	 * @param limit The number of records to fetch
	 * @param offset The offset when the range starts
	 * @result An array container the object as an array
	 */
	@Override
	protected Object[][] fetchAll(int limit, int offset) {
		ArrayList<Question> items = new Questions().findAll(limit, offset);

		Iterator<Question> it = items.iterator();
		Object[][] results = new Object[items.size()][];
		int i = 0;

		while (it.hasNext()) {
			Question question = it.next();
			results[i] = new Object[] { question.getId(), question.getText(), question.getCategoryId(), question.getTimer(),question.getGoodAnswer(), question.getUpdatedAt(), question.getCreatedAt() };
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
		return new Questions().count();
	}

	/**
	 * Add a record in the database
	 * @param window The window containing the form
	 */
	@Override
	protected void addRecord(AddWindow window) {
		Question question = new Question();
		question.setText(this.nameInput.getText());
		question.setTimer(new Integer(this.timerInput.getText()));

		String selectedGame = (String) this.categoriesList.getSelectedItem();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\] (.)+");
		Matcher matcher = pattern.matcher(selectedGame);
		matcher.find();
		int categoryId = new Integer(matcher.group(1));

		question.setCategoryId(categoryId);

		new Questions().create(question);
		window.getSection().refreshTab();	
	}

	/**
	 * Update a record in the database
	 * @param data An array containing the raw values
	 */
	@Override
	protected void updateRecord(String[] data) {
		Question question = new Question();
		question.setId(new Integer(data[0]));
		question.setText(data[1]); 
		question.setCategoryId(new Integer(data[2]));
		question.setTimer(new Integer(data[3]));
		question.setGoodAnswer(new Integer(data[4]));
		new Questions().update(question);
	}

	/**
	 * Destroy a record in the database
	 * @param id The id of the record
	 */
	@Override
	protected void destroyRecord(int id) {
		Question question = new Question();
		question.setId(id);
		new Questions().destroy(question);	
	}

	/**
	 * Render the form for adding a record
	 */
	@Override
	protected JPanel renderForm(AddWindow window) {

		this.panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

		this.nameLabel = new JLabel("Texte de la question");
		this.nameInput = new TabbedTextField(null, "name", 16);
		this.components.add(this.nameInput);

		this.timerLabel = new JLabel("Temps pour répondre (secondes)");
		this.timerInput = new TabbedTextField(null, "name", 16);
		this.components.add(this.timerInput);

		this.categoryLabel = new JLabel("Jeu parent");
		this.categoriesList = new TabbedComboBox("category", this.getCategoriesList());
		this.components.add(this.categoriesList);

		TabbedButton cancel = new TabbedButton("Annuler", "cancel");
		cancel.addActionListener(window);

		TabbedButton submit = new TabbedButton("Enregistrer", "submit");
		submit.addActionListener(window);

		this.panel.add(this.nameLabel);
		this.panel.add(this.nameInput);
		this.panel.add(this.timerLabel);
		this.panel.add(this.timerInput);
		this.panel.add(this.categoryLabel);
		this.panel.add(this.categoriesList);
		this.panel.add(cancel);
		this.panel.add(submit);

		this.panel.validate();

		return panel;

	}

	/**
	 * Return the list of categories formatted
	 * for a ComboBox
	 */
	private String[] getCategoriesList() {

		Categories categories = new Categories();

		int maxRecords = categories.count();
		ArrayList<Category> records = categories.findAll(maxRecords, 0);
		Iterator<Category> it = records.iterator();

		String[] output = new String[maxRecords];
		int i = 0;

		while (it.hasNext()) {

			Category category = it.next();
			output[i] = "[" + category.getId() + "] " + category.getName();
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
		return 210;
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