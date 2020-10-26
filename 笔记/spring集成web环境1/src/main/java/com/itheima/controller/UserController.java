package com.itheima.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//测试RequestMapping
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping( value="/quick",method = RequestMethod.GET,params = {"username"})
    public String save(){
        System.out.println("com.itheima.controller save running");
        return "/success.jsp";
    }
}
