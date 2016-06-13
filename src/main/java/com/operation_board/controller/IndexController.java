package com.operation_board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

	static int nowYear = getCalendar.getNowYear();
	static int nowMonth = getCalendar.getNowMonth();
	static int nowDay = getCalendar.getNowDay();

	@RequestMapping(value = "/it")
	public String indexIt(Operation operation, Model model) {
		setDate(operation);
		operation.setProfile(0);

		int lastDay = getCalendar.getLastDay(nowYear, nowMonth);
		List<Operation> operationHistory = operationManager.getPeriodOperation(nowYear, nowMonth, operation.getProfile());
		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("history", operationHistory);

		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay, operation.getProfile());
		model.addAttribute("number", number);

		return "indexIt";
	}

	@RequestMapping(value = "/it/select")
	public String updateIt(Operation operation, RedirectAttributes attributes, @RequestParam("dispYear") int dispYear, @RequestParam("dispMonth") int dispMonth) {
		setDate(operation);
		operation.setProfile(0);
		operationManager.updateOperation(operation);

		int lastDay = getCalendar.getLastDay(dispYear, dispMonth);
		List<Operation> operationHistory = operationManager.getPeriodOperation(dispYear, dispMonth, operation.getProfile());
		attributes.addFlashAttribute("dispYear", dispYear);
		attributes.addFlashAttribute("dispMonth", dispMonth);
		attributes.addFlashAttribute("lastDay",lastDay);
		attributes.addFlashAttribute("history", operationHistory);
		return "redirect:/it";
	}

	@RequestMapping(value = "/it/{year}/{month}")
	public String getPreviousIt(Operation operation, Model model, @PathVariable("year") int year, @PathVariable("month") int month) {
		operation.setProfile(0);

		int lastDay = getCalendar.getLastDay(year, month);
		List<Operation> operationHistory = operationManager.getPeriodOperation(year, month, operation.getProfile());
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay",lastDay);
		model.addAttribute("history", operationHistory);

		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay, operation.getProfile());
		model.addAttribute("number", number);
		return "indexIt";
	}


	@RequestMapping(value = "/dev")
	public String indexDev(Operation operation, Model model) {
		setDate(operation);
		operation.setProfile(1);

		int lastDay = getCalendar.getLastDay(nowYear, nowMonth);
		List<Operation> operationHistory = operationManager.getPeriodOperation(nowYear, nowMonth, operation.getProfile());
		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);
		model.addAttribute("lastDay", lastDay);
		model.addAttribute("history", operationHistory);

		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay, operation.getProfile());
		model.addAttribute("number", number);

		return "indexDev";
	}

	@RequestMapping(value = "/dev/select")
	public String updateDev(Operation operation, RedirectAttributes attributes, @RequestParam("dispYear") int dispYear, @RequestParam("dispMonth") int dispMonth) {
		setDate(operation);
		operation.setProfile(1);
		operationManager.updateOperation(operation);

		int lastDay = getCalendar.getLastDay(dispYear, dispMonth);
		List<Operation> operationHistory = operationManager.getPeriodOperation(dispYear, dispMonth, operation.getProfile());
		attributes.addFlashAttribute("dispYear", dispYear);
		attributes.addFlashAttribute("dispMonth", dispMonth);
		attributes.addFlashAttribute("lastDay",lastDay);
		attributes.addFlashAttribute("history", operationHistory);
		return "redirect:/dev";
	}

	@RequestMapping(value = "/dev/{year}/{month}")
	public String getPreviousDev(Operation operation, Model model, @PathVariable("year") int year, @PathVariable("month") int month) {
		operation.setProfile(1);

		int lastDay = getCalendar.getLastDay(year, month);
		List<Operation> operationHistory = operationManager.getPeriodOperation(year, month, operation.getProfile());
		model.addAttribute("dispYear", year);
		model.addAttribute("dispMonth", month);
		model.addAttribute("lastDay",lastDay);
		model.addAttribute("history", operationHistory);

		int number = operationManager.getOperationNum(nowYear, nowMonth, nowDay, operation.getProfile());
		model.addAttribute("number", number);
		return "indexDev";
	}

	public void setDate(Operation operation) {
		operation.setYear(nowYear);
		operation.setMonth(nowMonth);
		operation.setDay(nowDay);
	}
}