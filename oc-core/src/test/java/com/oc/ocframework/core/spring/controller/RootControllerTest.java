package com.oc.ocframework.core.spring.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 *	Spring MVC 应用根目录Controller测试类
 */
public class RootControllerTest {
	
	private static RootController rootController;
	
	@BeforeClass
	public static void instantiate() {
		RootControllerTest.rootController = new RootController();
	}
	
	@Test
	public void testSignIn() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(RootControllerTest.rootController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/signIn")).andExpect(MockMvcResultMatchers.view().name("basic/signIn/signIn"));
	}
	
	@Test
	public void testHome() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(RootControllerTest.rootController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/home")).andExpect(MockMvcResultMatchers.view().name("basic/home/home"));
	}
}
