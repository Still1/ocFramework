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
    	T dataObject = OcFrameworkJsonUtil.fromJsonExceptForeignKey(dataJson, dataClass);
    	JsonObject jsonObject = new JsonParser().parse(dataJson).getAsJsonObject();
    	Field[] fields = dataClass.getDeclaredFields();
    	for(Field field : fields) {
    		if(field.getAnnotation(ManyToMany.class) != null) {
    			List<Object> list = new ArrayList<> ();
    			String fieldName = field.getName();
    			jsonObject.getAsJsonArray(fieldName).forEach(new Consumer<JsonElement>() {
					@Override
					public void accept(JsonElement element) {
						Integer id = element.getAsInt();
						Type genericType = field.getGenericType();
						ParameterizedType pt = (ParameterizedType)genericType;
						Type[] actualTypeArguments = pt.getActualTypeArguments();
						Object object = DefaultDataService.this.loadObjectById((Class<?>)actualTypeArguments[0], id);
						list.add(object);
					}
    			});
    			field.setAccessible(true);
    			field.set(dataObject, list);
    		}
    	}
    	this.genericDao.saveOrUpdate(dataObject);
	}
}
