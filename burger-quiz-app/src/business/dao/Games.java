package business.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import business.models.Game;

public class Games extends DAO<Game> {

	/**
	 * Create a new game in the database
	 * @param game The game to save
	 * @return game The game we saved
	 */
	@Override
	public Game create(Game game) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"INSERT INTO games (name, created_at, updated_at) VALUES (?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS
							);

			/* Binding variables */
			statement.setString(1, game.getName());
			statement.setTimestamp(2, this.getTimestamp());
			statement.setTimestamp(3, this.getTimestamp());

			/* Executing query */
			statement.executeUpdate();		

			/* Assigning game ID */
			game.setId(this.getLastInsertedId(statement));

			return game;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Find one record in the database
	 * @param id The id of the record
	 * @return obj The game we fetched
	 */
	@Override
	public Game findOne(int id) {
		Game obj = new Game();
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM games WHERE game_id = ?"
							);

			/* Binding variables */
			statement.setInt(1, id);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Building obj with the new variables */
			if(result.next()){
				obj.setId(id);
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

	/**
	 * Find a range of records in the database
	 * @param limit The maximum number of records in the range
	 * @param offset The number where the range starts
	 * @return tab The ArrayList containing our objects
	 */
	@Override
	public ArrayList<Game> findAll(int limit, int offset) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM games ORDER BY game_id DESC LIMIT ?, ?"
							);

			/* Binding variables */
			statement.setInt(1, offset);
			statement.setInt(2, limit);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Declaration ArrayList*/
			ArrayList<Game> tab = new ArrayList<Game>();

			/* While result -> add new obj in tab */
			while(result.next()){
				Game obj = new Game();

				obj.setId(result.getInt("game_id"));
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

	/**
	 * Update a game in the database
	 * @param obj The game to update
	 * @return The updated game
	 */
	@Override
	public Game update(Game obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"UPDATE games SET name = ?, updated_at = ? WHERE game_id = ?"
							);

			/* Binding variables */    
			statement.setString(1, obj.getName());
			statement.setTimestamp(2, this.getTimestamp());
			statement.setInt(3, obj.getId());

			/* Executing query */
			statement.executeUpdate();	

			/* Affectation with the updated object */
			obj = this.findOne(obj.getId());

			return obj;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Destroy a record in the database
	 * @param record The record to destroy
	 */
	@Override
	public void destroy(Game obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"DELETE FROM games WHERE game_id = ?"
							);

			/* Binding variables */    
			statement.setInt(1, obj.getId());

			/* Executing query */
			statement.executeUpdate();	

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Count all records in the database
	 * @return num The number of records
	 */
	@Override
	public int count() {
		int num = 0;
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"SELECT COUNT(*) FROM games"
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