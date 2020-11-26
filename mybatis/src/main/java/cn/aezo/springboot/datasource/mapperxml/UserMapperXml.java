package cn.aezo.springboot.datasource.mapperxml;

import cn.aezo.springboot.datasource.model.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by smalle on 2017/9/12.
 * 基于xml的Dao层定义
 */
public interface UserMapperXml {
    List<UserInfo> findAll(Map<String, Object> context);

    UserInfo getOne(Long id);

    void insert(UserInfo user);

    void update(UserInfo user);

    void delete(Long id);
}
