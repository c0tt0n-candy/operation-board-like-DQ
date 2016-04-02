/*
package com;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OperationController {

	@Autowired
	OperationManager operationManager;
	
	@RequestMapping(value = "/rest/operation", method = RequestMethod.POST)
	@ResponseBody
	Map<String, String> add(Operation operation) {
		int id = this.operationManager.add(operation);
		Map<String, String> m = new HashMap<>();
		m.put("success", String.valueOf(true));
		m.put("id", String.valueOf(id));
		return m;
		
		// return new Json("{\"success\":true,\"id\":" + id + "}");

	}
}
*/