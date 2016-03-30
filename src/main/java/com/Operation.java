package com;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Operation {

	private int id;
	private int number;
	private String content;
	private String created;
/*	
	public Operation(int id, int number, String content, Timestamp created) {
		super();
		this.id = id;
		this.number = number;
		this.content = content;
		this.created = created;
	}
*/	
	public Operation(int id,String content, String created) {
		super();
		this.id = id;
		this.content = content;
		this.created = created;
	}
/*
	public Operation(int number, String content){
		super();
		this.number = number;
		this.content = content;
	}
*/	
	
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

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreated(){
		return 	created;
	}
	public void setCreated(String created){
		this.created = created;
	}
	
	public String getformattedCreated() {
		return new SimpleDateFormat("yyyy/MM/dd").format(created);
	}
	
}
