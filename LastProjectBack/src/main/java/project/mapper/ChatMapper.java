package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.ChatDto;
import project.dto.ChatroomDto;
import project.dto.ChatroomUserDto;

@Mapper
public interface ChatMapper {
	
	//채팅방UUID 검색
	public String selectChatroomByAccompanyId(int accompanyIdx) throws Exception;
	
	//채팅방 없으면 채팅방 만들기
	public void insertChatroom(ChatroomDto chatroomDto) throws Exception;
	
	//채팅방 리스트 ByUserId
	public List<ChatroomDto> selectChatroomByUserId(String userId) throws Exception;
	
	//채팅방Id 기준으로 UserId 조회 (유저가 채팅방에 있는지 여부)
	public List<ChatroomUserDto> selectUserIdByChatroomId(String chatroomId) throws Exception;
	
	//채팅방 목록 조회 By 채팅방ID, 유저Id
	public List<ChatroomUserDto> selectListByChatroomIdAndUserId(ChatroomUserDto chatroomUserDto) throws Exception;
	
	//채팅방_유저 테이블에 유저아이디, 채팅방ID 추가
	public void insertUserIdToChatroomUser(ChatroomUserDto chatroomUserDto) throws Exception;
	
	//등록한 채팅방 삭제(퇴장)
	public int deleteChatroom(ChatroomUserDto chatroomUserDto) throws Exception;
	
	//안읽은 메시지 개수 조회
	//마지막 채팅 조회
	public String selectLastMsgTime(ChatDto chatDto) throws Exception;
	
	//안읽은 메시지 개수
	public int selectUnreadMsgCnt(ChatDto chatDto) throws Exception;
	
	
	//1.글로벌 채팅 입력/조회(최근 채팅 10개)
	public List<ChatDto> selectMessage(int num) throws Exception;
	public void insertMessage(ChatDto chatDto) throws Exception;
	
	//2.동행 채팅 입력/조회
	public List<ChatDto> selectMessageChatroom(String chatroomId) throws Exception;
	public void insertMessageChatroom(ChatDto chatDto) throws Exception;
	
	
}
