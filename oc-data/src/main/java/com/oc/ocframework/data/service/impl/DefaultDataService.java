package com.oc.ocframework.data.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.persistence.ManyToMany;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oc.ocframework.data.repository.GenericDao;
import com.oc.ocframework.data.service.DataService;
import com.oc.ocframework.util.component.json.OcFrameworkJsonUtil;
import com.oc.ocframework.util.component.sql.OcFrameworkSqlUtil;

import net.sf.jsqlparser.JSQLParserException;

@Service
public class DefaultDataService implements DataService {

    @Autowired
    private GenericDao genericDao;
    
    @Override
    public String getDataGridResultSetJson(String fileName, String sqlName, Map<String, String[]> parameterMap) throws IOException, DocumentException, JSQLParserException{
        String[] statementDual = OcFrameworkSqlUtil.getSqlStatementDual(fileName, sqlName, parameterMap);
        String json = this.genericDao.findJsonBySql(statementDual[0], statementDual[1]);
        return json;
    }
    
    @Override
	public <T> T getObjectById(Class<T> objectClass, Integer id) {
		return this.genericDao.getObjectById(objectClass, id);
	}

	@Override
	public <T> T loadObjectById(Class<T> objectClass, Integer id) {
		return this.genericDao.loadObjectById(objectClass, id);
	}

	@Override
    public void saveOrUpdate(Object obj) {
        this.genericDao.saveOrUpdate(obj);
    }

	@Override
	public <T> void saveOrUpdate(String dataJson, Class<T> dataClass) throws IllegalArgumentException, IllegalAccessException {
    	T dataObject = OcFrameworkJsonUtil.fromJson(dataJson, dataClass, FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    	JsonObject jsonObject = new JsonParser().parse(dataJson).getAsJsonObject();
    	Field[] fields = dataClass.getFields();
    	for(Field field : fields) {
    		if(field.getAnnotation(ManyToMany.class) != null) {
    			List<Object> list = new ArrayList<> ();
    			jsonObject.getAsJsonArray().forEach(new Consumer<JsonElement>() {
					@Override
					public void accept(JsonElement element) {
						Integer id = element.getAsInt();
						Type genericType = field.getGenericType();
						ParameterizedType pt = (ParameterizedType) genericType;
						Class<?> genericClass = (Class<?>) pt.getRawType();
						Object object = DefaultDataService.this.loadObjectById(genericClass, id);
						list.add(object);
					}
    			});
    			field.set(dataObject, list);
    		}
    	}
    	this.genericDao.saveOrUpdate(dataObject);
	}
}
