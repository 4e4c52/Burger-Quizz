package business.models;

import java.util.Date;

public class Answer extends Model {

	/* 
	 * Attributes
	 */
	private int id;
	private String text;
	private int question_id;
	private Date created_at;
	private Date updated_at;


	/**
	 * Answer Default Constructor
	 */
	public Answer() {}

	/**
	 * Answer Constructor
	 * @param text Answer text
	 * @param question_id Parent question
	 * @param good_answer Set the answer truth
	 */
	public Answer(String text, int question_id, boolean good_answer) {
		this.text = text;
		this.question_id = question_id;

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

	public int getQuestionId() {
		return this.question_id;
	}

	public void setQuestionId(int id) {
		this.question_id = id;
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