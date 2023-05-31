package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.TravelcourseDetailListDto;
import project.dto.TravelcourseDto;
import project.dto.TravelcourseListDto;
import project.service.TravelcourseService;

@Slf4j
@RestController
public class TravelcourseController {

	@Autowired
	private TravelcourseService service;

	
	//1-1.여행코스 입력(제목, 시작일, 끝일, 작성자)
	@PostMapping("/api/course")
	public ResponseEntity<String> insertCourse(@RequestBody TravelcourseDto travelcourseDto) throws Exception {
		try {
			// 코스 입력 후 인덱스 번호 가져옴
			int insertedCount = service.insertCourse(travelcourseDto);
			int travelcourseIdx = travelcourseDto.getTravelcourseIdx();

			// travelcourseDto에서 Day별 list 추출
			List<TravelcourseDetailListDto> courseData = travelcourseDto.getTravelcourseDetailListDto();

			// courseData에 travelcourseIdx 입력하기
			courseData.forEach(course -> course.setTravelcourseIdx(travelcourseIdx));

			// Day별 인서트 한개 하고 dayinfo 입력하기
			// foreach로 하고 싶지만, 순서가 어려워서 for문으로 하겠음.
			for (int i = 0; i < courseData.size(); i++) {
				// 1개의 day를 입력하고 반환 idx값을 반환 받음.
				int courseInsertedCount = service.insertCourseDay(courseData.get(i));
				int travelcourseListIdx = courseData.get(i).getTravelcourseListIdx();

				// 해당 값을 dayinfo에 다시 입력해줌
				courseData.get(i).getDayinfo().forEach(dayinfo -> dayinfo.setTravelcourseListIdx(travelcourseListIdx));

				System.out.println(courseData.get(i).getDayinfo());

				// xml에서 foreach문으로 반복 입력함.
				int dayinfoInsertedCount = service.insertCourseDayinfo(courseData.get(i).getDayinfo());
			}

			System.out.println(">>>>>>>>>" + insertedCount);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록오류");
		}
		return ResponseEntity.status(HttpStatus.OK).body("정상처리");
	}

	//1-2. 여행코스 list 조회
	@GetMapping("/api/course")
	public ResponseEntity<List<TravelcourseListDto>> openCourseList() throws Exception {
		List<TravelcourseListDto> list = service.selectCourseList();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}
	
	//1-2-1. 여행코스 개별 detail 조회
	@GetMapping("/api/course/{travelcourseIdx}")
	public ResponseEntity<TravelcourseListDto> openCourseDetail1(@PathVariable("travelcourseIdx") int travelcourseIdx)
			throws Exception {
		TravelcourseListDto travelcourseDto = service.selectCourseDetail(travelcourseIdx);
		if (travelcourseDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(travelcourseDto);
		}
	}

	@PutMapping("/api/course/{travelcourseIdx}")
	public ResponseEntity<Integer> updateCourse(@PathVariable("travelcourseIdx") int travelcourseIdx,
			@RequestBody TravelcourseDto travelcourseDto) throws Exception {
		travelcourseDto.setTravelcourseIdx(travelcourseIdx);
		int updatedCount = service.updateCourse(travelcourseDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}

	@DeleteMapping("/api/course/{travelcourseIdx}")
	public ResponseEntity<Integer> deleteCourse(@PathVariable("travelcourseIdx") int travelcourseIdx) throws Exception {
		int deletedCount = service.deleteCourse(travelcourseIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}

}
