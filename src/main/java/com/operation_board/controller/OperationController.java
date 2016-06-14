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

	// OperationHistory全件取得
	@RequestMapping(method = RequestMethod.GET)
	List<Operation> getAll() {
		List<Operation> operationHistories = operationManager.getAllOperation();
		return operationHistories;
	}

	// OperationHistory該当Profile全件取得
	@RequestMapping(value = "{profile}", method = RequestMethod.GET)
	List<Operation> getProfile(@PathVariable String profile) {
		List<Operation> operationProfileHistories = operationManager.getProfileAllOperation(profile);
		return operationProfileHistories;
	}

	// OperationHistory該当年月取得
	@RequestMapping(value = "{year}/{month}", method = RequestMethod.GET)
	List<Operation> getPeriod(@PathVariable int year, @PathVariable int month) {
		List<Operation> operationHistory = operationManager.getPeriodOperation(year, month);
		return operationHistory;
	}

	// OperationHistory該当Profile該当年月取得
	@RequestMapping(value = "{profile}/{year}/{month}", method = RequestMethod.GET)
	List<Operation> getProfilePeriod(@PathVariable String profile, @PathVariable int year, @PathVariable int month) {
		List<Operation> operationProfileHistory = operationManager.getPeriodOperation(year, month, profile);
		return operationProfileHistory;
	}

	// OperationHistory1件取得
	@RequestMapping(value = "{profile}/{year}/{month}/{day}", method = RequestMethod.GET)
	Operation getOne(@PathVariable String profile, @PathVariable int year, @PathVariable int month, @PathVariable int day) {
		Operation operation = operationManager.getOneOperation(year, month, day, profile);
		return operation;
	}

	// Operation新規作成
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	void update(@RequestBody Operation operation) {
		operationManager.updateOperation(operation);
	}

	// Operation1件削除
	@RequestMapping(value = "{profile}/{year}/{month}/{day}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable String profile, @PathVariable int year,@PathVariable int month,@PathVariable int day) {
		operationManager.deleteOperation(year, month ,day, profile);
	}
}
