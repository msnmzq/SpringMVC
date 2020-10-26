### 获取基本类型参数

需要请求参数和方法中的参数一致，则框架会自动进行封装

请求：http://localhost:8888/springmvc_war_exploded/quick8?username=msn&age=21

```java
  @RequestMapping("quick8")
  @ResponseBody//表示不进行页面跳转
    public void test8(String username,int age){

        System.out.println(username);
        System.out.println(age);
    }


会打印msn和21
```



### 获取POJO类型参数

请求参数和实体类中的属性名一致，并且实体类中含有set方法或者含有带参数的构造方法，或者二者都有

请求：http://localhost:8888/springmvc_war_exploded/quick9?name=msn

```java
    @RequestMapping("quick9")
    @ResponseBody
    public void test9(User user){
        System.out.println(user);
    }
```



### 获取数组类型参数

数组名字和请求中的属性一致，而是数组打印时需要遍历

请求：http://localhost:8888/springmvc_war_exploded/quick10?hobbies=打篮球hobbies=游泳

```java
 @RequestMapping("quick10")
    @ResponseBody
    public void test10(String [] hobbies){
        for(String s:hobbies){
            System.out.println(s);
        }

    }
```



### 获取集合类型参数★

- 使用表单提交的

  - 提交页面userList是Vo实体类的属性名   name是user对象的属性名

    ```
    <form action="quick11">
        <input type="text" name="userList[0].name">
        <input type="text" name="userList[1].name">
        <input type="text" name="userList[2].name">
        <input type="submit" value="提交">
    </form>
    ```

    

  - 将提交的list作为成员变量存储在Vo实体类中

    ```
    package domain;
    import java.util.List;
    public class Vo {
        private List<User> userList;
    
        public Vo() {}
    
        public Vo(List<User> userList) {
            this.userList = userList;
        }
    
        public List<User> getUserList() {
            return userList;
        }
    
        public void setUserList(List<User> userList) {
            this.userList = userList;
        }
    }
    ```

    

  - 测试

    ```
    	@RequestMapping("quick11")
        @ResponseBody
        public void test11(Vo vo){
            System.out.println(vo.getUserList());
    
        }
    ```

    

    

- 使用ajax提交的

  - json发送

    ```jsp
    <script>
        var userList=new Array();
        userList.push({name:'msn'})
        userList.push({name:'mzq'})
    
        $.ajax({
            url:'quick12',
            data:JSON.stringify(userList),
            type:'POST',
            contentType : 'application/json;charset=utf-8'
        })
    </script>
    ```

    

  - 配置spring-mvc.xml

    ```xml
        <mvc:resources mapping="/js/**" location="/js/"/>
    ```

    

  - 测试

    ```java
        @RequestMapping("quick12")
        @ResponseBody
        public void test12(@RequestBody List<User> userList){
            System.out.println(userList);
        }
    ```

    

### 访问静态资源

我们配置的前端控制器中配置的是缺省的servlet,也就是说从jsp发送信息时，一定会经过此servlet，并且寻找requestMapping找对应的地址，但是许多资源，比如图片，jquery文件，我们在引入这些资源时，默认的是寻找经过RequestMapping配置的内容，但是并没有东西配置成类似RequestMapping("js/jquery.js")的，所以会找不到我们需要的内容。

所以需要配置

通用的方式：< mvc:default-servlet-handler > 表示所有找不到的资源都交给tomcat来找

针对某一资源：<mvc:resources mapping="/js/**" location="/js/"/> ：寻找根目录下的js文件夹

### 防止post请求乱码

ajax中，type的类型如果是POST，必须大写

此编码是防止获取请求参数的乱码，对于相应没有效果

在web.xml配置

```xmL

  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

### @RequestParam()★

作用：

​	当请求参数和方法的参数不一致时，可以显示的进行映射

url：http://localhost:8888/springmvc_war_exploded/quick13?username=lisi

```
   @RequestMapping("quick13")
    @ResponseBody
    public void test13( @RequestParam("username")String name){
        System.out.println(name);

    }
```

​	规定请求时不带参数是否报错required=false表示可以不带参数

​	规定默认参数：defaultValue = "zs"

```
  @RequestMapping("quick13")
    @ResponseBody
    public void test13( @RequestParam(value="username", required=false,defaultValue = "zs")String name){
        System.out.println(name);

    }
```

### 自定义类型转换器

springmvc已经帮我们做了一些类型转换，但是有的需要我们自定义

步骤：

①定义转换器类实现Converter接口

②在配置文件中声明转换器

③在<annotation-driven>中引用转换器

- 实现接口 Converter<N, S>S是目标类型

```
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date format = null;
        try {
            format = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }
}
```

- 配置

  

```
 <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <list>
                <bean class="converters.DateConverter"></bean>
            </list>
        </property>
    </bean>
    <mvc:annotation-driven conversion-service="conversionService"/>

```

