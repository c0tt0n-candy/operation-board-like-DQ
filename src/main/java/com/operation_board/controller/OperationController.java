package com.operation_board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.operation_board.Operation;
import com.operation_board.model.OperationManager;

@RestController
@RequestMapping("rest/operation")
public class OperationController {
	@Autowired
	OperationManager operationManager;
	
	// OperationList全件取得
	@RequestMapping(value = "list", method = RequestMethod.GET)
	List<Operation> getList() {
		List<Operation> operationList = operationManager.getList();
		return operationList;
	}

	// Operation全件取得
	@RequestMapping(method = RequestMethod.GET)
	List<Operation> getAll() {
		List<Operation> operations = operationManager.getAllOperation();
		return operations;
	}

	// Operation1件取得
	@RequestMapping(value = "{year}/{month}/{day}", method = RequestMethod.GET)
	Operation getOne(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
		Operation operation = operationManager.getOneOperation(year, month, day);
		return operation;
	}

	// Operation新規作成
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	void add(@RequestBody Operation operation) {
		operationManager.addOperation(operation);
	}
}
