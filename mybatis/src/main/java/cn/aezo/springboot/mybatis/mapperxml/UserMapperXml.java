package cn.aezo.springboot.mybatis.mapperxml;

import cn.aezo.springboot.mybatis.model.UserInfo;

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
