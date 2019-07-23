package com.oc.ocframework.core.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oc.ocframework.core.spring.service.HomeService;
import com.oc.ocframework.data.domain.easyui.TreeNode;
/**
 *	Spring MVC 应用根目录Controller类
 */
@Controller
public class RootController implements InitializingBean {
	
    //XXX 读取配置应该转移到模板解析器
    @Autowired
    @Qualifier("ocFrameworkSetting")
    private Properties ocFrameworkSetting;
    
    @Autowired
    private HomeService homeService;
    
    private String ocFrameworkUI;
    
    //FIXME 可能因为这里的配置导致登录成功依然跳转到登录页面，路径/应该转为重定向，也可能跟tab键有关
    @RequestMapping(value = {"/signIn", "/"}, method = RequestMethod.GET)
	public String signIn() {
        return "basic/" + ocFrameworkUI + "/signIn/signIn";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
	    this.handlePageContext(request, model);
	    List<TreeNode> menus = this.homeService.getMenus();
	    model.addAttribute("menus", menus);
	    return "basic/" + ocFrameworkUI + "/home/home";
	}
	
	@RequestMapping(value = "/home/{module}", method = RequestMethod.GET)
	public String homeContent(@PathVariable String module, Model model) {
	    model.addAttribute("module", module);
	    return module + "/" + ocFrameworkUI + "/" +  module;
	}
	
	private void handlePageContext(HttpServletRequest request, Model model) {
	    String requestURI = request.getRequestURI();
	    String contextPath = request.getContextPath();
        Map<String, Object> pageContext = new HashMap<String, Object>();
        pageContext.put("requestURI", requestURI);
        pageContext.put("contextPath", contextPath);
        model.addAttribute("pageContext", pageContext);
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        ocFrameworkUI = ocFrameworkSetting.getProperty("ocFramework.UI");
    }
    
    public void setOcFrameworkSetting(Properties ocFrameworkSetting) {
        this.ocFrameworkSetting = ocFrameworkSetting;
    }
    
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }
}
