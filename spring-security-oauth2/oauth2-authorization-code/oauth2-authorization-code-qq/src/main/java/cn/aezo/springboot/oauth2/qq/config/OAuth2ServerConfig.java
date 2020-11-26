/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.aezo.springboot.oauth2.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfig {

    private static final String QQ_RESOURCE_ID = "qq";

    @Configuration
    @EnableResourceServer()
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(QQ_RESOURCE_ID).stateless(true);

            // 如果关闭 stateless(开启session)，则 access_token 使用时的 session id 会被记录，后续请求不携带 access_token 也可以正常响应
            // resources.resourceId(QQ_RESOURCE_ID).stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    // .sessionManagement()
                    // .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    // .and()
                    .requestMatchers()
                    // 保险起见，防止被主过滤器链路拦截
                    .antMatchers("/qq/**")
                    .and()
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/qq/info/**").access("#oauth2.hasScope('get_user_info')")
                    .antMatchers("/qq/userInfo/**").access("#oauth2.hasScope('get_user_info')")
                    .antMatchers("/qq/fans/**").access("#oauth2.hasScope('get_fanslist')");
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        RedisConnectionFactory redisConnectionFactory;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        // 从数据库获取客户端配置
        // @Autowired
        // DataSource dataSource;
        //
        // @Bean
        // public ClientDetailsService clientDetails() {
        //     return new JdbcClientDetailsService(dataSource); // 管理客户端信息(自定创建)
        // }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // clients.withClientDetails(clientDetails()); // 从数据获取客户端配置

            clients.inMemory()
                    .withClient("aiqiyi") // app_id
                    .resourceIds(QQ_RESOURCE_ID)
                    .authorizedGrantTypes("authorization_code", "refresh_token", "implicit") // 授权码模式
                    .authorities("ROLE_CLIENT")
                    .scopes("get_user_info", "get_fanslist") // 授权范围
                    .secret("my-secret-888888") // app_secret
                    .redirectUris("http://localhost:9090/jump") // 重定向地址
                    .autoApprove(true)
                    .autoApprove("get_user_info")
                    .and()
                    .withClient("youku")
                    .authorizedGrantTypes("authorization_code", "refresh_token", "implicit")
                    .scopes("get_user_info", "get_fanslist")
                    .secret("my-secret-999999")
                    .redirectUris("http://aezocn.local:8081/login/oauth2/code/youku") // 本地hosts文件中加 `127.0.1 aezocn.local` 的映射
                    .autoApprove(true)
                    // sso测试
                    .and()
                    .withClient("client1")
                    .authorities("USER") // 客户端的权限。Granted Authorities
                    .authorizedGrantTypes("authorization_code", "refresh_token", "implicit")
                    .scopes("get_user_info", "read", "write")
                    .secret("my-secret-999999")
                    .redirectUris("http://aezocn.local:8081/login")
                    // .autoApprove(true) // 设置为true则默认授权上面所以的scope，否则需要选择授权scope
                    .and()
                    .withClient("client2")
                    .authorizedGrantTypes("authorization_code", "refresh_token", "implicit")
                    .scopes("get_user_info")
                    .secret("my-secret-999999")
                    .redirectUris("http://smalle.local:8082/login") // 本地hosts文件中加 `127.0.1 smalle.local` 的映射
                    .autoApprove(true)
            ;
        }

        @Bean
        public ApprovalStore approvalStore() {
            TokenApprovalStore store = new TokenApprovalStore();
            store.setTokenStore(tokenStore());
            return store;
        }

        @Bean
        public TokenStore tokenStore() {
            // 保存在内存
            return new InMemoryTokenStore();

            // 需要使用 redis 的话，放开这里
            // return new RedisTokenStore(redisConnectionFactory);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.tokenStore(tokenStore())
                    .authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer.realm(QQ_RESOURCE_ID).allowFormAuthenticationForClients();
        }

    }
}
