package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import project.dto.AccompanyDto;

@Mapper
public interface AccompanyMapper {

	// Accompany 페이지별 조회
		public List<AccompanyDto> listAccompanyDtoByPages(@Param("pages") int pages, @Param("search") String search, @Param("accompanyRegion") String accompanyRegion)
				throws Exception;

		// Accompany 페이지 수 조회
		public int listAccompanyDtoSearchPageCount(@Param("search") String search, @Param("accompanyRegion") String accompanyRegion) throws Exception;

		// Accompany 상세페이지 조회
		public AccompanyDto accompanyDetail(int accompanyIdx) throws Exception;
		
		// Accompany 상세페이지 조회수 증가
		public void updateCount(int accompanyIdx);

		// Accompany 작성
		public int insertAccompany(AccompanyDto accompanyDto) throws Exception;

		// Accompany 삭제
		public int deleteAccompany(int accompanyIdx) throws Exception;

		// Accompany 수정
		public int updateAccompany(AccompanyDto accompanyDto) throws Exception;
	
}
