package project.dto;

import lombok.Data;

@Data
public class AccompanyDto {
	//동행페이지 + 채팅
	int accompanyIdx;				//게시글인덱스
	String accompanyTitle;			//제목
	String accompanyContent;		//내용
	String accompanyImage;			//사진
	int accompanyNumbers;			//동행인원
	String accompanyStartTime;		//시작일
	String accompanyEndTime;		//종료일
	int accompanyCnt;				//조회수
	String accompanyCreatedTime;				//작성시간
	String userId;					//유저아이디(외래키)
	String accompanyRegion;			//지역
	String accompanyDeleted;		//삭제여부
}
