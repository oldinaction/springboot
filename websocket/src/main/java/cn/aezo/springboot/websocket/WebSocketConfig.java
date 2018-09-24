package cn.aezo.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/user"); // 表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
		config.setApplicationDestinationPrefixes("/app"); // 定义websocket前缀，指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
		config.setUserDestinationPrefix("/user"); // 定义一对一(点对点)推送前缀，默认是`/user`，可省略此配置
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws/aezo") // 定义stomp端点，供客户端使用
				.setAllowedOrigins("*")
				.withSockJS(); // 开启SockJS支持
	}
}
