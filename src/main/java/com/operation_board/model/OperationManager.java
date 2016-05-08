package com.operation_board.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.operation_board.Operation;

@Component
public class OperationManager {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Operation> getList() {
		List<Operation> operationList = jdbcTemplate.query("select * from operation_list", new OperationListRowMapper());
		return operationList;
	}

	public List<Operation> getAllOperation() {
		List<Operation> operations = jdbcTemplate.query("select * from operation_history_tbl", new OperationRowMapper());
		return operations;
	}

	public Operation getOneOperation(int year, int month, int day) {
		int count = countOperation(year, month, day);
		Operation operation = null;
		if (count == 1) {
			operation = jdbcTemplate.queryForObject("select * from operation_history_tbl where year=? and month=? and day=?", new OperationRowMapper(), year, month, day);
		}
		return operation;
	}

	public void addOperation(Operation operation) {
		String content = jdbcTemplate.queryForObject("select content from operation_list where number=?", String.class, operation.getNumber());
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=?", Integer.class,
				operation.getYear(), operation.getMonth(), operation.getDay());
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_tbl(year,month,day,content) values(?,?,?,?)", getCalendar.getNowYear(),
					operation.getYear(), operation.getMonth(), operation.getDay(), content);
		} else {
			jdbcTemplate.update("update operation_history_tbl set content=? where year=? and month=? and day=?",
					content, operation.getYear(), operation.getMonth(), operation.getDay());
		}
	}


	public int countOperation(int year, int month, int day) {
		int count = jdbcTemplate.queryForObject("select count(*) from operation_history_tbl where year=? and month=? and day=?",
				Integer.class, year, month, day);
		return count;
	}

	public int getOperationNum(int year, int month, int day) {
		int number = -1;
		if (countOperation(year, month, day) == 1) {
			String content = jdbcTemplate.queryForObject("select content from operation_history_tbl where year=? and month=? and day=?", String.class, year, month, day);
			number = jdbcTemplate.queryForObject("select number from operation_list where content=?", Integer.class, content);
		}
		return number;
	}

	public String getOperationContent(int number) {
		String content = jdbcTemplate.queryForObject("select content from operation_list where number=?", String.class, number);
		return content;
	}

	public List<Operation> getOperationHistory(int year, int month) {
		List<Operation> operationHistory = jdbcTemplate.query("select day, content from operation_history_tbl where year=? and month=? order by day",
				new OperationHistoryRowMapper(), year, month);
		return operationHistory;
	}


	private class OperationListRowMapper implements RowMapper<Operation>{
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			String content = rs.getString("content");
			int number = rs.getInt("number");
			return new Operation(content, number);
		}
	}

	private class OperationRowMapper implements RowMapper<Operation>{
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			int year = rs.getInt("year");
			int month = rs.getInt("month");
			int day = rs.getInt("day");
			String content = rs.getString("content");
			return new Operation(year, month, day, content);
		}
	}

	private class OperationHistoryRowMapper implements RowMapper<Operation> {
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			int day = rs.getInt("day");
			String content = rs.getString("content");
			return new Operation(day, content);
		}
	}

}
