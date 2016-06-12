package com.operation_board;

public class Operation {

	private int year;
	private int month;
	private int day;
	private int number;
	private String content;
	int profile;

	public Operation() {}

	public Operation(String content, int number) {
		super();
		this.content = content;
		this.number = number;
	}

	public Operation(int year, int month, int day, String content, int profile) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.content = content;
		this.profile = profile;
	}

	public Operation(int year, int month, int day, String content) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.content = content;
	}

	public Operation(int day, String content, int profile) {
		super();
		this.day = day;
		this.content = content;
		this.profile = profile;
	}

	public Operation(int day, String content) {
		super();
		this.day = day;
		this.content = content;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
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

	public int getProfile() {
		return profile;
	}
	public void setProfile(int profile) {
		this.profile = profile;
	}
}
