package cn.aezo.springboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@EnableScheduling
@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate; // Spring-WebSocket内置的一个消息发送工具，可以将消息发送到指定的客户端或所有客户端

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 功能类似@RequestMapping，定义消息的基本请求(客户端发送消息)。拼上定义的客户端请求的前缀/app，最终客户端请求为/app/send
    @MessageMapping("/send")
    // @SendTo发送消息给所有人，@SendToUser只能推送给请求消息的那个人
    @SendTo("/topic/send")
    public Message send(Message message) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.date = df.format(new Date());
        if (message.toUser != null && !"".equals(message.toUser)) {
            // 给某个人发送消息，此时@SendTo被忽略
            simpMessagingTemplate.convertAndSendToUser(message.toUser, "/private", message); // 发送到/user/${message.toUser}/private通道
            return null;
        } else {
            return message;
        }
    }

    // 定时1秒执行执行一次，向/topic/callback通道发送信息
    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/callback")
    public Object callback() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // convertAndSend(destination, payload); //将消息广播到特定订阅路径中，类似@SendTo
        // convertAndSendToUser(user, destination, payload); //将消息推送到固定的用户订阅路径中，类似@SendToUser
        simpMessagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
        return "callback"; // 此处返回什么不重要
    }
}
