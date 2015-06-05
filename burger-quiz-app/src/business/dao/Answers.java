package business.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

import business.models.Answer;

public class Answers extends DAO<Answer> {

	@Override
	public Answer create(Answer answer) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"INSERT INTO answers (text, question_id, created_at, updated_at) VALUES (?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS
							);

			/* Binding variables */
			statement.setString(1, answer.getText());
			statement.setInt(2, answer.getQuestionId());
			statement.setTimestamp(3, this.getTimestamp());
			statement.setTimestamp(4, this.getTimestamp());

			/* Executing query */
			statement.executeUpdate();		

			/* Assigning answer ID */
			answer.setId(this.getLastInsertedId(statement));

			return answer;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Answer findOne(int id) {
		Answer obj = new Answer();
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM answers WHERE answer_id = ?"
							);

			/* Binding variables */
			statement.setInt(1, id);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Building obj with the new variables */
			if(result.next()){
				obj.setId(id);
				obj.setText(result.getString("text"));
				obj.setQuestionId(result.getInt("question_id"));
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
	public ArrayList<Answer> findAll(int limit, int offset) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM answers ORDER BY answer_id DESC LIMIT ?, ?"
							);

			/* Binding variables */
			statement.setInt(1, offset);
			statement.setInt(2, limit);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Declaration ArrayList*/
			ArrayList<Answer> tab = new ArrayList<Answer>();

			/* While result -> add new obj in tab */
			while(result.next()){
				Answer obj = new Answer();

				obj.setId(result.getInt("answer_id"));
				obj.setText(result.getString("text"));
				obj.setQuestionId(result.getInt("question_id"));
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
	public Answer update(Answer obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"UPDATE answers SET question_id = ?, text = ?, updated_at = ? WHERE answer_id = ?"
							);

			/* Binding variables */    
			statement.setInt(1, obj.getQuestionId());
			statement.setString(2, obj.getText());
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
	public void destroy(Answer obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"DELETE FROM answers WHERE answer_id = ?"
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
							"SELECT COUNT(*) FROM answers"
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
