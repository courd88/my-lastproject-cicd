package project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.ChatDto;
import project.dto.ChatroomDto;
import project.dto.ChatroomUserDto;
import project.dto.UserDto;
import project.service.ChatService;

@Slf4j
@RestController
public class RestChatController {

	@Autowired
	ChatService service;

	// 테스트완료, 동행글idx 있고, 방이 없으면 생성해서 채팅방 조회 잘 됨.
	// 방 있으면 채팅방 조회 잘 됨.
	@GetMapping("/chatroom/{accompanyIdx}")
	public ResponseEntity<String> selectChatroomIdx(@PathVariable("accompanyIdx") int accompanyIdx) throws Exception {

		String 채팅UUID = service.selectChatroomByAccompanyId(accompanyIdx);

		if (채팅UUID == null) {

			// 방하나 만들어 줌.
			ChatroomDto chatroom = new ChatroomDto();
			String chatroomId = UUID.randomUUID().toString();

			chatroom.setChatroomId(chatroomId);
			chatroom.setAccompanyIdx(accompanyIdx);

			// 만들고
			service.insertChatroom(chatroom);

			// 다시 조회
			채팅UUID = service.selectChatroomByAccompanyId(accompanyIdx);

		} else {
			// 있으면 조회됐겠지?
		}

		return ResponseEntity.status(HttpStatus.OK).body(채팅UUID);
	}

	@RequestMapping("/chatroombyuser")
	public ResponseEntity<List<ChatroomDto>> selectChatroomByUserId(@RequestBody UserDto userDto) throws Exception {

		System.out.println(userDto);
		String userId = userDto.getUserId();
		System.out.println(userId);

		// 유저Id기준 채팅방 목록 조회.
		List<ChatroomDto> list = service.selectChatroomByUserId(userId);

		// 안읽은 채팅개수랑 채팅방 목록 담을 예정
		List<Map<String, Object>> listMap = new ArrayList<>();

		if (list == null) {
			System.out.println("userID로 조회되는 채팅방 없음");
		}

		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@DeleteMapping("/chatroom/delete/{chatroomId}/{userId}")
	public ResponseEntity<String> deleteChatroom(@PathVariable("chatroomId") String chatroomId,
			@PathVariable("userId") String userId) throws Exception {

		ChatroomUserDto chatroomUserDto = new ChatroomUserDto();
		chatroomUserDto.setChatroomId(chatroomId);
		String originUserId = userId.replace("-", ".");
		chatroomUserDto.setUserId(originUserId);
		System.out.println(chatroomUserDto.toString());

		// 삭제가 잘 됐는지 어떻게 확인 할 수 있지?
		int deletedCnt = service.deleteChatroom(chatroomUserDto);

		if (deletedCnt == 0) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("삭제안됨");
		} else {

			return ResponseEntity.status(HttpStatus.OK).body(chatroomId);
		}
	}
	
	@GetMapping("/chatroom/unreadmessage/{chatroomId}/{userId}")
	public ResponseEntity<Integer> selectUnreadMessage(@PathVariable("chatroomId") String chatroomId,
			@PathVariable("userId") String userId) throws Exception {
		
		ChatDto chatDto = new ChatDto();
		chatDto.setChatroomId(chatroomId);
		String originUserId = userId.replace("-", ".");
		chatDto.setUserId(originUserId);
		
		String lastMsgTime = service.selectLastMsgTime(chatDto);
		ChatDto newChatDto = new ChatDto();
		newChatDto.setCreatedDt(lastMsgTime);
		newChatDto.setChatroomId(chatroomId);
		int unreadMsgCnt = service.selectUnreadMsgCnt(newChatDto);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(unreadMsgCnt);
		
	}
	

}