package project.dto;

import lombok.Data;

@Data
public class UserDto {
	//유저정보
	private String userId;			//메일주소 -> 메일 보내서 인증처리 가능한가??
	private String userPw;  		//비번(인코딩 돼서 기록됨)
	private String userNickname;	//닉네임
	private int countryIdx;			//국가번호(외래키)	
	private String userName;		//유저네임
	private String userImg;			//유저프사
	
}
