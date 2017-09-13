package cn.aezo.springboot.mybatis.model;

import cn.aezo.springboot.mybatis.enums.HobbyEnum;

import java.io.Serializable;

/**
 * Created by smalle on 2016/12/30.
 */
// @Alias("userInfo") // typeAlias类型别名定义, 默认是包名小写
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long groupId;
    private String nickName;
    private HobbyEnum hobby;

    public UserInfo() {
        super();
    }

    public UserInfo(String nickName, Long groupId, HobbyEnum hobby) {
        super();
        this.nickName = nickName;
        this.groupId = groupId;
        this.hobby = hobby;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HobbyEnum getHobby() {
        return hobby;
    }

    public void setHobby(HobbyEnum hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "id: " + this.id + ", nickName: " + this.nickName + ", groupId: " + this.groupId + ", hobby: " + hobby.name();
    }
}
