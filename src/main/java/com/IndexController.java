package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping(value = "/")
	public String index() {
		return "today";
	}

	@RequestMapping(value = "/today")
	public String today() {
		return "today";
	}
}