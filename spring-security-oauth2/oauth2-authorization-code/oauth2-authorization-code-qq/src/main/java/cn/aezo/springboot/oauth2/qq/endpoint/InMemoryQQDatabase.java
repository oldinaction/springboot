package cn.aezo.springboot.oauth2.qq.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟数据库
 */
public class InMemoryQQDatabase {

    public static Map<String, QQAccount> database;

    static {
        database = new HashMap<>();
        database.put("123456", QQAccount.builder().qq("123456").nickName("smalle").level("54").build());
        database.put("654321", QQAccount.builder().qq("654321").nickName("aezocn").level("31").build());

        QQAccount qqAccount1 = database.get("123456");
        qqAccount1.setFans(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            qqAccount1.getFans().add(QQAccount.builder().qq("1000000" + i).nickName("fan" + i).level(i + "").build());
        }

        QQAccount qqAccount2 = database.get("654321");
        qqAccount2.setFans(new ArrayList<>());
        for (int i = 0; i < 3; i++) {
            qqAccount2.getFans().add(QQAccount.builder().qq("2000000" + i).nickName("fan" + i).level(i + "").build());
        }
    }

}
