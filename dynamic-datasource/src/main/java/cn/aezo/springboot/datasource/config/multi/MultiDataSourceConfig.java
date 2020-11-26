package cn.aezo.springboot.datasource.config.multi;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

// 多数据源
@Configuration
public class MultiDataSourceConfig {
    // access数据源
    @Bean(name = "accessDataSource")
    @Qualifier("accessDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.access-one")
    public DataSource accessDataSource() {
        return DataSourceBuilder.create().build();
    }

    // mysql数据源
    @Bean(name = "mysqlDataSource")
    @Qualifier("mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql-one")
    @Primary // 默认数据源
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    // sqlserver数据源
    @Bean(name = "sqlserverDataSource")
    @Qualifier("sqlserverDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlserver-one")
    public DataSource sqlserverDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "accessJdbcTemplate")
    public JdbcTemplate accessJdbcTemplate(
            @Qualifier("accessDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "mysqlJdbcTemplate")
    public JdbcTemplate mysqlJdbcTemplate(
            @Qualifier("mysqlDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "sqlserverJdbcTemplate")
    public JdbcTemplate sqlserverJdbcTemplate(
            @Qualifier("sqlserverDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
