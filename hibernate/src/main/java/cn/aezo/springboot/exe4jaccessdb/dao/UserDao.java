package cn.aezo.springboot.exe4jaccessdb.dao;

import cn.aezo.springboot.exe4jaccessdb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by smalle on 2017/9/10.
 */
public interface UserDao extends JpaRepository<User, Long> {
    @Query("select u.classId, u.sex, count(u.classId) as count from User u " +
            "   where u.password = :password " +
            "   group by u.classId, u.sex")
    List<Object[]> countUser(@Param("password") String password);

    @Query(value = "select u.* from user u, user_class uc where uc.class_id = u.class_id and uc.class_name = 'one'", nativeQuery = true)
    List<Object[]> findUsers();
}
