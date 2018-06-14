package com.oc.ocframework.core.spring.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.oc.ocframework.data.domain.dto.GenericDto;
import com.oc.ocframework.data.service.DataService;

import net.sf.jsqlparser.JSQLParserException;

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
    public void saveData(GenericDto genericDto) {
    	Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    	try {
			Object dataObject = gson.fromJson(genericDto.getDataJson(), Class.forName(genericDto.getClassName()));
			//TODO 处理关联实体
			dataService.saveOrUpdate(dataObject);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
