package com.oc.ocframework.core.spring.config;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import junit.framework.TestCase;

/**
 * Web应用Spring应用上下文配置测试类
 */
public class RootConfigTest {
	@Test
	public void testRootConfigAnnotation() {
		RootConfig rootConfig = new RootConfig();
		Configuration configuration = rootConfig.getClass().getAnnotation(Configuration.class);
		TestCase.assertNotNull(configuration);
		
		ComponentScan componentScan = rootConfig.getClass().getAnnotation(ComponentScan.class);
		TestCase.assertNotNull(componentScan);
		String[] basePackages = componentScan.basePackages();
		TestCase.assertNotNull(basePackages);
		TestCase.assertTrue(basePackages.length == 1);
		TestCase.assertEquals("com.oc", basePackages[0]);
		
		Filter[] includeFilters = componentScan.includeFilters();
		TestCase.assertNotNull(includeFilters);
		TestCase.assertTrue(includeFilters.length == 2);
		List<String> patternList = new ArrayList<String>() {
			private static final long serialVersionUID = 7010548592111707686L;
			{
				this.add("com.oc..service..*");
				this.add("com.oc..component..*");
			}
		};
		for(int i = 0; i < 2; i++) {
			TestCase.assertEquals(FilterType.ASPECTJ, includeFilters[i].type());
			TestCase.assertEquals(patternList.get(i), includeFilters[i].pattern()[0]);
		}
	}
}
