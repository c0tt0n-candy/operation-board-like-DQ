package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

import com.Operation;

@RestController
public class OperationController{
	
	@Autowired
	OperationManager operationManager;
	
	@RequestMapping(value="/rest/operation",method=RequestMethod.POST)
	@ResponseBody
	Json add(Operation operation) {
    	int id = this.operationManager.add(operation);
    	return new Json("{\"success\":true,\"id\":" + id + "}");
    	
   }	
}