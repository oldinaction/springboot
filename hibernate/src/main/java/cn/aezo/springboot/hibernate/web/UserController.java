package cn.aezo.springboot.hibernate.web;

import cn.aezo.springboot.hibernate.domain.User;
import cn.aezo.springboot.hibernate.domain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by smalle on 2016/12/30.
 */
@RestController // @Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String findByUsername(String username) {
        String userId;
        User user = userDao.findByUsername(username);
        if (user != null) {
            userId = String.valueOf(user.getId());
            return "The user id is: " + userId;
        }
        return "user " + username + " is not exist.";
    }

    /**
     * `@RequestBody` 时, Content-Type 必须为 application/json. 传入参数：(1) 无需传入主键, 会自动递增 (2) 参数和实体字段不必完全匹配, 可多可少
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String create(@RequestBody User user) {
        userDao.save(user);
        return "New user id is: " + user.getId();
    }


}
