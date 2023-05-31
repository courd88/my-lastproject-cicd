package project.dto;

import lombok.Data;

@Data
public class TravelcourseChatDto {
	//여행코스 채팅내용정보
	int travelcourseChatIdx;			//채팅내용인덱스
	String travelcourseChatContent;		//채팅내용
	String travelcourseChatTime;		//채팅시간
	int travelcourseIdx;				//여행코스인덱스(외래키)
	String userId;						//유저아이디(외래키)
}
