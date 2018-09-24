package cn.aezo.springboot.springdatarest.dao;

import cn.aezo.springboot.springdatarest.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "people") // 修改默认的节点路径(实体名加s)。此时访问 http://localhost:8080/api/people
public interface PersonRepository extends JpaRepository<Person, Long> {
    @RestResource(path = "nameStartsWith") // 自定义服务暴露为REST资源，访问 http://localhost:8080/api/people/search/nameStartsWith?name=sma
    List<Person> findByNameStartsWith(@Param("name") String name);

    @RestResource(exported = false) // 屏蔽话默认的delete方法
    @Override
    void delete(Long id);
}
