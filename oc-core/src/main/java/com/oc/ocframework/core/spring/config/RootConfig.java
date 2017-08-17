package com.oc.ocframework.core.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Web应用Spring应用上下文配置类
 */
@Configuration
@ComponentScan(basePackages = "com.oc", 
	includeFilters = {
		@Filter(type = FilterType.ASPECTJ, pattern = "com.oc..spring.service..*"),
		@Filter(type = FilterType.ASPECTJ, pattern = "com.oc..spring.repository..*"),
		@Filter(type = FilterType.ASPECTJ, pattern = "com.oc..spring.component..*")
	})
public class RootConfig {
}
