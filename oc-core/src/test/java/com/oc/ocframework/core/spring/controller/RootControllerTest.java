package com.oc.ocframework.core.spring.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 *	Spring MVC 应用根目录Controller测试类
 */
public class RootControllerTest {
	@Test
	public void testRoot() throws Exception {
		RootController homeController = new RootController();
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.view().name("basic/signIn/signIn"));
	}
}
