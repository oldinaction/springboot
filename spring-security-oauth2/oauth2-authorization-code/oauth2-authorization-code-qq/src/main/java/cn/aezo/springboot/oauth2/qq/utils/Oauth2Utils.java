package cn.aezo.springboot.oauth2.qq.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class Oauth2Utils {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * oauth2 认证服务器直接处理校验请求的逻辑
     *
     * @param accessToken
     * @return
     */
    public OAuth2AccessToken checkTokenInOauth2Server(String accessToken) {
        TokenStore tokenStore = (TokenStore) applicationContext.getBean(TokenStore.class);
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
        return oAuth2AccessToken;
    }

    /**
     * oauth2 认证服务器直接处理校验请求的逻辑
     *
     * @param accessToken
     * @return
     */
    public OAuth2Authentication getAuthenticationInOauth2Server(String accessToken) {
        TokenStore tokenStore = (TokenStore) applicationContext.getBean(TokenStore.class);
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
        return oAuth2Authentication;
    }
}
