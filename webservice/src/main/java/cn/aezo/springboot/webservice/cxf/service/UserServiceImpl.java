package cn.aezo.springboot.webservice.cxf.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@WebService(
    serviceName = "UserServiceWeb", // 服务名(默认UserServiceImplService)
    targetNamespace = "http://service.cxf.webservice.springboot.aezo.cn/", // 实现类包名倒写 (默认，可省略，基于JAX-WS调用服务会用到)
    endpointInterface = "cn.aezo.springboot.webservice.cxf.service.UserService" // 接口的全路径
)
@Service
public class UserServiceImpl implements UserService {
    private Map<String, User> userMap = new HashMap<>();

    public UserServiceImpl() {
        User user = new User();
        user.setUserId("1");
        user.setUsername("zhansan");
        user.setAge("20");
        user.setUpdateTime(new Date());
        userMap.put(user.getUserId(), user);

        user = new User();
        user.setUserId("2");
        user.setUsername("lisi");
        user.setAge("30");
        user.setUpdateTime(new Date());
        userMap.put(user.getUserId(), user);

        user = new User();
        user.setUserId("3");
        user.setUsername("wangwu");
        user.setAge("40");
        user.setUpdateTime(new Date());
        userMap.put(user.getUserId(), user);
    }

    @Override
    public String getName(String userId) {
        return "id-" + userId + "_" + System.currentTimeMillis();
    }
    @Override
    public User getUser(String userId) {
        User user = userMap.get(userId);
        return user;
    }

    @Override
    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        for(Map.Entry<String, User> entry : userMap.entrySet()) {
            users.add(entry.getValue());
        }
        return users;
    }
}