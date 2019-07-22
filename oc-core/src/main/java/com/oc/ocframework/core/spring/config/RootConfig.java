package com.oc.ocframework.core.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.oc.ocframework.data.spring.config.DataConfig;

/**
 * Web应用Spring应用上下文配置类
 */
@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackages = "com.oc", 
	includeFilters = {
		@Filter(type = FilterType.ASPECTJ, pattern = "com.oc..service..*"),
		@Filter(type = FilterType.ASPECTJ, pattern = "com.oc..component..*")
	})
public class RootConfig {
}
