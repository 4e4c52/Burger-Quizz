package business.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import business.models.Category;


public class Categories extends DAO<Category> {

	@Override
	public Category create(Category category) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"INSERT INTO categories (game_id, name, created_at, updated_at) VALUES (?, ?, ?,?)",
							Statement.RETURN_GENERATED_KEYS
							);

			/* Binding variables */
			statement.setInt(1, category.getGameId());
			statement.setString(2, category.getName());
			statement.setTimestamp(3, this.getTimestamp());
			statement.setTimestamp(4, this.getTimestamp());

			/* Executing query */
			statement.executeUpdate();		

			/* Assigning category ID */
			category.setId(this.getLastInsertedId(statement));

			return category;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Category findOne(int id) {
		Category obj = new Category();
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM categories WHERE category_id = ?"
							);

			/* Binding variables */
			statement.setInt(1, id);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Building obj with the new variables */
			if(result.next()){
				obj.setId(id);
				obj.setGameId(result.getInt("game_id"));
				obj.setName(result.getString("name"));
				obj.setCreatedAt(result.getDate("created_at"));
				obj.setUpdatedAt(result.getDate("updated_at"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return obj;
	}

	@Override
	public ArrayList<Category> findAll(int limit, int offset) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM categories ORDER BY category_id DESC LIMIT ?, ?"
							);

			/* Binding variables */
			statement.setInt(1, offset);
			statement.setInt(2, limit);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Declaration ArrayList*/
			ArrayList<Category> tab = new ArrayList<Category>();

			/* While result -> add new obj in tab */
			while(result.next()){
				Category obj = new Category();

				obj.setId(result.getInt("category_id"));
				obj.setGameId(result.getInt("game_id"));
				obj.setName(result.getString("name"));
				obj.setCreatedAt(result.getDate("created_at"));
				obj.setUpdatedAt(result.getDate("updated_at"));

				tab.add(obj);
			}

			return tab;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Category update(Category obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"UPDATE categories SET game_id= ?, name = ?, updated_at = ? WHERE category_id = ?"
							);

			/* Binding variables */    
			statement.setInt(1, obj.getGameId());
			statement.setString(2, obj.getName());
			statement.setTimestamp(3, this.getTimestamp());
			statement.setInt(4, obj.getId());

			/* Executing query */
			statement.executeUpdate();	

			/* Affectation with the updated object */
			obj = this.findOne(obj.getId());

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return obj;
	}

	@Override
	public void destroy(Category obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"DELETE FROM categories WHERE category_id = ?"
							);

			/* Binding variables */    
			statement.setInt(1, obj.getId());

			/* Executing query */
			statement.executeUpdate();	

		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

	@Override
	public int count() {
		int num = 0;
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"SELECT COUNT(*) FROM categories"
							);

			/* Executing query */
			ResultSet result = statement.executeQuery();


			if(result.next()){
				num = result.getInt("COUNT(*)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return num;
	}

}
