package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.dto.AccompanyDto;
import project.dto.IdealrealDto;
import project.dto.NoticeDto;
import project.dto.QnaCommentDto;
import project.dto.QnaDto;
import project.dto.RcmdDto;
import project.mapper.ProjectMapper;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public List<IdealrealDto> selectIdealRealList() throws Exception {
		return projectMapper.selectIdealRealList();
	}

	@Override
	public List<IdealrealDto> selectIdealRealListWithLike() throws Exception {
		return projectMapper.selectIdealRealListWithLike();
	}

	@Override
	public IdealrealDto selectIdealrealDetail(int idealrealIdx) throws Exception {
		projectMapper.updatecnt(idealrealIdx);
		return projectMapper.selectIdealrealDetail(idealrealIdx);
	}

	@Override
	public void insertIdealreal(IdealrealDto idealrealDto) throws Exception {
		projectMapper.insertIdealreal(idealrealDto);

	}

	@Override
	public int updateIdealreal(IdealrealDto idealrealDto) throws Exception {
		return projectMapper.updateIdealreal(idealrealDto);

	}

	@Override
	public int deleteIdealreal(int idealrealIdx) throws Exception {
		return projectMapper.deleteIdealreal(idealrealIdx);

	}

//	@Override
//	public int uploadIdealreal(IdealrealDto idealrealDto, MultipartFile[] files) throws Exception {
//		return projectMapper.uploadIdealreal(idealrealDto);
//	}

	@Override
	public Integer selectLikesCount(int idealrealIdx) throws Exception {
		Integer count = projectMapper.selectLikesCount(idealrealIdx);
		return count != null ? count : 0;
	}

	@Override
	public int updateLikesCount(RcmdDto rcmdDto) throws Exception {
		return projectMapper.updateLikesCount(rcmdDto);
	}

	@Override
	public List<IdealrealDto> selectIdealRealListPage(int page) throws Exception {
		return projectMapper.selectIdealRealListPage(page);
	}

	@Override
	public List<IdealrealDto> selectIdealRealListWithLikePage(int page) throws Exception {
		return projectMapper.selectIdealRealListWithLikePage(page);
	}

	// 게시판 페이징
	@Override
	public int listidealrealpagecount() throws Exception {
		return projectMapper.listidealrealpagecount();
	}

	// 게시글 수정 사진 저장
	@Override
	public int reuploadIdealreal(IdealrealDto idealrealDto) throws Exception {
		return projectMapper.reuploadIdealreal(idealrealDto);
	}

	// 용석이꺼
	// 공지사항 리스트 조회
	public List<NoticeDto> listNoticeDto() throws Exception {
		return projectMapper.listNoticeDto();
	}

	// 공지사항 글별 상세페이지 조회
	public NoticeDto noticeDetail(int noticeIdx) throws Exception {

		return projectMapper.noticeDetail(noticeIdx);
	}

	// 공지사항 작성
	public int insertNotice(NoticeDto noticeDto) throws Exception {
		return projectMapper.insertNotice(noticeDto);
	}

	// 공지사항 삭제
	public int deleteNotice(int noticeIdx) throws Exception {
		return projectMapper.deleteNotice(noticeIdx);
	}

	// 공지사항 수정
	public int updateNotice(NoticeDto noticeDto) throws Exception {
		return projectMapper.updateNotice(noticeDto);
	}

	// QNA 리스트 조회
//			public List<QnaDto> listQnaDto() throws Exception {
//				return mapper.listQnaDto();
//			}

	// QNA 상세페이지 조회
	// public QnaDto qnaDetail(int qnaIdx) throws Exception {
	// return mapper.qnaDetail(qnaIdx);
	// }

	// QNA 작성
	public int insertQna(QnaDto qnaDto) throws Exception {
		return projectMapper.insertQna(qnaDto);
	}

	// QNA 삭제
	public int deleteQna(int qnaIdx) throws Exception {
		return projectMapper.deleteQna(qnaIdx);
	}

	// QNA 수정
	public int updateQna(QnaDto qnaDto) throws Exception {
		return projectMapper.updateQna(qnaDto);
	}

	// QNA 페이지별 조회
	public List<QnaDto> listQnaDtoByPages(int pages, String search) throws Exception {

		int offsetStart = (pages - 1) * 10;

		return projectMapper.listQnaDtoByPages(offsetStart, search);
	}

	// QNA 페이지수 조회
	public int listQnaDtoSearchPageCount(String search) throws Exception {
		return projectMapper.listQnaDtoSearchPageCount(search);
	}
//			public int listQnaDtoPageCount()throws Exception {
	// listTriedDtoByPages
//				return mapper.listQnaDtoPageCount();
//			}

	// QNA 상세페이지 COMMENT 리스트
	public List<QnaCommentDto> selectCommentList(int qnaIdx) throws Exception {
		return projectMapper.selectCommentList(qnaIdx);
	}

	// QNA 상세페이지 조회
	public QnaDto selectQnaInfo(int qnaIdx) throws Exception {
		return projectMapper.selectQnaInfo(qnaIdx);
	}

	// QNA 상세페이지 COMMENT 등록
	public int insertComment(QnaCommentDto qnaCommentDto) throws Exception {
		return projectMapper.insertComment(qnaCommentDto);
	}

}
