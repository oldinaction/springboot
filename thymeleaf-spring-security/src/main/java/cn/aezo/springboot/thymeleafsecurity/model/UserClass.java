package cn.aezo.springboot.thymeleafsecurity.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by smalle on 2017/9/11.
 */
@Entity
public class UserClass {
    @Id
    @GeneratedValue
    private Long classId;

    private String className;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
