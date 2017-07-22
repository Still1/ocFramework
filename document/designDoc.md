# ocFramework

## 设计文档

### 系统模块概述

* oc-core 架构核心模块，主要包含MVC框架、依赖注入
* oc-util 包含辅助工具
* oc-data 数据处理模块

### 系统模块设计

* oc-core
    * 项目结构概述
        * com.oc.ocframework.core 模块主包
        * com.oc.ocframework.core.web.config  Servlet容器配置类
        * com.oc.ocframework.core.spring.config  SpringMVC应用上下文配置类
        * com.oc.ocframework.core.spring.controller  SpringMVC controller类
        * resources/common/ 公共页面资源
        * resources/template/ 页面模板资源
