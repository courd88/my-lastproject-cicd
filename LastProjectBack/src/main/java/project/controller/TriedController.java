package project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import project.dto.TriedDto;
import project.service.TriedService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TriedController {

	final String UPLOAD_DIR = "C:/java/upload/";

	@Autowired
	private TriedService service;


	// 1. 조회
	// 1-1. 어디까지 필터별 보기
	@GetMapping("/api/tried/{triedCategoryIdx}/{order}/{syear}/{lyear}/{pages}")
	public ResponseEntity<List<TriedDto>> selectTriedList(@PathVariable("triedCategoryIdx") int triedCategoryIdx,
			@PathVariable("order") String order, @PathVariable("syear") Date syear, @PathVariable("lyear") Date lyear,
			@PathVariable("pages") int pages) throws Exception {

		TriedDto triedDto = new TriedDto();
		pages = (pages - 1) * 10;
		triedDto.setPages(pages);
		triedDto.setTriedCategoryIdx(triedCategoryIdx);

		java.sql.Date sqlDate = new java.sql.Date(syear.getTime());
		triedDto.setSyear(sqlDate);

		sqlDate = new java.sql.Date(lyear.getTime());
		triedDto.setLyear(sqlDate);

		List<TriedDto> triedDtoList;

		System.out.println(">>>>>>"+triedDto.toString());
		
		if (order.equals("recent")) {

			triedDtoList = service.selectTriedRecent(triedDto);
		} else if (order.equals("count")) {

			triedDtoList = service.selectTriedCount(triedDto);
		} else if (order.equals("rcmd")) {

			triedDtoList = service.selectTriedRecommend(triedDto);
		} else {
			triedDtoList = service.selectTriedRecent(triedDto);
		}

		if (triedDtoList.size() == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(triedDtoList);
		}
	}

	// 1-2. 어디까지 필터별 총 페이지 수
	@GetMapping("/api/tried/totalpage/{triedCategoryIdx}/{order}/{syear}/{lyear}/{pages}")
	public ResponseEntity<Integer> selectTriedListTotalPage(@PathVariable("triedCategoryIdx") int triedCategoryIdx,
			@PathVariable("order") String order, @PathVariable("syear") Date syear, @PathVariable("lyear") Date lyear,
			@PathVariable("pages") int pages) throws Exception {

		TriedDto triedDto = new TriedDto();
		pages = (pages - 1) * 10;
		triedDto.setPages(pages);
		triedDto.setTriedCategoryIdx(triedCategoryIdx);

		java.sql.Date sqlDate = new java.sql.Date(syear.getTime());
		triedDto.setSyear(sqlDate);

		sqlDate = new java.sql.Date(lyear.getTime());
		triedDto.setLyear(sqlDate);

		int totalPage;

		if (order == "recent") {
			totalPage = service.selectTriedRecentTotalPage(triedDto);
		} else if (order == "count") {
			totalPage = service.selectTriedCountTotalPage(triedDto);
		} else if (order == "recommend") {
			totalPage = service.selectTriedRecommendTotalPage(triedDto);
		} else {
			totalPage = service.selectTriedRecentTotalPage(triedDto);
		}

		if (totalPage == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(totalPage);
		}
	}

	// 1-3. 어디까지 상세페이지 조회
	@GetMapping("/api/tried/detail/{triedIdx}")
	public ResponseEntity<TriedDto> openTriedDetail(@PathVariable("triedIdx") int triedIdx) throws Exception {
		TriedDto triedDto = service.selectTriedDetail(triedIdx);
		if (triedDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(triedDto);
		}
	}

//	// 1-4. 어디까지 글쓰기에 사진 가져오기
//	@GetMapping("/api/getimage/{filename}")
//	public void getImage(@PathVariable("filename") String filename, HttpServletResponse response) throws Exception {
//		FileInputStream fis = null;
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//		try {
//			response.setHeader("Content-Disposition", "inline;"); // 4
//
//			byte[] buf = new byte[1024];
//			fis = new FileInputStream(UPLOAD_DIR + filename);
//			bis = new BufferedInputStream(fis);
//			bos = new BufferedOutputStream(response.getOutputStream());
//			int read;
//			while ((read = bis.read(buf, 0, 1024)) != -1) {
//				bos.write(buf, 0, read);
//			}
//			} finally {
//				bos.close();
//				bis.close();
//				fis.close();
//			}
//		} finally {
//			if (bos != null) {
//				bos.close();
//			}
//			if (bis != null) {
//				bis.close();
//			}
//			if (fis != null) {
//				fis.close();
//			}
//		}
//	}

	//2. 어디까지 글쓰기 저장
	@PostMapping("/upload")
	public ResponseEntity<String> uploadTried(
			@RequestPart(value = "triedImg", required = false) MultipartFile[] triedImg,
			@RequestPart(value = "data", required = false) TriedDto data) throws Exception {

		List<Map<String, String>> resultList = saveFiles(triedImg);
		for (Map<String, String> result : resultList) {
			String triedImage = result.get("savedFileName");
			data.setTriedImg(triedImage);
		}

		service.insertTried(data);

		if (data != null) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	//사진(다수) 저장 함수
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

	//사진(단일) 저장 함수
	private String saveFile(MultipartFile file) {
		if (file != null) {
			String originFileName = file.getOriginalFilename();
			System.out.println(">>>>>>>>>>>>>" + originFileName);
			String savedFileName = System.currentTimeMillis() + originFileName;

			try {
				File f = new File(UPLOAD_DIR + savedFileName);
				file.transferTo(f);
				return savedFileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// 3. 어디까지 글쓰기 사진 수정
	@PutMapping("/reupload/{triedIdx}")
	public ResponseEntity<String> reuploadTried(@PathVariable("triedIdx") int triedIdx,
			@RequestPart(value = "updateImg", required = false) MultipartFile[] updateImg,
			@RequestPart(value = "data") TriedDto data) throws Exception {
		
		data.setTriedIdx(triedIdx);

		List<Map<String, String>> resultList = resaveFiles(updateImg);
		for (Map<String, String> result : resultList) {
			String triedImage = result.get("savedFileName");
			data.setTriedImg(triedImage);
		}

		int resultCnt = service.updateTried(data);

		if (resultCnt != 0) {
			return ResponseEntity.status(HttpStatus.OK).body("정상 처리");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생");
		}
	}

	//사진 재저장 함수
	private List<Map<String, String>> resaveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>" + originFileName);
				String savedFileName = System.currentTimeMillis() + originFileName;
				//
//						try {
//							File f = new File(UPLOAD_DIR + ); // 3 ??
//							mf.transferTo(f);

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

	// 4. 어디까지 삭제
	@DeleteMapping("/api/tried/{triedIdx}")
	public ResponseEntity<Integer> deleteTried(@PathVariable("triedIdx") int triedIdx) throws Exception {
		int deletedCount = service.deleteTried(triedIdx);
		if (deletedCount != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(deletedCount);
		}
	}
	
	
	

	// 어디까지 리스트 조회(삭제예정)
//	@GetMapping("/api/tried")
//	public ResponseEntity<List<TriedDto>> openTriedList() throws Exception {
//		List<TriedDto> list = service.selectTriedList();
//		if (list != null && list.size() > 0) {
//			return ResponseEntity.status(HttpStatus.OK).body(list);
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//	}

	// 어디까지 글쓰기(삭제예정)
//	@PostMapping("/api/tried/write")
//	public ResponseEntity<Map<String, Object>> insertTried(@RequestBody TriedDto triedDto) throws Exception {
//		int insertedCount = 0;
//		try {
//			insertedCount = service.insertTried(triedDto);
//			if (insertedCount > 0) {
//				Map<String, Object> result = new HashMap<>();
//
//				File mf = null;
//				result.put("message", "정상적으로 등록되었습니다."); // String -> Object
//				result.put("count", insertedCount); // Integer-> Object
//				result.put("triedIdx", triedDto.getTriedIdx());
//				return ResponseEntity.status(HttpStatus.OK).body(result);
//			} else {
//				Map<String, Object> result = new HashMap<>();
//				result.put("message", "등록된 내용이 없습니다.");
//				result.put("count", insertedCount);
//				return ResponseEntity.status(HttpStatus.OK).body(result);
//			}
//		} catch (Exception e) {
//			Map<String, Object> result = new HashMap<>();
//			result.put("message", "등록 중 오류가 발생했습니다.");
//			result.put("count", insertedCount);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
//		}
//	}
	
	// 어디까지 수정(삭제예정)
//	@PutMapping("/api/tried/{triedIdx}")
//	public ResponseEntity<Integer> updateTried(@PathVariable("triedIdx") int triedIdx, @RequestBody TriedDto triedDto)
//			throws Exception {
//		triedDto.setTriedIdx(triedIdx);
//		int updatedCount = service.updateTried(triedDto);
//		if (updatedCount != 1) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedCount);
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(updatedCount);
//		}
//	}

}