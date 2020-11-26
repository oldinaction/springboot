package cn.aezo.springboot.oauth2.qq.controller;

import cn.aezo.springboot.oauth2.qq.utils.Oauth2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResController {
    @Autowired
    Oauth2Utils oauth2Utils;

    @RequestMapping("/res")
    public User info(@RequestParam("access_token") String accessToken) {
        OAuth2Authentication oAuth2Authentication = oauth2Utils.getAuthenticationInOauth2Server(accessToken);
        User user = ((User) oAuth2Authentication.getUserAuthentication().getPrincipal());
        return user;
    }
}
