package com.oc.ocframework.core.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //XXX SQL语句配置化
    public static final String QUERY_USER_SQL = "select username, password, enabled from t_users where username = ?";
    public static final String QUERY_AUTHORITY_SQL = "select username, authority from t_authorities inner join t_users on t_authorities.user_id = t_users.id where username = ?";
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //XXX 配置化
        http.authorizeRequests().antMatchers("/resource/common/**", "/resource/template/**").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/signIn").permitAll().defaultSuccessUrl("/home");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(QUERY_USER_SQL).authoritiesByUsernameQuery(QUERY_AUTHORITY_SQL);
    }
}
