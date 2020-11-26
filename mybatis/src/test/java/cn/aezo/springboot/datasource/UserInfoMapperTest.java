package cn.aezo.springboot.datasource;

import cn.aezo.springboot.datasource.enums.HobbyEnum;
import cn.aezo.springboot.datasource.mapper.UserMapper;
import cn.aezo.springboot.datasource.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;

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
        UserInfo userInfo = userMapper.findByNickName("${smalle}");
        System.out.println("userInfo = " + userInfo);

        String str = Matcher.quoteReplacement("${smalle}${smalle}$$123{45}");
        System.out.println("str = " + str); // \${smalle}\${smalle}\$\$123{45}
    }

    @Test
    public void testInsert() throws Exception {
        userMapper.insert(new UserInfo("test", 1L, HobbyEnum.READ));
    }

}
