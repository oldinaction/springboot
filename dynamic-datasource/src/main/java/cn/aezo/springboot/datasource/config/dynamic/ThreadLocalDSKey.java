package cn.aezo.springboot.datasource.config.dynamic;

/**
 * ThreadLocal存储数据，解决线程安全问题
 */
public class ThreadLocalDSKey {
    // 默认数据源标识
    public static final String DEFAULT_DS_KEY = "mysql-one-dynamic";

    private static final ThreadLocal<String> dsKeyHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDS(String dbKey) {
        dsKeyHolder.set(dbKey);
    }

    // 获取数据源名
    public static String getDS() {
        return (dsKeyHolder.get());
    }

    // 清除数据源名
    public static void clearDS() {
        dsKeyHolder.remove();
    }
}
