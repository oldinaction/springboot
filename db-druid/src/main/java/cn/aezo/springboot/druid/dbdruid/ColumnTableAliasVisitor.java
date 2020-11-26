package cn.aezo.springboot.druid.dbdruid;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLUnique;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;

import java.util.HashMap;
import java.util.Map;

/**
 * 只有是DDL语句才会进
 * @author smalle
 * @date 2020-11-26 17:31
 */
public class ColumnTableAliasVisitor extends SchemaStatVisitor {

    public ColumnTableAliasVisitor() {
        super("mysql");
    }

    public Map<String, String> columnComment = new HashMap<>();

    @Override
    public boolean visit(SQLColumnDefinition x) {
        String tableName = null;
        SQLObject parent = x.getParent();
        if (parent instanceof SQLCreateTableStatement) {
            tableName = ((SQLCreateTableStatement) parent).getName().toString();
        }

        if (tableName == null) {
            return true;
        } else {
            String columnName = x.getName().toString();
            //获取列注释，如果注释为空则使用列名做为注释
            String comment = x.getComment() == null ? columnName : x.getComment().toString();
            columnComment.put(columnName, (comment == null || "".equals(comment)) ? columnName : comment);
            TableStat.Column column = this.addColumn(tableName, columnName);
            if (x.getDataType() != null) {
                column.setDataType(x.getDataType().getName());
            }

            for (Object item : x.getConstraints()) {
                if (item instanceof SQLPrimaryKey) {
                    column.setPrimaryKey(true);
                } else if (item instanceof SQLUnique) {
                    column.setUnique(true);
                }
            }

            return false;
        }
    }
}
