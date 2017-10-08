package com.oc.ocframework.core.spring.controller;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oc.ocframework.util.component.sql.SQLUtil;

@Controller
public class DataController {
    @RequestMapping(value = "/data/{fileName}/{sqlName}", method = RequestMethod.GET)
    @ResponseBody
    public String getData(@PathVariable String fileName, @PathVariable String sqlName) throws IOException, DocumentException {
        SQLUtil.getStatement(fileName, sqlName);
        return "ss";
    }
}
