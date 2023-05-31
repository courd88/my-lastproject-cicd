package project.dto;

import lombok.Data;

@Data
public class RawinfoDto {
	//어디까지 로우데이터정보
	int rawinfoIdx;				//로우정보인덱스
	String rawinfoName;			//이름
	String rawinfoImg;			//사진
	String rawinfoDescription;	//설명
	int triedCategoryIdx;		//어디까지게시판카테고리인덱스(외래키)
}
