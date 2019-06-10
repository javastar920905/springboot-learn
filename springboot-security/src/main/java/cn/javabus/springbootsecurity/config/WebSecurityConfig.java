package cn.javabus.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ouzhx on 2019/6/6.
 * WebSecurityConfigurerAdapter 提供了一种便利的方式去创建 WebSecurityConfigurer的实例，只需要重写 WebSecurityConfigurerAdapter 的方法，即可配置拦截什么URL、设置什么权限等安全控制
 */
@Configuration
@EnableWebSecurity//启用Spring Security的Web安全支持
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 定义了哪些URL路径应该被保护，哪些不应该
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()//配置为不需要任何身份验证。
                .anyRequest().authenticated()//所有其他路径必须经过身份验证。
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //There is no PasswordEncoder mapped for the id "null" 问题解决 https://blog.csdn.net/canon_in_d_major/article/details/79675033
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //它将单个用户设置在内存中。该用户的用户名为“user”，密码为“password”，角色为“USER”。
        auth
                .inMemoryAuthentication().passwordEncoder(passwordEncoder)
                .withUser("user").password(passwordEncoder.encode("password")).roles("USER");
    }
}
