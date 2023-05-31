package project.dto;

import lombok.Data;

@Data
public class FashionPicDto {

	//패션사진정보
	int fashionPicIdx;		//패션사진인덱스
	String fashionPicImg;	//패션사진이미지
	int fashionIdx;			//패션인덱스(외래키)
}
