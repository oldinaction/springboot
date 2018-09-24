package cn.aezo.springboot.springdatarest.controller;

import cn.aezo.springboot.springdatarest.dao.PersonRepository;
import cn.aezo.springboot.springdatarest.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Spring Data JPA只会处理repository. 此controller按照自身返回
@RestController
@RequestMapping("/people")
public class MyController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/myPerson")
    public Person myPerson(Long id) {
        // springboot 1.5.6：getOne是"Returns a reference" findOne比getOne更通用。推荐使用findOne
        // Person person = personRepository.findOne(id);

        // springboot 2.0.1：findOne被改成findById
        Person person = personRepository.findById(id).get();
        return person;
    }

    @RequestMapping("/listPerson")
    public List<Person> listPerson(String name) {
        return personRepository.findByNameStartsWith(name);
    }
}
