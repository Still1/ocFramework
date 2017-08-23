package com.oc.ocframework.core.spring.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import junit.framework.TestCase;

/**
 * Spring MVC dispatcherServlet Spring应用上下文配置测试类
 */

public class WebConfigTest {
	
	private static WebConfig webConfig;
	
	@BeforeClass
	public static void instantiate() {
		WebConfigTest.webConfig = new WebConfig();
	}
	
	@Test
	public void testWebConfigTestAnnotation() {
		Configuration configuration = WebConfig.class.getAnnotation(Configuration.class);
		TestCase.assertNotNull(configuration);
		
		EnableWebMvc enableWebMvc = WebConfig.class.getAnnotation(EnableWebMvc.class);
		TestCase.assertNotNull(enableWebMvc);
		
		ComponentScan componentScan = WebConfig.class.getAnnotation(ComponentScan.class);
		TestCase.assertNotNull(componentScan);
		
		String[] basePackages = componentScan.basePackages();
		TestCase.assertTrue(basePackages.length  == 1);
		TestCase.assertEquals("com.oc", basePackages[0]);
		
		Filter[] includeFilters = componentScan.includeFilters();
		TestCase.assertTrue(includeFilters.length == 1);
		TestCase.assertEquals(FilterType.ASPECTJ, includeFilters[0].type());
		TestCase.assertEquals("com.oc..spring.controller..*", includeFilters[0].pattern()[0]);
	}
	
	@Test
	public void testConfigureDefaultServletHandling() {
		DefaultServletHandlerConfigurer defaultServletHandlerConfigurer = Mockito.mock(DefaultServletHandlerConfigurer.class);
		WebConfigTest.webConfig.configureDefaultServletHandling(defaultServletHandlerConfigurer);
		Mockito.verify(defaultServletHandlerConfigurer).enable();
	}
	
	@Test
	public void testViewResolver() throws NoSuchMethodException, SecurityException {
		Method viewResolverMethod = WebConfig.class.getDeclaredMethod("viewResolver", TemplateEngine.class);
		this.testBeanAnnotation(viewResolverMethod);
		
		TemplateEngine templateEngine = Mockito.mock(TemplateEngine.class);
		ViewResolver viewResolver = WebConfigTest.webConfig.viewResolver(templateEngine);
		TestCase.assertNotNull(viewResolver);
	}
	
	@Test
	public void testTemplateEngine() throws NoSuchMethodException, SecurityException {
		Method templateEngineMethod = WebConfig.class.getDeclaredMethod("templateEngine", ITemplateResolver.class);
		this.testBeanAnnotation(templateEngineMethod);
		
		ITemplateResolver templateResolver = Mockito.mock(ITemplateResolver.class);
		TemplateEngine templateEngine = WebConfigTest.webConfig.templateEngine(templateResolver);
		TestCase.assertNotNull(templateEngine);
	}
	
	@Test
	public void testTemplateResolver() throws NoSuchMethodException, SecurityException {
		Method templateResolverMethod = WebConfig.class.getDeclaredMethod("templateResolver");
		this.testBeanAnnotation(templateResolverMethod);
		
		ITemplateResolver templateResolver = WebConfigTest.webConfig.templateResolver();
		TestCase.assertNotNull(templateResolver);
	}
	
	@Test
	public void testAddResourceHandlers() {
		ResourceHandlerRegistry resourceHandlerRegistry = Mockito.mock(ResourceHandlerRegistry.class);
		ResourceHandlerRegistration templateResourceHandlerRegistration = Mockito.mock(ResourceHandlerRegistration.class);
		ResourceHandlerRegistration commonResourceHandlerRegistration = Mockito.mock(ResourceHandlerRegistration.class);
		
		Mockito.when(resourceHandlerRegistry.addResourceHandler("/resource/template/**")).thenReturn(templateResourceHandlerRegistration);
		Mockito.when(resourceHandlerRegistry.addResourceHandler("/resource/common/**")).thenReturn(commonResourceHandlerRegistration);
		
		WebConfigTest.webConfig.addResourceHandlers(resourceHandlerRegistry);
		Mockito.verify(resourceHandlerRegistry).addResourceHandler("/resource/template/**");
		Mockito.verify(resourceHandlerRegistry).addResourceHandler("/resource/common/**");
		Mockito.verify(templateResourceHandlerRegistration).addResourceLocations("classpath:/template/");
		Mockito.verify(commonResourceHandlerRegistration).addResourceLocations("classpath:/common/");
	}
	
	private void testBeanAnnotation(Method method) {
		Bean bean = method.getAnnotation(Bean.class);
		TestCase.assertNotNull(bean);
	}
	
    @Test
	public void testConfigureMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<>();
	    WebConfigTest.webConfig.configureMessageConverters(converters);
	    TestCase.assertTrue(converters.size() > 0);
	}
}
