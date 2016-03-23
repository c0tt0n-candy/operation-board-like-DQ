/*package com;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OperationController{
	
	@Autowired
	Operation operation;
	
	@RequestMapping(value="/rest/operation",method=RequestMethod.POST)
	@ResponseBody
	public Operation add(@ModelAttribute Map<String, String> map) {
	    System.out.println("addが呼び出されました。");
	    System.out.println("map : " + map);
	    return new Operation(1,"ガンガンいこうぜ",null);
   }
		
}*/