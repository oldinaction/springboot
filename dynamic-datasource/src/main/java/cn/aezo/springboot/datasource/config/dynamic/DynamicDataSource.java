package cn.aezo.springboot.datasource.config.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// AbstractRoutingDataSource 只支持单库事务，也就是说切换数据源要在开启事务之前执行
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    // 预备一份用于存储targetDataSource，否则之前的设置的数据源会丢失。(此处无法获取AbstractRoutingDataSource的targetDataSources值)
    private ConcurrentHashMap<String, DataSource> backupTargetDataSources = new ConcurrentHashMap<>();

    public Map<String, DataSource> getBackupTargetDataSources() {
        return backupTargetDataSources;
    }

    /**
     * 添加数据源配置关系
     * @param key
     * @param ds
     */
    public void addDataSourceToTargetDataSource(String key, DataSource ds){
        this.backupTargetDataSources.put(key, ds);
    }

    /**
     * 重设多数据源配置
     */
    public void reSetTargetDataSource() {
        Map targetDataSources = this.backupTargetDataSources;
        super.setTargetDataSources(targetDataSources);
        this.afterPropertiesSet();
    }

    /**
     * 决定了当前操作选择哪个数据源. 第一次使用才会初始化，之后切换数据源则不需重复初始化
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalDSKey.getDS();
    }
}

