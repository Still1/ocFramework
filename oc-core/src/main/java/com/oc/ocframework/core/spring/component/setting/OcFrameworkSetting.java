package com.oc.ocframework.core.spring.component.setting;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * ocFramework系统配置类
 */
@Component
public class OcFrameworkSetting extends Properties {

    private static final long serialVersionUID = -9200756606847837083L;
    
    //XXX 考虑多份不同properties，单例问题
    public OcFrameworkSetting() throws IOException {
        Resource ocFrameworkSetting = new ClassPathResource("config/properties/ocFrameworkSetting.properties");
        InputStream inputStream = ocFrameworkSetting.getInputStream();
        this.load(inputStream);
        inputStream.close();
        inputStream = null;
    }
}
