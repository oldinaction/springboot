package cn.aezo.springboot.thymeleafsecurity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by smalle on 2016/12/30.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private Long classId;

    @Column
    private String username;

    @Column
    private String password;

    private Integer sex;

    // 用于spring security自定义字段认证(微信公众号登录返回Code，实际开发微信公众号应该存储openid)
    private String wxCode;

    // 用于spring security定义用户角色
    private String roleCode;

    protected User() {
    }

    public User(String username) {
        this.username = username;
    }

    // 用于spring security自定义字段认证
    public User(User user) {
        this.id = user.getId();
        this.classId = user.getClassId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.sex = user.getSex();
        this.wxCode = user.getWxCode();
        this.roleCode = user.getRoleCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
