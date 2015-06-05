package ui;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.dao.Answers;
import business.dao.Questions;
import business.models.Answer;
import business.models.Model;
import business.models.Question;

public class AnswersTab extends Tab<AnswersTab> {

	private final static String[] columnNames = { "#", "Question", "Texte", "Création", "Modification" };
	private ArrayList<JComponent> components;
	private JPanel panel;
	private JLabel textLabel;
	private TabbedTextField textInput;
	private JLabel truthLabel;
	private JCheckBox truthBox;
	private JLabel questionLabel;
	private TabbedComboBox questionsList;

	/*
	 * CategoryTab Constructor
	 * @param name Display name
	 */
	public AnswersTab(String name, String buttonText) {
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
		return AnswersTab.columnNames;
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
		ArrayList<Answer> items = new Answers().findAll(limit, offset);

		Iterator<Answer> it = items.iterator();
		Object[][] results = new Object[items.size()][];
		int i = 0;

		while (it.hasNext()) {
			Answer answer = it.next();
			results[i] = new Object[] { answer.getId(), answer.getQuestionId(), answer.getText(),answer.getCreatedAt(), answer.getUpdatedAt() };
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
		return new Answers().count();
	}

	/**
	 * Add a record in the database
	 * @param window The window containing the form
	 */
	@Override
	protected void addRecord(AddWindow window) {
		Answer answer = new Answer();
		answer.setText(this.textInput.getText());
		//answer.setGoodAnswer(this.truthBox.isSelected());

		String selectedGame = (String) this.questionsList.getSelectedItem();
		Pattern pattern = Pattern.compile("\\[(\\d+)\\] (.)+");
		Matcher matcher = pattern.matcher(selectedGame);
		matcher.find();
		int questionId = new Integer(matcher.group(1));

		answer.setQuestionId(questionId);

		Answer obj = new Answers().create(answer);
		
		if(this.truthBox.isSelected()){
			int id_question = obj.getQuestionId();
			int id_answer = obj.getId();
		
			Question q = new Questions().findOne(id_question);
			q.setGoodAnswer(id_answer);
			new Questions().update(q);
		}
		window.getSection().refreshTab();	
	}

	/**
	 * Update a record in the database
	 * @param data An array containing the raw values
	 */
	@Override
	protected void updateRecord(String[] data) {
		Answer answer = new Answer();
		answer.setId(new Integer(data[0]));
		answer.setQuestionId(new Integer(data[1]));
		answer.setText(data[2]);
	
		new Answers().update(answer);
	}

	/**
	 * Destroy a record in the database
	 * @param id The id of the record
	 */
	@Override
	protected void destroyRecord(int id) {
		Answer answer = new Answer();
		answer.setId(id);
		new Answers().destroy(answer);	
	}

	/**
	 * Render the form for adding a record
	 */
	@Override
	protected JPanel renderForm(AddWindow window) {

		this.panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		this.textLabel = new JLabel("Texte de la réponse");
		this.textInput = new TabbedTextField(null, "name", 16);
		this.components.add(this.textInput);

		this.truthLabel = new JLabel("Bonne réponse ?");
		this.truthBox = new JCheckBox();
		this.components.add(this.truthBox);

		JPanel checkboxPanel = new JPanel();
		checkboxPanel.add(this.truthLabel);
		checkboxPanel.add(this.truthBox);

		this.questionLabel = new JLabel("Jeu parent");
		this.questionsList = new TabbedComboBox("category", this.getQuestionsList());
		this.components.add(this.questionsList);

		TabbedButton cancel = new TabbedButton("Annuler", "cancel");
		cancel.addActionListener(window);

		TabbedButton submit = new TabbedButton("Enregistrer", "submit");
		submit.addActionListener(window);

		this.panel.add(this.textLabel);
		this.panel.add(this.textInput);
		this.panel.add(checkboxPanel);
		this.panel.add(this.questionLabel);
		this.panel.add(this.questionsList);
		this.panel.add(cancel);
		this.panel.add(submit);

		this.panel.validate();

		return panel;

	}

	/**
	 * Return the list of questions formatted
	 * for a ComboBox
	 */
	private String[] getQuestionsList() {

		Questions questions = new Questions();

		int maxRecords = questions.count();
		ArrayList<Question> records = questions.findAll(maxRecords, 0);
		Iterator<Question> it = records.iterator();

		String[] output = new String[maxRecords];
		int i = 0;

		while (it.hasNext()) {

			Question question = it.next();
			output[i] = "[" + question.getId() + "] " + question.getText();
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
		return 200;
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
