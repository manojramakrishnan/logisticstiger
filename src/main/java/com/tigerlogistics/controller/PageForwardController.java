package com.tigerlogistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class PageForwardController {

	@RequestMapping("login")
	public String loginPageForward() {
		
			return "login";
	}
}
