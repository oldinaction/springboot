package cn.aezo.springboot.oauth2.github.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Order(101)
@Configuration
public class MyWebSecurityConfig2 extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll()
            .anyRequest().authenticated();
    }
}
