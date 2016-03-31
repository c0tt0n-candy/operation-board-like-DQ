package com;

//import java.text.DateFormat;
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

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);

		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);

		return "today";
	}

	@RequestMapping(value = "/today")
	public String update(Model model, @RequestParam("number") String number) {
		// カレンダー取得
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);

		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		
		// operation_Listからnumberに対応したさくせんを得,operation_history_Tblを更新する.
		String content = jdbcTemplate.queryForObject("select content from operation_List where number=?", String.class,
				number);
		
		Date date = new Date();
		String strdate = new SimpleDateFormat("yyyy/MM/dd").format(date);
/*		
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String strpreviousdate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
*/
		int count = jdbcTemplate.queryForObject("select count(*) from operation_history_Tbl where created=?",
				Integer.class, strdate);
		
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_Tbl(content,created) values(?,?)", content,
					strdate);
		} else {
			jdbcTemplate.update("update operation_history_Tbl set content=? where created=?", content, strdate);
		}
		
		model.addAttribute("number", number);
		
		// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select content, created from operation_history_Tbl",
				(rs, rowNum) -> new Operation(rs.getString("content"), rs.getString("created")));
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}
}