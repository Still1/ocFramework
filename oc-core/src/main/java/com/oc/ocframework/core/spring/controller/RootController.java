package com.oc.ocframework.core.spring.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *	Spring MVC 应用根目录Controller类
 */
@Controller
public class RootController implements InitializingBean {
	
    //XXX 读取配置应该转移到模板解析器
    @Autowired
    @Qualifier("ocFrameworkSetting")
    private Properties ocFrameworkSetting;
    
    private String ocFrameworkUI;
    
    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String signIn() {
        return "basic/" + ocFrameworkUI + "/signIn/signIn";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
	    this.handlePageContext(request, model);
	    return "basic/" + ocFrameworkUI + "/home/home";
	}
	
	private void handlePageContext(HttpServletRequest request, Model model) {
	    String requestURI = request.getRequestURI();
	    String contextPath = request.getContextPath();
        Map<String, Object> pageContext = new HashMap<String, Object>();
        pageContext.put("requestURI", requestURI);
        pageContext.put("contextPath", contextPath);
        model.addAttribute("pageContext", pageContext);
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
