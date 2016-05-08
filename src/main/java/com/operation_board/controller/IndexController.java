package com.operation_board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String index(Operation operation, Model model) {
		setDate(operation);

		int lastDay = getCalendar.getLastDay(nowYear, nowMonth);
		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay);
		List<Operation> operationHistory = operationManager.getOperationHistory(nowYear, nowMonth);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("number", number);
		model.addAttribute("history", operationHistory);
		
		return "today";
	}

	@RequestMapping(value = "/select")
	public String update(Operation operation, RedirectAttributes attributes, @RequestParam("dispYear") int dispYear, @RequestParam("dispMonth") int dispMonth) {
		setDate(operation);
		operationManager.addOperation(operation);

		int lastDay = getCalendar.getLastDay(dispYear, dispMonth);
		List<Operation> operationHistory = operationManager.getOperationHistory(dispYear, dispMonth);
		attributes.addFlashAttribute("dispYear", dispYear);
		attributes.addFlashAttribute("dispMonth", dispMonth);
		attributes.addFlashAttribute("lastDay",lastDay);
		attributes.addFlashAttribute("history", operationHistory);
		return "redirect:/today";
	}

	@RequestMapping(value = "/previous")
	public String getPrevious(Model model, RedirectAttributes attributes, @RequestParam("prevYear") int prevYear, @RequestParam("prevMonth") int prevMonth) {
		if (prevMonth == 0) {
			prevYear -= 1;
			prevMonth = 12;
		}
		int lastDay = getCalendar.getLastDay(prevYear, prevMonth);
		List<Operation> operationHistory = operationManager.getOperationHistory(prevYear, prevMonth);
		attributes.addFlashAttribute("dispYear", prevYear);
		attributes.addFlashAttribute("dispMonth", prevMonth);
		attributes.addFlashAttribute("lastDay",lastDay);
		attributes.addFlashAttribute("history", operationHistory);

		return "redirect:/today";
	}

	@RequestMapping(value = "/next")
	public String getNext(Model model, RedirectAttributes attributes, @RequestParam("nextYear") int nextYear, @RequestParam("nextMonth") int nextMonth) {
		if (nextMonth == 13) {
			nextYear += 1;
			nextMonth = 1;
		}
		int lastDay = getCalendar.getLastDay(nextYear, nextMonth);
		List<Operation> operationHistory = operationManager.getOperationHistory(nextYear, nextMonth);
		attributes.addFlashAttribute("dispYear", nextYear);
		attributes.addFlashAttribute("dispMonth", nextMonth);
		attributes.addFlashAttribute("lastDay",lastDay);
		attributes.addFlashAttribute("history", operationHistory);

		return "redirect:/today";
	}
	
	@RequestMapping(value = "/today")
	public String redirect(Operation operation, Model model) {
		setDate(operation);
		int number = operationManager.getOperationNum(operation.getYear(), operation.getMonth(), operation.getDay());
		model.addAttribute("number", number);

		return "today";
	}
	
	public void setDate(Operation operation) {
		operation.setYear(nowYear);
		operation.setMonth(nowMonth);
		operation.setDay(nowDay);
	}
}