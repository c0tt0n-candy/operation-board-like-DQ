package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value="/")
    public String index() {
        return "index";
    }
    
	@RequestMapping(value="/today")
	public String main() {
		//System.out.println("okubo");
		return "today";
	}
}
