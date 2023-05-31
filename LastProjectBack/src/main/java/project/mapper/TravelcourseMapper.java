package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.TravelcourseDetailDto;
import project.dto.TravelcourseDetailListDto;
import project.dto.TravelcourseDto;
import project.dto.TravelcourseListDto;

@Mapper
public interface TravelcourseMapper {

	//1-1.여행코스 입력(제목, 시작일, 끝일, 작성자)
	int insertCourse(TravelcourseDto travelcourseDto) throws Exception;

	//1-1-1. 여행코스 DAY별 입력 (DAY, Day설명, 여행코스idx)
	int insertCourseDay(TravelcourseDetailListDto travelcourseDetailListDto) throws Exception;
	
	//1-1-2. 여행코스 DAY별 Dayinfo별 입력 (order, lat, lng, placeName, listIdx)
	int insertCourseDayinfo(List<TravelcourseDetailDto> travelcourseDetailDto) throws Exception;
	
	//1-2. 여행코스 list 조회
	List<TravelcourseListDto> selectCourseList() throws Exception;
	
	//1-2-1. 여행코스 개별 detail 조회
	TravelcourseListDto selectCourseDetail(int travelcourseDtoIdx) throws Exception;

	

	int updateCourse(TravelcourseDto travelcourseDto) throws Exception;

	int deleteCourse(int travelcourseIdx) throws Exception;
}
