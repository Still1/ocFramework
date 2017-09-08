package com.oc.ocframework.core.spring.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Spring MVC dispatcherServlet Spring应用上下文配置类
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.oc", includeFilters = @Filter(type = FilterType.ASPECTJ, pattern = "com.oc..spring.controller..*"))
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置Spring MVC静态资源处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	/**
	 * 声明视图解析器bean
	 * 
	 * @param templateEngine 
	 * 		  SpringTemplateEngine模板引擎bean
	 * 
	 * @return ThymeleafViewResolver视图解析器
	 */
	@Bean
	public ViewResolver viewResolver(TemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setCharacterEncoding("UTF-8");
		return viewResolver;
	}
	
	/**
	 * 声明模板引擎bean
	 * 
	 * @param templateResolver
	 * 		  SpringResourceTemplateResolver模板解析器bean
	 * 
	 * @return SpringTemplateEngine模板引擎
	 */
	@Bean
	public TemplateEngine templateEngine(ITemplateResolver templateResolver) {
		TemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
	
	/**
	 * 声明模板解析器bean
	 * 
	 * @return SpringResourceTemplateResolver模板解析器
	 */
	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		//XXX 配置化
		templateResolver.setPrefix("classpath:/template/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	/**
	 * 配置Spring MVC静态资源位置及其路径映射
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//XXX 配置化
	    registry.addResourceHandler("/resource/template/**").addResourceLocations("classpath:/template/");
		registry.addResourceHandler("/resource/common/**").addResourceLocations("classpath:/common/");
	}

	/**
	 * 配置返回字符串的编码类型，主要影响AJAX请求返回的内容
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Charset utf8Charset = Charset.forName("UTF-8");
		StringHttpMessageConverter converter = new StringHttpMessageConverter(utf8Charset);
		converters.add(converter);
	}
}
