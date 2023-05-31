package project.dto;

import lombok.Data;

@Data
public class WebcartoonDto {
	//웹카툰 정보
	int webcartoonIdx;				//웹카툰인덱스
	String webcartoonImg1;			//웹카툰이미지1
	String webcartoonContent1;		//웹카툰내용1
	String webcartoonCreatedTime;	//작성시간
	String webcartoonDeleted;		//삭제여부
	int webcartoonCnt;				//조회수
	int webcartoonRcmd;				//추천수
	String userId;					//유저아이디(외래키)
}
