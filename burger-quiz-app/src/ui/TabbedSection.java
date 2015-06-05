package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TabbedSection extends JPanel implements ActionListener, MouseListener {

	/*
	 * Attributes
	 */
	private static final long serialVersionUID = 6730811090139910426L;
	private Tab<?> object;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	private JPanel header;
	private JPanel footer;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane tableScroll; 
	private TabbedButton addButton;
	private TabbedButton destroyButton;
	private TabbedButton nextButton;
	private TabbedButton prevButton;
	private JLabel pagination;
	private int limit;
	private int currentOffset;
	private int recordsNumber;
	private int currentPage;
	private int maxPages;
	private int currentSelectedRow;

	/**
	 * TabbedSection Constructor
	 * @param object Tab object
	 */	
	public TabbedSection(Tab<?> object) {
		// The object this tab is all about (e.g Game or Question)
		this.object = object;

		// How many records per page
		this.limit = 20;

		// Total of records in the database for this object
		this.recordsNumber = this.object.fetchRecordsNumber();
		// A bit of Math, guess what, guess yourself
		this.maxPages = (int) Math.ceil(Math.ceil((double) this.recordsNumber / (double) this.limit));

		// The current offset in the database
		this.currentOffset = 0;
		// The current page
		this.currentPage = 1;
		
		// Create the view containing the table
		this.tableScroll = new JScrollPane();

		// I don't give a shit of this
		this.layout = new GridBagLayout();
		this.constraints = new GridBagConstraints();
		this.setLayout(this.layout);

		// Render the UI above the table
		this.setupHeader();
		// Render the... table
		this.setupTable();
		// Render the UI below the table
		this.setupFooter();

		// Activate or deactivate the Next and Prev buttons
		// in function of the current page
		this.handleButtonsActivation();

		// Dafuq?
		this.validate();
	}

	/**
	 * Return the tab object
	 * @return Tab subclass
	 */
	public Tab<?> getObject() {
		return this.object;
	}

	/**
	 * Set the tab object
	 * @param object Tab subclass
	 */
	public void setObject(Tab<?> object) {
		this.object = object;
	}

	/**
	 * Setup the header panel
	 */
	private void setupHeader() {	

		this.header = new JPanel(new FlowLayout(FlowLayout.LEADING));

		// The add new record button
		this.addButton = new TabbedButton(this.object.getAddNewButtonText(), "add");
		this.addButton.addActionListener(this);
		this.header.add(this.addButton);

		// The delete selection button
		this.destroyButton = new TabbedButton("Supprimer l'enregistrement", "destroy");
		this.destroyButton.addActionListener(this);
		this.header.add(this.destroyButton);

		// Dafuq?
		this.header.validate();

		// Set the constraints of the layout
		// Seriously, don't mess with this.
		this.setupConstraints(0, 0, 1000,  0);
		this.add(this.header, this.constraints);

	}

	/**
	 * Setup the table containing the data
	 */
	public void setupTable() {

		// Fetch the columns names for the object
		Object[] columns = this.object.getColumnNames();
		// Fetch a range of records
		Object[][] data = this.object.fetchAll(this.limit, this.currentOffset);

		// Set the data model which is a kind of sorcery that I don't really understand
		// but you should not underestimate this thing
		this.model = new DefaultTableModel(data, columns);

		// This trick is for disabling the editing capabilities of the first
		// and last two columns
		this.table = new JTable(this.model) {
			private static final long serialVersionUID = 1799543663856809002L;
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column >= (this.getColumnCount() - 2)) return false;
				return true;
			}
		};

		// We set the listener responsible for firing an event when a cell
		// is updated in our table
		this.table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) { updateRow(); }      
		});

		// Special home made renderer to alternate the background of the rows
		this.table.setDefaultRenderer(Object.class, new CellRenderer());
		// Useless
		this.table.setPreferredScrollableViewportSize(new Dimension(1000, 800));
		// Don't care
		this.table.setFillsViewportHeight(true);

		// UI related stuff, Google is your friend
		this.table.setRowHeight(26);
		this.table.setIntercellSpacing(new Dimension(3, 0));
		this.table.setShowHorizontalLines(false);
		this.table.setShowVerticalLines(true);
		this.table.setGridColor(Color.lightGray);
		this.table.addMouseListener(this);

		// Dafuq?
		this.table.validate();

		// Create the scroll pane and add the table to it
		this.tableScroll = new JScrollPane(this.table);

		// Dafuq?
		this.tableScroll.validate();

		this.setupConstraints(0, 1, 1000, 800);
		this.add(this.tableScroll, this.constraints);

	}

	/**
	 * Setup the footer panel
	 */
	private void setupFooter() {

		this.footer = new JPanel();

		// Pagination buttons
		this.nextButton = new TabbedButton("Suivant", "next");	
		this.nextButton.addActionListener(this);
		this.prevButton = new TabbedButton("Précédent", "prev");
		this.prevButton.addActionListener(this);

		// Label showing where we are
		this.pagination = new JLabel("Affichage de la page " + this.currentPage + " sur un total de " + this.maxPages);

		this.footer.add(this.prevButton);
		this.footer.add(this.pagination);
		this.footer.add(this.nextButton);

		// Dafuq?
		this.footer.validate();

		this.setupConstraints(0, 2, 1000, 0);		
		this.add(this.footer, this.constraints);

	}

	/**
	 * Set up the constraints for the Grid(Douche)BagLayout
	 * @param gridx
	 * @param gridy
	 * @param weightx
	 * @param weighty
	 */
	private void setupConstraints(int gridx, int gridy, int weightx, int weighty) {
		this.constraints.gridx = gridx;
		this.constraints.gridy = gridy;
		this.constraints.gridwidth = 1;
		this.constraints.gridheight = 1;
		this.constraints.weightx = weightx;
		this.constraints.weighty = weighty;
		this.constraints.fill = GridBagConstraints.BOTH;		
	}

	/**
	 * Handle the next page logic
	 */
	private void nextPage() {
		this.currentOffset += this.limit;
		this.currentPage += 1;
		this.handleButtonsActivation();
		this.refreshTab();		
	}

	/**
	 * Handle the previous page logic
	 */
	private void previousPage() {
		this.currentOffset -= this.limit;
		this.currentPage -= 1;
		this.handleButtonsActivation();
		this.refreshTab();
	}

	/**
	 * Show the window for adding a new record
	 */
	private void addWindow() {		
		new AddWindow("Ajouter un objet", this, this.object);
	}

	/**
	 * Update the last edited row in the database
	 * A-U-T-O-M-A-T-I-C-A-L-L-Y
	 */
	private void updateRow() {		
		// Removing the date columns, fuck this shit
		int size = (this.object.getColumnNames().length) - 2;
		String[] data = new String[size];

		// We store all cells value in an array
		for (int i = 0; i < size; i++) {
			data[i] = this.table.getValueAt(this.currentSelectedRow, i).toString();
		}

		// We call the method on the object
		// which takes care of the details
		this.object.updateRecord(data);	
	}

	/**
	 * Destroy a record
	 */
	private void destroyRow() {	
		// We fetch the id we want to delete
		int id = (Integer) this.table.getValueAt(this.currentSelectedRow, 0);
		// The object takes care of really deleting it
		this.object.destroyRecord(id);
		this.refreshTab();
	}

	/**
	 * Refresh the current tab when browsing records
	 */
	public void refreshTab() {
		this.remove(this.tableScroll);
		this.setupTable();
		
		this.handleButtonsActivation();
		this.pagination.setText("Affichage de la page " + this.currentPage + " sur un total de " + this.maxPages);
		
		// Dafuq?
		this.validate();	
	}

	/**
	 * Activate or deactivate pagination buttons
	 */
	private void handleButtonsActivation() {

		// Should we activate the next button?
		if (this.currentPage == this.maxPages) {
			this.nextButton.setEnabled(false);
		}
		else {
			this.nextButton.setEnabled(true);
		}

		// Should we activate the previous button?
		if (this.currentPage == 1) {
			this.prevButton.setEnabled(false);
		}
		else {
			this.prevButton.setEnabled(true);
		}

	}	

	/**
	 * Handle the clicks on the buttons
	 * @param event Some kind of sorcery
	 */
	@Override
	public void actionPerformed(ActionEvent event) {			
		TabbedButton button = (TabbedButton) event.getSource();

		if (button.getCode() == "next") this.nextPage();
		else if (button.getCode() == "prev") this.previousPage();
		else if (button.getCode() == "destroy") this.destroyRow();
		else this.addWindow();		
	}

	/**
	 * Handle the mouse click
	 * Useful to fetch the current selected row
	 * @param event Some kind of sorcery
	 */
	@Override
	public void mouseClicked(MouseEvent event) {
		this.currentSelectedRow = this.table.getSelectedRow();
	}

	// Bah! I hate you niggas.
	@Override
	public void mouseEntered(MouseEvent event) {}
	@Override
	public void mouseExited(MouseEvent event) {}
	@Override
	public void mousePressed(MouseEvent event) {}
	@Override
	public void mouseReleased(MouseEvent event) {}

}
