package cn.aezo.springboot.datasource.config.dynamic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {
    // mysql-one数据源
    @Bean(name = "mysqlDataSourceDynamic")
    @Qualifier("mysqlDataSourceDynamic")
    @ConfigurationProperties(prefix = "spring.datasource.mysql-one")
    public DataSource mysqlDataSourceDynamic() {
        return DataSourceBuilder.create().build();
    }

    // mysql-two数据源
    @Bean(name = "mysqlTwoDataSourceDynamic")
    @Qualifier("mysqlTwoDataSourceDynamic")
    @ConfigurationProperties(prefix = "spring.datasource.mysql-two")
    public DataSource mysqlTwoDataSourceDynamic() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源(池)：将所有数据加入到动态数据源管理中
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(mysqlDataSourceDynamic());

        // 配置多数据源
        dynamicDataSource.addDataSourceToTargetDataSource("mysql-one-dynamic", mysqlDataSourceDynamic());
        dynamicDataSource.addDataSourceToTargetDataSource("mysql-two-dynamic", mysqlTwoDataSourceDynamic());
        Map targetDataSources = dynamicDataSource.getBackupTargetDataSources();
        dynamicDataSource.setTargetDataSources(targetDataSources);

        return dynamicDataSource;
    }
}
