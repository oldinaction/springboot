package cn.aezo.demo.springboot.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by smalle on 2016/12/30.
 */
// CrudRepository 已经定义好了基本增删查改相关方法
public interface UserDao extends CrudRepository<User, Long> {
    // spring data jpa 根据属性名和查询关键字自动生成查询方法
    User findByUsername(String username);
}
