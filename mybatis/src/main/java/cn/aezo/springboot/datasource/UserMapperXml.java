package cn.aezo.springboot.datasource;

import cn.aezo.springboot.datasource.model.UserInfo;

import java.util.List;

/**
 * Created by smalle on 2017/9/12.
 * 基于xml的Dao层定义
 */
public interface UserMapperXml {
    List<UserInfo> findAll();

    UserInfo getOne(Long id);

    void insert(UserInfo user);

    void update(UserInfo user);

    void delete(Long id);
}
