package project.dto;

import java.util.List;

import lombok.Data;

@Data
public class TravelcourseDetailListDto {
	
	private int travelcourseListIdx;
	private int day;
	private List<TravelcourseDetailDto> dayinfo;
	private String dayDescription;
	private int travelcourseIdx;
}
