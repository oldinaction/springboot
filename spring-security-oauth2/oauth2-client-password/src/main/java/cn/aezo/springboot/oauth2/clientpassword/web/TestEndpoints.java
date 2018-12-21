package cn.aezo.springboot.oauth2.clientpassword.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// 资源
@RestController
public class TestEndpoints {
    // 未受保护的资源
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }

    // 受保护的资源(在Oauth2ServerConfig中配置的)
    // 获取token后访问才会显示资源：http://localhost:8080/order/1?access_token=b7236239-7dee-404d-9e7d-14a035052be9
    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }

    @GetMapping("/order/check/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getOrder2(@PathVariable String id) {
        return "order id : " + id;
    }

}
