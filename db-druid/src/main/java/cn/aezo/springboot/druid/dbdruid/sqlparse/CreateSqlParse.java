package cn.aezo.springboot.druid.dbdruid.sqlparse;

import cn.aezo.springboot.druid.dbdruid.visitor.ColumnTableAliasVisitor;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @author smalle
 * @date 2020-11-27 13:02
 */
public class CreateSqlParse {

    /**
     * 单纯的解析SQL，不连接数据库，因此只能获取SQL本身的信息。连接数据库后可以通过 connection.getMetaData() 获取字段类型/备注等信息，具体参考测试包中程序
     */
    public static void main(String[] args) {
        // `show create table sys_user_info` 获取建表语句
        String sql = "CREATE TABLE `sys_user_info` (\n" +
                "  `id` bigint(20) NOT NULL,\n" +
                "  `username` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',\n" +
                "  `password` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',\n" +
                "  `mobile` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电话',\n" +
                "  `email` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',\n" +
                "  `nick_name` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',\n" +
                "  `sex` smallint(1) DEFAULT '0' COMMENT '性别. 0：未知；1：男；2：女',\n" +
                "  `full_name` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '全名',\n" +
                "  `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',\n" +
                "  `card_no` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证号',\n" +
                "  `logout_time` timestamp NULL DEFAULT NULL COMMENT '最近登出时间',\n" +
                "  `valid_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '有效状态. 1：有效；0：无效',\n" +
                "  `creator` bigint(20) DEFAULT NULL COMMENT '创建人',\n" +
                "  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `updater` bigint(20) DEFAULT NULL COMMENT '更新人',\n" +
                "  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户信息'";
        String dbType = JdbcConstants.MYSQL;

        // 格式化输出，缺省大写格式
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result);

        // 解析出的独立语句的个数
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement stmt = stmtList.get(i);

            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            stmt.accept(visitor);

            // 获取表名称 {sys_user_info=Create}
            System.out.println("Tables : " + visitor.getTables());
            // 获取操作方法名称，依赖于表名称 {sys_user_info=Create}
            System.out.println("Manipulation : " + visitor.getTables());
            // 获取字段名称 [sys_user_info.id, sys_user_info.username, sys_user_info.password, sys_user_info.mobile, sys_user_info.email, sys_user_info.nick_name, sys_user_info.sex, sys_user_info.full_name, sys_user_info.avatar, sys_user_info.card_no, sys_user_info.logout_time, sys_user_info.valid_status, sys_user_info.creator, sys_user_info.create_time, sys_user_info.updater, sys_user_info.update_time]
            System.out.println("fields : " + visitor.getColumns());

            // 仅DDL语句才会进
            ColumnTableAliasVisitor visitor2 = new ColumnTableAliasVisitor();
            stmt.accept(visitor2);
            // 获取字段备注 {`card_no`='身份证号', `full_name`='全名', `sex`='性别. 0：未知；1：男；2：女', `mobile`='电话', `id`=`id`, `updater`='更新人', `update_time`='更新时间', `avatar`='头像', `create_time`='创建时间', `nick_name`='昵称', `email`='邮箱', `valid_status`='有效状态. 1：有效；0：无效', `username`='用户名', `password`='密码', `logout_time`='最近登出时间', `creator`='创建人'}
            System.out.println("visitor2 = " + visitor2.columnComment);
        }
    }
}
