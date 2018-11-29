package cn.aezo.springboot.datasource.config.multi;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"cn.aezo.springboot.datasource.mapper.sqlserver"}, sqlSessionFactoryRef = "sqlSessionFactorySqlserver")
public class SqlserverMybatisConfig {
    @Autowired
    @Qualifier("sqlserverDataSource")
    private DataSource ds2;

    @Bean
    public SqlSessionFactory sqlSessionFactorySqlserver() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds2);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateSqlserver() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactorySqlserver());
        return template;
    }
}