package project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration						//스프링의 설정 클래스 지정.
@EnableWebSocketMessageBroker		//STOMP 를 웹소켓의 메시지 브로커로 사용하겠다.
public class WebChatConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")	//stomp 접속 Url -> /ws
		.setAllowedOriginPatterns("*")		//브라우저에서 교차 출처 요청이 허용되는 출처를 패턴으로 설정
		.withSockJS(); 						//SockJs를 사용해서 WebSocket을 지원하지 않는 브라우저는 대체 프로토콜 사용
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	
		//메시지 구독하는 요청 url -> 메시지 받을 때	//"/Topic" 이라는 주소 많이 쓰는 듯
		registry.enableSimpleBroker("/topic");			//구독(감사님은 /topic)
		
		//메시지 발행하는 요청 url -> 메시지 보낼 때(강사님은 /app)
		registry.setApplicationDestinationPrefixes("/app");
	}
}
