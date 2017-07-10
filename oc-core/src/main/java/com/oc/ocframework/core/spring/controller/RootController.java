package com.oc.ocframework.core.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {
		return "basic/signIn";
	}
}
