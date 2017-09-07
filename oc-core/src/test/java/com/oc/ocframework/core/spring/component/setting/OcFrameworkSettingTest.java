package com.oc.ocframework.core.spring.component.setting;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * ocFramework系统配置测试类
 */
public class OcFrameworkSettingTest {

    @Test
    public void testOcFrameworkSetting() throws IOException {
        Properties ocFrameworkSetting = new OcFrameworkSetting();
        TestCase.assertNotNull(ocFrameworkSetting);
    }
}
