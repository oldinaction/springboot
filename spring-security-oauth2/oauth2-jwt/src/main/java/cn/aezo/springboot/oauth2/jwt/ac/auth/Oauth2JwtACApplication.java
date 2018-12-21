package cn.aezo.springboot.oauth2.jwt.ac.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

// 采用jwt非对称加密
// 1.生成 JKS Java KeyStore 文件(keytool为jdk工具, 一路回车,最后Y. 在当前目录生成的mytest.jks文件保存到认证服务器)
// keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass
// 2.导出公钥PUBLIC KEY部分(口令mypass)保存到pubkey.txt（存储在资源服务器）
// keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey

/*
 1.访问：http://localhost:8084/oauth/token?grant_type=password&username=user_1&password=123456&client_id=client_1&client_secret=my_secret
    (client_credentials模式无refresh_token返回 http://localhost:8084/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=my_secret)
 2.返回
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoiYWV6b2NuIiwidXNlcl9uYW1lIjoidXNlcl8xIiwic2NvcGUiOlsic2VsZWN0Il0sImV4cCI6MTU0NTQxNTcwOCwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiI4OTM0MGY0ZC1mMWE4LTQ1ZTMtOTE3Ni1kMzY0ZWE3MmY5ODYiLCJjbGllbnRfaWQiOiJjbGllbnRfMSJ9.A_do_6S1A7FKUWzdE1p7x6pBvPFNNOFL5JuDwUvfJOY",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoiYWV6b2NuIiwidXNlcl9uYW1lIjoidXNlcl8xIiwic2NvcGUiOlsic2VsZWN0Il0sImF0aSI6Ijg5MzQwZjRkLWYxYTgtNDVlMy05MTc2LWQzNjRlYTcyZjk4NiIsImV4cCI6MTU0Nzk2NDUwOCwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiJhNmM5NTQ4NS0yNTUzLTRlZjMtYWUwMi0wY2JjZjg2ZmQ1N2EiLCJjbGllbnRfaWQiOiJjbGllbnRfMSJ9.OoNAbGd62PxKPzzH03ASDUus-aYcWT5ktqaHMkezha0",
    "expires_in": 43200,
    "scope": "select",
    "license": "aezocn",
    "jti": "89340f4d-f1a8-45e3-9176-d364ea72f986"
}
3.访问 http://localhost:8084/order/1?access_token=xxxxxx
*/
@SpringBootApplication
public class Oauth2JwtACApplication {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "ac");

        SpringApplication.run(Oauth2JwtACApplication.class, args);
    }

}
