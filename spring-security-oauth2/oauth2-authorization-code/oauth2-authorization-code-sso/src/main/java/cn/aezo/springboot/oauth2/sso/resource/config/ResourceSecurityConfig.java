package cn.aezo.springboot.oauth2.sso.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启在方法上配置@PreAuthorize("#oauth2.hasScope('read')")等
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {

    /**
     * 获取权限验证配置
     * 1.对HttpSecurity进行配置时存在先后顺序(LinkedHashMap存储. 底层getAttributes匹配到一个路径就返回)
     * 2.authorizeRequests().anyRequest().authenticated()表示
     * **所有请求**只要认证就通过(对应的的内置配置为#oauth2.throwOnError(authenticated)),
     * 因此最好放在最后, 否则后面的access相关配置将失效
     * 3.如对HttpSecurity配置通过后, 当执行的方法有方法级别的权限控制则还会再调用一次decide检查
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/webjars/**").permitAll()
                // 只有GET类型的此路径才匹配
                .antMatchers(HttpMethod.GET, "/api/read/**").access("#oauth2.hasScope('read')")
                // 匹配所有HTTP请求类型
                .antMatchers("/api/write/**").access("hasRole('ROLE_USER') and #oauth2.hasScope('write')")
                // 一般放在最后
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
}