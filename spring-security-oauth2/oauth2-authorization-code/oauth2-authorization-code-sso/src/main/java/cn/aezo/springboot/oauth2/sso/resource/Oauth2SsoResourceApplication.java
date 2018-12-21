package cn.aezo.springboot.oauth2.sso.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Oauth2SsoResourceApplication {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "resource");

        SpringApplication.run(Oauth2SsoResourceApplication.class, args);
    }

}