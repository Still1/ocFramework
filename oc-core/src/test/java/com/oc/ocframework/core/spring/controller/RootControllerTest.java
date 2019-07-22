package com.oc.ocframework.core.spring.controller;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.oc.ocframework.core.spring.service.HomeService;
/**
 *	Spring MVC 应用根目录Controller测试类
 */
public class RootControllerTest {
	
	private RootController rootController;
	
	private static String mockOcFrameworkUI = "test";
	
	@Before
	public void instantiate() throws Exception {
		rootController = new RootController();
		Properties properties = Mockito.mock(Properties.class);
		Mockito.when(properties.getProperty("ocFramework.UI")).thenReturn(RootControllerTest.mockOcFrameworkUI);
		rootController.setOcFrameworkSetting(properties);
		HomeService homeService = Mockito.mock(HomeService.class);
		rootController.setHomeService(homeService);
		rootController.afterPropertiesSet();
	}
	
	@Test
	public void testSignIn() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(rootController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/signIn")).andExpect(MockMvcResultMatchers.view().name("basic/" + RootControllerTest.mockOcFrameworkUI + "/signIn/signIn"));
	}
	
	@Test
	public void testHome() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(rootController).build();
		mockMvc.perform(MockMvcRequestBuilders.get("/home")).andExpect(MockMvcResultMatchers.view().name("basic/"  + RootControllerTest.mockOcFrameworkUI + "/home/home"));
	}
}
