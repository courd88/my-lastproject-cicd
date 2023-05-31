package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.KoreapriceDto;
import project.exchange.ExchangeDto;

@Mapper
public interface KoreapriceMapper {

	//물가 정보 조회
	public List<KoreapriceDto> selectKoreapriceList() throws Exception;
	
	
	//환율 정보 조회
	public List<ExchangeDto> selectExchangeList() throws Exception;
	
	//환율 정보 입력
	public void insertExchangeList(ExchangeDto exchangeDto) throws Exception;
	
	//환율 정보 업데이트
	public void updateExchangeList(ExchangeDto exchagneDto) throws Exception;
	
}
