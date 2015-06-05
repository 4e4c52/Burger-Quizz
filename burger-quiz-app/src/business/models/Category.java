package business.models;

import java.util.Date;

public class Category extends Model{

	/*
	 *  Attributes 
	 */
	private int id;
	private String name;
	private int game_id;
	private Date created_at;
	private Date updated_at;

	/**
	 * Category Default Constructor
	 */
	public Category() {}

	/**
	 * Category Constructor
	 * @param name Category name
	 * @param game_id Parent game
	 */
	public Category(String name, int game_id) {
		this.name = name;
		this.game_id = game_id;

		Date today = new Date();

		this.created_at = today;
		this.updated_at = today;
	}

	/*
	 * Accessors
	 */
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGameId() {
		return this.game_id;
	}

	public void setGameId(int id) {
		this.game_id = id;
	}

	public Date getCreatedAt() {
		return this.created_at;
	}

	public void setCreatedAt(Date date) {
		this.created_at = date;
	}

	public Date getUpdatedAt() {
		return this.updated_at;
	}

	public void setUpdatedAt(Date date) {
		this.updated_at = date;
	}

}