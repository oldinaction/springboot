package cn.aezo.springboot.oauth2.youku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// authorization_request_not_found => 测试时客户端和认证服务器不能都使用localhost(会导致SESSIONID对应的cookie被覆盖)。可以修改hosts文件，增加一个域名映射到127.0.0.1
@SpringBootApplication
public class Oauth2YoukuApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2YoukuApplication.class, args);
    }
}