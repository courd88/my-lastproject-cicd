package project.dto;

import lombok.Data;

@Data
public class IdealworldcupDto {
	//이상형월드컵정보
	int idealworldcupIdx;				//이상형월드컵글 인덱스
	String idealworldcupCreatedTime;	//작성시간
	String userId;						//유저아이디(외래키)
	int rawinfoIdx;						//로우정보인덱스(외래키)
	
}
