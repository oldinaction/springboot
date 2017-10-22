package cn.aezo.springboot.thymeleafsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by smalle on 2016/12/30.
 */
@Controller // 此时不能是@RestController
@RequestMapping("/manage")
public class AdminController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/")
    public String home() {
        return "/home";
    }

    @GetMapping("/home")
    public String home1() {
        return "/home";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "/error/404";
    }
}