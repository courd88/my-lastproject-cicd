package project.dto;

import lombok.Data;

@Data
public class WebcartoonCommentDto {
	//웹카툰 댓글정보
	int webcartoonCommentIdx;		//웹카툰댓글인덱스
	String webcartoonCommentContent;//웹카툰댓글내용
	String webcartoonCommentTime;	//웹카툰댓글작성시간
	String userId;					//유저아이디(외래키)
	int webcartoonIdx;				//웹카툰인덱스(외래키)
}
