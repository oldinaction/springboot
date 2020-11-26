package cn.aezo.springboot.springdatarest.dao;

import cn.aezo.springboot.springdatarest.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

// @RepositoryRestResource(excerptProjection = ListCard.class) // http://localhost:8080/api/cards 此时可以获取到Person信息，无此注解默认无法获取
public interface CardRepository extends JpaRepository<Card, Long> {
}
