package cn.aezo.springboot.oauth2.sso.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class Oauth2SsoClient2Application {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "client2");

        SpringApplication.run(Oauth2SsoClient2Application.class, args);
    }


    @GetMapping("/client2")
    public String client2(Authentication user) {
        System.out.println("user = " + user);
        return "client2";
    }
}