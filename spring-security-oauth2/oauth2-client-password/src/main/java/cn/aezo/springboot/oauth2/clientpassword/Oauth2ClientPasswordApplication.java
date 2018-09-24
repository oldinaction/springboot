package cn.aezo.springboot.oauth2.clientpassword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 客户端模式（client credentials）
// 请求：http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
// 响应：{"access_token":"b7236239-7dee-404d-9e7d-14a035052be9","token_type":"bearer","expires_in":43196,"scope":"select"}

// 密码模式（resource owner password credentials）
// 请求：http://localhost:8080/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
// 响应：{"access_token":"e3b3083b-2afb-452a-9a5c-7349833c447f","token_type":"bearer","refresh_token":"8faf5956-4113-4192-a660-b62835707a1f","expires_in":43181,"scope":"select"}
@SpringBootApplication
public class Oauth2ClientPasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ClientPasswordApplication.class, args);
    }

}
