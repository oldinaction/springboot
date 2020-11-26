package cn.aezo.springboot.springdatarest.domain.projection;

import cn.aezo.springboot.springdatarest.domain.Card;
import cn.aezo.springboot.springdatarest.domain.Person;
import org.springframework.data.rest.core.config.Projection;

// http://localhost:8080/api/cards?projection=list 此时在Card的返回中也有Person的信息(必须通过projection访问，或者再CardRepository中定义projection)
@Projection(name = "list", types = Card.class)
public interface ListCard {
    String getCardNo();

    Person getPerson();
}
