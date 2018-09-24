package cn.aezo.springboot.swagger.controller;

import cn.aezo.springboot.swagger.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// 查看接口文档及在线测试：http://localhost:8080/swagger-ui.html
@RestController
@RequestMapping("/users")
@Api(description = "用户管理") // 可选参数tags=""(默认类名转中划线)
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET) // 如果此处不写method = RequestMethod.GET则会生成每种类型的api文档
    @ApiOperation(value = "最简配置文档注释（DONE）")
    public String test(String param) {
        return "hello swagger";
    }

    // 多个参数用 @ApiImplicitParams
    @GetMapping
    @ApiOperation(value = "条件查询（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
    })
    public User query(String username, String password) {
        return new User(1L, username, password);
    }

    // 单个参数用 ApiImplicitParam
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）")
    @ApiImplicitParam(name = "id", value = "用户编号")
    public void delete(@PathVariable Long id) {}

    // 如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam
    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        return user;
    }

    // 省略 @ApiImplicitParam，那么 swagger 也会使用默认的参数名作为描述信息
    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) { }
}