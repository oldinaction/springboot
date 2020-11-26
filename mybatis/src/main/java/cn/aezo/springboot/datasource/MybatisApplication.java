package cn.aezo.springboot.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"cn.aezo.springboot.datasource.mapper", "cn.aezo.springboot.datasource.mapperxml"}) // 声明需要扫描mapper的路径
public class MybatisApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MybatisApplication.class, args);
    }
}
