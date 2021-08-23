package cn.aezo.springboot.druid.dbdruid.sqlparse;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLExistsExpr;
import com.alibaba.druid.sql.ast.expr.SQLInSubQueryExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;

import java.util.List;

/**
 * @author smalle
 * @date 2020-11-27 13:06
 */
public class SelectSqlParse {

    public static void main(String[] args) {
        String sql1 = "select *\n" +
                "  from (select rownum as rn, paging_t1.*\n" +
                "          from (select ys.box_number as V1, substr(bcc.bcc_cont_size, 1, 2) as V2,\n" +
                "                       bcc.bcc_cont_type as V3, to_char(yiorin.input_tm, 'yyyy/MM/dd HH24:mi:ss') as V10,\n" +
                "                       yiorin.source_Go_Code as V11, ypmpti.note as V12,\n" +
                "                       decode(yyi.box_Status, 'GOOD_BOX', '好', 'BAD_BOX', '坏', '') as V13,\n" +
                "                       yiorin.customer_Num as V17,\n" +
                "                       decode(ybts.match_Id, null, ybts.box_Type, ybts.box_Type || ' - ' || ybts.match_Id) as V18,\n" +
                "                       yiorin.plan_Remark as V19, yiorin.license_Plate_Num as V20, ypctrans.short_Name as V21, ypcbox.short_Name as V31,\n" +
                "                       count(*) over() paging_total\n" +
                "                  from Ycross_In_Out_Regist yiorin\n" +
                "                  left join Ycross_Storage ys on ys.in_out_regist_id = yiorin.in_out_regist_id\n" +
                "                  left join Ybase_Cont_Code bcc on bcc.id = ys.bcc_cont_id\n" +
                "                  left join Ybase_Type_Maintenance ypmpti on ypmpti.type in ('PLAN_TYPE_IN', 'PLAN_TYPE_IN_INSIDE')\n" +
                "                    and ypmpti.code = yiorin.plan_Classification_Code\n" +
                "                  left join Yrebx_Yanxiang_Info yyi on yyi.history_Id is null and yyi.yes_status not in (7) and yyi.id = yiorin.yanxiang_Id\n" +
                "                  left join Yyard_Box_Type_Set ybts on ybts.id = ys.box_Type_Id\n" +
                "                  left join Ybase_Party_Company ypctrans on ypctrans.party_id = yiorin.transport_Company_Id\n" +
                "                  left join Ybase_Party_Company ypcbox on ypcbox.party_id = ys.party_id\n" +
                "                 where 1 = 1\n" +
                "                   and yiorin.yes_status = 1\n" +
                "                   and (yiorin.input_tm between to_date('2018/07/26 00:00:00', 'yyyy/MM/dd HH24:mi:ss')\n" +
                "                        and to_date('2018/07/27 00:00:00', 'yyyy/MM/dd HH24:mi:ss')\n" +
                "                       and exists\n" +
                "                        (select 1 from Yyard_Location_Set yls\n" +
                "                          where 1 = 1 and yls.location_id = ys.location_id and yls.yard_Party_Id in ('11651')))\n" +
                "                 order by V1 ASC) paging_t1\n" +
                "         where rownum < 51) paging_t2\n" +
                " where paging_t2.rn >= 1";

        // 测试多个 SQLStatement
        sql1 += ";";
        sql1 += "select username, f_name || l_name from t_user1 where name like ? and level > (select level from t_dict where code = 'vip') " +
                " union all select username2, f_name2 || l_name2 as fl_name from t_user2";

        test1(sql1);
    }

    private static void test1(String sql) {
        // 解析出的独立语句的个数。可写多个select语句，使用;分割，这样就有多条SQLStatement数据
        String dbType = JdbcConstants.ORACLE;
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        System.out.println("stmtList.size() = " + stmtList.size());

        for (SQLStatement sqlStatement : stmtList) {
            if(!(sqlStatement instanceof SQLSelectStatement)) {
                continue;
            }

            parseItem(sqlStatement);
        }
    }

    private static void parseItem(SQLStatement stmt) {
        SQLSelectStatement selectStatement = (SQLSelectStatement) stmt;

        OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();
        selectStatement.accept(visitor);
        // Tables : {t_user1=Select, t_user2=Select}
        // fields : [t_user1.username, t_user1.f_name, t_user1.l_name, t_user2.username2, t_user2.f_name, t_user2.l_name]
        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());

        SQLSelectQuery sqlSelectQuery = selectStatement.getSelect().getQuery();

        // union的查询语句
        if (sqlSelectQuery instanceof SQLUnionQuery) {
            SQLUnionQuery sqlUnionQuery = (SQLUnionQuery) sqlSelectQuery;
            SQLSelectQuery sqlSelectQuery1 = sqlUnionQuery.getLeft();
            SQLSelectQuery sqlSelectQuery2 = sqlUnionQuery.getRight();

            if (sqlSelectQuery1 instanceof SQLSelectQueryBlock) {
                parseBlock(sqlSelectQuery1);
            }
            if (sqlSelectQuery2 instanceof SQLSelectQueryBlock) {
                parseBlock(sqlSelectQuery2);
            }

            return;
        }

        // 解析简单的select
        parseBlock(sqlSelectQuery);
    }

    private static void parseBlock(SQLSelectQuery sqlSelectQuery) {
        // 非union的查询语句
        if (sqlSelectQuery instanceof SQLSelectQueryBlock) {
            SQLSelectQueryBlock sqlSelectQueryBlock = (SQLSelectQueryBlock) sqlSelectQuery;

            // 获取字段列表
            List<SQLSelectItem> selectItems = sqlSelectQueryBlock.getSelectList();
            // selectItems = [username, f_name || l_name]
            System.out.println("selectItems = " + selectItems);

            // 获取表
            SQLTableSource table = sqlSelectQueryBlock.getFrom();
            System.out.println("table = " + table);
            if (table instanceof SQLExprTableSource) {
                // 普通单表

            } else if (table instanceof SQLJoinTableSource) {
                // join多表

            } else if (table instanceof SQLSubqueryTableSource) {
                // 子查询作为表
                System.out.println("============================");
                System.out.println("SQLSubqueryTableSource = " + table);
                test1(((SQLSubqueryTableSource) table).getSelect().toString());
            }
            // 获取where条件
            SQLExpr where = sqlSelectQueryBlock.getWhere();
            // where = name LIKE ?
            System.out.println("where = " + where);
            if (where instanceof SQLBinaryOpExpr) {
                // 如果是二元表达式
                parseWhere(where);
            } else if (where instanceof SQLInSubQueryExpr) {
                // 如果是子查询
                SQLInSubQueryExpr sqlInSubQueryExpr = (SQLInSubQueryExpr) where;
                System.out.println("sqlInSubQueryExpr = " + sqlInSubQueryExpr);
            } else {
                System.out.println("where else................");
            }
            // 获取分组
            SQLSelectGroupByClause groupBy = sqlSelectQueryBlock.getGroupBy();
            System.out.println("groupBy = " + groupBy);

            // 获取排序
            SQLOrderBy orderBy = sqlSelectQueryBlock.getOrderBy();
            System.out.println("orderBy = " + orderBy);

            // 获取分页
            SQLLimit limit = sqlSelectQueryBlock.getLimit();
            System.out.println("limit = " + limit);
        } else {
            System.out.println("parseBlock else................");
        }
    }

    private static void parseWhere(SQLExpr where) {
        if (where instanceof SQLBinaryOpExpr) {
            // 如果是二元表达式
            SQLBinaryOpExpr   sqlBinaryOpExpr = (SQLBinaryOpExpr) where;
            SQLExpr           left            = sqlBinaryOpExpr.getLeft();
            SQLBinaryOperator operator        = sqlBinaryOpExpr.getOperator();
            SQLExpr           right           = sqlBinaryOpExpr.getRight();
            // left = name, operator = Like, right = ? com.alibaba.druid.sql.ast.expr.SQLExistsExpr@f83e35f6
            System.out.println("left = " + left + "\n operator = " + operator + "\n right = " + right);

            if(right instanceof SQLBinaryOpExpr) {
                SQLExpr subRight = ((SQLBinaryOpExpr) right).getRight();

                if(subRight instanceof SQLExistsExpr) {
                    parseExists(subRight);
                } else if(subRight instanceof SQLQueryExpr) {
                    SQLQueryExpr sqlQueryExpr = (SQLQueryExpr) subRight;
                    SQLSelect subQuery = sqlQueryExpr.getSubQuery();

                    SQLSelectQuery sqlSelectQuery = ((SQLQueryExpr) subRight).getSubQuery().getQuery();
                    if(sqlSelectQuery instanceof SQLUnionQuery) {
                        // AST 树，无穷无尽也。。。
                        SQLSelectQuery sqlSelectQuery1 = ((SQLUnionQuery) sqlSelectQuery).getLeft();
                        System.out.println("parseWhere.sqlSelectQuery1 = " + sqlSelectQuery1);
                        parseBlock(sqlSelectQuery1);
                    }
                }

                parseWhere(subRight);
            } else {
                System.out.println("parseWhere else................");
            }
        }
    }

    private static void parseExists(SQLExpr subRight) {
        if(subRight instanceof SQLExistsExpr) {
            SQLExistsExpr sqlExistsExpr = (SQLExistsExpr) subRight;
            SQLSelect sqlSelect = sqlExistsExpr.getSubQuery();
            if(sqlSelect.getQuery() != null && sqlSelect.getQuery() instanceof SQLSelectQuery) {
                System.out.println("sqlSelect.getQuery() = " + sqlSelect);
                parseBlock(sqlSelect.getQuery());
            }
            return;
        } else {
            System.out.println("parseExists else................");
        }
    }
}
