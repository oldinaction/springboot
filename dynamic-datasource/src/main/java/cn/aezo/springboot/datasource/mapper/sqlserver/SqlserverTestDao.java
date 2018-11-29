package cn.aezo.springboot.datasource.mapper.sqlserver;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SqlserverTestDao {
    @Select({"<script>",
            "select * from t_test",
            "</script>"})
    List<Map<String, Object>> findTest();
}
