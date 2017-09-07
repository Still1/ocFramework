package com.oc.ocframework.core.web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.oc.ocframework.core.spring.config.RootConfig;
import com.oc.ocframework.core.spring.config.WebConfig;

/**
 * Spring MVC DispatcerServlet配置类
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	/**
	 * 配置Web应用的Spring应用上下文
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {RootConfig.class};
	}

	/**
	 * 配置Spring MVC的DispatcherServlet的Spring应用上下文
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	/**
	 * 配置Spring MVC的DispatcherServlet的路径映射
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
}
