package project.dto;

import lombok.Data;

@Data
public class TravelcourseDetailDto {
	//여행코스세부내용정보
	int travelcourseDetailIdx;			//여행코스세부내용인덱스
	int orders;						//여행코스 순서
	double lat;						//x좌표
	double lng;						//y좌표
	String placeName;				//여행장소
	int travelcourseIdx;				//여행코스인덱스(외래키)
	int travelcourseListIdx;			//리스트 인덱스
//	int travelcourseDetailOrder;		//여행코스 순서
//	int travelcourseDetailLocationx;	//x좌표
//	int travelcourseDetailLocationy;	//y좌표
//	String travelcourseDetailPlace;		//여행장소
}
