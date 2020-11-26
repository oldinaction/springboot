package cn.aezo.springboot.busi.pay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller
@SpringBootApplication
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }

    // 默认端口8080, 访问http://localhost:8080/
    @RequestMapping("/")
    String home() {
        return "Hello Pay...";
    }

    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                "2016101600697472",
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCM4q2phXhR3vyW8/bvd4Z0hrI+OKBTQ/5VvilLL6o9jKE2LuUbHr0HPzlFI+id/aLlbITla2w8QVLhhzrRJHT4j/M9aqvb7WbCKvCFv9q24JGz5W68Z5W92UjL/d9Yc+sj0AvemPvmF2Vuvzzv4c45CtqG/1M+qlWzeIhteTUAx1+VZ8ycSaPKIqCVfMeWQM08MN2jTzqAR6WX6zl9s5QYo1txVxwgdvMAjVWV2HQtpA/7I5a6SOgjgxBAENqtORzr9fAtp+/zatXJxfGWQ7ocOYwmMSWexWLt1OLpNKHpT81+rUpqaO7u9TYgCM6yfZKd67pow12EUBkn7XUePCyTAgMBAAECggEAd/xqGLEyas+1Yb5/NS0flshMr488hA2pY07kDkOXAnptph7EQpeoN67R0J+ncj7cZ/ZHLLiTYrHjq22nWw1ojXTTnR/nJBQI6x7034Qo+o1BqeuhptW1gw214P3jJIr83PBH25mBF1IfsfUvKcGvB4fF2ylCtWEOQh5KWqnayUDADSxXmO7SEgP2mXrU0iKs20SQGeBHnQXRxXWHaJDvEMhg9H55axymyyNHhyz4a2sO3bBv/+tgH8fJzMys5X+rKK1XCzUd4N7WgeRQAuEaTFya/Vkj4pzfrm3pSdIz30VhJugtuX9/IxU/SxFnEIDBh6vLZWPqjNrqls1fgJe4gQKBgQDBiJk3KXnSCaryI50SU42ZpnomL/nqUegmI1jAoRAPdXhuhTBjkxeNt8xAb4tMQx8r8CYSmphkGKCo1AR6lZivj3wZ+B0jbzFq1VpEilDI59UfW3iK1duzUmxlfqz1ermSW5LQtwK45+N6i1cDqs1oyhrImnrZXR8r59DzpyL7wQKBgQC6W9dTrPe8gMFSHD645waXFO80B3piYRpsxwvyxKY8LN+F9qcNyC+geGwXGMl7TGBOkm9qhRVVTfaqMzIL3RAHiZ1hI4VRSfUpxFXx9+VcS2rYNbYw5nARHJ5cT7lHYgJgRM3ZXVTAaUjX5sqF+GbiKP35rO7MY2DxDv+YWQvNUwKBgHDJ3REq2NYx0DCB1N6DVzi+rt1mhG2tAnCHlQfeVe/MdD1k3ciKRuXOwfijiTkOvTHsKSQaeMmNGMSLm3idinh2VMsFsGWgPdqXMJ5VRbNweOJnep7e98VQOeAErWP6+/9x4uREZA/DBO28GyAGAOQbNPTIIuRewvXhigMV2uuBAoGAC8T/HWbAIqSTEQd9opncnbARx1xti0cMrzAG/icTRJuk5kYEmOc6sszILtE+8E2Li4rOBJExHrkUyfz/vSYR/Bfh25+trMuZ8zBWKTj5wFF3Bv6lagMzsrD3A2d5kRuMgN33jjHJx3kew5vs0Hc2sDPcjmcwkbH5DmBfHhUJnMcCgYA4h4riHklVvhiDMeqNLZNXpyGNiDRSTvoEnGGH3Mc5Gqh1BXFUdEhpFBIgzL4r9+7GWc+kYidNTOJSIwdblYrdD+hrB/ZHWRc4E8V6arwG+tg60HYhKilACRtG84zklNNcJToQ6jYC0S6AWajFSv9F4xA4I9alhi/Z4j6OSQdVnQ==",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhhlsCy5i69i601mRh3HU5cvUq7XaeD7nf7HHsVDQtTxnaL1JOO8G0IMBrFOP0su6PmbOzOYTH03UUOTz1i72w9lKX+ipEsw+w5VsikJZF3+/ABhS5i1+sOGdPmVGq8VrFRZbFBUC56t6TT1gc5ecfi+2AadnjveD7AFHxRxwUHCZoZAl/8DJO9j4t9S2kuFjxFopThJAZYHvmKZx/Y089ENFowrbTV62A+S1/6EbXAEpg41c1Z5eOAy8m2NBu0G++ZbN5KikrOWxNY6/5Mq1R68d/U4FG/WwH4pBjq9jrV10jbcoehsi+XmGhlUded1hkovU7IdW41b59/gFj6lPzwIDAQAB",
                "RSA2");
        return alipayClient;
    }
}
