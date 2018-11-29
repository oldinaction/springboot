package cn.aezo.springboot.datasource.mapper.dynamic;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TestDao {
    @Select({"<script>",
            "select * from test",
            "</script>"})
    List<Map<String, Object>> findTest();
}
