package business.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import business.models.Question;

public class Questions extends DAO<Question> {

	@Override
	public Question create(Question question) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"INSERT INTO questions (category_id, text, timer, good_answer, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS
							);

			/* Binding variables */
			statement.setInt(1, question.getCategoryId());
			statement.setString(2, question.getText());
			statement.setInt(3, question.getTimer());
			statement.setInt(4, question.getGoodAnswer());
			statement.setTimestamp(5, this.getTimestamp());
			statement.setTimestamp(6, this.getTimestamp());

			/* Executing query */
			statement.executeUpdate();		

			/* Assigning question ID */
			question.setId(this.getLastInsertedId(statement));

			return question;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Question findOne(int id) {
		Question obj = new Question();
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM questions WHERE question_id = ?"
							);

			/* Binding variables */
			statement.setInt(1, id);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Building obj with the new variables */
			if(result.next()){
				obj.setId(id);
				obj.setCategoryId(result.getInt("category_id"));
				obj.setText(result.getString("text"));
				obj.setTimer(result.getInt("timer"));
				obj.setGoodAnswer(result.getInt("good_answer"));
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
	public ArrayList<Question> findAll(int limit, int offset) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this.conn
					.prepareStatement(
							"SELECT * FROM questions ORDER BY question_id DESC LIMIT ?, ?"
							);

			/* Binding variables */
			statement.setInt(1, offset);
			statement.setInt(2, limit);

			/* Executing Query */
			ResultSet result = statement.executeQuery();

			/* Declaration ArrayList*/
			ArrayList<Question> tab = new ArrayList<Question>();

			/* While result -> add new obj in tab */
			while(result.next()){
				Question obj = new Question();

				obj.setId(result.getInt("question_id"));
				obj.setCategoryId(result.getInt("category_id"));
				obj.setText(result.getString("text"));
				obj.setTimer(result.getInt("timer"));
				obj.setGoodAnswer(result.getInt("good_answer"));
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
	public Question update(Question obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"UPDATE questions SET category_id= ?, text = ?, timer = ?, good_answer = ?, updated_at = ? WHERE question_id = ?"
							);

			/* Binding variables */    
			statement.setInt(1, obj.getCategoryId());
			statement.setString(2, obj.getText());
			statement.setInt(3, obj.getTimer());
			statement.setInt(4, obj.getGoodAnswer());
			statement.setTimestamp(5, this.getTimestamp());
			statement.setInt(6, obj.getId());

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
	public void destroy(Question obj) {
		try {

			/* Preparing the query statement */
			PreparedStatement statement = this .conn	
					.prepareStatement(
							"DELETE FROM questions WHERE question_id = ?"
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
							"SELECT COUNT(*) FROM questions"
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
