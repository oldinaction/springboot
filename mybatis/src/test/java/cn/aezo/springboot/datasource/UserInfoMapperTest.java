package cn.aezo.springboot.datasource;

import cn.aezo.springboot.datasource.enums.HobbyEnum;
import cn.aezo.springboot.datasource.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by smalle on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {
    @Autowired
    UserMapperXml userMapperXml;

    @Test
    public void testFindByNickName() {
        List<UserInfo> list = userMapperXml.findAll();
        System.out.println("list = " + list);

        UserInfo userInfo = userMapperXml.getOne(1L);
        System.out.println("userInfo = " + userInfo);
    }

    @Test
    public void testInsert() throws Exception {
        userMapperXml.insert(new UserInfo("testxml", 1L, HobbyEnum.READ));
    }

}
