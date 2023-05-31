package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.AccompanyDto;
import project.dto.IdealrealDto;
import project.dto.TravelcourseDto;
import project.dto.TriedDto;
import project.dto.UserDto;
import project.mapper.MypageMapper;

@Service
public class MypageService {

	@Autowired
	private MypageMapper mapper;
	
	//프로필 이미지 수정
	public int updateProfileImg(UserDto data) throws Exception{
		return mapper.updateProfileImg(data);
	}
	
	
	//닉네임 중복 확인
	public String selectDuplicateNickname(String userNickname) throws Exception{
		return mapper.selectDuplicateNickname(userNickname);
	}
	
	//서브메뉴 닉네임 조회
	public String selectuserNickname(String userId) throws Exception{
		return mapper.selectuserNickname(userId);
	}
	
	
	
	//닉네임 수정
	public int updateUserNickname(UserDto userDto) throws Exception{
		return mapper.updateUserNickname(userDto);
	}
	
	
	
	//유저정보 조회
	public UserDto selectUser(String userId) throws Exception{
		return mapper.selectUser(userId);
	}
	
	//유저 게시글 조회
	//(1)여행코스
	public List<TravelcourseDto> selectTravelcourseListByUserId(String userId) throws Exception{
		return mapper.selectTravelcourseListByUserId(userId);
	}
	//(2)어디까지
	public List<TriedDto> selectTriedListByUserId(String userId) throws Exception{
		return mapper.selectTriedListByUserId(userId);
	}
	//(3)여행친구
	public List<AccompanyDto> selectAccompanyListByUserId(String userId) throws Exception{
		return mapper.selectAccompanyListByUserId(userId);
	}
	//(4)이상과현실
	public List<IdealrealDto> selectIdealrealListByUserId(String userId) throws Exception{
		return mapper.selectIdealrealListByUserId(userId);
	}
	
	
}
