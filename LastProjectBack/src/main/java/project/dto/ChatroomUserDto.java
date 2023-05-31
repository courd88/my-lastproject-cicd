package project.dto;

import lombok.Data;

@Data
public class ChatroomUserDto {

	private int chatroomUserIdx;	//채팅방유저idx
	private String userId;			//유저아이디(외래키)
	private String chatroomId;		//채팅방UUID
	private String accompanyTitle;	//채팅방 제목
	private int accompanyIdx;		//동행방 idx
}
