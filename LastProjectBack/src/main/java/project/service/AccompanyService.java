package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.AccompanyDto;
import project.mapper.AccompanyMapper;

@Service
public class AccompanyService {

	@Autowired
	private AccompanyMapper mapper;
	
	// Accompany 페이징 및 검색
	public List<AccompanyDto> listAccompanyDtoByPages(int pages, String search, String accompanyRegion)
			throws Exception {

		int offsetStart = (pages - 1) * 9;

		return mapper.listAccompanyDtoByPages(offsetStart, search, accompanyRegion);
	}

	// Accompany 페이지 수 조회
	public int listAccompanyDtoSearchPageCount(String search, String accompanyRegion) throws Exception {
		return mapper.listAccompanyDtoSearchPageCount(search, accompanyRegion);
	}

	// Accompany 상세페이지 조회 및 조회수 증가
	public AccompanyDto accompanyDetail(int accompanyIdx) throws Exception {
		mapper.updateCount(accompanyIdx);
		return mapper.accompanyDetail(accompanyIdx);
	}

	// Accompany 작성
	public int insertAccompany(AccompanyDto accompanyDto) throws Exception {
		return mapper.insertAccompany(accompanyDto);
	}

	// Accompany 삭제
	public int deleteAccompany(int accompanyIdx) throws Exception {
		return mapper.deleteAccompany(accompanyIdx);
	}

	// Accompany 수정
	public int updateAccompany(AccompanyDto accompanyDto) throws Exception {
		return mapper.updateAccompany(accompanyDto);
	}
}
