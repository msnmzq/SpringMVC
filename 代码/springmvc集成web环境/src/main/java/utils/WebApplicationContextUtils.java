package utils;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

public class WebApplicationContextUtils {
    public static ApplicationContext getApplicationContext(ServletContext sc){
        ApplicationContext app = (ApplicationContext) sc.getAttribute("app");
        return app;
    }
}
