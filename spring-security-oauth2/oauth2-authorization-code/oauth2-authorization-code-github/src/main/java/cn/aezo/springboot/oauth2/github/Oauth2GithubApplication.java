package cn.aezo.springboot.oauth2.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
public class Oauth2GithubApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2GithubApplication.class, args);
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("123 = " + 123);
        return principal;
    }

    @RequestMapping("/login")
    public String login() {
        System.out.println("true = " + true);
        return "login1";
    }
}
