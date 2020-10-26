package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class UserController {
//    @RequestMapping(value="quick",method = RequestMethod.GET,params = {"username"})
    @RequestMapping("quick1")
    public String test(){

        return "success";
    }
}
