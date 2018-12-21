package cn.aezo.springboot.oauth2.aiqiyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/jump")
    public String jump() {
        return "jump";
    }

}
