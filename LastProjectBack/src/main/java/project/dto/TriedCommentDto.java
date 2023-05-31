package project.dto;

import lombok.Data;

@Data
public class TriedCommentDto {
	//어디까지댓글정보
	int triedCommentIdx;		//어디까지댓글 인덱스
	String triedCommentContent; //어디까지댓글내용
	int triedCommentRcmd;		//어디까지추천수
	int triedIdx;				//어디까지게시글 인덱스
	String userId;				//유저아이디(외래키)
}
