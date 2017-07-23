package cn.aezo.springboot.rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by smalle on 2017/7/23.
 */
@Component
public class Provider {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    // 发送消息
    public void send() {
        String context = "hello " + new Date();
        System.out.println("Provider: " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
