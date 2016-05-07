package com.operation_board.model;

import java.util.Calendar;

public class getCalendar {

	static Calendar calendar = Calendar.getInstance();

	public static int getNowYear() {
		return calendar.get(Calendar.YEAR);
	}

	public static int getNowMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getNowDay() {
		return calendar.get(Calendar.DATE);
	}

	public static int getLastDay(int year, int month, int day) {
		calendar.set(year, month - 1, day);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(getNowYear(), getNowMonth() - 1, getNowDay());
		return lastDay;
	}
}