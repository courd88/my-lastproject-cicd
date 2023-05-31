package project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import project.dto.AccompanyDto;
import project.dto.IdealrealDto;
import project.dto.TravelcourseDto;
import project.dto.TriedDto;
import project.dto.UserDto;
import project.imgTransform.ImgTransform;
import project.service.MypageService;

@RestController
public class MyPageController {

	private ImgTransform imgTransform = new ImgTransform();
	
	@Autowired
	private MypageService service;
	
	//프로필 이미지 수정
	@PutMapping("/api/mypage/profileimg")
	public ResponseEntity<String> updateProfileImg(@RequestPart(value="prfImg", required=false) MultipartFile[] prfImg,
			@RequestPart(value="data", required=false) UserDto data ) throws Exception{ 
		
		List<Map<String, String>> resultList = imgTransform.saveFiles(prfImg);
		for (Map<String,String> result : resultList) {
			String img = result.get("savedFileName");
			data.setUserImg(img);
		}
		
		service.updateProfileImg(data);
		
		if(data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("정상처리");
		}
	}
	
	
	
//	//닉네임 수정
//	@PutMapping("/api/mypage/nickname")
//	public ResponseEntity<String> updateNickname(@RequestBody("nickname") String nickname) throws Exception{
//		
//		return ResponseEntity.status(HttpStatus.OK).body("수정완료");
//	}
//	
	
	//닉네임 중복확인
	@GetMapping("/api/mypage/nickname")
	public ResponseEntity<String> selectDuplicateNickname(@RequestParam("userNickname") String userNickname) throws Exception{
		String duplicateNickname = service.selectDuplicateNickname(userNickname);
		
		if(duplicateNickname == null) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(duplicateNickname);
		}
	}
	
	//서브메뉴 닉네임 조회
	@GetMapping("/api/submenu/nickname")
	public ResponseEntity<String> selectuserNickname(@RequestParam("userId") String userId) throws Exception{
		String nickname = service.selectuserNickname(userId);
		
		if(nickname == null) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(nickname);
		}
	}
	
	//닉네임 수정
	@PutMapping("/api/mypage/nickname")
	public ResponseEntity<String> updateUserNickname(@RequestBody UserDto userDto) throws Exception{
		int updatedCnt = service.updateUserNickname(userDto);
		
		System.out.println(">>>>>"+userDto);
		
		if(updatedCnt == 1) {
			return ResponseEntity.status(HttpStatus.OK).body("수정완료");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정실패");
		}
	}
	
	
	//마이페이지 조회(게시글 들 정보, 유저 정보)
	@GetMapping("/api/mypage")
	public ResponseEntity<Map<String,Object>> selectMypage(@RequestParam("userId") String userId) throws Exception{
		
		//service 유저 정보 조회
		UserDto userDto = service.selectUser(userId);
		
		//service 게시판별 게시글 조회
		List<TravelcourseDto> travelcourseList = service.selectTravelcourseListByUserId(userId);
		List<TriedDto> triedList = service.selectTriedListByUserId(userId);
		List<AccompanyDto> accompanyList = service.selectAccompanyListByUserId(userId);
		List<IdealrealDto> idealrealList = service.selectIdealrealListByUserId(userId);
		
		Map<String,Object> result = new HashMap<>();
		
		result.put("user", userDto);
		result.put("travelcourse", travelcourseList);
		result.put("tried", triedList);
		result.put("accompany", accompanyList);
		result.put("idealreal", idealrealList);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
}
