package project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import project.dto.AccompanyDto;
import project.dto.IdealrealDto;
import project.dto.NoticeDto;
import project.dto.QnaCommentDto;
import project.dto.QnaDto;
import project.dto.RcmdDto;
import project.service.ProjectService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiController {

	final String UPLOAD_DIR = "C:/java/upload/";

	@Autowired
	private ProjectService projectService;

	// (1-1)이상과 현실 목록 출력
	@GetMapping("/api/listidealreal")
	public ResponseEntity<List<IdealrealDto>> listidealreal() throws Exception {
		List<IdealrealDto> list = projectService.selectIdealRealList();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	// (4-1) 게시판 페이징
	@GetMapping("/api/listidealreal/{page}")
	public ResponseEntity<List<IdealrealDto>> listidealrealpage(@PathVariable("page") int page) throws Exception {
		List<IdealrealDto> list = projectService.selectIdealRealListPage(page);
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	// (4-2) 좋아요 게시판 페이징
		@GetMapping("/api/listidealrealwithlike/{page}")
		public ResponseEntity<List<IdealrealDto>> listidealrealwithlikepage(@PathVariable("page") int page) throws Exception {
			List<IdealrealDto> list = projectService.selectIdealRealListWithLikePage(page);
			if (list != null && list.size() > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(list);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}
		// (4-3) 게시판 페이징 리스트
		@GetMapping("/api/listidealrealpagecount")
		public  ResponseEntity<Integer> listidealrealpagecount() throws Exception{
			int pageCount = projectService.listidealrealpagecount();
			if (pageCount != 0) {
				return ResponseEntity.status(HttpStatus.OK).body(pageCount);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}
	
	// (3-3) 좋아요 수 리스트
	@GetMapping("/api/listidealrealwithlike")
	public ResponseEntity<List<IdealrealDto>> listidealrealwithlike() throws Exception {
		List<IdealrealDto> list = projectService.selectIdealRealListWithLike();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	// (1-2)이상과 현실 글쓰기
	@PostMapping("/api/listidealreal/write")
	public ResponseEntity<String> insertidealreal(@RequestBody IdealrealDto idealrealDto) throws Exception {
		try {
			System.out.println(idealrealDto);
			projectService.insertIdealreal(idealrealDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록오류");
		}
		return ResponseEntity.status(HttpStatus.OK).body("정상처리");
	}

	// (2-1)이상과 현실 상세페이지 조회
	@GetMapping("/api/listidealreal/detail/{idealrealIdx}")
	public ResponseEntity<IdealrealDto> idealrealDetail(@PathVariable("idealrealIdx") int idealrealIdx)
			throws Exception {
		IdealrealDto idealrealDto = projectService.selectIdealrealDetail(idealrealIdx);
		if (idealrealDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(idealrealDto);
		}
	}

	// (2-3)이상과 현실 글 수정
//	@PutMapping("/api/listidealreal/{idealrealIdx}")
//	public ResponseEntity<String> updateIdealreal(@PathVariable("idealrealIdx") int idealrealIdx,
//			@RequestBody IdealrealDto idealrealDto) throws Exception {
////		try {
////			IdealrealDto detail = projectService.selectIdealrealDetail(idealrealIdx);	//조회 
////			UserDto userDto = (UserDto) authentication.getPrincipal();
////			if (userDto.getUserId().equals(detail.getUserId()) || userDto.getUserId().equals("test")) {
//		idealrealDto.setIdealrealIdx(idealrealIdx);
//		int updatedCount = projectService.updateIdealreal(idealrealDto);
//		if (updatedCount != 1) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다");
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body("정상 수정되었습니다");
//		}
////			} else {
////				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자만 수정 가능합니다");
////			}
////		} catch (Exception e) {
////			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 해주세요");
////		}
//	}
	
	// (2-3-1)이상과 현실 글 쓰기에 사진 넣기
		@PutMapping("/rereupload/{idealrealIdx}")
		public ResponseEntity<String> reuploadIdealreal(
				@PathVariable("idealrealIdx") int idealrealIdx,
				@RequestPart(value = "idealrealIdealImg", required = false) MultipartFile[] idealrealIdealImg,
				@RequestPart(value = "idealrealRealImg", required = false) MultipartFile[] idealrealRealImg,
				@RequestPart(value = "data", required = false) IdealrealDto data) throws Exception {

//			String uploadedDatas = "";
//			uploadedDatas += "userId: " + data.getUserId() + "\n";
//			uploadedDatas += "userName: " + data.getUserName() + "\n";
//			uploadedDatas += "userEmail: " + data.getUserEmail() + "\n";

			List<Map<String, String>> resultList = resaveFiles(idealrealIdealImg);
			for (Map<String, String> result : resultList) {
				String idealImg = result.get("savedFileName");
				data.setIdealrealIdealImg(idealImg);
			}

			resultList = resaveFiles(idealrealRealImg);
			for (Map<String, String> result : resultList) {
				String realImg = result.get("savedFileName");
				data.setIdealrealRealImg(realImg);
			}

			projectService.updateIdealreal(data);

			if (data != null) {
				return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
			}
		}

		private List<Map<String, String>> resaveFiles(MultipartFile[] files) {
			List<Map<String, String>> resultList = new ArrayList<>();

			if (files != null) {
				for (MultipartFile mf : files) {
					String originFileName = mf.getOriginalFilename();
					System.out.println(">>>>>>>>>>>>>>>>>>" + originFileName);
					String savedFileName = System.currentTimeMillis() + originFileName;

					try {
						File f = new File(UPLOAD_DIR + savedFileName);
						mf.transferTo(f);

						Map<String, String> result = new HashMap<>();
						result.put("originalFileName", originFileName);
						result.put("savedFileName", savedFileName);
						resultList.add(result);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return resultList;
		}

	// (2-4)이상과 현실 글 삭제
	@DeleteMapping("/api/listidealreal/{idealrealIdx}")
	public ResponseEntity<Integer> deleteIdealreal(@PathVariable("idealrealIdx") int idealrealIdx) throws Exception {
		int deletedCount = projectService.deleteIdealreal(idealrealIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}

	}

	// (1-3)이상과 현실 글 쓰기에 사진 넣기
	@PostMapping("/upupload")
	public ResponseEntity<String> uploadIdealreal(
			@RequestPart(value = "idealrealIdealImg", required = false) MultipartFile[] idealrealIdealImg,
			@RequestPart(value = "idealrealRealImg", required = false) MultipartFile[] idealrealRealImg,
			@RequestPart(value = "data", required = false) IdealrealDto data) throws Exception {

//		String uploadedDatas = "";
//		uploadedDatas += "userId: " + data.getUserId() + "\n";
//		uploadedDatas += "userName: " + data.getUserName() + "\n";
//		uploadedDatas += "userEmail: " + data.getUserEmail() + "\n";

		List<Map<String, String>> resultList = saveFiles(idealrealIdealImg);
		for (Map<String, String> result : resultList) {
			String idealImg = result.get("savedFileName");
			data.setIdealrealIdealImg(idealImg);
		}

		resultList = saveFiles(idealrealRealImg);
		for (Map<String, String> result : resultList) {
			String realImg = result.get("savedFileName");
			data.setIdealrealRealImg(realImg);
		}

		projectService.insertIdealreal(data);

		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	private List<Map<String, String>> saveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>>>>>>" + originFileName);
				String savedFileName = System.currentTimeMillis() + originFileName;

				try {
					File f = new File(UPLOAD_DIR + savedFileName);
					mf.transferTo(f);

					Map<String, String> result = new HashMap<>();
					result.put("originalFileName", originFileName);
					result.put("savedFileName", savedFileName);
					resultList.add(result);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	// (2-2)이상과 현실 글에 사진 가져오기
	@GetMapping("/api/getimage/{filename}")
	public void getImage(@PathVariable("filename") String filename, HttpServletResponse response) throws Exception {
		// Image를 읽어서 전달
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			response.setHeader("Content-Disposition", "inline;");

			byte[] buf = new byte[1024];
			fis = new FileInputStream(UPLOAD_DIR + filename);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
		} finally {
			bos.close();
			bis.close();
			fis.close();
		}
	}

	// (3-1) 좋아요 상세
	@GetMapping("/api/{idealrealIdx}/getlike")
	public ResponseEntity<Integer> openGetLike(@PathVariable("idealrealIdx") int idealrealIdx) throws Exception {
		int count = projectService.selectLikesCount(idealrealIdx);
//		if (count == null) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(rcmdDto);
//		}
		return ResponseEntity.status(HttpStatus.OK).body(count);
	}

	// (3-2) 사용자 : 좋아요 수 업데이트
	@PostMapping("/api/{rcmdIdx}/like")
	public ResponseEntity<Integer> insertLike(@PathVariable("rcmdIdx") int rcmdIdx, @RequestBody RcmdDto rcmdDto)
			throws Exception {
		int updatedCount = projectService.updateLikesCount(rcmdDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}
	// (3-3) 좋아요 취소
	@DeleteMapping("/api/{rcmdIdx}/unlike")
	public ResponseEntity<Integer> deleteLike(@PathVariable("rcmdIdx") int rcmdIdx, @RequestBody RcmdDto rcmdDto)
			throws Exception {
		int updatedCount = projectService.updateLikesCount(rcmdDto);
		if (updatedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
		}
	}
	
	//용석이꺼
	// 공지사항 리스트 조회
		@GetMapping("/api/noticeList")
		public ResponseEntity<List<NoticeDto>> listNotice() throws Exception {
			List<NoticeDto> NoticetDto = projectService.listNoticeDto();
			if (NoticetDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(NoticetDto);
			}
		}

		// 공지사항 글별 상세페이지 조회
		@GetMapping("/api/notice/{noticeIdx}")
		public ResponseEntity<NoticeDto> noticeDetail(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
			NoticeDto noticeDto = projectService.noticeDetail(noticeIdx);
			if (noticeDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(noticeDto);
			}
		}

		// 공지사항 작성
		@PostMapping("/api/notice/write")
		public ResponseEntity<String> insertNotice(@RequestBody NoticeDto noticeDto) throws Exception {
			int insertedCount = projectService.insertNotice(noticeDto);
			if (insertedCount != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성을 실패하였습니다");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body("작성을 성공하였습니다");
			}
		}

		// 공지사항 삭제
		@DeleteMapping("/api/notice/{noticeIdx}")
		public ResponseEntity<Integer> deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
			int deletedCount = projectService.deleteNotice(noticeIdx);
			if (deletedCount != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
			}
		}

		// 공지사항 수정
		@PutMapping("/api/notice/update/{noticeIdx}")
		public ResponseEntity<Integer> updateNotice(@PathVariable("noticeIdx") int noticeIdx,
				@RequestBody NoticeDto noticeDto) throws Exception {
			noticeDto.setNoticeIdx(noticeIdx);
			int updatedCount = projectService.updateNotice(noticeDto);
			if (updatedCount != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
			}
		}

		// QNA 리스트 조회
//		@GetMapping("/api/qnalist")
//		public ResponseEntity<List<QnaDto>> listQna() throws Exception {
//			List<QnaDto> QnaDto = projectService.listQnaDto();
//			if (QnaDto == null) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body(QnaDto);
//			}
//		}

		// QNA 상세페이지 조회
		// @GetMapping("/api/qna/{qnaIdx}")
		// public ResponseEntity<QnaDto> qnaDetail(@PathVariable("qnaIdx") int qnaIdx)
		// throws Exception {
		// QnaDto qnaDto = projectService.qnaDetail(qnaIdx);
		// if (qnaDto == null) {
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// } else {
		// return ResponseEntity.status(HttpStatus.OK).body(qnaDto);
		// }
		// }

		// QNA 작성
		@PostMapping("/api/qna/write")
		public ResponseEntity<String> insertQna(@RequestBody QnaDto qnaDto) throws Exception {
			int insertedCount = projectService.insertQna(qnaDto);
			if (insertedCount != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성을 실패하였습니다");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body("작성을 성공하였습니다");
			}
		}

		// QNA 삭제
		@DeleteMapping("/api/qna/{qnaIdx}")
		public ResponseEntity<Integer> deleteQna(@PathVariable("qnaIdx") int qnaIdx) throws Exception {
			int deletedCount = projectService.deleteQna(qnaIdx);
			if (deletedCount != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
			}
		}

		// QNA 수정
		@PutMapping("/api/qna/update/{qnaIdx}")
		public ResponseEntity<Integer> updateQna(@PathVariable("qnaIdx") int qnaIdx, @RequestBody QnaDto qnaDto)
				throws Exception {
			qnaDto.setQnaIdx(qnaIdx);
			int updatedCount = projectService.updateQna(qnaDto);
			if (updatedCount != 1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
			}
		}

		// QNA 페이징 및 검색
		@GetMapping("/api/qnalistbypage")
		public ResponseEntity<List<QnaDto>> listQnaDtoByPages(@RequestParam("pages") int pages,
				@RequestParam("search") String search) throws Exception {

			String decodeSearch = URLDecoder.decode(search, "UTF-8");
			List<QnaDto> QnaDto = projectService.listQnaDtoByPages(pages, decodeSearch);

			if (QnaDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(QnaDto);
			}
		}

		// QNA 페이지수 조회
		@GetMapping("/api/qnapagecount")
		public ResponseEntity<Integer> listPageCount(@RequestParam("pages") int pages,
				@RequestParam("search") String search) throws Exception {
			String decodeSearch = URLDecoder.decode(search, "UTF-8");

			int pCount = projectService.listQnaDtoSearchPageCount(decodeSearch);

			return ResponseEntity.status(HttpStatus.OK).body(pCount);
		}

		// QNA 페이지수 조회
//		@GetMapping("/api/qnapagecounts")
//		public ResponseEntity<Integer> listQnaDtoPageCount(@RequestParam("pages") int pages) throws Exception {
	//
//			
//			int pCount1 = projectService.listQnaDtoPageCount();
	//
//			return ResponseEntity.status(HttpStatus.OK).body(pCount1);
//		}

		// QNA 상세페이지 COMMENTS 리스트
		@GetMapping("/api/qna/{qnaIdx}")
		@ResponseBody
		public ResponseEntity<Map<String, Object>> qnaDetail(@PathVariable("qnaIdx") int qnaIdx) throws Exception {
			List<QnaCommentDto> list = projectService.selectCommentList(qnaIdx);
			QnaDto qnaDto = projectService.selectQnaInfo(qnaIdx);

			Map<String, Object> result = new HashMap<>();

			result.put("selectCommentList", list);
			result.put("selectQnaInfo", qnaDto);
			if (result != null && result.size() > 0) {
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		}

		// QNA COMMENTS 등록
		@PostMapping("/api/qna/comments/write/{qnaIdx}")
		public ResponseEntity<Map<String, Object>> insertcomments(@RequestBody QnaCommentDto qnaCommentDto,
				@PathVariable("qnaIdx") int qnaIdx) throws Exception {
			int insertedCount = 0;
			try {
				qnaCommentDto.setQnaIdx(qnaIdx);
				insertedCount = projectService.insertComment(qnaCommentDto);
				if (insertedCount > 0) {
					Map<String, Object> result = new HashMap<>();
					result.put("message", "정상적으로 등록되었습니다.");
					result.put("count", insertedCount);
					result.put("commentIdx", qnaCommentDto.getQnaCommentIdx());
					return ResponseEntity.status(HttpStatus.OK).body(result);
				} else {
					Map<String, Object> result = new HashMap<>();
					result.put("message", "등록된 내용이 없습니다.");
					result.put("count", insertedCount);
					return ResponseEntity.status(HttpStatus.OK).body(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록 중 오류가 발생했습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
			}
		}


	}
