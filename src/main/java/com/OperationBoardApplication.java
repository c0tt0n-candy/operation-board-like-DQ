package com;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class OperationBoardApplication implements CommandLineRunner {

	//private static final Logger log = LoggerFactory.getLogger(OperationBoardApplication.class);

	public static void main(String args[]) {
		SpringApplication.run(OperationBoardApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {

		//log.info("Creating tables");
		jdbcTemplate.execute("drop table operation_List if exists");
		jdbcTemplate
				.execute("create table operation_List(number integer, content varchar, primary key(number))");

		List<Object[]> opeList = Arrays.asList("0 みんながんばれ", "1 ガンガンいこうぜ", "2 テストをだいじに", "3 いろいろやろうぜ").stream()
				.map(s -> s.split(" ")).collect(Collectors.toList());
		jdbcTemplate.batchUpdate("insert into  operation_List(number, content) values(?,?)", opeList);

		jdbcTemplate.execute("drop table operation_history_Tbl if exists");
		jdbcTemplate.execute(
				"create table operation_history_Tbl(id serial, content varchar, created timestamp, primary key(id))");

	}
}