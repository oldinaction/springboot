package cn.aezo.springboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by smalle on 2017/7/23.
 */
@Component
@RabbitListener(queues = "hello")
public class Consumer {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("Consumer: " + msg);
    }
}
