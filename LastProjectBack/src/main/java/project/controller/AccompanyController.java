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
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import project.dto.AccompanyDto;
import project.dto.ChatroomDto;
import project.dto.ChatroomUserDto;
import project.service.AccompanyService;
import project.service.ChatService;

@Controller
public class AccompanyController {

	final String UPLOAD_DIR = "C:/java/upload/";
	
	@Autowired
	private AccompanyService service;
	
	@Autowired
	private ChatService chatService;


	// Accompany 페이징 및 검색
	@GetMapping("/api/accompanylistbypage")
	public ResponseEntity<List<AccompanyDto>> listAccompanyDtoByPages(@RequestParam("pages") int pages,
			@RequestParam("search") String search, @RequestParam("accompanyRegion") String accompanyRegion)
			throws Exception {

		String decodeSearch = URLDecoder.decode(search, "UTF-8");
		List<AccompanyDto> AccompanyDto = service.listAccompanyDtoByPages(pages, decodeSearch, accompanyRegion);

		if (AccompanyDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(AccompanyDto);
		}
	}

	// Accompany 페이지 수 조회
	@GetMapping("/api/accompanypagecount")
	public ResponseEntity<Integer> listAccompanyDtoPageCount(@RequestParam("pages") int pages,
			@RequestParam("search") String search, @RequestParam("accompanyRegion") String accompanyRegion)
			throws Exception {
		String decodeSearch = URLDecoder.decode(search, "UTF-8");
		String decodeRegion = URLDecoder.decode(accompanyRegion, "UTF-8");

		int pCount = service.listAccompanyDtoSearchPageCount(decodeSearch, decodeRegion);

		return ResponseEntity.status(HttpStatus.OK).body(pCount);
	}

	// Accompany 상세페이지 조회
	@GetMapping("/api/accompany/{accompanyIdx}")
	public ResponseEntity<AccompanyDto> accompanyDetail(@PathVariable("accompanyIdx") int accompanyIdx)
			throws Exception {
		AccompanyDto accompanyDto = service.accompanyDetail(accompanyIdx);
		if (accompanyDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(accompanyDto);
		}
	}

	// Accompany 상세페이지 이미지 조회
	@GetMapping("/api/getImage/{filename}")
	public void getImage(@PathVariable("filename") String filename, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			response.setHeader("Content-Disposition", "inline;"); // 4

			byte[] buf = new byte[1024];
			fis = new FileInputStream(UPLOAD_DIR + filename);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
//				} finally {
//					bos.close();
//					bis.close();
//					fis.close();
//				}
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (bis != null) {
				bis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	// Accompany 동행채팅 연결
	@GetMapping("/api/addchatuser")
	public ResponseEntity<String> addUserChatroom(@RequestParam("chatroomId") String chatroomId,@RequestParam("userId") String userId) throws Exception{
		
		//chatroom_user테이블에 chatroomId로 조회해서 userId가 없다면 insert입력
		ChatroomUserDto chtroomUserDto = new ChatroomUserDto();
		chtroomUserDto.setChatroomId(chatroomId);
		chtroomUserDto.setUserId(userId);
		List<ChatroomUserDto> chatroomUserDtoList = chatService.selectListByChatroomIdAndUserId(chtroomUserDto);
		
		//조회된 리스트의 개수가 0이면, 현재 접속하려는 chatroomId에 userId가 없다는 뜻,
		//chatroom_user 테이블에 데이터를 추가입력 해줘야 함.
		if ( chatroomUserDtoList.size() == 0) {

			chatService.insertUserIdToChatroomUser(chtroomUserDto);
			return ResponseEntity.status(HttpStatus.OK).body("새로운채팅연결");
			
		} else {
		
			return ResponseEntity.status(HttpStatus.OK).body("기존채팅연결");
		}
	}
	
	
	// Accompany 작성
	@PostMapping("/api/accompany/write")
	public ResponseEntity<String> insertAccompany(
			@RequestPart(value = "accompanyImg", required = false) MultipartFile[] accompanyImg,
			@RequestPart(value = "data", required = false) AccompanyDto data) throws Exception {

		List<Map<String, String>> resultList = saveFiles(accompanyImg);
		for (Map<String, String> result : resultList) {
			String accImg = result.get("savedFileName");
			data.setAccompanyImage(accImg);
		}

		//작성 후 인덱스 받아옴.
		service.insertAccompany(data);
		int accompanyIdx = data.getAccompanyIdx();
		//해당 인덱스로 채팅방 생성
		String 채팅UUID = chatService.selectChatroomByAccompanyId(accompanyIdx);

		if (채팅UUID == null) {

			// 방하나 만들어 줌.
			ChatroomDto chatroom = new ChatroomDto();
			String chatroomId = UUID.randomUUID().toString();

			chatroom.setChatroomId(chatroomId);
			chatroom.setAccompanyIdx(accompanyIdx);

			// 만들고
			chatService.insertChatroom(chatroom);

			// 다시 조회
			채팅UUID = chatService.selectChatroomByAccompanyId(accompanyIdx);

		} else {
			// 있으면 조회됐겠지?
		}
		
		
		System.out.println(">>>>>>>>" + data.getUserId() + 채팅UUID);
		//해당 채팅방 UUID로 유저ID 등록
		ChatroomUserDto chtroomUserDto = new ChatroomUserDto();
		chtroomUserDto.setChatroomId(채팅UUID);
		chtroomUserDto.setUserId(data.getUserId());
		
		chatService.insertUserIdToChatroomUser(chtroomUserDto);
		

		if (accompanyIdx != 0) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	// 사진(다수) 저장 함수
	private List<Map<String, String>> saveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>" + originFileName);
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

	// 사진(단일) 저장 함수
//		private String saveFile(MultipartFile file) {
//			if (file != null) {
//				String originFileName = file.getOriginalFilename();
//				System.out.println(">>>>>>>>>>>>>" + originFileName);
//				String savedFileName = System.currentTimeMillis() + originFileName;
	//
//				try {
//					File f = new File(UPLOAD_DIR + savedFileName);
//					file.transferTo(f);
//					return savedFileName;
	//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			return null;
//		}

//		@PostMapping("/api/accompany/write")
//		public ResponseEntity<String> insertAccompany(@RequestBody AccompanyDto accompanyDto) throws Exception {
//			int insertedCount = projectService.insertAccompany(accompanyDto);
//			if (insertedCount != 1) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성을 실패하였습니다");
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body("작성을 성공하였습니다");
//			}
//		}

	// Accompany 삭제
	@DeleteMapping("/api/accompany/{accompanyIdx}")
	public ResponseEntity<Integer> deleteAccompany(@PathVariable("accompanyIdx") int accompanyIdx) throws Exception {
		int deletedCount = service.deleteAccompany(accompanyIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}

	// Accompany 수정
	@PutMapping("/api/accompany/update/{accompanyIdx}")
	public ResponseEntity<String> updateAccompany(@PathVariable("accompanyIdx") int accompanyIdx,
			@RequestPart(value = "accompanyImg", required = false) MultipartFile[] accompanyImg,
			@RequestPart(value = "data", required = false) AccompanyDto data) throws Exception {

		List<Map<String, String>> resultList = resaveFiles(accompanyImg);
		for (Map<String, String> result : resultList) {
			String accImg = result.get("savedFileName");
			data.setAccompanyImage(accImg);
		}

		service.updateAccompany(data);

		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	// 사진 재저장 함수
	private List<Map<String, String>> resaveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>" + originFileName);
				String savedFileName = System.currentTimeMillis() + originFileName;
				//
//							try {
//								File f = new File(UPLOAD_DIR + ); // 3 ??
//								mf.transferTo(f);

				try {
					File dir = new File(UPLOAD_DIR);
					if (!dir.exists()) {
						dir.mkdir();
					}
					File f = new File(dir, savedFileName);
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
}
