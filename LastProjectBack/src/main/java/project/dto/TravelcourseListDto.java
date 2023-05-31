package project.dto;

import lombok.Data;

@Data
public class TravelcourseListDto {

	private int travelcourseIdx;
	private String travelcourseCreatedtime;
	private int travelcourseCnt;
	private int travelcourseRcmd;
	private String travelcourseDeleted;
	private String userId;
	private String userNickname;
	private String userImg;
	private String travelcourseTitle;
	private String travelcourseStartDate;
	private String travelcourseEndDate;
	private int travelcourseListIdx;
	private int day;
	private String dayDescription;
	private int travelcourseDetailIdx;
	private int orders;
	private double lat;
	private double lng;
	private String placeName;
	
}
