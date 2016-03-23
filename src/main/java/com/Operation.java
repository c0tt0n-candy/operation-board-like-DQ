package com;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Operation {

	private int id;
	private int number;
	private String content;
	private Timestamp created;

	public Operation(int id, int number, String content, Timestamp created) {
		super();
		this.id = id;
		this.number = number;
		this.content = content;
		this.created = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getContent() {
		return content;
	}

	public void setCoutent(String content) {
		this.content = content;
	}

	public String getformattedCreated() {
		return new SimpleDateFormat("yyyy/MM/dd").format(created);
	}

	@Override
	public String toString() {
		return String.format("Operation[date=%d : content=%s]", created, content);
	}

}