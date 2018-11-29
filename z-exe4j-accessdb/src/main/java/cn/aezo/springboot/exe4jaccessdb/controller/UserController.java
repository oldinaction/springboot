package cn.aezo.springboot.exe4jaccessdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.List;
import java.util.Map;

/**
 * Created by smalle on 2016/12/30.
 */
@Controller
public class UserController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping(path = "users")
    public String findUsers(Map<String, Object> model) {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("select * from Users");
        System.out.println("users = " + users);
        model.put("users", users);
        return "users";
    }

    // access加密测试
    @RequestMapping(path = "h2000")
    public String findH2000(Map<String, Object> model) {
        List<Map<String, Object>> heads = jdbcTemplate.queryForList("select top 10 * from Form_Head order by pre_entry_id");
        System.out.println("heads = " + heads);
        model.put("heads", heads);
        return "h2000";
    }
}
