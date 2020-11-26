package cn.aezo.springboot.datasource.sqlstr;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无需定义定义 mapper 声明，只需要基于 mybatis 语法写sql即可解析运行。尚未考虑效率问题
 *
 * @author smalle
 * @date 2020-11-25 13:47
 */
@Service
public class SqlStrTool {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public void getUserPartyInfo() {
        // List list = userPartyMapper.getUserPartyInfo();
        // System.out.println(list);

        String sqlScript = "<script>select id from sys_user_party where 1=1 <if test='partyId != null'>and party_id = #{partyId}</if></script>";
        Map<String, Object> parameterObject = new HashMap<>();
        parameterObject.put("partyId", 2L);

        Configuration configuration = sqlSessionFactory.getConfiguration();
        LanguageDriver languageDriver = configuration.getLanguageRegistry().getDriver(XMLLanguageDriver.class);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlScript, Map.class);
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        System.out.println("sql = " + sql);

        Connection connection = sqlSessionFactory.openSession().getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            setParameters(ps, boundSql, parameterObject, configuration);
            rs = ps.executeQuery();
            List<Map<String, Object>> list = resultSetToList(rs);
            System.out.println("list = " + list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setParameters(PreparedStatement ps, BoundSql boundSql, Object parameterObject, Configuration configuration) {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    try {
                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
                    } catch (TypeException | SQLException e) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
                    }
                }
            }
        }
    }

    /**
     * ResultSet转成List，Map中的key为数据库字段大写
     *
     * @param rs
     * @return
     * @throws SQLException
     * @author smalle
     * @date 2016年10月23日 下午10:48:45
     */
    public List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }
}
