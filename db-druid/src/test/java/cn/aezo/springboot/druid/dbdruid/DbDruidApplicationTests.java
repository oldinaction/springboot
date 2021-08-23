package cn.aezo.springboot.druid.dbdruid;

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
