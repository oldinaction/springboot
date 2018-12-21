package cn.aezo.springboot.oauth2.jwt.ac.res.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// 资源
@RestController
public class TestEndpoints {
    @Autowired
    DefaultTokenServices tokenServices;

    // 未受保护的资源
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    // 受保护的资源(在Oauth2ServerConfig中配置的)
    // 获取token后访问才会显示资源：http://localhost:8080/order/1?access_token=b7236239-7dee-404d-9e7d-14a035052be9
    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id, String access_token, Authentication authentication) {
        System.out.println("authentication = " + authentication);
        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication2 = " + authentication2);

        // 获取token中额外信息
        OAuth2AccessToken oAuth2AccessToken = tokenServices.readAccessToken(access_token);
        System.out.println("additionalInformation = " + oAuth2AccessToken.getAdditionalInformation());
        return "order id : " + id;
    }

}
