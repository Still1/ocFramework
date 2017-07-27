package com.oc.ocframework.core.web.config;

import org.junit.Before;
import org.junit.Test;

import com.oc.ocframework.core.spring.config.RootConfig;
import com.oc.ocframework.core.spring.config.WebConfig;

import junit.framework.TestCase;
/**
 * Spring MVC DispatcerServlet配置测试类
 */
public class WebAppInitializerTest {
	
	private WebAppInitializer webAppInitializer;
	
	@Before
	public void instantiate() {
		this.webAppInitializer = new WebAppInitializer();
	}
	
	@Test
	public void testGetRootConfigClasses() {
		Class<?>[] rootConfigClasses = this.webAppInitializer.getRootConfigClasses();
		TestCase.assertEquals(rootConfigClasses[0], RootConfig.class);
	}
	
	@Test
	public void testGetServletConfigClasses() {
		Class<?>[] rootConfigClasses = this.webAppInitializer.getServletConfigClasses();
		TestCase.assertEquals(rootConfigClasses[0], WebConfig.class);
	}
	
	@Test
	public void testGetServletMappings() {
		String[] servletMappings = this.webAppInitializer.getServletMappings();
		TestCase.assertEquals(servletMappings[0], "/");
	}
}
