package cn.aezo.springboot.thymeleafsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by smalle on 2016/12/30.
 */
@Controller // 此时不能是@RestController
@RequestMapping("/manage")
public class AdminController {

    @GetMapping("/login")
	// return "/hello"; // 加上/后，打成jar包路径找不到。可以去掉/或者使用return new ModelAndView("hello");
    // public String login() {
    //     return "/login";
    // }
    public ModelAndView login(Map<String, Object> map) {
        return new ModelAndView("login");
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/home")
    public String home1() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

}