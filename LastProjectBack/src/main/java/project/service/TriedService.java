package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.dto.TriedDto;
import project.mapper.TriedMapper;

@Service
public class TriedService {

	@Autowired
	private TriedMapper mapper;

	// 1. 조회
	// 1-1,2. 어디까지 카테고리 최신순, 페이지수
	public List<TriedDto> selectTriedRecent(TriedDto triedDto) throws Exception {
		System.out.println(">>>>>>서비스단"+triedDto);
		return mapper.selectTriedRecent(triedDto);
	}

	public int selectTriedRecentTotalPage(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecentTotalPage(triedDto);
	}
	
	// 1-1,2. 어디까지 카테고리 조회순, 페이지수
	public List<TriedDto> selectTriedCount(TriedDto triedDto) throws Exception {
		return mapper.selectTriedCount(triedDto);
	}

	public int selectTriedCountTotalPage(TriedDto triedDto) throws Exception {
		return mapper.selectTriedCountTotalPage(triedDto);
	}
	
	// 1-1,2. 어디까지 카테고리 추천순, 페이지수
	public List<TriedDto> selectTriedRecommend(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecommend(triedDto);
	}

	public int selectTriedRecommendTotalPage(TriedDto triedDto) throws Exception {
		return mapper.selectTriedRecommendTotalPage(triedDto);
	}
	
	// 1-3. 어디까지 디테일 조회
	public TriedDto selectTriedDetail(int triedIdx) throws Exception {
		mapper.triedCnt(triedIdx); 					// 조회수를 증가
		return mapper.selectTriedDetail(triedIdx);  // 게시판 상세 내용 조회
	}
	
	// 2. 입력
	// 2-1. 입력
	public int insertTried(TriedDto triedDto) throws Exception {
		return mapper.insertTried(triedDto);
	}


	// 3. 수정
	public int updateTried(TriedDto triedDto) throws Exception {
		return mapper.updateTried(triedDto);
	}

	// 4. 삭제
	public int deleteTried(int triedIdx) throws Exception {
		return mapper.deleteTried(triedIdx);
	}

	
	
	
	
	//업로드(삭제예정)
	public int uploadTried(TriedDto triedDto, MultipartFile[] files) throws Exception {
		return mapper.uploadTried(triedDto);
	}

	//이미지 가져오기(삭제예정)
	public byte[] getImageData(String triedImg) throws Exception {
		return mapper.getImageData(triedImg);
	}
	
	// 조회(삭제예정)
	public List<TriedDto> selectTriedList() throws Exception {
		return mapper.selectTriedList();
	}


}
