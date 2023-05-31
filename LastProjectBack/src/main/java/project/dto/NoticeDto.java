package project.dto;

import lombok.Data;

@Data
public class NoticeDto {
	//공지사항
	int noticeIdx;				//공지인덱스
	String noticeCreatedTime;	//작성일
	String noticeTitle;			//제목
	String noticeContent;		//내용
	String noticeImg;			//이미지
	int noticeCnt;				//조회수
	String noticeDeleted;		//삭제여부
	String userId;				//유저아이디(외래키)
}
