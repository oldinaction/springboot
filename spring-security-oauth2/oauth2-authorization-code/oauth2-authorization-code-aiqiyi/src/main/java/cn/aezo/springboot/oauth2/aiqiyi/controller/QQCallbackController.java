package cn.aezo.springboot.oauth2.aiqiyi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class QQCallbackController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/aiqiyi/qq/redirect")
    public String getToken(@RequestParam String code){
        log.info("receive code {}", code);

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

        log.info("token => {}", token);
        return token;
    }

}
