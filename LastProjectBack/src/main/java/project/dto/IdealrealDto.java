package project.dto;

import lombok.Data;

@Data
public class IdealrealDto {
	//이상과현실
	int idealrealIdx;				//이상과현실 게시글 인덱스
	String idealrealCreatedTime;	//작성시간
	String idealrealTitle;			//제목
	String idealrealContent;		//내용
	int idealrealRcmd;				//추천수
	int idealrealCnt;				//조회수
	String idealrealDeleted;		//삭제여부
	String idealrealIdealImg;		//이상사진
	String idealrealRealImg;		//현실사진
	String userId;					//유저아이디(외래키)
}
