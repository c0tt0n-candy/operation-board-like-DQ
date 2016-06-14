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

	// OperationList全件取得
	public List<Operation> getList() {
		List<Operation> operationList = jdbcTemplate.query("select * from operation_list",
				new OperationListRowMapper());
		return operationList;
	}

	// OperationHistory全件取得
	public List<Operation> getAllOperation() {
		List<Operation> operationHistories = jdbcTemplate.query("select * from operation_history_tbl",
				new OperationAllHistoryRowMapper());
		return operationHistories;
	}

	// OperationHistory該当Profile全件取得
	public List<Operation> getProfileAllOperation(String profile) {
		List<Operation> operationProfileHistories = jdbcTemplate.query(
				"select year, month, day, content from operation_history_tbl where profile=?",
				new OperationProfileHistoryRowMapper(), profile);
		return operationProfileHistories;
	}

	// OperationHistory該当年月取得
	public List<Operation> getPeriodOperation(int year, int month) {
		List<Operation> operationHistory = jdbcTemplate.query(
				"select day, content, profile from operation_history_tbl where year=? and month=? order by day",
				new OperationPeriodHistoryRowMapper(), year, month);
		return operationHistory;
	}

	// OperationHistory該当Profile該当年月取得
	public List<Operation> getPeriodOperation(int year, int month, String profile) {
		List<Operation> operationProfileHistory = jdbcTemplate.query(
				"select day, content from operation_history_tbl where year=? and month=? and profile=? order by day",
				new OperationProfilePeriodHistoryRowMapper(), year, month, profile);
		return operationProfileHistory;
	}

	// OperationHistory1件取得
	public Operation getOneOperation(int year, int month, int day, String profile) {
		int count = countOperation(year, month, day, profile);
		Operation operation = null;
		if (count == 1) {
			operation = jdbcTemplate.queryForObject(
					"select * from operation_history_tbl where year=? and month=? and day=? and profile=?",
					new OperationAllHistoryRowMapper(), year, month, day, profile);
		}
		return operation;
	}

	// OperationHistory新規登録・更新
	public void updateOperation(Operation operation) {
		String content = jdbcTemplate.queryForObject("select content from operation_list where number=?", String.class,
				operation.getNumber());
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=? and profile=?",
				Integer.class, operation.getYear(), operation.getMonth(), operation.getDay(), operation.getProfile());
		if (count == 0) {
			jdbcTemplate.update("insert into operation_history_tbl(year,month,day,content,profile) values(?,?,?,?,?)",
					operation.getYear(), operation.getMonth(), operation.getDay(), content, operation.getProfile());
		} else {
			jdbcTemplate.update(
					"update operation_history_tbl set content=? where year=? and month=? and day=? and profile=?",
					content, operation.getYear(), operation.getMonth(), operation.getDay(), operation.getProfile());
		}
	}

	// OperationHistory1件削除
	public void deleteOperation(int year, int month, int day, String profile) {
		int count = countOperation(year, month, day, profile);
		if (count != 0) {
			jdbcTemplate.update("delete from operation_history_tbl where year=? and month=? and day=? and profile=?",
					year, month, day, profile);
		}
	}


	// OperationList取得用
	private class OperationListRowMapper implements RowMapper<Operation> {
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			String content = rs.getString("content");
			int number = rs.getInt("number");
			return new Operation(content, number);
		}
	}

	// OperationHistory全件取得用
	private class OperationAllHistoryRowMapper implements RowMapper<Operation> {
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			int year = rs.getInt("year");
			int month = rs.getInt("month");
			int day = rs.getInt("day");
			String content = rs.getString("content");
			String profile = rs.getString("profile");
			return new Operation(year, month, day, content, profile);
		}
	}

	// OperationHistory該当Profile全件取得用
	private class OperationProfileHistoryRowMapper implements RowMapper<Operation> {
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			int year = rs.getInt("year");
			int month = rs.getInt("month");
			int day = rs.getInt("day");
			String content = rs.getString("content");
			return new Operation(year, month, day, content);
		}
	}

	// OperationHistory該当年月分取得用
	private class OperationPeriodHistoryRowMapper implements RowMapper<Operation> {
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			int day = rs.getInt("day");
			String content = rs.getString("content");
			String profile = rs.getString("profile");
			return new Operation(day, content, profile);
		}
	}

	// OperationHistory該当Profile該当年月分取得用
	private class OperationProfilePeriodHistoryRowMapper implements RowMapper<Operation> {
		@Override
		public Operation mapRow(ResultSet rs, int rowNum) throws SQLException {
			int day = rs.getInt("day");
			String content = rs.getString("content");
			return new Operation(day, content);
		}
	}

	// 該当年月日にOperationが登録されているか否かを判断するツール
	public int countOperation(int year, int month, int day, String profile) {
		int count = jdbcTemplate.queryForObject(
				"select count(*) from operation_history_tbl where year=? and month=? and day=? and profile=?",
				Integer.class, year, month, day, profile);
		return count;
	}


	// OperationのNumber取得
	public int getOperationNum(int year, int month, int day, String profile) {
		int number = -1;
		Operation operation = getOneOperation(year, month, day, profile);
		if (operation != null) {
			number = jdbcTemplate.queryForObject("select number from operation_list where content=?", Integer.class,
					operation.getContent());
		}
		return number;
	}

}
