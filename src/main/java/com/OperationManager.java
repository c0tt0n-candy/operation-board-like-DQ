/*
package com;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.Operation;

@Component
public class OperationManager {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int add(Operation operation) {
		jdbcTemplate.update("insert into operation_history_Tbl(content,created) values(?,?)", operation.getContent(),
				new Timestamp(System.currentTimeMillis()));

		int id = jdbcTemplate.queryForObject(
				"select content from operation_history_Tbl where created = ?", Integer.class,
				operation.getNumber(), operation.getformattedCreated());
		return id;
	}
}
*/