package cn.aezo.springboot.thymeleafsecurity.dao;

import cn.aezo.springboot.thymeleafsecurity.model.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by smalle on 2017/9/11.
 */
// 继承了JpaRepository(JpaRepository又继承了CrudRepository已经定义好了基本增删查改相关方法)
public interface UserClassDao extends JpaRepository<UserClass, Long> {
    // spring data 根据属性名和查询关键字自动生成查询方法(spring data会自动实现)
    UserClass findByClassName(String className);
}
