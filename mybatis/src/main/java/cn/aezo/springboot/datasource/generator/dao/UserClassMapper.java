package cn.aezo.springboot.datasource.generator.dao;

import cn.aezo.springboot.datasource.generator.model.UserClass;
import cn.aezo.springboot.datasource.generator.model.UserClassExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserClassMapper {
    long countByExample(UserClassExample example);

    int deleteByExample(UserClassExample example);

    int deleteByPrimaryKey(Long classId);

    int insert(UserClass record);

    int insertSelective(UserClass record);

    List<UserClass> selectByExample(UserClassExample example);

    UserClass selectByPrimaryKey(Long classId);

    int updateByExampleSelective(@Param("record") UserClass record, @Param("example") UserClassExample example);

    int updateByExample(@Param("record") UserClass record, @Param("example") UserClassExample example);

    int updateByPrimaryKeySelective(UserClass record);

    int updateByPrimaryKey(UserClass record);
}