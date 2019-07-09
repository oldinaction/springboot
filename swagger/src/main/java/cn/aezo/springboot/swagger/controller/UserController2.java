package cn.aezo.springboot.swagger.controller;

import cn.aezo.springboot.swagger.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users2")
@Api(description = "用户管理测试2")
public class UserController2 {

    private static final Logger log = LoggerFactory.getLogger(UserController2.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "返回Result（DONE）")
    public Result test(String param) {
        Map<String, Object> data = new HashMap<>(10);
        data.put("myKey", "123");
        return Result.success(data);
    }

    @GetMapping
    @ApiOperation(value = "返回Map（DONE）")
    public Map<String, Object> query() {
        Map<String, Object> data = new HashMap<>(10);
        data.put("myKey", "123");
        return data;
    }
}