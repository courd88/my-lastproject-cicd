package project.dto;

import java.util.List;
import lombok.Data;

@Data
public class ChatDto {

	public enum MessageType {
		JOIN, CHAT, LEAVE
	}
	
	private MessageType type;
	private int chatIdx;	//chatIdx
	private String message;	//message
	private String userId;	//userId
	private String userNickname;	//유저 닉네임
	private String userImg;			//유저 프사
	private String createdDt;//createdDt
	private String chatroomId; //채팅룸 아이디 (외래키)
	private List<ChatDto> history;
	
}
