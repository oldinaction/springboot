package cn.aezo.springboot.datasource;

import cn.aezo.springboot.datasource.enums.HobbyEnum;
import cn.aezo.springboot.datasource.mapperxml.UserMapperXml;
import cn.aezo.springboot.datasource.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smalle on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoXmlMapperTest {
    @Autowired
    UserMapperXml userMapperXml;

    @Test
    public void testFindByNickName() {
        Map<String, Object> map = new HashMap<>();
        map.put("nickName", "smalle");
        List<UserInfo> list = userMapperXml.findAll(map);
        System.out.println("list = " + list);
    }

    @Test
    public void testFindByNickName2() {
        UserInfo userInfo = userMapperXml.getOne(1L);
        System.out.println("userInfo = " + userInfo);
    }

    @Test
    public void testInsert() throws Exception {
        userMapperXml.insert(new UserInfo("testxml", 1L, HobbyEnum.READ));
    }

}
