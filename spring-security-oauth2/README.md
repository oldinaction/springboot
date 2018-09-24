## spring security oauth2 [^2]

- [理解Oauth 2.0-阮一峰](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
- oauth2根据使用场景不同，分成了4种模式
    - 授权码模式（authorization code）：授权码模式使用到了回调地址，是最为复杂的方式，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式
    - 简化模式（implicit）：不常用
    - 密码模式（resource owner password credentials）：password模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret。此时，access_token所包含的权限是用户本身的权限，而不是客户端的权限。
    - 客户端模式（client credentials）：client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请access_token，客户端有自己的client_id,client_secret对应于用户的username,password，而客户端也拥有自己的authorities，当采取client模式认证时，对应的权限也就是客户端自己的authorities。

### 客户端模式和密码模式

- 依赖

```xml
<!-- 不是starter,手动配置 -->
<dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
    <version>2.3.2.RELEASE</version>
</dependency>

<!-- 将token存储在redis中(存储在内存中则不需要) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

- 认证服务器和资源服务器配置（认证服务器基于Spring Security验证用户名/密码的配置省略）

```java
@Configuration
public class OAuth2ServerConfig {

    private static final String DEMO_RESOURCE_ID = "order";

    // 资源服务器配置
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        }

        // 将路径为/order/**的资源标识为order资源(资源ID)
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/order/**").authenticated(); // 配置order访问控制，必须认证过后才可以访问
        }
    }

    // 认证服务器配置(一般和资源服务器配置处于不同的项目)
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        RedisConnectionFactory redisConnectionFactory;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456"); // 测试用户的密码
            // 配置两个客户端,一个用于password认证一个用于client认证
            clients.inMemory()
                    // 基于client认证
                    .withClient("client_1")
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("select")
                    .authorities("oauth2")
                    .secret(finalSecret)
                    .and()
                    // 基于password认证
                    .withClient("client_2")
                    .resourceIds(DEMO_RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("select")
                    .authorities("oauth2")
                    .secret(finalSecret);
        }

        @Bean
        public TokenStore tokenStore() {
            // token保存在内存
            return new InMemoryTokenStore();

            // 需要使用 redis 的话，放开这里
            // return new RedisTokenStore(redisConnectionFactory);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST); // 允许的请求类型
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }

    }
}
```
- 资源`@GetMapping("/product/{id}")`和`@GetMapping("/order/{id}")`的定义省略
- 访问

```java
// 客户端模式（client credentials）
// 请求：http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
// 响应：{"access_token":"b7236239-7dee-404d-9e7d-14a035052be9","token_type":"bearer","expires_in":43196,"scope":"select"}
// 请求资源：http://localhost:8080/order/1?access_token=b7236239-7dee-404d-9e7d-14a035052be9

// 密码模式（resource owner password credentials）
// 请求：http://localhost:8080/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
// 响应：{"access_token":"e3b3083b-2afb-452a-9a5c-7349833c447f","token_type":"bearer","refresh_token":"8faf5956-4113-4192-a660-b62835707a1f","expires_in":43181,"scope":"select"}
// 请求资源：http://localhost:8080/order/1?access_token=e3b3083b-2afb-452a-9a5c-7349833c447f
```

### 授权码模式

#### 授权服务器

- 依赖

```xml
<dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
    <version>2.3.2.RELEASE</version>
</dependency>

<!-- 将token存储在redis中(存储在内存中则不需要) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
- 认证服务器配置（其他和基本类似）

```java
@Override
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
            .withClient("aiqiyi") // app_id
            .resourceIds(QQ_RESOURCE_ID)
            .authorizedGrantTypes("authorization_code", "refresh_token", "implicit") // 授权码模式
            .authorities("ROLE_CLIENT")
            .scopes("get_user_info", "get_fanslist") // 授权范围
            .secret("my-secret-888888") // app_secret
            .redirectUris("http://localhost:9090/aiqiyi/qq/redirect") // 重定向地址
            .autoApprove(true)
            .autoApprove("get_user_info")
            .and()
            .withClient("youku")
            .resourceIds(QQ_RESOURCE_ID)
            .authorizedGrantTypes("authorization_code", "refresh_token", "implicit")
            .authorities("ROLE_CLIENT")
            .scopes("get_user_info", "get_fanslist")
            .secret("secret")
            .redirectUris("http://localhost:9090/youku/qq/redirect");
}
```

#### 客户端

- 获取token

```java
@Autowired
RestTemplate restTemplate;

@RequestMapping("/aiqiyi/qq/redirect")
public String getToken(@RequestParam String code){
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
    params.add("grant_type","authorization_code");
    params.add("code",code);
    params.add("client_id","aiqiyi");
    params.add("client_secret","my-secret-888888");
    params.add("redirect_uri","http://localhost:9090/aiqiyi/qq/redirect"); // 此时地址必须和请求认证的回调地址一模一样
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
    
    ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
    String token = response.getBody();

    return token;
}
```

#### 访问资源

- 访问授权服务器：http://localhost:8080/oauth/authorize?client_id=aiqiyi&response_type=code&redirect_uri=http://localhost:9090/aiqiyi/qq/redirect
- 浏览器跳转到授权服务器登录页面：http://localhost:8080/login
- 认证通过，获取到授权码，认证服务器重定向到：http://localhost:9090/aiqiyi/qq/redirect?code=TLFxg1 (进入客户端后台服务)
- 客户端服务请求认证服务器获取token：http://localhost:8080/oauth/token
- 获取token成功：{"access_token":"3b017a2d-3e3d-4536-b978-d3d8e05f4b05","token_type":"bearer","refresh_token":"4593b664-9107-404f-8e77-2073515b42c9","expires_in":43199,"scope":"get_user_info get_fanslist"}
- 浏览器地址变为：http://localhost:9090/aiqiyi/qq/redirect?code=TLFxg1
- 携带 access_token 访问资源服务器：http://localhost:8080/qq/info/123456?access_token=3b017a2d-3e3d-4536-b978-d3d8e05f4b05

---

参考文章

[^2]: https://github.com/lexburner/oauth2-demo (Spring Security Oauth2)