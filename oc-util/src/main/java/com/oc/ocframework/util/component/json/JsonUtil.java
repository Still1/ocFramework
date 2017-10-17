package com.oc.ocframework.util.component.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil {
    public static <T> T fromJson(String json, Type convertType) {
        Gson gson = new Gson();
        //TODO 可以研究一下泛型T怎么通过Type类型参数确定的(有可能是没有确定的，返回值可以赋值给任何类型的变量，返回值的实际类型应该是通过Type确定的)
        T result = gson.fromJson(json, convertType);
        return result;
    }
    
    public static String toJson(Object src) {
        Gson gson = new Gson();
        String result = gson.toJson(src);
        return result;
    }
}
