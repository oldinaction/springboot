package cn.aezo.springboot.thymeleafsecurity.controller;

import cn.aezo.springboot.thymeleafsecurity.base.security.HasAdminRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by smalle on 2017/10/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    // @PreAuthorize("hasRole('ADMIN')") // 使用自定义注解@HasAdminRole进行封装(可组合更复杂的权限注解)
    @HasAdminRole
    @GetMapping("/adminRole")
    public String adminRole() {
        return "adminRole";
    }
}
