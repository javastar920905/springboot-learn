# springboot-learn
spring boot 学习示例

## 参考文档
[爱折腾的爪哇猿博客-springboot auth2](http://javastar920905.coding.me/2019/06/spring-boot-auth2/)

## spring boot 基础
### 使用idea新建spring boot 项目
- [ ] maven 配置多模块
> * 新建子模块,选中父项目>new module>spring Initializr >输入模块名> 选中 devtools,lombok,web 依赖> 右键启动main方法就可以了
> * 根据[Spring指南 Getting Started guides,](https://spring.io/guides) 编写hello world
> * application.properties  配置端口号,server.port=8888(**spring-boot-autoconfigure 包下查看模块对应的xxProperties类,可以找到配置项** )
> * 访问url: http://localhost:8888/greeting
> * webmvc mock 单元测试
### spring boot security
- 添加maven 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```   
> * 根据[security 入门指南](https://spring.io/guides/gs/securing-web/) 编写demo
> * 1 配置MvcConfig,先添加一个unSecurity的demo,即不添加security依赖 
> * 2 配置WebSecurityConfig ,添加security依赖,设置Spring Security --todo 测试不通过
     
# 专题 

    
## spring boot auth2