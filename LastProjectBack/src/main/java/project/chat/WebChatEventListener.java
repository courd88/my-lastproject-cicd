package project.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;
import project.dto.ChatDto;
import project.service.ChatService;

//웹소켓 연결해제시 세션에서 사용자 정보가 있는 경우 퇴장 메시지(토픽)를 발행 
@Slf4j
@Component
public class WebChatEventListener {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private ChatService service;

	// 연결시에는 그냥 로그만 출력(처리는 이미 컨트롤러에서 입장문 처리함)
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		log.info("Received a new web socket connection");
	}

	// 연결이 끊길 시( 브라우저 종료, 나가기나 뒤로가기 버튼 클릭) 헤더에 있는 내용을 기준으로
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws Exception {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");
		String userNickname = (String) headerAccessor.getSessionAttributes().get("userNickname");
		String chatroomId = (String) headerAccessor.getSessionAttributes().get("chatroomId");
		String userImg = (String) headerAccessor.getSessionAttributes().get("userImg");
		log.info("채팅룸 ID" + chatroomId);		
		
		ChatDto chatMessage = new ChatDto();
		chatMessage.setType(ChatDto.MessageType.LEAVE);
		chatMessage.setUserId(username);
		chatMessage.setChatroomId(chatroomId);
		chatMessage.setUserImg(userImg);
		chatMessage.setMessage(userNickname + "님이 퇴장하셨습니다.");
		
		// 유저 아이디랑 채팅방ID가 다 있다면 = 동행채팅방
		if (username != null && chatroomId != null) {
			log.info("User Disconnected : " + username);

			service.insertMessageChatroom(chatMessage);

			// 구독채팅방에 데이터 전송
			messagingTemplate.convertAndSend("/topic/chatting/" + chatroomId, chatMessage);
		}
		
		// 유저 아이디만 있다면 글로벌 채팅방
		if (username != null && chatroomId == null) {

			// 채팅방ID가 없다면 글로벌 채팅방으로 메시지 보내버리고, 입력하기
			// 지금은 글로벌 채팅방에 보내버리지만, 채팅방이 여러개면 찾아서 보내줘야 할 듯.
			service.insertMessage(chatMessage);
			// 전체채팅방에 데이터 전송
			messagingTemplate.convertAndSend("/topic/chatting", chatMessage);

		}

	}

}
