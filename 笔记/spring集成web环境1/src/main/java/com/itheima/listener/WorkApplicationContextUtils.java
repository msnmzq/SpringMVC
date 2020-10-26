package com.itheima.listener;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

public class WorkApplicationContextUtils {
    public static ApplicationContext getApplicationContext(ServletContext servletContext){
        ApplicationContext app = (ApplicationContext) servletContext.getAttribute("app");
        return app;
    }
}
