package cn.aezo.springboot.thymeleafsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class ThymeleafSecurityApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ThymeleafSecurityApplication.class, args);
    }

}
