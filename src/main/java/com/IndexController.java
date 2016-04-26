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

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);

		// 現在選択中のさくせんがあればそのnumberを送る.
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=?", Integer.class, nowYear,
				nowMonth, nowDay);
		if (count == 1) {
			String content = jdbcTemplate.queryForObject(
					"select content from operation_history_tbl where year=? and month=? and day=?", String.class,
					nowYear, nowMonth, nowDay);
			int number = jdbcTemplate.queryForObject("select number from operation_list where content=?", Integer.class,
					content);
			model.addAttribute("number", number);
		}

		// operation_history_tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query(
				"select day, content from operation_history_tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), nowYear, nowMonth);

		model.addAttribute("history", operationHistory);

		return "today";
	}

	@RequestMapping(value = "/today")
	public String update(Model model, @RequestParam("number") String str_number) {
		int number = Integer.parseInt(str_number);
		model.addAttribute("number", number);

		// カレンダー取得
		Calendar calendar = Calendar.getInstance();
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);

		model.addAttribute("dispYear", nowYear);
		model.addAttribute("dispMonth", nowMonth);

		calendar.set(nowYear, nowMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);

		// operation_listからnumberに対応したさくせんを得,operation_history_tblを更新する.
		String content = jdbcTemplate.queryForObject("select content from operation_list where number=?", String.class,
				number);

		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=?", Integer.class, nowYear,
				nowMonth, nowDay);
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_tbl(year,month,day,content) values(?,?,?,?)", nowYear,
					nowMonth, nowDay, content);
		} else {
			jdbcTemplate.update("update operation_history_tbl set content=? where year=? and month=? and day=?",
					content, nowYear, nowMonth, nowDay);
		}

		// operation_history_tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query(
				"select day, content from operation_history_tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), nowYear, nowMonth);

		model.addAttribute("history", operationHistory);

		return "today";
	}

	@RequestMapping(value = "/previous")
	public String getPrevious(Model model, @RequestParam("previous") String previous) {
		String[] prev = previous.split("/");
		int prevYear = Integer.parseInt(prev[0]);
		int prevMonth = Integer.parseInt(prev[1]);
		if (prevMonth == 0) {
			prevYear -= 1;
			prevMonth = 12;
		}
		model.addAttribute("dispYear", prevYear);
		model.addAttribute("dispMonth", prevMonth);

		Calendar calendar = Calendar.getInstance();

		// 現在選択中のさくせんがあればそのnumberを送る.
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=?", Integer.class, nowYear,
				nowMonth, nowDay);
		if (count == 1) {
			String content = jdbcTemplate.queryForObject(
					"select content from operation_history_tbl where year=? and month=? and day=?", String.class,
					nowYear, nowMonth, nowDay);
			int number = jdbcTemplate.queryForObject("select number from operation_list where content=?", Integer.class,
					content);
			model.addAttribute("number", number);
		}

		calendar.set(prevYear, prevMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);

		// operation_history_tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query(
				"select day, content from operation_history_tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), prevYear, prevMonth);

		model.addAttribute("history", operationHistory);

		return "today";
	}

	@RequestMapping(value = "/next")
	public String getNext(Model model, @RequestParam("next") String next) {
		String[] Next = next.split("/");

		int nextYear = Integer.parseInt(Next[0]);
		int nextMonth = Integer.parseInt(Next[1]);
		if (nextMonth == 13) {
			nextYear += 1;
			nextMonth = 1;
		}
		model.addAttribute("dispYear", nextYear);
		model.addAttribute("dispMonth", nextMonth);

		Calendar calendar = Calendar.getInstance();

		// 現在選択中のさくせんがあればそのnumberを送る.
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth = calendar.get(Calendar.MONTH) + 1;
		int nowDay = calendar.get(Calendar.DATE);
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=?", Integer.class, nowYear,
				nowMonth, nowDay);
		if (count == 1) {
			String content = jdbcTemplate.queryForObject(
					"select content from operation_history_tbl where year=? and month=? and day=?", String.class,
					nowYear, nowMonth, nowDay);
			int number = jdbcTemplate.queryForObject("select number from operation_list where content=?", Integer.class,
					content);
			model.addAttribute("number", number);
		}

		calendar.set(nextYear, nextMonth - 1, 1);
		int lastDay = calendar.getActualMaximum(Calendar.DATE);
		model.addAttribute("lastDay", lastDay);

		// operation_history_tblから過去の履歴を取得する.
		List<Operation> operationHistory = jdbcTemplate.query(
				"select day, content from operation_history_tbl where year=? and month=? order by day",
				(rs, rowNum) -> new Operation(rs.getInt("day"), rs.getString("content")), nextYear, nextMonth);

		model.addAttribute("history", operationHistory);

		return "today";
	}

}