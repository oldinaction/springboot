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
// cn.aezo.springboot.datasource.mapper.mysql下的Mapper接口，都会使用mysql-one数据源
@MapperScan(basePackages = {"cn.aezo.springboot.datasource.mapper.mysql"}, sqlSessionFactoryRef = "sqlSessionFactoryMysql")
public class MysqlMybatisConfig {
    @Autowired
    @Qualifier("mysqlDataSource")
    private DataSource ds1;

    // 此处生成的Bean的名字(sqlSessionFactoryMysql)必须和sqlSessionFactoryRef中指定的一致
    @Bean
    public SqlSessionFactory sqlSessionFactoryMysql() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds1);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateMysql() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryMysql()); // 使用上面配置的Factory
        return template;
    }
}