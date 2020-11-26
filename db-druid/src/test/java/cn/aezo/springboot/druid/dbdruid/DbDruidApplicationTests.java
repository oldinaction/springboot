package cn.aezo.springboot.druid.dbdruid;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbDruidApplicationTests {

    @Autowired
    DataSource dataSource;

    /**
     * 单纯的解析SQL，不连接数据库，因此只能获取SQL本身的信息
     */
    @Test
    public void test1() {
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

        //格式化输出
        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        //解析出的独立语句的个数
        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {

            SQLStatement stmt = stmtList.get(i);
            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            ColumnTableAliasVisitor visitor2 = new ColumnTableAliasVisitor();
            stmt.accept(visitor);
            stmt.accept(visitor2);

            //获取表名称
            System.out.println("Tables : " + visitor.getTables());
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());

            // 获取字段备注
            System.out.println("visitor2 = " + visitor2.columnComment);
        }
    }

    @Test
    public void test2() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        System.out.println(metaData.getDriverName()); // MySQL Connector Java
        System.out.println(metaData.getURL()); // jdbc:mysql://localhost/minionsdemo?useUnicode=true&characterEncoding=utf-8&useSSL=false
        System.out.println(metaData.getUserName()); // root@localhost

        // 获取所有表信息
        ResultSet rs = metaData.getTables(null, null, null, new String[] { "TABLE" });
        List<Map<String, Object>> tableList = resultSetToList(rs);
        // [{TABLE_CAT=minionsdemo, TABLE_NAME=sys_dept, SELF_REFERENCING_COL_NAME=null, TABLE_SCHEM=null, TYPE_SCHEM=null, TYPE_CAT=null, TABLE_TYPE=TABLE, REMARKS=, REF_GENERATION=null, TYPE_NAME=null}]
        System.out.println("tableList = " + tableList);

        // 获取字段信息
        ResultSet rsCols = metaData.getColumns(null, "%", "user", "%");
        List<Map<String, Object>> colsList = resultSetToList(rsCols);
        // [{SCOPE_TABLE=null, TABLE_CAT=minionsdemo, BUFFER_LENGTH=65535, IS_NULLABLE=YES, TABLE_NAME=sys_dept, COLUMN_DEF=null, SCOPE_CATALOG=null, TABLE_SCHEM=null, COLUMN_NAME=party_id, NULLABLE=1, REMARKS=组织ID, DECIMAL_DIGITS=0, NUM_PREC_RADIX=10, SQL_DATETIME_SUB=0, IS_GENERATEDCOLUMN=NO, IS_AUTOINCREMENT=NO, SQL_DATA_TYPE=0, CHAR_OCTET_LENGTH=null, ORDINAL_POSITION=2, SCOPE_SCHEMA=null, SOURCE_DATA_TYPE=null, DATA_TYPE=-5, TYPE_NAME=BIGINT, COLUMN_SIZE=19}]
        System.out.println("colsList = " + colsList);
    }

    public static List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String,Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

}
