package cn.aezo.springboot.datasource.services.dynamic;

import cn.aezo.springboot.datasource.config.dynamic.aop.DS;
import cn.aezo.springboot.datasource.mapper.dynamic.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {
    @Autowired
    TestDao testDao;

    @DS("mysql-one-dynamic")
    public void testMysqlOne() {
        List<Map<String, Object>> data = testDao.findTest();
        System.out.println("data = " + data); // data = [{password=123456, id=1, username=smalle}]
    }

    @DS("mysql-two-dynamic")
    public void testMysqlTwo() {
        List<Map<String, Object>> data = testDao.findTest();
        System.out.println("data = " + data); // data = [{password=ABC123, id=1, username=test_two}]
    }

    // 此数据源默认没有，需要手动添加后，才可使用。否则使用的默认数据源
    @DS("mysql-three-dynamic")
    public void testMysqlThree() {
        List<Map<String, Object>> data = testDao.findTest();
        System.out.println("data = " + data); // data = [{password=EFG456, id=1, username=test_three}]
    }

    public List<Map<String, Object>> testMysql() {
        return testDao.findTest();
    }
}
