package cn.aezo.springboot.oauth2.qq.controller;

import cn.aezo.springboot.oauth2.qq.db.InMemoryQQDatabase;
import cn.aezo.springboot.oauth2.qq.db.QQAccount;
import cn.aezo.springboot.oauth2.qq.utils.Oauth2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qq")
public class QQProviderController {
    @Autowired
    Oauth2Utils oauth2Utils;

    // oauth2 客户端手动获取用户信息测试
    @RequestMapping("/info")
    public Map<String, Object> info(@RequestParam("access_token") String accessToken){
        OAuth2Authentication oAuth2Authentication = oauth2Utils.getAuthenticationInOauth2Server(accessToken);
        User user = ((User) oAuth2Authentication.getUserAuthentication().getPrincipal());
        QQAccount account = InMemoryQQDatabase.database.get(user.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("qq", account.getQq());
        map.put("nickName", account.getNickName());
        map.put("level", account.getLevel());
        return map;
    }

    @RequestMapping("/fans")
    public List<QQAccount> fans(@RequestParam("access_token") String accessToken){
        OAuth2Authentication oAuth2Authentication = oauth2Utils.getAuthenticationInOauth2Server(accessToken);
        User user = ((User) oAuth2Authentication.getUserAuthentication().getPrincipal());
        return InMemoryQQDatabase.database.get(user.getUsername()).getFans();
    }

    // oauth2客户端自动配置获取用户信息测试. Authorization: Bearer 39277f60-e2af-4abc-891f-89f711660edc
    @RequestMapping("/userInfo")
    public Map<String, Object> userInfo(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Assert.notNull(authorization, "Header[Authorization] cannot be null");

        String token = authorization.replace("Bearer ", "");
        Assert.notNull(token, "token cannot be null");

        OAuth2Authentication oAuth2Authentication = oauth2Utils.getAuthenticationInOauth2Server(token);
        User user = ((User) oAuth2Authentication.getUserAuthentication().getPrincipal());
        QQAccount account = InMemoryQQDatabase.database.get(user.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("qq", account.getQq());
        map.put("nickName", account.getNickName());
        map.put("level", account.getLevel());
        return map;
    }

    // sso 单点登录测试使用. 必须定义成客户端可以访问到(此时/qq/**被定义成了资源，所以认证后就可以访问到)
    // java.security.Principal
    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }

}
