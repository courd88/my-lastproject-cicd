package project.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import project.dto.IdealworldcupDto;
import project.dto.IdealworldcupStaticDto;
import project.dto.RawinfoDto;

@Mapper
public interface WorldcupMapper {

	int insertWorldcup(IdealworldcupDto idealworldcupDto) throws Exception;

	IdealworldcupDto selectWorldcupDetail(int idealworldcupDto) throws Exception;

	List<IdealworldcupDto> selectWorldcupList() throws Exception;

	int idealworldcupStaticWincnt(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception;

	int insertStaticWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception;

	List<RawinfoDto> selectRawinfoList() throws Exception;
	
	List<RawinfoDto> selectRawinfoListByCategory(int triedCategoryIdx) throws Exception;
	
	int updateRawinfoWincnt(int rawinfoIdx);
	
	RawinfoDto selectRawinfowinDetail(int rawinfoIdx) throws Exception;
	
	int selectTotalwincnt(int triedCategoryIdx) throws Exception;
}
