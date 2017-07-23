package cn.aezo.springboot.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class HibernateApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HibernateApplication.class, args);
    }

}
