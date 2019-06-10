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
- 为什么要使用spring security
  - 1 在前文的介绍中，Spring Security支持防止csrf攻击，session-fixation protection，支持表单认证，basic认证，rememberMe…等等一些特性，有很多是开箱即用的功能，而大多特性都可以通过配置灵活的变更，这是它的强大之处。
  - 2 Spring Security的兄弟的项目Spring Security SSO，OAuth2等支持了多种协议，而这些都是基于Spring Security的，方便了项目的扩展。
  - 3 SpringBoot的支持，更加保证了Spring Security的开箱即用。
  - 4 为什么需要理解其内部工作原理?一个有自我追求的程序员都不会满足于浅尝辄止，如果一个开源技术在我们的日常工作中十分常用，那么我偏向于阅读其源码，这样可以让我们即使排查不期而至的问题，也方便日后需求扩展。
  - 5 Spring及其子项目的官方文档是我见过的最良心的文档！相比较于Apache的部分文档

这一节，为了对之前分析的Spring Security源码和组件有一个清晰的认识，介绍一个使用IP完成登录的简单demo。
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
> * 根据[security 入门指南](http://www.spring4all.com/article/429) 编写demo
> * 1 配置MvcConfig,先添加一个unSecurity的demo,即不添加security依赖 
> * 假设你希望防止未经授权的用户访问“/ hello”。 此时，如果用户点击主页上的链接，他们会看到问候语，请求被没有被拦截。 你需要添加一个障碍，使得用户在看到该页面之前登录。
> * 2 配置WebSecurityConfig ,添加security依赖,设置Spring Security 

- [security 剩余教程](http://www.spring4all.com/article/428) 
> * [《Spring-Security-入门（二）：基于数据库验证》](http://www.spring4all.com/article/420) Spring Security 校验流程图,源码分析得出 userDetails 是由 UserDetailsService 的 #loadUserByUsername(username) 来获取的  
> * [Spring Security 源码分析系列](http://www.spring4all.com/article/428)    

# 专题 
## [Spring Security 源码分析系列](http://www.spring4all.com/article/428)   
* Architecture First和Code First。前者致力于让读者先了解整体的架构，方便我们对自己的认知有一个宏观的把控，而后者以特定的demo配合讲解，可以让读者在解决问题的过程中顺便掌握一门技术。
- [x] 介绍一些在Spring Security中常见且核心的Java类，它们之间的依赖，构建起了整个框架。想要理解整个架构，最起码得对这些类眼熟。
- [x] 《Spring Security(二)—Guides》 官方入门指引,翻译
- [ ] [《Spring Security(三)—核心配置解读》](http://www.spring4all.com/article/446) @EnableWebSecurity,HttpSecurity常用配置 
- [ ] [《Spring Security(四)—核心过滤器源码分析》](http://www.spring4all.com/article/447) 们关注了Spring Security是如何完成认证工作的，但是另外一部分核心的内容：过滤器，一直没有提到，我们已经知道Spring Security使用了springSecurityFillterChian作为了安全过滤的入口
- [ ] 《Spring Security(五)—动手实现一个IP_Login》
- [ ] 《Spring Security源码分析一：Spring Security认证过程》
- [ ] 《Spring Security源码分析二：Spring Security授权过程》
- [ ] 《Spring Security源码分析三：Spring Social实现QQ社交登录》
- [ ] 《Spring Security源码分析四：Spring Social实现微信社交登录 》
- [ ] 《Spring Security源码分析五：Spring Security实现短信登录》
- [ ] 《Spring Security源码分析六：Spring Social社交登录源码解析》
- [ ] 《Spring Security源码分析七：Spring Security 记住我》
- [ ] 《Spring Security源码分析八：Spring Security 退出》

###  介绍一些在Spring Security中常见且核心的Java类
* 1.1 SecurityContextHolder  用于存储安全上下文（security context）的信息。当前操作的用户是谁，该用户是否已经被认证，他拥有哪些角色权限…
> SecurityContextHolder默认使用ThreadLocal 策略来存储认证信息。看到ThreadLocal 也就意味着，这是一种与线程绑定的策略。Spring Security在用户登录时自动绑定认证信息到当前线程，在用户退出时，自动清除当前线程的认证信息。    
 * 获取当前用户的信息
```
因为身份信息是与线程绑定的，所以可以在程序的任何地方使用静态方法获取用户信息。一个典型的获取当前登录用户的姓名的例子如下所示：  

Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

if (principal instanceof UserDetails) {
String username = ((UserDetails)principal).getUsername();
} else {
String username = principal.toString();
}
getAuthentication()返回了认证信息，再次getPrincipal()返回了身份信息，UserDetails便是Spring对身份信息封装的一个接口。Authentication和UserDetails的介绍在下面的小节具体讲解，本节重要的内容是介绍SecurityContextHolder这个容器。     

```
* 1.2 Authentication  先看看这个接口的源码长什么样
```
public interface Authentication extends Principal, Serializable {//直接继承自Principal类，而Principal是位于java.security包中的。可以见得，Authentication在spring security中是最高级别的身份/认证的抽象。
    Collection<? extends GrantedAuthority> getAuthorities();//权限信息列表， 由这个顶级接口，我们可以得到用户拥有的权限信息列表，密码，用户细节信息，用户身份信息，认证信息。

    Object getCredentials();//密码信息，用户输入的密码字符串，在认证过后通常会被移除，用于保障安全。

    Object getDetails();//细节信息，web应用中的实现接口通常为 WebAuthenticationDetails，它记录了访问者的ip地址和sessionId的值。

    Object getPrincipal();//敲黑板！！！最重要的身份信息，大部分情况下返回的是UserDetails接口的实现类

    boolean isAuthenticated();

    void setAuthenticated(boolean var1) throws IllegalArgumentException;
}
```
* Spring Security是如何完成身份认证的？
  * 1 用户名和密码被过滤器获取到，封装成Authentication,通常情况下是UsernamePasswordAuthenticationToken这个实现类。
  * 2 AuthenticationManager 身份管理器负责验证这个Authentication
  * 3 认证成功后，AuthenticationManager身份管理器返回一个被填充满了信息的（包括上面提到的权限信息，身份信息，细节信息，但密码通常会被移除）Authentication实例。
  * 4 SecurityContextHolder安全上下文容器将第3步填充了信息的Authentication，通过SecurityContextHolder.getContext().setAuthentication(…)方法，设置到其中。

* 1.3 AuthenticationManager  
> 初次接触Spring Security的朋友相信会被AuthenticationManager，ProviderManager ，AuthenticationProvider …这么多相似的Spring认证类搞得晕头转向，但只要稍微梳理一下就可以理解清楚它们的联系和设计者的用意。AuthenticationManager（接口）是认证相关的核心接口，也是发起认证的出发点，因为在实际需求中，我们可能会允许用户使用用户名+密码登录，同时允许用户使用邮箱+密码，手机号码+密码登录，甚至，可能允许用户使用指纹登录（还有这样的操作？没想到吧），所以说AuthenticationManager一般不直接认证，AuthenticationManager接口的常用实现类ProviderManager 内部会维护一个List<AuthenticationProvider>列表，存放多种认证方式，实际上这是委托者模式的应用（Delegate）。也就是说，核心的认证入口始终只有一个：AuthenticationManager，不同的认证方式：用户名+密码（UsernamePasswordAuthenticationToken），邮箱+密码，手机号码+密码登录则对应了三个AuthenticationProvider。这样一来四不四就好理解多了？熟悉shiro的朋友可以把AuthenticationProvider理解成Realm。在默认策略下，只需要通过一个AuthenticationProvider的认证，即可被认为是登录成功。
```
只保留了关键认证部分的ProviderManager源码：

public class ProviderManager implements AuthenticationManager, MessageSourceAware,
        InitializingBean {

    // 维护一个AuthenticationProvider列表
    private List<AuthenticationProvider> providers = Collections.emptyList();

    public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {
       Class<? extends Authentication> toTest = authentication.getClass();
       AuthenticationException lastException = null;
       Authentication result = null;

       // 依次认证
       for (AuthenticationProvider provider : getProviders()) {
          if (!provider.supports(toTest)) {
             continue;
          }
          try {
             result = provider.authenticate(authentication);

             if (result != null) {
                copyDetails(authentication, result);
                break;
             }
          }
          ...
          catch (AuthenticationException e) {
             lastException = e;
          }
       }
       // 如果有Authentication信息，则直接返回
       if (result != null) {
            if (eraseCredentialsAfterAuthentication
                    && (result instanceof CredentialsContainer)) {
                   //移除密码
                ((CredentialsContainer) result).eraseCredentials();
            }
             //发布登录成功事件
            eventPublisher.publishAuthenticationSuccess(result);
            return result;
       }
       ...
       //执行到此，说明没有认证成功，包装异常信息
       if (lastException == null) {
          lastException = new ProviderNotFoundException(messages.getMessage(
                "ProviderManager.providerNotFound",
                new Object[] { toTest.getName() },
                "No AuthenticationProvider found for {0}"));
       }
       prepareException(lastException, authentication);
       throw lastException;
    }
}
ProviderManager 中的List<AuthenticationProvider>，会依照次序去认证，认证成功则立即返回，若认证失败则返回null，下一个AuthenticationProvider会继续尝试认证，如果所有认证器都无法认证成功，则ProviderManager 会抛出一个ProviderNotFoundException异常。

到这里，如果不纠结于AuthenticationProvider的实现细节以及安全相关的过滤器，认证相关的核心类其实都已经介绍完毕了：身份信息的存放容器SecurityContextHolder，身份信息的抽象Authentication，身份认证器AuthenticationManager及其认证流程。姑且在这里做一个分隔线。下面来介绍下AuthenticationProvider接口的具体实现。
```  

* 1.4 DaoAuthenticationProvider  AuthenticationProvider最最最常用的一个实现便是DaoAuthenticationProvider。
> 按照我们最直观的思路，怎么去认证一个用户呢？用户前台提交了用户名和密码，而数据库中保存了用户名和密码，认证便是负责比对同一个用户名，提交的密码和保存的密码是否相同便是了。在Spring Security中。提交的用户名和密码，被封装成了UsernamePasswordAuthenticationToken，而根据用户名加载用户的任务则是交给了UserDetailsService，在DaoAuthenticationProvider中，对应的方法便是retrieveUser，虽然有两个参数，但是retrieveUser只有第一个参数起主要作用，返回一个UserDetails。还需要完成UsernamePasswordAuthenticationToken和UserDetails密码的比对，这便是交给additionalAuthenticationChecks方法完成的，如果这个void方法没有抛异常，则认为比对成功。比对密码的过程，用到了PasswordEncoder和SaltSource，密码加密和盐的概念相信不用我赘述了，它们为保障安全而设计，都是比较基础的概念。
  
  如果你已经被这些概念搞得晕头转向了，不妨这么理解DaoAuthenticationProvider：它获取用户提交的用户名和密码，比对其正确性，如果正确，返回一个数据库中的用户信息（假设用户信息被保存在数据库中）。
  
* 1.5 UserDetails与UserDetailsService
```$xslt
上面不断提到了UserDetails这个接口，它代表了最详细的用户信息，这个接口涵盖了一些必要的用户信息字段，具体的实现类对它进行了扩展。

public interface UserDetails extends Serializable {

   Collection<? extends GrantedAuthority> getAuthorities();

   String getPassword();

   String getUsername();

   boolean isAccountNonExpired();

   boolean isAccountNonLocked();

   boolean isCredentialsNonExpired();

   boolean isEnabled();
}

它和Authentication接口很类似，比如它们都拥有username，authorities，区分他们也是本文的重点内容之一。
Authentication的getCredentials()与UserDetails中的getPassword()需要被区分对待，前者是用户提交的密码凭证，后者是用户正确的密码，认证器其实就是对这两者的比对。
Authentication中的getAuthorities()实际是由UserDetails的getAuthorities()传递而形成的。还记得Authentication接口中的getUserDetails()方法吗？其中的UserDetails用户详细信息便是经过了AuthenticationProvider之后被填充的。

public interface UserDetailsService {
   UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

UserDetailsService和AuthenticationProvider两者的职责常常被人们搞混，关于他们的问题在文档的FAQ和issues中屡见不鲜。
记住一点即可，敲黑板！！！UserDetailsService只负责从特定的地方（通常是数据库）加载用户信息，仅此而已，记住这一点，可以避免走很多弯路。
UserDetailsService常见的实现类有JdbcDaoImpl，InMemoryUserDetailsManager，前者从数据库加载用户，后者从内存中加载用户，也可以自己实现UserDetailsService，通常这更加灵活
```
  
## spring boot auth2