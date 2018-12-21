package cn.aezo.springboot.oauth2.jwt.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

// 采用jwt对称加密
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
4.刷新token: http://localhost:8084/oauth/token?grant_type=refresh_token&client_id=client_1&client_secret=my_secret&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoiYWV6b2NuIiwidXNlcl9uYW1lIjoidXNlcl8xIiwic2NvcGUiOlsic2VsZWN0Il0sImF0aSI6ImQ0YjFmZjAzLTZmMzMtNGMyNS04MWFiLTQ1YWYwMjg5NmQ4NCIsImV4cCI6MTU0Nzk2NTE2MywiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiJkMDc0MzJiYy1kNDk5LTQ1ZDgtYTY2My1lMWQ0ZDg0NmNhMTQiLCJjbGllbnRfaWQiOiJjbGllbnRfMSJ9.F0ZaPI-pDXm98MFT2gXtet82Pfbc-Woh5yBp_SPbFtk
*/
@SpringBootApplication
public class Oauth2JwtSCApplication {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "sc");

        SpringApplication.run(Oauth2JwtSCApplication.class, args);
    }

}
