package project.dto;

import lombok.Data;

@Data
public class TiermakerDto {

	//티어메이커 정보
	int tiermakerIdx;			//티어메이커인덱스
	String tiermakerDate;		//등록날짜
	String userId;				//유저아이디(외래키)
}
