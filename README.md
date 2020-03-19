# springboot-springcloud
搭建springboot+springcloud分布式项目实现增删改查功能

作者：betake
springboot+springcloud 链接：https://www.jianshu.com/p/63665a2f2414
springboot+swagger2 链接：https://www.jianshu.com/p/f8bb599cc80c
来源：简书
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

一、环境
1、jdk1.8

2、IDEA

3、apache maven

4、Spring Boot 2.1.9.RELEASE

5、Spring Cloud Finchley版本

二、服务发现框架 - Eureka
        Eureka是Netflix开发的服务发现框架，本身是一个基于REST的服务，主要用于定位运行在AWS域中的中间层服务，以达到负载均衡和中间层服务故障转移的目的。SpringCloud将它集成在其子项目spring-cloud-netflix中，以实现SpringCloud的服务发现功能。

        Eureka包含两个组件：Eureka Server和Eureka Client。

        Eureka Server提供服务注册服务，各个节点启动后，会在Eureka Server中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到。

        Eureka Client是一个java客户端，用于简化与Eureka Server的交互，客户端同时也就是一个内置的、使用轮询(round-robin)负载算法的负载均衡器。

        在应用启动后，将会向Eureka Server发送心跳,默认周期为30秒，如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，Eureka Server将会从服务注册表中把这个服务节点移除(默认90秒)。

        Eureka Server之间通过复制的方式完成数据的同步，Eureka还提供了客户端缓存机制，即使所有的Eureka Server都挂掉，客户端依然可以利用缓存中的信息消费其他服务的API。综上，Eureka通过心跳检查、客户端缓存等机制，确保了系统的高可用性、灵活性和可伸缩性。

3、服务注册服务- Eureka Server  
1、使用IDEA新建工程


点击next


选择 Cloud Discovery 下的 Eureka Server 组件，点击next


选择Web中的Spring Web和Spring Cloud Discovery中的Eureka Server，点击next


填写项目名和选择项目保存的路径后，点击finish


2、在EurekaServerApplication类中使用 @EnableEurekaServer 来说明项目是一个 Eureka

@SpringBootApplication

@EnableEurekaServer

public class EurekaServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServerApplication.class, args);

    }

}

3、修改配置文件，可以是 application.properties 或 application.yml，后缀不一样，内容差不多，只是格式不一样，使用其中一个文件就可以了，下面列出他们的区别，后面统一使用application.properties

 application.properties 文件的配置

server.port=8001

spring.application.name=eurka-server

eureka.instance.hostname=localhost

eureka.client.register-with-eureka=false

eureka.client.fetch-registry=false

eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

application.yml文件的配置

server:

  port: 8001 #服务端口

spring:

  application:

    name: eurka-server #服务应用名称

eureka:

  instance:

    hostname: localhost

  client:

    registerWithEureka: false #是否将自己注册到Eureka Server，默认为true

    fetchRegistry: false #是否从Eureka Server获取注册信息，默认为true

    service-url:

      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #服务注册的 URL

默认 eureka server 也是一个 eureka client，registerWithEureka: false 和 fetchRegistry: false 来表明项目是一个 Eureka Server。

4.运行 EurekaServerApplication类，启动项目，访问项目 http://localhost:8001/，目前还没有服务


2、服务提供者provider-one
1、使用MySQL建立测试表user

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (

  `id` varchar(10) DEFAULT NULL,

  `username` varchar(64) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('3', '张三');

2、新建工程，命名为：provider-one

选择spring boot组件时，选择Eureka Discovery Client，而不是Eureka Server，SQL组件是用于连接mysql数据库


整个项目的结构如下：


2、ProviderOneApplication启动类增加三个注解

@EnableEurekaClient ：说明这是一个Eureka服务

@SpringBootApplication：表明他是一个springboot项目

@MapperScan("com.spring.providerone.dao")：指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会生成相应的实现类

@SpringBootApplication

@EnableEurekaClient

@MapperScan("com.spring.providerone.dao")

public class ProviderOneApplication {

    public static void main(String[] args) {

    SpringApplication.run(ProviderOneApplication.class, args);

    }

}

3、新建实体类User.java

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    public String getId() {

        return id;

    }

    public void setId(String id) {

    this.id = id;

    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

}

4、新建dao层：UserDao.java

public interface UserDao {

/**

* 新增用户

    * @param user

    */

  void createUser(User user);

/**

* 查询用户列表

    * @return

    */

  List findAllUser();

/**

* 删除用户

    * @return

    */

  void delUser(String id);

/**

* 修改用户

    * @return

    */

  void updateUser(String id,String username);

}

5、新建接口层：IUserService.java

public interface IUserService {

/**

* 新增用户

    * @param user

    */

  public void createUser(User user);

/**

* 查询用户列表

    * @return

    */

  public List findAllUser();

/**

* 删除用户

    * @return

    */

  void delUser(String id);

/**

* 修改用户

    * @return

    */

  void updateUser(String id,String username);

}

6、新建实现层：UserServiceImpl.java

@Service

@Component

public class UserServiceImplimplements IUserService {

@Autowired

  public UserDaouserDao;

@Override

  public void createUser(User user) {

userDao.createUser(user);

}

@Override

  public List findAllUser() {

return userDao.findAllUser();

}

@Override

  public void delUser(String id) {userDao.delUser(id);}

@Override

  public void updateUser(User user) {userDao.updateUser(user);}

}

7、新建控制层：UserController .java

@RestController

@RequestMapping("/user")

public class UserController {

@Autowired

  private IUserServiceservice;

@RequestMapping("/userList")

public List getUserList(){

return service.findAllUser();

}

@RequestMapping("/add")

public String addUser(@RequestBody User user){

if(user!=null){

service.createUser(user);

return "success";

}else{

return "error";

}

}

@RequestMapping("/delUser")

public String delUser(@RequestParam String id){

try {

service.delUser(id);

return "del success";

}catch (Exception e ){

e.printStackTrace();

return "del false";

}

}

@RequestMapping("/updateUser")

public String updateUser(@RequestBody User user){

try {

service.updateUser(user);

return "update success";

}catch (Exception e ){

e.printStackTrace();

return "update false";

}

}

//测试方法，返回服务器端口，以判断是访问哪个服务

  @Value("${server.port}")

Stringport;

@RequestMapping("/hi")

public String home(@RequestParam(value ="name", defaultValue ="zhangsan") String name) {

return "hi " + name +" ,i am from port:" +port;

}

}

8、配置文件修改：application.properties

注：serverTimezone=UTC，MySQL jdbc 6.0 版本以上须配置此参数，用户名和密码改为你本地的用户名和密码服务之间的调用都是根据spring.application.name=provider-user中的值：provider-user

spring.datasource.url=jdbc:mysql://localhost:3306/garbage?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false

spring.datasource.username=lch

spring.datasource.password=123456

spring.datasource.driver-class-name=com.mysql.jdbc.Driver

mybatis.typeAliasesPackage=com.spring.*.*.entity

mybatis.mapperLocations=classpath:mapper/*.xml

server.port=8002

spring.application.name=provider-user

eureka.client.service-url.defaultZone=http://localhost:8001/eureka/

9、在resources下新增mapper文件夹，这个文件夹名称对应application.properties文件中mybatis.mapperLocations=classpath的配置，mapper文件夹下添加映射文件：UserDaoMapper.xml

<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.spring.providerone.dao.UserDao">

<resultMap id="BaseResultMap" type="com.spring.providerone.entity.User">

<result column="id" property="id" />

<result column="username" property="username" />

</resultMap>

<parameterMap id="User" type="com.spring.providerone.entity.User"/>

    <sql id="Base_Column_List">

        id, username

    </sql>

    <select id="findAllUser" resultMap="BaseResultMap" >

        select

        <include refid="Base_Column_List" />

        from user

    </select>

    <insert id="createUser" parameterMap="User" useGeneratedKeys="true" keyProperty="id">

        insert into

        user(id,username)

        values

        (#{id},#{username})

    </insert>

    <delete id="delUser">

        delete from user where id=#{id}

    </delete>

    <update id="updateUser">

        update user set username=#{username} where id=#{id}

    </update>

</mapper>

3、服务提供者provider-two
        新建一个新的服务，相当于一个小集群，工程跟provider-one差不多，新建provider-two工程，其他文件可复制过来，注意UserDaoMapper.xml文件中引用的包名也需要改过来，然后修改application.properties：

server.port：8003

最后启动provider- two服务

这是去eureka服务注册中心查看，就有两个服务被注册了


4、服务消费者 - Ribbon | Feign
Spring Cloud 两种调用服务的方式，ribbon + restTemplate，和 feign。

4.1、Ribbon+Hystrix
ribbon 实现了负载均衡，策略默认为轮询

Hystrix：断路器，

1、新建工程，命名为：consumer-ribbon，选择组件Spring Web，Spring Cloud Discovery中的Eureka Client、Routing中Ribbon和Spring Cloud Circuit Breaker 中的Hystrix


2、application.properties配置

server.port=8004

spring.application.name=consumer-ribbon

eureka.client.service-url.defaultZone=http://localhost:8001/eureka/

3、启动类新增@EnableEurekaClient注解，加 @EnableHystrix 注解开启Hystrix，向 Spring 注入一个 bean: restTemplate，并添加 @LoadBalanced 注解，表明这个 restTemplate 开启负载均衡功能

@SpringBootApplication

@EnableEurekaClient

@EnableHystrix

public class ConsumerRibbonApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConsumerRibbonApplication.class, args);

    }

    @Bean

    @LoadBalanced

    RestTemplate restTemplate() {

        return new RestTemplate();

    }

}

4、实体类User.java，把服务器的实体类复制过来，放到com.spring.consumerribbon.entity包下

5、新建服务层service：UserService

PROVIDER-USER为注册服务里的名称，用来代替具体的URL，来调用/user/hi接口，

RestTemplate详解，有兴趣可以看看：https://my.oschina.net/u/3177357/blog/2239749

@Service

public class UserService {

@Autowired

    RestTemplaterestTemplate;

public String hiService(String name) {

return restTemplate.postForObject("http://PROVIDER-USER/user/hi?name="+name,null,String.class);

}

@HystrixCommand(fallbackMethod ="userListError")

public List userList() {

return restTemplate.postForObject("http://PROVIDER-USER/user/userList","",List.class);

}

@HystrixCommand(fallbackMethod ="addUserError")

public String addUser(User user) {

return restTemplate.postForEntity("http://PROVIDER-USER/user/add",user,String.class).getBody();

}

@HystrixCommand(fallbackMethod ="updateUserError")

public String updateUser(User user){

return restTemplate.postForEntity("http://PROVIDER-USER/user/updateUser",user,String.class).getBody();

}

@HystrixCommand(fallbackMethod ="delUserError")

public String delUser(String id){

return restTemplate.postForObject("http://PROVIDER-USER/user/delUser?id="+id,null,String.class);

}

public String hiError(String  name) {

return "服务器开了小差，请休息一会再试！";

}

public List userListError() {

return null;

}

public String addUserError(User user){return "服务器开了小差，请休息一会再试！";}

public String updateUserError(User user){return "服务器开了小差，请休息一会再试！";}

public String delUserError(String id){return "服务器开了小差，请休息一会再试！";}

}

6、新建控制层controller：UserControler

@RestController

public class UserControler {

@Autowired

    UserServiceuserService;

@RequestMapping(value ="/hi")

public String hi(@RequestParam String name){

return userService.hiService(name);

}

@RequestMapping(value ="/userList")

public List userList(){

return userService.userList();

}

@RequestMapping(value ="/addUser")

public String addUser(@RequestParam String id,String username){

User user =new User();

user.setId(id);

user.setUsername(username);

return userService.addUser(user);

}

@GetMapping(value ="/updateUser")

public String updateUser(@RequestParam String id,String username){

User user =new User();

user.setId(id);

user.setUsername(username);

return userService.updateUser(user);

}

@GetMapping(value ="/delUser")

public String delUser(@RequestParam String id){return userService.delUser(id);}

}

6、启动consumer-ribbon工程

多次请求hi接口：http://localhost:8004/hi?name=test，下面信息会交替出现，说明负载均衡已经起作用了：

hi test ,i am from port:8002

hi test ,i am from port:8003

访问：http://localhost:8004/addUser?id=2&username=lisi，新增一条数据

success

再次访问：http://localhost:8004/userList， 出现以下信息说明新增成功！

[{"id":"3","username":"张三"},{"id":"2","username":"lisi"}]

访问：http://localhost:8004/updateUser?id=2&username=李四，修改数据

update success

访问：http://localhost:8004/delUser?id=2，删除数据

del success

4.2、Feign+Hystrix
1、新建一个Spring Boot工程，命名为：consumer-feign，组件选择如下


创建完工程后，pom.xml依赖如下：


2、application.properties配置。Feign 自带断路器，它没有默认打开，需要在配置文件中配置打开，feign.hystrix.enabled=true

server.port=8005

spring.application.name=consumer-feign

eureka.client.service-url.defaultZone=http://localhost:8001/eureka/

feign.hystrix.enabled=true

3、启动类ConsumerFeignApplication上添加 @EnableEurekaClient 和 @EnableFeignClients 注解

@SpringBootApplication

@EnableEurekaClient

@EnableFeignClients

public class ConsumerFeignApplication {

    public static void main(String[] args) {

        SpringApplication.run(ConsumerFeignApplication.class, args);

    }

}

4、增加实体类user.java

public class Userimplements Serializable {

    private static final long serialVersionUID =1L;

    private Stringid;

    private Stringusername;

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

}

5、增加断路实现类：HystricService.java

@Component

public class HystricServiceimplements UserService{

@Override

    public String sayHiFromClientOne(String name) {

return "服务器开了小差，请休息一会再试！";

}

@Override

    public List getUserList(){

return null;

}

@Override

    public String addUser(User user){return "服务器开了小差，请休息一会再试！";}

@Override

    public String updateUser(User user){return "服务器开了小差，请休息一会再试！";}

@Override

    public String delUser(String id){return "服务器开了小差，请休息一会再试！";}

}

6、新增service服务：UserService.java

        通过@ FeignClient（“服务名”），来指定调用哪个服务，@GetMapping("接口名")，来向接口发送 Get 请求，@RequestParam 是请求参数

        fallback指向熔断实现类HystricService.java，当服务不可用时会执行熔断实现类

@FeignClient(value ="provider-user",fallback = HystricService.class)

public interface  UserService {

@GetMapping("/user/hi")

String sayHiFromClientOne(@RequestParam(value ="name") String name);

@GetMapping("/user/userList")

List getUserList();

@GetMapping("/user/add")

String addUser(User user);

@GetMapping("/user/updateUser")

String updateUser(User user);

@GetMapping("/user/delUser")

String delUser(@RequestParam String id);

}

7、增加controller类：UserController.java

@RestController

public class UserController {

@Autowired

    UserServiceuserService;

@GetMapping(value ="/hi")

public String sayHi(@RequestParam String name) {

return userService.sayHiFromClientOne( name );

}

@GetMapping(value ="/userList")

public List userList() {

return userService.getUserList();

}

@GetMapping(value ="/addUser")

public String addUser(@RequestParam String id,String username){

User user =new User();

user.setId(id);

user.setUsername(username);

return userService.addUser(user);

}

@GetMapping(value ="/updateUser")

public String updateUser(@RequestParam String id,String username){

User user =new User();

user.setId(id);

user.setUsername(username);

return userService.updateUser(user);

}

@GetMapping(value ="/delUser")

public String delUser(@RequestParam String id){return userService.delUser(id);}

}

8、启动consumer-feign项目，访问http://localhost:8005/hi?name=test,以下信息交替出现，说明已经启动负载均衡了

hi test ,i am from port:8002

hi test ,i am from port:8003

访问：http://localhost:8005/addUser?id=2&username=lisi ，新增一条数据

success

再次访问：http://localhost:8005/userList， 出现以下信息说明新增成功！

[{"id":"3","username":"张三"},{"id":"2","username":"lisi"}]

访问：http://localhost:8005/updateUser?id=2&username=李四，修改数据

update success

访问：http://localhost:8005/delUser?id=2，删除数据

del success

9、把两个服务停止，再去访问http://localhost:8005/hi?name=test，显示以下信息说明断路器已经生效了

服务器开了小差，请休息一会再试！

到此springcloud演示到此结束！

源码链接：https://pan.baidu.com/s/1fN_abeQ-QPIRjpblUmvgeA

提取码：4y72

