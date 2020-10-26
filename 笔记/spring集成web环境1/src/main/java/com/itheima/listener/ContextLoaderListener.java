package com.itheima.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        //获取全局初始化参数
        String c = servletContext.getInitParameter("applicationConfig");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(c);

        servletContext.setAttribute("app",applicationContext);
        System.out.println("创建完毕");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
