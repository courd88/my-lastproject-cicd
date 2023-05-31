package project.dto;

import java.util.List;

import lombok.Data;

@Data
public class TravelcourseDto {
	//여행코스정보
	private int travelcourseIdx;				//여행코스인덱스
	private String travelcourseCreatedtime;		//여행코스작성일
	private int travelcourseCnt;				//여행코스조회수
	private int travelcourseRcmd;				//여행코스추천수
	private String travelcourseDeleted;			//삭제여부
	private String userId;						//유저아이디(외래키)
	private String travelcourseTitle; 			//여행코스제목
	private String travelcourseStartDate;		//여행 시작일
	private String travelcourseEndDate;         //여행 끝일
	private List<TravelcourseDetailListDto> travelcourseDetailListDto; //여행코스 일정 세부내역
}
