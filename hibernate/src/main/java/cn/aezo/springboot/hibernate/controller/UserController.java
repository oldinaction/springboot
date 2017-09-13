package cn.aezo.springboot.hibernate.controller;

import cn.aezo.springboot.hibernate.dao.UserClassDao;
import cn.aezo.springboot.hibernate.dao.UserDao;
import cn.aezo.springboot.hibernate.model.User;
import cn.aezo.springboot.hibernate.model.UserClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smalle on 2016/12/30.
 */
@RestController // @Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserClassDao userClassDao; // 继承了JpaRepository(继承了CrudRepository)

    @Autowired
    UserDao userDao;

    /**
     * `@RequestBody` 时, Content-Type 必须为 application/json. 传入参数：(1) 无需传入主键, 会自动递增 (2) 参数和实体字段不必完全匹配, 可多可少
     * @param user
     * @return
     */
    @RequestMapping(path = "user", method = RequestMethod.POST)
    public String create(@RequestBody User user) {
        userDao.save(user);
        return "New user id is: " + user.getId();
    }

    // http://127.0.0.1:9526/api/class-name?className=one
    @RequestMapping(value = "/class-name", method = RequestMethod.GET)
    public String findByClassName(String className) {
        UserClass userClass = userClassDao.findByClassName(className);
        if (userClass != null) {
            return "userClass " + userClass.getClassId() + " is exist.";
        }
        return null;
    }

    // 查询UserClass信息, 并获取子表User的前5条数据. http://127.0.0.1:9526/api/classes?className=one
    @RequestMapping(value = "/classes")
    public Map<String, Object> findClasses(UserClass userClass) {
        Map<String, Object> result = new HashMap<>();

        // 前台传一个类似的UserClass对象，会把此对象做作为条件进行查询
        Example<UserClass> example = Example.of(userClass);
        result.put("userClass", userClassDao.findAll(example));

        // 分页获取User数据：如果使用classes.getUsers()获取则需要写实体对应关系(@OneToMany), 且会产生外键. 此时单表查询不需关联关系
        Pageable pageable = new PageRequest(0, 5, new Sort(Sort.Direction.DESC, "id")); // 获取第1页, 每页显示5条, 按照id排序
        result.put("users", userDao.findAll(pageable));

        return result;
    }

    // 分页. http://127.0.0.1:9526/api/users-page?page=1
    // org.springframework.data.domain.Pageable、org.springframework.data.domain.Example
    @RequestMapping(value = "/users-page")
    public Page<User> findUsersPage(
            @RequestParam(value = "username", defaultValue = "smalle") String username,
            Pageable pageable) {
        // 前台传一个类似的user对象，会把此对象做作为条件进行查询
        Example<User> example = Example.of(new User(username));

        return userDao.findAll(example, pageable);
    }

    // @Query自定义sql语句. http://127.0.0.1:9526/api/user-query
    @RequestMapping(value = "/user-query")
    public Map<String, Object> query() {
        Map<String, Object> result = new HashMap<>();

        result.put("count", userDao.countUser("123456"));
        result.put("users", userDao.findUsers());

        return result;
    }
}
