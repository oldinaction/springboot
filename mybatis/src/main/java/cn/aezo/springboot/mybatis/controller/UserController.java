package cn.aezo.springboot.mybatis.controller;

import cn.aezo.springboot.mybatis.mapper.UserMapper;
import cn.aezo.springboot.mybatis.model.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    // 分页查询：http://localhost:9526/api/users
    @RequestMapping(value = "/users")
    public PageInfo showAllUser(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize); // 默认查询第一页，显示5条数据
        List<UserInfo> users = userMapper.findAll(); // 第一条执行的SQL语句会被分页，实际上输出users是page对象
        PageInfo<UserInfo> pageUser = new PageInfo<UserInfo>(users); // 将users对象绑定到pageInfo

        return pageUser;
    }


}
