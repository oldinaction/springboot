package cn.aezo.springboot.oauth2.sso.client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 测试访问：http://aezocn.local:8081/client1 跳转到认证服务器登录页面(登录后显示client1的页面)
@SpringBootApplication
@Controller
public class Oauth2SsoClient1Application {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "client1");

        SpringApplication.run(Oauth2SsoClient1Application.class, args);
    }

    @GetMapping("/client1")
    public String client1(Authentication user) {
        System.out.println("user = " + user);
        return "client1";
    }
}