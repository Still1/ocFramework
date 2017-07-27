package com.oc.ocframework.core.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *	Spring MVC 应用根目录Controller类
 */
@Controller
public class RootController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root() {
		return "basic/signIn/signIn";
	}
}
