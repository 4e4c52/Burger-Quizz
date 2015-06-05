package business.models;

import java.util.Date;

public class Question extends Model {

	/* 
	 * Attributes
	 */
	private int id;
	private String text;
	private int category_id;
	private int timer;
	private int good_answer;
	private Date created_at;
	private Date updated_at;


	/**
	 * Question Default Constructor
	 */
	public Question() {}

	/**
	 * Question Constructor
	 * @param text Question text
	 * @param category_id Parent Category
	 * @param good_answer
	 */
	public Question(String text, int category_id,int good_answer) {
		this.text = text;
		this.category_id = category_id;
		this.good_answer = good_answer;
		
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCategoryId() {
		return this.category_id;
	}

	public void setCategoryId(int id) {
		this.category_id = id;
	}

	public int getTimer(){
		return this.timer;
	}

	public void setTimer(int time){
		this.timer = time;
	}
	
	public int getGoodAnswer() {
		return this.good_answer;
	}

	public void setGoodAnswer(int id) {
		this.good_answer = id;
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