package cn.aezo.springboot.oauth2.jwt.ac.res;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

// 采用jwt非对称加密
@SpringBootApplication
public class Oauth2JwtACResourceApplication {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "res");

        SpringApplication.run(Oauth2JwtACResourceApplication.class, args);
    }

}
