package business.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public abstract class DAO<T> {

	public Connection conn = DatabaseConnection.getInstance();

	/**
	 * Save a new record
	 * @param obj
	 */
	public abstract T create(T obj);

	/**
	 * Find a record by ID
	 * @param id
	 * @return T
	 */
	public abstract T findOne(int id);

	/**
	 * Find a range of records
	 * @param limit The number of records the query should return
	 * @param offset The offset the query starts at
	 * @return an array of T records
	 */
	public abstract ArrayList<T> findAll(int limit, int offset);

	/**
	 * Update a record
	 * @param obj
	 */
	public abstract T update(T obj);

	/**
	 * Destroy a record
	 * @param obj
	 */
	public abstract void destroy(T obj);




	/**
	 * Return the number or records
	 */
	public abstract int count();

	/**
	 * Give the timestamp of the current date and time
	 * @return Timestamp
	 */
	public Timestamp getTimestamp() {

		return new Timestamp(new Date().getTime());

	}

	/**
	 * Returns the last inserted ID for a database query
	 * @return id Last inserted ID
	 */
	public int getLastInsertedId(PreparedStatement statement) throws SQLException {
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) return (int) generatedKeys.getLong(1);
		return 0;
	}

}