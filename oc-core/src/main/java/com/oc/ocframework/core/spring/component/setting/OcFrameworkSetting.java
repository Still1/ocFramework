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
    
    public OcFrameworkSetting() throws IOException {
        Resource ocFrameworkSetting = new ClassPathResource("config/ocFrameworkSetting.properties");
        InputStream inputStream = ocFrameworkSetting.getInputStream();
        this.load(inputStream);
    }
}
