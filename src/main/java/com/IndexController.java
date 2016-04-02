package com;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		
		int nowDay = calendar.get(Calendar.DATE);
		model.addAttribute(nowDay);
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select content, created from operation_history_Tbl",
				(rs, rowNum) -> new Operation(rs.getString("content"), rs.getString("created")));
		
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
		
		model.addAttribute("calendar",calendar);
		model.addAttribute("nowYear",nowYear);
		model.addAttribute("nowMonth",nowMonth);
		
	// operation_Listからnumberに対応したさくせんを得,operation_history_Tblを更新する.
		String content = jdbcTemplate.queryForObject("select content from operation_List where number=?", String.class,
				number);
		Date date = new Date();
		String strDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
		model.addAttribute("date",date);
		
/*		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String strpreviousdate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
*/
		int count = jdbcTemplate.queryForObject("select count(*) from operation_history_Tbl where created=?",
				Integer.class, strDate);
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_Tbl(content,created) values(?,?)", content,
					strDate);
		} else {
			jdbcTemplate.update("update operation_history_Tbl set content=? where created=?", content, strDate);
		}
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select content, created from operation_history_Tbl",
				(rs, rowNum) -> new Operation(rs.getString("content"), rs.getString("created")));
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}
}