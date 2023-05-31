package project.dto;

import lombok.Data;

@Data
public class QnaDto {
	//문의사항
	int qnaIdx;				//문의사항인덱스
	String qnaTitle;		//문의사항제목
	String qnaContent;		//문의사항내용
	String qnaImg;			//문의사항사진
	int qnaCnt;				//조회수
	String qnaCreatedTime;	//작성일
	String qnaDeleted;		//삭제여부
	String userId;			//유저아이디(외래키)
}
