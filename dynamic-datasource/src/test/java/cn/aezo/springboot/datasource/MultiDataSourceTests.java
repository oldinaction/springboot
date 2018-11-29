package cn.aezo.springboot.datasource;

import cn.aezo.springboot.datasource.mapper.mysql.MysqlTestDao;
import cn.aezo.springboot.datasource.mapper.sqlserver.SqlserverTestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDataSourceTests {
    // 测试JdbcTemplate
    @Autowired
    @Qualifier("accessJdbcTemplate")
    JdbcTemplate accessJdbcTemplate;

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    JdbcTemplate mysqlJdbcTemplate;

    @Autowired
    @Qualifier("sqlserverJdbcTemplate")
    JdbcTemplate sqlserverTemplate;

    @Test
    public void test() {
        List<Map<String, Object>> accessData = accessJdbcTemplate.queryForList("select * from Users");
        System.out.println("accessData = " + accessData); // accessData = [{UserId=1, UserName=test07, UserSex=1, UserBirthday=2018-11-15 00:00:00.0, UserMarried=true}, {UserId=2, UserName=access2007, UserSex=1, UserBirthday=2018-11-14 00:00:00.0, UserMarried=true}, {UserId=3, UserName=123, UserSex=1, UserBirthday=2018-11-16 00:00:00.0, UserMarried=false}]

        List<Map<String, Object>> mysqlData = mysqlJdbcTemplate.queryForList("select * from test");
        System.out.println("mysqlData = " + mysqlData); // mysqlData = [{id=1, username=smalle, password=123456}]

        List<Map<String, Object>> sqlserverData = sqlserverTemplate.queryForList("select * from t_test");
        System.out.println("sqlserverData = " + sqlserverData); // sqlserverData = [{id=1001, name=阿婆, gender=女, age=125, note=null}, {id=1002, name=阿公, gender=男, age=130, note=null}, {id=1003, name=大爷, gender=男, age=90, note=null}, {id=1004, name=大妈, gender=女, age=88, note=null}, {id=1005, name=你, gender=嘿嘿, age=3, note=null}]
    }

    // 测试mybatis使用不同数据源
    @Autowired
    MysqlTestDao mysqlTestDao;

    @Autowired
    SqlserverTestDao sqlserverTestDao;

    @Test
    public void testMybatis() {
        List<Map<String, Object>> mysqlData = mysqlTestDao.findTest();
        System.out.println("mysqlData = " + mysqlData); // mysqlData = [{id=1, username=smalle, password=123456}]

        List<Map<String, Object>> sqlserverData = sqlserverTestDao.findTest();
        System.out.println("sqlserverData = " + sqlserverData); // sqlserverData = [{id=1001, name=阿婆, gender=女, age=125, note=null}, {id=1002, name=阿公, gender=男, age=130, note=null}, {id=1003, name=大爷, gender=男, age=90, note=null}, {id=1004, name=大妈, gender=女, age=88, note=null}, {id=1005, name=你, gender=嘿嘿, age=3, note=null}]
    }

}
