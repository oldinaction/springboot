package cn.aezo.springboot.oauth2.qq.db;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "qq")
@ToString(exclude = "fans")
@Builder // 可以使用此语法构建对象：QQAccount.builder().qq("123456").nickName("smalle").level("54").build()
public class QQAccount {

    private String qq;
    private String nickName;
    private String level;
    private List<QQAccount> fans;

}
