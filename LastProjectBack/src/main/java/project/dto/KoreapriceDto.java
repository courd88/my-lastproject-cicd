package project.dto;

import lombok.Data;

@Data
public class KoreapriceDto {
	//물가체험
	int koreapriceIdx;			//한국물가상품인덱스
	String koreapriceProduct;	//상품명
	double koreapricePrice;		//상품가격
	String koreapriceCapacity;  //물가용량
}
