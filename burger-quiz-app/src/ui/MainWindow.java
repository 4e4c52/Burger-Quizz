package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

public class MainWindow extends JFrame {

	/*
	 * Attributes
	 */
	private static final long serialVersionUID = -4722878451371801182L;

	private JTabbedPane panel;
	private JMenuBar menu = new JMenuBar();
	private JMenu file = null;
	private JMenuItem quit = null;

	/**
	 * MainWindow Constructor
	 */
	public MainWindow() {

		this.setTitle("Burger Quiz Admin");
		this.setSize(900, 710);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Render the menu bar at the top
		this.setupMenu();
		// Render the tabs
		this.setupTabs();

		this.validate();		
		this.setVisible(true);

	}

	/**
	 * Render the menu in the top of the window
	 */
	private void setupMenu() {

		this.file = new JMenu("Fichier");
		this.quit = new JMenuItem("Quitter");

		// Just a listener to quit the app when we click the quit menu item
		this.quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}				
		});

		this.file.add(this.quit);

		this.menu.add(this.file);

		this.setJMenuBar(this.menu);

	}

	/**
	 * Render the tabs
	 * That's where the fun part starts
	 */
	private void setupTabs() {

		TabbedSection[] tabs = {
				new TabbedSection(new GamesTab("Jeux", "Ajouter un nouveau jeu")),
				new TabbedSection(new CategoriesTab("Catégories", "Ajouter une nouvelle catégorie")),
				new TabbedSection(new QuestionsTab("Questions", "Ajouter une nouvelle question")),
				new TabbedSection(new AnswersTab("Réponses", "Ajouter une nouvelle réponse"))
		};

		this.panel = new JTabbedPane();

		for (TabbedSection tab : tabs) {
			this.panel.add(tab.getObject().getName(), tab);
		}

		this.add(this.panel);

	}

}
