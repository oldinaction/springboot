package cn.aezo.springboot.datasource.config.dynamic;

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
@MapperScan(basePackages = {"cn.aezo.springboot.datasource.mapper.dynamic"}, sqlSessionFactoryRef = "sqlSessionFactoryMysqlDynamic")
public class DynamicMybatisConfig {
    @Autowired
    @Qualifier("dynamicDataSource") // 此处注入动态数据源
    private DataSource dataSource;

    // 此处生成的Bean的名字, 必须和sqlSessionFactoryRef中指定的一致
    @Bean
    public SqlSessionFactory sqlSessionFactoryMysqlDynamic() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateMysqlDynamic() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryMysqlDynamic()); // 使用上面配置的Factory
        return template;
    }
}