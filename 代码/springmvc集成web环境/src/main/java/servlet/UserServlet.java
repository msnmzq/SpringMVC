package servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ApplicationContext classPathXmlApplicationCotext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        UserService bean = classPathXmlApplicationCotext.getBean(UserService.class);
//        bean.save();


//        ApplicationContext app = WebApplicationContextUtils.getApplicationContext(req.getServletContext());
//        UserService bean = app.getBean(UserService.class);
//        bean.save();


        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        UserService bean = webApplicationContext.getBean(UserService.class);
        bean.save();

    }
}
