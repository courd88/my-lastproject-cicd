package project.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.ChatDto;
import project.dto.ChatroomDto;
import project.dto.ChatroomUserDto;
import project.mapper.ChatMapper;

@Service
public class ChatService {

	@Autowired
	private ChatMapper mapper;

	// 0. 채팅방 입장 전 세팅
	// 채팅방에 동행글Idx가 있는지 찾기
	public String selectChatroomByAccompanyId(int accompanyIdx) throws Exception {
		return mapper.selectChatroomByAccompanyId(accompanyIdx);
	}

	// 채팅방이 없을 때 방 만들기
	public void insertChatroom(ChatroomDto chatroomDto) throws Exception {
		mapper.insertChatroom(chatroomDto);
		// 추가로 chatroom_user 테이블에 입력해줘야 됨. 그래야 아래에서 내가 지금 등록된 채팅방이 뭔지 알 수 있음.
	}

	// 채팅방리스트 ByUserId
	public List<ChatroomDto> selectChatroomByUserId(String userId) throws Exception {
		return mapper.selectChatroomByUserId(userId);
	}

	// 채팅방별 유저아이디 ByChatroomId
	public List<ChatroomUserDto> selectUserIdByChatroomId(String chatroomId) throws Exception {
		return mapper.selectUserIdByChatroomId(chatroomId);
	}

	// 조회 By 채팅방ID와 유저ID
	public List<ChatroomUserDto> selectListByChatroomIdAndUserId(ChatroomUserDto chatroomUserDto) throws Exception {
		return mapper.selectListByChatroomIdAndUserId(chatroomUserDto);
	}

	// 채팅방_유저 테이블에 유저아이디, 채팅방ID 추가
	public void insertUserIdToChatroomUser(ChatroomUserDto chatroomUserDto) throws Exception {
		mapper.insertUserIdToChatroomUser(chatroomUserDto);
	}

	// 유저채팅목록에서 채팅방 삭제
	public int deleteChatroom(ChatroomUserDto chatroomUserDto) throws Exception {
		return mapper.deleteChatroom(chatroomUserDto);
	}

	// 안읽은 메시지 개수 조회
	// 떠난시간 찾기
	public String selectLastMsgTime(ChatDto chatDto) throws Exception {
		return mapper.selectLastMsgTime(chatDto);
	}
	
	//안읽은 메시지 개수
	public int selectUnreadMsgCnt(ChatDto chatDto) throws Exception{
		return mapper.selectUnreadMsgCnt(chatDto);
	}
	
	
	
	
	// 1. 글로벌 채팅 메시지 조회/입력
	// 채팅방 입장시 출력되는 이전 채팅내역 개수
	private final int CONST_MAX_MESSAGE_COUNT = 10;

	public List<ChatDto> selectMessage() throws Exception {
		return mapper.selectMessage(CONST_MAX_MESSAGE_COUNT);
	}

	public void insertMessage(ChatDto chatDto) throws Exception {
		LocalDateTime now = LocalDateTime.now();
		chatDto.setCreatedDt(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		mapper.insertMessage(chatDto);
	}

	// 2. 동행 채팅 메시지 조회/입력
	public List<ChatDto> selectMessageChatroom(String chatroomId) throws Exception {
		return mapper.selectMessageChatroom(chatroomId);
	}

	public void insertMessageChatroom(ChatDto chatDto) throws Exception {
		LocalDateTime now = LocalDateTime.now();
		chatDto.setCreatedDt(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		mapper.insertMessageChatroom(chatDto);
	}
}
