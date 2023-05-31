package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.AccompanyDto;
import project.dto.IdealrealDto;
import project.dto.TravelcourseDto;
import project.dto.TriedDto;
import project.dto.UserDto;

@Mapper
public interface MypageMapper {

	//프로필 이미지 수정
	public int updateProfileImg(UserDto data) throws Exception;
	
	
	//닉네임 중복 확인
	public String selectDuplicateNickname(String userNickname) throws Exception;
	
	//서브메뉴 닉네임 조회
	public String selectuserNickname(String userId) throws Exception;
	
	
	//닉네임 수정
	public int updateUserNickname(UserDto userDto) throws Exception;
	
	
	//유저 정보 조회
	public UserDto selectUser(String userId) throws Exception;
	
	//유저 게시글 조회
	//(1)여행코스
	public List<TravelcourseDto> selectTravelcourseListByUserId(String userId) throws Exception;
	
	//(2)어디까지
	public List<TriedDto> selectTriedListByUserId(String userId) throws Exception;
	
	//(3)여행친구
	public List<AccompanyDto> selectAccompanyListByUserId(String userId) throws Exception;
	
	//(4)이상과현실
	public List<IdealrealDto> selectIdealrealListByUserId(String userId) throws Exception;
}
