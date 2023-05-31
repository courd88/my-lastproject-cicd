package project.dto;

import lombok.Data;

@Data
public class IdealrealCommentDto {
	//이상과현실 댓글
	int idealrealCommentIdx;			//댓글인덱스
	String idealrealCommentCreatedTime; //작성시간
	String idealrealCommentContent;		//댓글내용
	int idealrealIdx;					//이상과현실게시글인덱스(외래키)
	String userId;						//유저아이디(외래키)
	
}
