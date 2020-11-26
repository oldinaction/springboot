package cn.aezo.springboot.datasource.controller;

import cn.aezo.springboot.datasource.config.dynamic.ThreadLocalDSKey;
import cn.aezo.springboot.datasource.config.dynamic.DynamicDataSource;
import cn.aezo.springboot.datasource.services.dynamic.TestService;
import cn.aezo.springboot.datasource.util.SpringContextU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
public class AddDynamicController {

    @Autowired
    SpringContextU springContextU;

    @Autowired
    TestService testService;

    @Autowired
    HttpServletRequest httpServletRequest;

    // 增加一个动态数据源 curl ${baseurl}/add-dynamic?dsKey=mysql-three-dynamic&dbName=test_three
    @RequestMapping("/add-dynamic")
    public String AddDynamic(String dsKey, String dbName) {
        if (null == dsKey && dbName == null) return "dsKey,dbName不能为空";

        // 获取 DynamicDataSource。之前注册给spring 容器，这里可以通过ctx直接拿.
        ApplicationContext ctx = springContextU.getApplicationContext();
        DynamicDataSource dynamicDataSource = ctx.getBean(DynamicDataSource.class);

        // 构建新数据源. 第一次使用才会初始化，之后切换数据源则不需重复初始化
        DataSource ds = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8")
                .username("root")
                .password("root")
                .type(com.zaxxer.hikari.HikariDataSource.class)
                .build();

        // 增加并重设TargetDataSource
        dynamicDataSource.addDataSourceToTargetDataSource(dsKey, ds);
        dynamicDataSource.reSetTargetDataSource();

        return "success";
    }

    @RequestMapping("/test1")
    public String test1() {
        testService.testMysqlOne();

        testService.testMysqlTwo();
        return "test1";
    }

    // 必须运行在添加动态数据之后。如果尚未添加`mysql-three-dynamic`的数据源，则取默认数据源
    @RequestMapping("/must-run-after-add-dynamic")
    public String test2() {
        testService.testMysqlThree();
        return "test2";
    }

    // 动态传入需要使用的数据源
    // curl ${baseurl}/test3?dsKey=mysql-one-dynamic
    // curl ${baseurl}/test3?dsKey=mysql-two-dynamic
    @RequestMapping("/test3")
    public String test3(String dsKey) {
        ThreadLocalDSKey.setDS(dsKey);
        System.out.println(Thread.currentThread().getId() + "," + ThreadLocalDSKey.getDS() + "," + httpServletRequest.getClass());
        List<Map<String, Object>> data = testService.testMysql();
        return "dsKey = " + dsKey + ", " + "data = " + data;
    }
}
