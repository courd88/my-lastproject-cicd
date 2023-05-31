package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.TravelcourseDetailDto;
import project.dto.TravelcourseDetailListDto;
import project.dto.TravelcourseDto;
import project.dto.TravelcourseListDto;
import project.mapper.TravelcourseMapper;

@Service
public class TravelcourseService {

	@Autowired
	private TravelcourseMapper mapper;

	

	//1-1.여행코스 입력(제목, 시작일, 끝일, 작성자)
	public int insertCourse(TravelcourseDto travelcourseDto) throws Exception {
		return mapper.insertCourse(travelcourseDto);
	}
	
	//1-1-1. 여행코스 DAY별 입력 (DAY, Day설명, 여행코스idx)
	public int insertCourseDay(TravelcourseDetailListDto travelcourseDetailListDto) throws Exception{
		return mapper.insertCourseDay(travelcourseDetailListDto);
	}
	
	//1-1-2. 여행코스 DAY별 Dayinfo별 입력 (order, lat, lng, placeName, listIdx)
	public int insertCourseDayinfo(List<TravelcourseDetailDto> travelcourseDetailDto) throws Exception{
		return mapper.insertCourseDayinfo(travelcourseDetailDto);
	}
	
	//1-2. 여행코스 list 조회
	public List<TravelcourseListDto> selectCourseList() throws Exception {
		return mapper.selectCourseList();
	}
	
	//1-2-1. 여행코스 개별 detail 조회
	public TravelcourseListDto selectCourseDetail(int travelcourseIdx) throws Exception {
		return mapper.selectCourseDetail(travelcourseIdx);
	}

	public int updateCourse(TravelcourseDto travelcourseDto) throws Exception {
		int updatedCount = mapper.updateCourse(travelcourseDto);
		return updatedCount;
	}

	public int deleteCourse(int travelcourseIdx) throws Exception {
		int deletedCount = mapper.deleteCourse(travelcourseIdx);
		return deletedCount;
	}

}
