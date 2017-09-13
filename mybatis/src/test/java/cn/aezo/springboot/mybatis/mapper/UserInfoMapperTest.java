package cn.aezo.springboot.mybatis.mapper;

import cn.aezo.springboot.mybatis.enums.HobbyEnum;
import cn.aezo.springboot.mybatis.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by smalle on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testFindByNickName() {
        UserInfo userInfo = userMapper.findByNickName("smalle");
        System.out.println("userInfo = " + userInfo);
    }

    @Test
    public void testInsert() throws Exception {
        userMapper.insert(new UserInfo("test", 1L, HobbyEnum.READ));
    }

}
