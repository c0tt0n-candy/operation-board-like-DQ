package com;

//import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/")
	public String index(Model model) {
	// カレンダー取得	
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		
		model.addAttribute("calendar",calendar);
		model.addAttribute("nowYear",nowYear);
		model.addAttribute("nowMonth",nowMonth);
		
//		int nowDay = calendar.get(Calendar.DATE);
//		model.addAttribute(nowDay);
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select * from operation_history_Tbl",
				(rs, rowNum) -> new Operation(rs.getInt("year"), rs.getInt("month"), rs.getInt("day"), rs.getString("content")));
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}

	@RequestMapping(value = "/today")
	public String update(Model model, @RequestParam("number") String number) {
		model.addAttribute("number", number);
		
	// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);
		
		model.addAttribute("calendar",calendar);
		model.addAttribute("nowYear",nowYear);
		model.addAttribute("nowMonth",nowMonth);
		
	// operation_Listからnumberに対応したさくせんを得,operation_history_Tblを更新する.
		String content = jdbcTemplate.queryForObject("select content from operation_List where number=?", String.class,
				number);
		
//		Date date = new Date();
//		model.addAttribute("date",date);
		
//		calendar.setTime(date);
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		String strPreviousDate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());

		int count = jdbcTemplate.queryForObject("select count(*) from operation_history_Tbl where year=? and month=? and day=?",
				Integer.class, nowYear, nowMonth, nowDay);
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_Tbl(year,month,day,content) values(?,?,?,?)",  nowYear, nowMonth, nowDay, content);
		} else {
			jdbcTemplate.update("update operation_history_Tbl set content=? where year=? and month=? and day=?", content, nowYear, nowMonth, nowDay);
		}
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select * from operation_history_Tbl",
				(rs, rowNum) -> new Operation(rs.getInt("year"), rs.getInt("month"), rs.getInt("day"), rs.getString("content")));
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}
}