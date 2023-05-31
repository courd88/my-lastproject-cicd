package project.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class TriedDto {
	//어디까지정보(음식,문화,장소)
		int triedIdx;			// 어디까지 인덱스           (Auto Increment) 1,2,3,4,5,6,7,8,9,.....
		String triedTitle;		// 어디까지 제목 // 추가됨
		String triedImg;		// 어디까지 사진
		String triedContent;	// 어디까지 내용
		String triedCreatedTime;// 어디까지 작성시간         기본값 : 현재시간
		String triedDeleted;	// 삭제 여부                 기본값 : N
		int triedCnt;			// 조회수                    기본값 : 0
		int triedRcmd;			// 추천수                    기본값 : 0
		String userId;			// 유저 아이디(외래키)
		String userNickname;	// 유저닉네임
		int triedCategoryIdx;	// 어디까지 카테고리 인덱스(외래키)
		Date syear;				// 어디까지 조회순 시작 날짜
		Date lyear;				// 어디까지 조회순 끝 날짜
		int pages;				// 어디까지 조회순 페이지
}
