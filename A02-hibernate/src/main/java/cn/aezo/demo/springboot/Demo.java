package cn.aezo.demo.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by smalle on 2016/12/29.
 */
@RestController
public class Demo {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

}

