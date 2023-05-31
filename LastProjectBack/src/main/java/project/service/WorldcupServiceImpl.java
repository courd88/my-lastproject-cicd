package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.IdealworldcupDto;
import project.dto.IdealworldcupStaticDto;
import project.dto.RawinfoDto;
import project.mapper.WorldcupMapper;

@Service
public class WorldcupServiceImpl implements WorldcupService {

	@Autowired
	private WorldcupMapper worldcupMapper;

	@Override
	public int insertWorldCup(IdealworldcupDto idealworldcupDto) throws Exception {
		return worldcupMapper.insertWorldcup(idealworldcupDto);

	}

	@Override
	public int insertStaticWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception {
		return worldcupMapper.insertStaticWorldcup(idealworldcupStaticDto);

	}
	
	
	@Override
	public int idealworldcupStaticWincnt(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception {
	    int idealworldcupStaticWncnt = worldcupMapper.idealworldcupStaticWincnt(idealworldcupStaticDto);
	    return idealworldcupStaticWncnt;
	}

	@Override
	public IdealworldcupDto selectWorldcupDetail(int idealworldcupIdx) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdealworldcupDto> selectWorldcupList() throws Exception {
		return worldcupMapper.selectWorldcupList();
	}

	@Override
	public int insertWorldcup(IdealworldcupStaticDto idealworldcupStaticDto) throws Exception{
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<RawinfoDto> selectRawinfoList() throws Exception{
		return worldcupMapper.selectRawinfoList();
	}

	@Override
	public List<RawinfoDto> selectRawinfoListByCategory(int triedCategoryIdx) throws Exception {
		
		return worldcupMapper.selectRawinfoListByCategory(triedCategoryIdx);
	}

	@Override
	public int updateRawinfoWincnt(int rawinfoIdx) {
		return worldcupMapper.updateRawinfoWincnt(rawinfoIdx);
	}
	
	@Override
	public RawinfoDto selectRawinfowinDetail(int rawinfoIdx) throws Exception {
		return worldcupMapper.selectRawinfowinDetail(rawinfoIdx);
	}
	
	@Override
	public int selectTotalwincnt(int triedCategoryIdx) throws Exception{
		return worldcupMapper.selectTotalwincnt(triedCategoryIdx);
	}
	
}
