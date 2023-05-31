package project.dto;

import lombok.Data;

@Data
public class AccompanyChatDto {
	//동반채팅정보
	int accompanyChatIdx;			//채팅인덱스
	String accompanyChatContent;	//채팅내용
	String accompanyChatTime;		//채팅시간
	String userId;					//유저아이디(외래키)
	int accompanyIdx;				//동행게시글인덱스(외래키)
}
