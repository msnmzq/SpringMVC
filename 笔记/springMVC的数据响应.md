

### 数据响应方式

#### 一、页面跳转

##### 返回字符串

- 请求转发

  - 请求转发时，会通过视图解析器，进行前后缀的拼接

  - 使用方式：可以通过配置文件设置前后缀，可以省略forword

    ```java
    @Controller
    @RequestMapping("/user")
    public class UserController {
        @RequestMapping(value="/quick",method = RequestMethod.GET,params = {"username"})
        public String test(){
            return "success";
        }
    }
    ```

    

- 重定向

  - 属于客户端，不会进行前后缀的拼接

  - 使用方式

    ```java
    @Controller
    @RequestMapping("/user")
    public class UserController {
        @RequestMapping(value="/quick",method = RequestMethod.GET,params = {"username"})
        public String test(){
            return "redirect:/jsp/success.jsp";//redierct:不能省略。第一个/代表根目录。不写的话时相对路径
        }
    }
    ```

##### 返回ModelAndView

- ModelAndView是直接new出来的

- 通过```model.setViewName("名字");```设置，而且是请求转发的形式，会进行前后缀拼接

- 通过```model.addObject("user",user);```设置.此代码完全等于```request.setAttribute(name,value);```

- 方法的返回值是ModelAndView

  代码示例：

  ```java
  @Controller
  public class UserController {
      @RequestMapping("/quick2")
      public ModelAndView test(){
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("success");
          User user=new User();
          user.setName("mzq");
          modelAndView.addObject("user",user);
          return modelAndView;
      }
  }
  ```

- ```xml
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page isELIgnored="false" %>  必须填加此代码，否则取不出来，也不知道为啥
  <html>
  <head>
      <title>Title</title>
  </head>
  <body>
  <h1>${ user.name } is successfull </h1>
  </body>
  </html>
  ```

##### modelAndView做参数

springMVC会自动注入参数

```
   @RequestMapping("/quick3")
    public ModelAndView test3(ModelAndView modelAndView){
      
        modelAndView.setViewName("success");
        User user=new User();
        user.setName("mzq");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
```



##### Model做参数，返回字符串

```
  @RequestMapping("/quick4")
    public String test4(Model model){
        User user=new User();
        user.setName("mzq");
        model.addAttribute(user);
        return "success";
    }
```





#### 二、回写数据

##### 回写普通字符串，在页面显示（基本不用）

- 通过注解@ResponseBody来<u>区分回写数据和页面跳转</u>

  ```
     @RequestMapping("quick5")
      @ResponseBody
      public String test5(){
          return "msn";
      }
  ```


##### 回写json

- 返回json格式：将对象通过objectMapper.writeValueAsString()方法转成json字符串，然后再返回

  - 导包：**注意必须要导入2.9.0版本的包，我也不知道为啥**

    ```
     <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-core</artifactId>
          <version>2.9.0</version>
        </dependency>
        <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-annotations</artifactId>
          <version>2.9.0</version>
        </dependency>
        <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-databind</artifactId>
          <version>2.9.0</version>
        </dependency>
    ```

    

  - 创建对象，调用方法

    ```
     @RequestMapping("quick6")
        @ResponseBody
        public String test6() throws JsonProcessingException {
            User user = new User();
            user.setName("msn");
            ObjectMapper objectMapper=new ObjectMapper();
            String s = objectMapper.writeValueAsString(user);
            return s;
        }
    ```

    

- 通过配置文件，我们只需要返回对象，springmvc自动将其转为json.

  - 配置处理器适配器

    ```xml
     <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
            <property name="messageConverters" >
                <list>
                    <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
    
                    </bean>
                </list>
            </property>
        </bean>
    ```

    

  - 编写测试代码

    ```java
       @RequestMapping("quick7")
        @ResponseBody
        public User test7() throws JsonProcessingException {
            User user = new User();
            user.setName("mzq");
    
            return user;
        }
    ```

    

  - 配置文件简化：[annotation-driven介绍](https://www.cnblogs.com/afeng2010/p/10133797.html)

    ```
    
        <mvc:annotation-driven/>
    ```

    

