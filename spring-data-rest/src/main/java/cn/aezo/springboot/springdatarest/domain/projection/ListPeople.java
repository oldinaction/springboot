package cn.aezo.springboot.springdatarest.domain.projection;

import cn.aezo.springboot.springdatarest.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

// @Projection必须在domain(model)包或者自包才会被扫描到
@Projection(name = "list", types = Person.class)
// 基于Person实现一个投射(可以定义多个)，http://localhost:8080/api/people?projection=list
public interface ListPeople {
    // 此时只会返回id、name属性
    Long getId();

    String getName();

    @Value("#{target.name} #{target.age}")
        // 这里把Person中的name和age合并成一列，这里需要注意String getFullInfo();方法名前面一定要加get，不然无法序列化为JSON数据
    String getFullInfo();
}