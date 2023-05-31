package project.service;

import java.util.List;

import project.dto.AccompanyDto;
import project.dto.IdealrealDto;
import project.dto.NoticeDto;
import project.dto.QnaCommentDto;
import project.dto.QnaDto;
import project.dto.RcmdDto;

public interface ProjectService {

	// 게시판 리스트
	public List<IdealrealDto> selectIdealRealList() throws Exception;

	// 게시판 리스트 페이지
	public List<IdealrealDto> selectIdealRealListPage(int page) throws Exception;

	// 게시판 좋아요 리스트
	public List<IdealrealDto> selectIdealRealListWithLike() throws Exception;

	// 게시판 좋아요 리스트 페이지
	public List<IdealrealDto> selectIdealRealListWithLikePage(int page) throws Exception;

	// 게시판 상세
	public IdealrealDto selectIdealrealDetail(int idealrealIdx) throws Exception;

	// 게시판 글 쓰기
	public void insertIdealreal(IdealrealDto idealrealDto) throws Exception;

	// 게시판 글 수정
	public int updateIdealreal(IdealrealDto idealrealDto) throws Exception;

	// 게시판 글 삭제
	public int deleteIdealreal(int idealrealIdx) throws Exception;

	// 게시판 사진 등록
	//public int uploadIdealreal(IdealrealDto idealrealDto, MultipartFile[] files) throws Exception;

	// 게시판 글 좋아요 리스트
	public Integer selectLikesCount(int idealrealIdx) throws Exception;

	// 게시판 글 좋아요
	public int updateLikesCount(RcmdDto rcmdDto) throws Exception;

	// 게시판 페이징
	public int listidealrealpagecount() throws Exception;

	// 게시판 수정 사진 등록
	public int reuploadIdealreal(IdealrealDto idealrealDto) throws Exception;
	
	//용석이꺼
	// 공지사항 리스트 조회
		public List<NoticeDto> listNoticeDto() throws Exception;

		// 공지사항 글별 상세페이지 조회
		public NoticeDto noticeDetail(int noticeIdx) throws Exception;

		// 공지사항 작성
		public int insertNotice(NoticeDto noticeDto) throws Exception;

		// 공지사항 삭제
		public int deleteNotice(int noticeIdx) throws Exception;
		// 공지사항 수정
		public int updateNotice(NoticeDto noticeDto) throws Exception;

		// QNA 리스트 조회
//		public List<QnaDto> listQnaDto() throws Exception {
//			return mapper.listQnaDto();
//		}

		// QNA 상세페이지 조회
		// public QnaDto qnaDetail(int qnaIdx) throws Exception {
		// return mapper.qnaDetail(qnaIdx);
		// }

		// QNA 작성
		public int insertQna(QnaDto qnaDto) throws Exception ;

		// QNA 삭제
		public int deleteQna(int qnaIdx) throws Exception;

		// QNA 수정
		public int updateQna(QnaDto qnaDto) throws Exception;

		// QNA 페이지별 조회
		public List<QnaDto> listQnaDtoByPages(int pages, String search) throws Exception;

		// QNA 페이지수 조회
		public int listQnaDtoSearchPageCount(String search) throws Exception;
//		public int listQnaDtoPageCount()throws Exception {
	//listTriedDtoByPages
//			return mapper.listQnaDtoPageCount();
//		}

		// QNA 상세페이지 COMMENT 리스트
		public List<QnaCommentDto> selectCommentList(int qnaIdx) throws Exception;

		// QNA 상세페이지 조회
		public QnaDto selectQnaInfo(int qnaIdx) throws Exception;

		// QNA 상세페이지 COMMENT 등록
		public int insertComment(QnaCommentDto qnaCommentDto) throws Exception;


}
