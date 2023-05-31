package project.dto;

import lombok.Data;

@Data
public class GlobalchatDto {

	int globalchatIdx;			//글로벌채팅인덱스
	String globalchatContent;	//채팅내용
	String globalchatTime;		//채팅시간
	String userId;				//유저아이디(외래키)
	
}
