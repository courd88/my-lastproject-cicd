package project.service;

import java.util.List;

import project.dto.IdealworldcupDto;
import project.dto.IdealworldcupStaticDto;
import project.dto.RawinfoDto;

public interface WorldcupService {

	//우승 데이터 입력
	public int insertWorldCup(IdealworldcupDto idealworldcupDto) throws Exception;

	//로우데이터별 우승 횟수 조회
	public IdealworldcupDto selectWorldcupDetail(int idealworldcupIdx) throws Exception;

	//삭제 예정
	public List<IdealworldcupDto> selectWorldcupList() throws Exception;

	//우승횟수 입력 + 1
	public int idealworldcupStaticWincnt(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception;

	//모르겠다
	public int insertWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception;
	
	//삭제 예정
	public int insertStaticWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception;

	//월드컵 리스트 불러오기
	public List<RawinfoDto> selectRawinfoList() throws Exception;
	
	//월드컵 리스트 카테고리별 조회
	public List<RawinfoDto> selectRawinfoListByCategory(int triedCategoryIdx) throws Exception;
	
	//로우정보에 월드컵우승 횟수 +1
	public int updateRawinfoWincnt(int rawinfoIdx);
	
	//월드컵 우승후 결과페이지
	public RawinfoDto selectRawinfowinDetail(int rawinfoIdx) throws Exception;
	
	
	//우승 총 횟수 조회
	public int selectTotalwincnt(int triedCategoryIdx) throws Exception;
}