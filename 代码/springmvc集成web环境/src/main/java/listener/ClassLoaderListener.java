package listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ClassLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String classPathConfig = servletContext.getInitParameter("classPathConfig");
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(classPathConfig);
        servletContext.setAttribute("app",applicationContext);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
