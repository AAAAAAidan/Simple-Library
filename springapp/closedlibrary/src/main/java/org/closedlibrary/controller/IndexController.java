package org.closedlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView index = new ModelAndView("index");
		return index;
	}
	
}
