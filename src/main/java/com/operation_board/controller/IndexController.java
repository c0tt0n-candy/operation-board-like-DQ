package com.operation_board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.operation_board.Operation;
import com.operation_board.model.OperationManager;
import com.operation_board.model.getCalendar;

@Controller
public class IndexController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	OperationManager operationManager;

	final int nowYear = getCalendar.getNowYear();
	final int nowMonth = getCalendar.getNowMonth();
	final int nowDay = getCalendar.getNowDay();

	@RequestMapping(value = "/")
	public String index(@ModelAttribute Operation operation, Model model) {
		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay);
		int lastDay = getCalendar.getLastDay(nowYear, nowMonth, nowDay);
		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);
		model.addAttribute("number", number);
		model.addAttribute("lastDay", lastDay);

		List<Operation> operationHistory = operationManager.getOperationHistory(nowYear, nowMonth);
		model.addAttribute("history", operationHistory);

		operation.setYear(nowYear);
		operation.setMonth(nowMonth);
		operation.setDay(nowDay);
		operation.setNumber(number);
		model.addAttribute("ope", operation);
		return "today";
	}

	@RequestMapping(value = "/today")
	public String update(Operation operation, Model model, @RequestParam("number") int number) {
		model.addAttribute("number", number);

		int lastDay = getCalendar.getLastDay(dispYear, dispMonth, nowDay);
		model.addAttribute("dispYear", dispYear);
		model.addAttribute("dispMonth", dispMonth);
		model.addAttribute("lastDay", lastDay);

		operation.setNumber(number);
		operationManager.addOperation(operation);

		List<Operation> operationHistory = operationManager.getOperationHistory(dispYear, dispMonth);
		model.addAttribute("history", operationHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/previous")
	public String getPrevious(Operation operation, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		if (month == 0) {
			year -= 1;
			month = 12;
		}

		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("number", number);

		int lastDay = getCalendar.getLastDay(year, month, 1);
		model.addAttribute("lastDay", lastDay);

		List<Operation> operationHistory = operationManager.getOperationHistory(year, month);
		model.addAttribute("history", operationHistory);

		return "redirect:/";
	}

	@RequestMapping(value = "/next")
	public String getNext(Operation operation, Model model, @RequestParam("year") int year, @RequestParam("month") int month) {
		if (month == 13) {
			year += 1;
			month = 1;
		}

		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay);

		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("number", number);

		int lastDay = getCalendar.getLastDay(year, month, 1);
		model.addAttribute("lastDay", lastDay);

		List<Operation> operationHistory = operationManager.getOperationHistory(year, month);
		model.addAttribute("history", operationHistory);

		return "redirect:/";
	}

}