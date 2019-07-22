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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oc.ocframework.data.repository.GenericDao;
import com.oc.ocframework.data.service.DataService;
import com.oc.ocframework.util.component.json.OcFrameworkJsonUtil;
import com.oc.ocframework.util.component.sql.OcFrameworkSqlUtil;
import com.oc.ocframework.util.component.string.OcFrameworkStringUtil;

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
		T dataObject = this.deserialize(dataJson, dataClass);
    	this.genericDao.saveOrUpdate(dataObject);
	}
	
	private <T> T deserialize(String dataJson, Class<T> dataClass) throws IllegalArgumentException, IllegalAccessException {
    	T dataObject = OcFrameworkJsonUtil.fromJsonExceptForeignKey(dataJson, dataClass);
    	JsonObject jsonObject = new JsonParser().parse(dataJson).getAsJsonObject();
    	Field[] fields = dataClass.getDeclaredFields();
    	//XXX 是否需要进一步抽象
    	for(Field field : fields) {
			String fieldName = field.getName();
			String separatorFieldName = OcFrameworkStringUtil.camelCaseToSeparator(fieldName, '_');
    		if(field.getAnnotation(ManyToMany.class) != null || field.getAnnotation(OneToMany.class) != null) {
    			JsonArray foreignKeyIdArray = jsonObject.getAsJsonArray(separatorFieldName);
    			if(foreignKeyIdArray != null && !foreignKeyIdArray.isJsonNull() && foreignKeyIdArray.isJsonArray() && foreignKeyIdArray.size() > 0) {
    				List<Object> list = new ArrayList<> ();
    				foreignKeyIdArray.forEach(new Consumer<JsonElement>() {
    					@Override
    					public void accept(JsonElement element) {
    						Integer id = element.getAsInt();
    						Type genericType = field.getGenericType();
    						ParameterizedType pt = (ParameterizedType)genericType;
    						Type[] actualTypeArguments = pt.getActualTypeArguments();
    						Object object = DefaultDataService.this.loadObjectById((Class<?>)actualTypeArguments[0], id);
    						//XXX 限定了是List
    						list.add(object);
    					}
    				});
    				//XXX Could it be more elegant?
    				field.setAccessible(true);
    				field.set(dataObject, list);
    			}
    		} else if(field.getAnnotation(ManyToOne.class) != null) {
    			Integer foreignKeyId = jsonObject.getAsJsonPrimitive(separatorFieldName).getAsInt();
    			Class<?> foreignKeyClass = field.getType();
    			Object object = DefaultDataService.this.loadObjectById(foreignKeyClass, foreignKeyId);
    			//XXX Could it be more elegant?
				field.setAccessible(true);
				field.set(dataObject, object);
    		}
    	}
    	return dataObject;
	}
}
