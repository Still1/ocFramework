package com.oc.ocframework.core.spring.controller;

import com.oc.ocframework.data.domain.dto.GenericDto;
import com.oc.ocframework.data.service.DataService;
import net.sf.jsqlparser.JSQLParserException;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

//XXX 重构，放在oc-data模块中
@Controller
public class DataController {
    
    @Autowired
    private DataService dataService;
    
    @RequestMapping(value = "/data/{fileName}/{sqlName}", method = RequestMethod.GET)
    @ResponseBody
    public String getDataGridData(@PathVariable String fileName, @PathVariable String sqlName, HttpServletRequest request) throws IOException, DocumentException, JSQLParserException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String resultSetJson = this.dataService.getDataGridResultSetJson(fileName, sqlName, parameterMap);
        return resultSetJson;
    }
    
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public void saveData(GenericDto genericDto) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
    	//TODO 处理异常
    	String dataJson = genericDto.getDataJson();
    	Class<?> dataClass = Class.forName(genericDto.getClassName());
    	this.dataService.saveOrUpdate(dataJson, dataClass);
    }
    
    // XXX RESTful
    @RequestMapping(value = "/ddata", method = RequestMethod.POST)
    @ResponseBody
    public void deleteData(GenericDto genericDto) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
    	//TODO 处理异常
    	String dataJson = genericDto.getDataJson();
    	Class<?> dataClass = Class.forName(genericDto.getClassName());
    	this.dataService.delete(dataJson, dataClass);
    }
}
