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
		Operation opeartion = jdbcTemplate.queryForObject("select * from operation_history_tbl where year=? and month=? and day=?", new OperationRowMapper(), year, month, day);
		return opeartion;
	}

	public void addOperation(Operation operation) {
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=?", Integer.class, operation.getYear(),
				operation.getMonth(), operation.getDay());
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_tbl(year,month,day,content) values(?,?,?,?)", operation.getYear(),
					operation.getMonth(), operation.getDay(), operation.getContent());
		} else {
			jdbcTemplate.update("update operation_history_tbl set content=? where year=? and month=? and day=?",
					operation.getContent(), operation.getYear(), operation.getMonth(), operation.getDay());
		}
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
}
