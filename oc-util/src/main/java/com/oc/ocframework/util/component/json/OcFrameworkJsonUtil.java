package com.oc.ocframework.util.component.json;

import java.lang.reflect.Type;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oc.ocframework.util.component.json.gson.config.ForeignKeyExclusionStrategy;

public class OcFrameworkJsonUtil {
    public static <T> T fromJson(String json, Class<T> classOfT) {
    	return fromJson(json, classOfT, null);
    }
    
    public static <T> T fromJson(String json, Type convertType) {
        Gson gson = new Gson();
        //TODO 可以研究一下泛型T怎么通过Type类型参数确定的(有可能是没有确定的，返回值可以赋值给任何类型的变量，返回值的实际类型应该是通过Type确定的)
        //TODO java.lang.reflect.Type这个类与java.lang.Class<T>类似乎有点关联，Type类型可以强转Class，它们都可以表示类型信息，需进一步了解
        T result = gson.fromJson(json, convertType);
        return result;
    }
    
    public static <T> T fromJson(String json, Class<T> classOfT, FieldNamingPolicy namingConvention) {
    	//TODO 可以研究一下FieldNamingPolicy类，神奇的enum
    	GsonBuilder gsonBuilder = new GsonBuilder();
    	if(namingConvention != null) {
    		gsonBuilder.setFieldNamingPolicy(namingConvention);
    	}
    	Gson gson = gsonBuilder.create();
        T result = gson.fromJson(json, classOfT);
        return result;
    }
    
    public static <T> T fromJsonExceptForeignKey(String json, Class<T> classOfT) {
    	Gson gson = new GsonBuilder()
    			.setExclusionStrategies(new ForeignKeyExclusionStrategy())
    			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    	T result = gson.fromJson(json, classOfT);
    	return result;
    }
    
    public static String toJson(Object src) {
        Gson gson = new Gson();
        String result = gson.toJson(src);
        return result;
    }
}
