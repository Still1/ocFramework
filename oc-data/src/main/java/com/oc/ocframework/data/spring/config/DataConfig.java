package com.oc.ocframework.data.spring.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.oc", 
includeFilters = {
    @Filter(type = FilterType.ASPECTJ, pattern = "com..repository..*"),
})
@EnableTransactionManagement
public class DataConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        //XXX 配置化
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/oc");
        dataSource.setUsername("root");
        dataSource.setPassword("mysqlroot");
        return dataSource;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource, ImplicitNamingStrategy implicitNamingStrategy) {
        LocalSessionFactoryBean localSessionFactoryBean =  new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        //XXX 配置化
        localSessionFactoryBean.setPackagesToScan(new String[] {"com.oc.**.domain"});
        localSessionFactoryBean.setImplicitNamingStrategy(implicitNamingStrategy);
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
    
    @Bean
    public JdbcOperations jdbcOperations(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

