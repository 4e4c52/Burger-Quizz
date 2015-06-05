package ui;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import business.models.Model;

/**
 * This abstract class define all the method our Tabs objects
 * should define
 * 
 * @author Nathan Le Ray
 *
 * @param <T>
 */
public abstract class Tab<T> {

	protected String name;
	protected String addNewButtonText;

	protected abstract void setName(String name);
	protected abstract String getName();

	protected abstract void setAddNewButtonText(String addNewButtonText);
	protected abstract String getAddNewButtonText();

	protected abstract String[] getColumnNames();
	protected abstract ArrayList<JComponent> getComponents();

	protected abstract Object[][] fetchAll(int limit, int offset);
	protected abstract Model fetchOne(int id);

	protected abstract int fetchRecordsNumber();

	protected abstract void addRecord(AddWindow window);
	protected abstract void updateRecord(String[] data);
	protected abstract void destroyRecord(int id);

	protected abstract int getAddWindowHeigh();
	protected abstract int getAddWindowWidth();

	protected abstract JPanel renderForm(AddWindow window);

}
