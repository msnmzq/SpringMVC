package controller;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.User;
import domain.Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @RequestMapping("quick15")
    @ResponseBody
    public void test15(MultipartFile [] file) throws IOException {
        System.out.println(file);
        //将文件保存到服务器
//        System.out.println(file.getOriginalFilename());
        for (MultipartFile multipartFile : file) {
            String s = multipartFile.getOriginalFilename();
            System.out.println(s);
            multipartFile.transferTo(new File("c:\\upload\\"+s));
        }

    }
    @RequestMapping("quick14")
    @ResponseBody
    public void test14(Date name){
        System.out.println(name);

    }
    @RequestMapping("quick13")
    @ResponseBody
    public void test13( @RequestParam(value="username", required=false,defaultValue = "zs")String name){
        System.out.println(name);

    }

    @RequestMapping("quick12")
    @ResponseBody
    public void test12(@RequestBody List<User> userList){
        System.out.println(userList);

    }

    @RequestMapping("quick11")
    @ResponseBody
    public void test11(Vo vo){
        System.out.println(vo.getUserList());

    }
    @RequestMapping("quick10")
    @ResponseBody
    public void test10(String [] hobbies){
        for(String s:hobbies){
            System.out.println(s);
        }

    }
    //获取数据
    @RequestMapping("quick9")
    @ResponseBody
    public void test9(User user){
        System.out.println(user);
    }
    @RequestMapping("quick8")
    @ResponseBody
    public void test8(String username,int age){

        System.out.println(username);
        System.out.println(age);
    }
    @RequestMapping("quick7")
    @ResponseBody
    public List<User> test7() throws JsonProcessingException {
        User user = new User();
        user.setName("mzq");
        User user2 = new User();
        user2.setName("mzn");
        List list=new ArrayList<User>();
        list.add(user);
        list.add(user2);
        return list;
    }
    //回写json数据
    @RequestMapping("quick6")
    @ResponseBody
    public String test6() throws JsonProcessingException {
        User user = new User();
        user.setName("msn");
        ObjectMapper objectMapper=new ObjectMapper();
        String s = objectMapper.writeValueAsString(user);
        return s;
    }
    //回写普通字符串
    @RequestMapping("quick5")
    @ResponseBody
    public String test5(){
        return "msn";
    }




    //页面跳转
    @RequestMapping("/quick4")
    public String test4(Model model){
        User user=new User();
        user.setName("mzq");
        model.addAttribute(user);
        return "success";

    }
    @RequestMapping("/quick3")
    public ModelAndView test3(ModelAndView modelAndView){

        modelAndView.setViewName("success");
        User user=new User();
        user.setName("mzq");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
    @RequestMapping("/quick2")
    public ModelAndView test2(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        User user=new User();
        user.setName("mzq");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
