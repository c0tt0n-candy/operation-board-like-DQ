package com;

import java.util.Calendar;
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
		int nowDay = calendar.get(Calendar.DATE);
		
		model.addAttribute("nowYear",nowYear);
		model.addAttribute("nowMonth",nowMonth);
		
		calendar.set(nowYear,nowMonth-1,1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay",lastDay);
		
	// 現在選択中のさくせんがあればそのnumberを送る.
		int count = jdbcTemplate.queryForObject("select count(*) from operation_history_Tbl where year=? and month=? and day=?",
				Integer.class, nowYear, nowMonth, nowDay);
		if (count == 1) {
			String content = jdbcTemplate.queryForObject("select content from operation_history_Tbl where year=? and month=? and day=?", String.class,
				nowYear,nowMonth,nowDay);
			int number = jdbcTemplate.queryForObject("select number from operation_List where content=?", Integer.class,content);
			model.addAttribute("number",number);
		}
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select day, content from operation_history_Tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), nowYear, nowMonth);
		
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
		
		model.addAttribute("nowYear",nowYear);
		model.addAttribute("nowMonth",nowMonth);
		
		calendar.set(nowYear,nowMonth-1,1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay",lastDay);
		
	// operation_Listからnumberに対応したさくせんを得,operation_history_Tblを更新する.
		String content = jdbcTemplate.queryForObject("select content from operation_List where number=?", String.class,
				number);

		int count = jdbcTemplate.queryForObject("select count(*) from operation_history_Tbl where year=? and month=? and day=?",
				Integer.class, nowYear, nowMonth, nowDay);
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_Tbl(year,month,day,content) values(?,?,?,?)",  nowYear, nowMonth, nowDay, content);
		} else {
			jdbcTemplate.update("update operation_history_Tbl set content=? where year=? and month=? and day=?", content, nowYear, nowMonth, nowDay);
		}
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select day, content from operation_history_Tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), nowYear, nowMonth);
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}
	
	
	@RequestMapping(value = "/previous")
	public String getPrevious(Model model, @RequestParam("previous") String previous) {
		String[] prev = previous.split("/");
		int prevYear = Integer.parseInt(prev[0]);
		int prevMonth = Integer.parseInt(prev[1]);
		if(prevMonth == 1){
			prevYear -= 1;
			prevMonth = 12;
		}
		model.addAttribute("nowYear",prevYear);
		model.addAttribute("nowMonth",prevMonth);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(prevYear,prevMonth-1,1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay",lastDay);
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select day, content from operation_history_Tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), prevYear, prevMonth);
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}
	
	@RequestMapping(value = "/next")
	public String getNext(Model model, @RequestParam("next") String next) {
		String[] nex = next.split("/");
		
		int nexYear = Integer.parseInt(nex[0]);
		int nexMonth = Integer.parseInt(nex[1]);
		if(nexMonth == 12){
			nexYear += 1;
			nexMonth = 1;
		}
		model.addAttribute("nowYear",nexYear);
		model.addAttribute("nowMonth",nexMonth);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(nexYear,nexMonth-1,1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay",lastDay);
		
	// operation_history_Tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query("select day, content from operation_history_Tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), nexYear, nexMonth);
		
		model.addAttribute("history", operationHistory);
		
		return "today";
	}
	
}