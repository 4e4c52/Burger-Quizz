package business.models;

import java.util.Date;

public class Game extends Model {

	/* 
	 * Attributes
	 */
	private int id;
	private String name;
	private Date created_at;
	private Date updated_at;

	/**
	 * Game Default Constructor
	 */
	public Game() {}

	/**
	 * Game Constructor
	 * @param name Game name
	 */
	public Game(String name) {
		this.name = name;

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