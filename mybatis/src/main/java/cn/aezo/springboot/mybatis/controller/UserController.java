package cn.aezo.springboot.mybatis.controller;

import cn.aezo.springboot.mybatis.mapper.UserMapper;
import cn.aezo.springboot.mybatis.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by smalle on 2016/12/30.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserMapper userMapper;

    // http://localhost:9526/api/user-info?nickName=smalle
    @RequestMapping(value = "/user-info", method = RequestMethod.GET)
    public UserInfo findUser(String nickName) {
        System.out.println("nickName = " + nickName);
        UserInfo userInfo = userMapper.findByNickName(nickName);
        return userInfo;
    }


}
