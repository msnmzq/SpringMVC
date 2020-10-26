package com.itheima.web;



import com.itheima.service.serviceImpl.UserServiceImpl;
import org.springframework.context.ApplicationContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
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

        ServletContext servletContext = req.getServletContext();
//      ApplicationContext app = (ApplicationContext) servletContext.getAttribute("app");
        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserServiceImpl ser = app.getBean(UserServiceImpl.class);
        ser.save();

    }
}
