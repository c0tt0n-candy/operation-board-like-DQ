package com;

import java.sql.Timestamp;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	// @Autowired
	// OperationManager operationManager;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/")
	public String index(Model model) {
		return "today";
	}

	@RequestMapping(value = "/today")
	public String update(Model model, @RequestParam("number") String number) {

		String content = jdbcTemplate.queryForObject("select content from operation_List where number=?", String.class,
				number);

		jdbcTemplate.update("insert into operation_history_Tbl(content,created) values(?,?)", content,
				new Timestamp(System.currentTimeMillis()));
		
		model.addAttribute("number",number);
		return "today";
	}
}