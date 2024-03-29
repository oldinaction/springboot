package cn.aezo.springboot.datasource.mapper;

import cn.aezo.springboot.datasource.model.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by smalle on 2017/9/9.
 */
// @Mapper // 在启动类中定义需要扫码mapper的包：@MapperScan("cn.aezo.springboot.mybatis.mapper"), 则此处无需声明@Mapper
public interface UserMapper {
    // 此处注入变量可以使用#或者$, 区别：# 创建的是一个prepared statement语句, $ 符创建的是一个inlined statement语句
    @Select("select * from user_info where nick_name = #{nickName}")
    // (使用配置<setting name="mapUnderscoreToCamelCase" value="true"/>因此无需转换) 数据库字段名和model字段名或javaType不一致的均需要@Result转换
    // @Results({
    //         @Result(property = "hobby",  column = "hobby", javaType = HobbyEnum.class),
    //         @Result(property = "nickName", column = "nick_name"),
    //         @Result(property = "groupId", column = "group_Id")
    // })
    UserInfo findByNickName(String nickName);

    @Select("select * from user_info")
    List<UserInfo> findAll();

    @Insert("insert into user_info(nick_name, group_id, hobby) values(#{nickName}, #{groupId}, #{hobby})")
    void insert(UserInfo userInfo);

    @Update("update user_info set nick_name = #{nickName}, hobby = #{hobby} where id = #{id}")
    void update(UserInfo userInfo);

    @Delete("delete from user_info where id = #{id}")
    void delete(Long id);
}
