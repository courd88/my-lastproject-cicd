package project.dto;

import lombok.Data;

@Data
public class FashionDto {
	//패션정보(크롤링)
	int fashionIdx;			//패션인덱스
	String fashionDate;		//패션등록날짜
	int fashionTemper;		//해당날의 온도
	String fashionWeather;	//해당날의 날씨
}
