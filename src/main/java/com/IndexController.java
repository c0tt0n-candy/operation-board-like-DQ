package com;

import java.util.Date;
import java.text.DateFormat;
import java.util.List;

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
/*		
		List<Operation> operationList = jdbcTemplate.query("select number, content from operation_List",
				(rs, rowNum) -> new Operation(rs.getInt("number"), rs.getString("content")));
		model.addAttribute("opeLis",operationList);
*/		
		return "today";
	}

	@RequestMapping(value = "/today")
	public String update(Model model, @RequestParam("number") String number) {

		//operation_Listからnumberに対応したさくせんを得,operation_history_Tblを更新する.
		String content = jdbcTemplate.queryForObject("select content from operation_List where number=?", 
				String.class,number);
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		jdbcTemplate.update("insert into operation_history_Tbl(content,created) values(?,?)", content,df.format(date));

		model.addAttribute("number",number);
		
		//operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select id,content, created from operation_history_Tbl",
				(rs, rowNum) -> new Operation(rs.getInt("id"),rs.getString("content"), rs.getString("created")));
		
		model.addAttribute("history",operationHistory);
		
		return "today";
	}
}