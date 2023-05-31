package project.dto;

import lombok.Data;

@Data
public class ReportDto {
	//신고정보
	int reportIdx;				//신고인덱스
	String reportReporter;		//신고자
	String reportReportedUser;  //피신고자
	int reportPostNumber;		//게시글번호
	int reportCommentNumber;	//게시댓글번호
	String reportContent;		//신고내용
	int reportReasonIdx;		//신고사유인덱스(외래키)
	int boardIdx;				//게시판종류인덱스(외래키)
}
