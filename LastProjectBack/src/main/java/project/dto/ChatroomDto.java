package project.dto;

import lombok.Data;

@Data
public class ChatroomDto {

	private String chatroomId;		//채팅방UUID
	private int accompanyIdx;		//동행글idx
	private String accompanyTitle; 	//동행글 제목
	private int accompanyNumbers;	//동행인원수
	
}
