package cn.aezo.springboot.datasource;

import cn.aezo.springboot.datasource.services.dynamic.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTests {
    @Autowired
    TestService testService;

    @Test
    public void test() {
        testService.testMysql();

        testService.testMysqlTwo();
    }
}
