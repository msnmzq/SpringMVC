[TOC]



本文主要讲了两个问题：

- spring写web文件时，会创建多个应用上下文：将其放在servletContext中。封装体现：spring-web坐标，配置<context-params></context-params>以及监听器<listeners></listeners>WebApplicationContextUtils
- springmvc
  - 针对的是servlet。servlet中有一些共同的功能，比如接收数据，跳转等。springmvc将这部分内容封装，我们只需要写一pojo类即可

### 纯spring实现web项目

步骤：

1.创建一个maven的web工程

2.完善工程目录,导入坐标

```
<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.2.7.RELEASE</version>
</dependency>
<dependency>
  <groupId>javax.servlet.jsp</groupId>
  <artifactId>javax.servlet.jsp-api</artifactId>
  <version>2.2.1</version>
</dependency>
<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>javax.servlet-api</artifactId>
  <version>3.1.0</version>
</dependency>
```

3.书写dao,service

4.书写servlet:servlet中通过classpathxmlapplicationcontext读取配置文件，getbean的方式获取service对象

注意：配置文件的路径，如果实在resources文件夹下，需要写成"classpath:路径"

5.配置tomcat,然后启动

**<u>注意点：</u>**

*不要忘记导入三个坐标*

*不要忘记spring使用set方法注入属性，需要提供不带参数的构造函数*

*如果注入的属性是对象，则使用ref属性*

### spring集成web的封装实现原理

- 知识储备

  - web开发四大域之ServletContext域

    作用范围：整个web程序

    特点：同一个web程序的所有servlet使用同一个servletContext域

  - web.xml配置全局参数

    ```
    	<context-param>
            <param-name>参数名</param-name>
            <param-value>参数值</param-value>
        </context-param>
    ```

  - spring注解：web层的只能使用Controller注解，普通的java类创建对象可以使用另外三种

    - | @Component  |     使用在类上用于实例化Bean      |
      | ----------- | :-------------------------------: |
      | @Controller |   使用在web层类上用于实例化Bean   |
      | @Service    | 使用在service层类上用于实例化Bean |
      | @Repository |   使用在dao层类上用于实例化Bean   |

- 纯spring有什么缺点？

  - 浪费资源：通过spring的学习我们知道，new ClassPathXmlApplicationContext的作用是创建上下文对象，但是每次创建对象时都需要加载配置文件，创建上下文对象，导致配置文件被加载多次，上下文对象多次被修改。但是我们只需要一个应用上下文便可以实现后面的功能。

- 解决方案：

  - 当spring是单例时，如果我们在项目启动时便创建一个ApplicationContext，并且把它存放在ServletContext域中，每次使用不用再次创建，只要取出来就可以使用，那么便可以节省资源和时间。

- 具体代码实现

  - 为什么用监听器：ServletContextListener监听器可以监听项目是否启动，如果启动了，则执行初始化方法中的内容

  - 监听器代码逻辑：

    - 实现ServletContextListener接口
    - 复写初始化方法：获取ServletContext域，通过ClassPathXmlApplicationContext获取应用上下文，将上下文存储在ServletContext域中
    - web.xml中配置监听器

  - 优化方法：

    - 获取应用上下文时需要提供配置文件的地址，直接写耦合度高，所以将此内容配置在web.xml
    - 在servlet中，我们需要记住应用上下文的名字，由于ServletContext中只有一个ApplicationContext,,所以我们希望通过ServletContext便可以获得ApplicationContext，所以封装一个工具类WebAppplicationContextUtils

    ```java
    public class ContextLoaderListener implements ServletContextListener {
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContext servletContext = sce.getServletContext();
            //获取全局初始化参数
            String c = servletContext.getInitParameter("applicationConfig");
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(c);
            servletContext.setAttribute("app",applicationContext);
        }
    
        @Override
        public void contextDestroyed(ServletContextEvent sce) {}
    }
    ```

    ```java
     <listener>
        <listener-class>com.itheima.listener.ContextLoaderListener</listener-class>
      </listener>
    
      <context-param>
        <param-name>applicationConfig</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
      </context-param>
    ```

    ```java
    public class WorkApplicationContextUtils {
        public static ApplicationContext getApplicationContext(ServletContext servletContext){
            ApplicationContext app = (ApplicationContext) servletContext.getAttribute("app");
            return app;
        }
    ```

    

### spring集成web环境

**主要**：记住配置的固定的名字

- 导入坐标：spring-web
- 配置web.xml：**tomcat启动后，便会加载spring容器，才可以使用spring中的bean**
  
  - ```xml
    
      <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
      </context-param>
        
      <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
      </listener>
    ```
- 客户端测试:使用WebApplicationContextUtils类获取应用存储在ServletContext域上下文

### springmvc快速入门

#### 步骤

![](F:\JAVA学习资料\黑马\2020黑马出品-java\零基础+就业课(2.1)新版\04-就业课(2.1)-SpringMVC\04-就业课(2.1)-SpringMVC\day01_Spring集成web开发环境\笔记\01-SpringMVC入门步骤.png)

理解：

Tomcat服务器包括两部分：tomcat引擎部分（用来进行逻辑操作）tomcat的web应用部分。引擎部分首先接收客户端的请求，然后（从Http协议说起，当你发一个请求到服务端的时候，你会把一些信息交给服务器，比如你的语言，session-id等基本信息，然后再加上比如你提交表单时填上的数据。所有所有这些数据，进入服务端后全被封装在Request里了，所以你可以在Request里很方便地获取到各种信息了）。得到客户端提供的各种信息之后，tomcat就需要找具体的类代码（servlet）。

- **导入坐标：spring-webmvc:含有前端控制器**，不能导入spring-context

- **web.xml中配置前端控制器**DispatcherServlet

  - ```xml
    <!--  配置前端控制器-->
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
      </servlet>
      <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
      </servlet-mapping>
    ```

- **创建controller和视图**

  - 视图即jsp或者html
  - com.itheima.controller：java类，具体要使用的方法。 方法的返回值表示要跳转的jsp文件名

- **配置controller**: 将其放在spring容器中

  - ```java
    @Controller<!--不能使用Component-->
    public class UserController {
        @RequestMapping("/quick")//请求映射，当访问quick时，便会执行save方法
        public String save(){
            System.out.println("com.itheima.controller save running");
            return "success.jsp";
        }
    
    ```

    

- **在spring-mvc.xml中配置组件扫描**：配置controller后，它是独立的，需要和spring相关联

- **将spring-mvc文件配置到servlet中**

  - ```xml
      <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>    <!--启动服务器便加载-->
      </servlet>
    ```

    
    
    

#### 出错的点

1、不要忘记配置Dispatcher的时候，要配置组件扫描的配置文件

2、可以不配置监听器，只配置上述步骤中的内容

### SpringMVC的工作原理（组件角度）

##### 原理

##### 组件

- Controller

- RequestMapping
  - 适用于：方法和类
  
    - 当用于方法上时：访问的url为http://localhost:8888+设置的applicationContext值/注解的值
  
      举例：访问的url为：http://localhost:8888/quick
  
      ```java
      @Controller
      public class UserController {
          @RequestMapping("/quick")
          public String test(){
              return "success.jsp";
          }
      }
      ```
  
      
  
    - 当用于类上时：访问的url为http://localhost:8888+设置的applicationContext值/类上的注解值/方法上的注解值
  
      举例：访问的url为：http://localhost:8888/user/quick
  
      ```
      @Controller
      @RequestMapping("/user")
      public class UserController {
          @RequestMapping("/quick")
          public String test(){
              return "/success.jsp";
          }
      }
      ```
  
  - 注意返回值表示的路径：
  
    - 当返回值为文件名时，表示'类上的注解值/文件名',如果文件在根目录下，返回值应该是  ‘/文件名'
  
  - 属性：
  
    - value
  
    - method(其值为枚举类型) ：method = RequestMethod.GET![](C:\Users\18654\Desktop\QQ截图20200927121936.png)
  
    - params（对请求的url的参数做限定）params = {"username"}正确的请求方式：http://localhost:8888/user/quick?username=xxx![](C:\Users\18654\Desktop\QQ截图20200927122133.png)
  
      ```
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
      ```
  
- 组件扫描：有空再说

- spring-mvc.xml配置资源解析器

  - 应用场景：跳转时，目标文件所在的文件夹地址可能会变。如果不做配置的话，每次都要修改controller文件代码，修改后只需要修改配置文件

  - ```xml
     <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <property name="prefix" value="/jsp/"></property>
            <property name="suffix" value=".jsp"></property>
      </bean>
    ```

    

​		