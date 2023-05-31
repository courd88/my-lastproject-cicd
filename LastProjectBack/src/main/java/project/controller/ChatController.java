package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import project.dto.ChatDto;
import project.dto.ChatroomUserDto;
import project.service.ChatService;

@Slf4j
@Controller
public class ChatController {

	@Autowired
	ChatService service;
	
	
	//-------------------------글로벌 채팅------------------------------------//
	//("/chat.addUser")로 요청이 오면
	//("/topic/chatting") 으로 보내버림(chatting)밖에 없으니까 글로벌 채팅방이라고 보면 됨. 
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/chatting")
	public ChatDto addUser(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception{
		
		//(헤더)세션에 사용자 이름 저장, WebSocketEventListener에서 연결 끊길 시 사용자 식별위해 사용
		headerAccessor.getSessionAttributes().put("username", chatDto.getUserId());
		headerAccessor.getSessionAttributes().put("userNickname", chatDto.getUserNickname());
		headerAccessor.getSessionAttributes().put("userImg", chatDto.getUserImg());
		
		chatDto.setMessage(chatDto.getUserNickname() + "님이 입장하셨습니다.");
		service.insertMessage(chatDto);
		List<ChatDto> list = service.selectMessage();
		chatDto.setHistory(list);
		
		return chatDto;
	}
	
	//("/chat.sendMessage")로 요청이 오면
	//("/topic/chatting") 으로 보내버림(chatting)밖에 없으니까 글로벌 채팅방이라고 보면 됨.
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/chatting")//리턴을 통해 토픽발행
	public ChatDto sendMessage(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception{
				
		//DB에 입력
		service.insertMessage(chatDto);
		
		System.out.println(">>>>>>>>채팅입력내역"+chatDto);
		
		return chatDto;
	}
	
	//---------------------------동행 채팅------------------------------------//
		//("/chat.addUser/{채팅방UUID}")로 요청이 오면
		//("/topic/chatting/{채팅방UUID}") 으로 보내버림
		@MessageMapping("/chat.addUser/{chatroomId}")
		@SendTo("/topic/chatting/{chatroomId}")
		public ChatDto addUserChatroom(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception{
			
			//(헤더)세션에 사용자 이름, 채팅방ID 저장, WebSocketEventListener에서 연결 끊길 시 사용자 식별위해 사용
			headerAccessor.getSessionAttributes().put("username", chatDto.getUserId());
			headerAccessor.getSessionAttributes().put("userNickname", chatDto.getUserNickname());
			headerAccessor.getSessionAttributes().put("userImg", chatDto.getUserImg());
			headerAccessor.getSessionAttributes().put("chatroomId", chatDto.getChatroomId());
			
			
			//chatroom_user테이블에 chatroomId로 조회해서 userId가 없다면 insert입력
			ChatroomUserDto chtroomUserDto = new ChatroomUserDto();
			chtroomUserDto.setChatroomId(chatDto.getChatroomId());
			chtroomUserDto.setUserId(chatDto.getUserId());
			List<ChatroomUserDto> chatroomUserDtoList = service.selectListByChatroomIdAndUserId(chtroomUserDto);
			
			//조회된 리스트의 개수가 0이면, 현재 접속하려는 chatroomId에 userId가 없다는 뜻,
			//chatroom_user 테이블에 데이터를 추가입력 해줘야 함.
			if ( chatroomUserDtoList.size() == 0) {

				service.insertUserIdToChatroomUser(chtroomUserDto);
			}
			
			chatDto.setMessage(chatDto.getUserNickname() + "님이 입장하셨습니다.");
			service.insertMessageChatroom(chatDto);
			List<ChatDto> list = service.selectMessageChatroom(chatDto.getChatroomId());
			chatDto.setHistory(list);
			
			return chatDto;
		}
		
		//("/chat.sendMessage/{채팅방UUID}")로 요청이 오면
		//("/topic/chatting/{채팅방UUID}") 으로 보내버림.
		@MessageMapping("/chat.sendMessage/{chatroomId}")
		@SendTo("/topic/chatting/{chatroomId}")//리턴을 통해 토픽발행
		public ChatDto sendMessageChatroom(@Payload ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception{
					
			//DB에 입력
			service.insertMessageChatroom(chatDto);
			
			System.out.println(">>>>>>>>채팅입력내역"+chatDto);
			
			return chatDto;
		}
		
	
}
