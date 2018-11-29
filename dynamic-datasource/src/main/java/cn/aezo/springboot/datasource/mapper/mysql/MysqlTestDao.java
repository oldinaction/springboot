package cn.aezo.springboot.datasource.mapper.mysql;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MysqlTestDao {
    @Select({"<script>",
            "select * from test",
            "</script>"})
    List<Map<String, Object>> findTest();
}
