package cn.aezo.springboot.thymeleafsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by smalle on 2017/9/14.
 */
@Controller // 此时不能是@RestController
@RequestMapping("/thymeleaf")
public class ThymeleafController {
    // http://localhost:9526/thymeleaf/hello
    @RequestMapping("/hello")
    public String hello(Map<String, Object> model) {
        model.put("hello", "UserController.thymeleaf");

        return "/thymeleaf/hello";
    }
}
