package com.oc.ocframework.core.spring.controller;

import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *	Spring MVC 应用根目录Controller类
 */
@Controller
public class RootController implements InitializingBean {
	
    @Autowired
    @Qualifier("ocFrameworkSetting")
    private Properties ocFrameworkSetting;
    
    private String ocFrameworkUI;
    
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String signIn() {
        return "basic/" + ocFrameworkUI + "/signIn/signIn";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
	    return "basic/" + ocFrameworkUI + "/home/home";
	}

    public Properties getOcFrameworkSetting() {
        return ocFrameworkSetting;
    }

    public void setOcFrameworkSetting(Properties ocFrameworkSetting) {
        this.ocFrameworkSetting = ocFrameworkSetting;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ocFrameworkUI = ocFrameworkSetting.getProperty("ocFramework.UI");
    }
}
