package cn.aezo.springboot.oauth2.sso.client1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestResourceController {
    // 测试资源服务器
    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @Bean
    OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext){
        return new OAuth2RestTemplate(new AuthorizationCodeResourceDetails(), oAuth2ClientContext);
    }

    @RequestMapping("/info")
    public String home(Authentication authentication) {
        System.out.println("authentication = " + authentication);
        String toke = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        System.out.println("toke = " + toke);
        String result = oAuth2RestTemplate.getForObject("http://localhost:8080/qq/info?access_token=" + toke, String.class);
        System.out.println("result = " + result);
        return result;
    }

    @RequestMapping("/fans")
    public String fans(Authentication authentication) {
        String toke = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        System.out.println("fans toke = " + toke);
        String result = oAuth2RestTemplate.getForObject("http://localhost:8080/qq/fans?access_token=" + toke, String.class);
        System.out.println("result = " + result);
        return result;
    }

    // 请求认证服务器(资源服务器)的资源
    @RequestMapping("/res")
    public String res(Authentication authentication) {
        String toke = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        String result = oAuth2RestTemplate.getForObject("http://localhost:8080/res?access_token=" + toke, String.class);
        return result;
    }

    // 请求资源服务器数据
    @RequestMapping("/res/read")
    public String read(Authentication authentication) {
        String toke = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        // 普通new RestTemplate()也可获取数据
        String result = new RestTemplate().getForObject("http://localhost:8083/api/read?id=1&access_token=" + toke, String.class);
        return result;
    }
    @RequestMapping("/res/write")
    public String write(Authentication authentication) {
        String toke = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue(); // 获取的是客户端token
        String result = oAuth2RestTemplate.postForObject("http://localhost:8083/api/write/1?access_token=" + toke, null, String.class);
        return result;
    }
}
