package project.dto;

import lombok.Data;

@Data
public class QnaCommentDto {

	//문의사항 댓글
	int qnaCommentIdx;			//문의사항댓글인덱스
	String qnaCommentContent;	//댓글내용
	String qnaCommentTime;		//작성일
	int qnaIdx;					//문의사항인덱스(외래키)
	String userId;				//유저아이디(외래키)
}
