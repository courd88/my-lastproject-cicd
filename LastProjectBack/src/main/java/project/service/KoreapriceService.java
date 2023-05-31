package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.KoreapriceDto;
import project.exchange.ExchangeDto;
import project.mapper.KoreapriceMapper;

@Service
public class KoreapriceService {

	@Autowired
	KoreapriceMapper mapper;
	
	//물가 정보 조회
	public List<KoreapriceDto> selectKoreapriceList() throws Exception{
		return mapper.selectKoreapriceList();
	};
	
	
	
	//환율 정보 조회
	public List<ExchangeDto> selectExchangeList() throws Exception{
		return mapper.selectExchangeList();
	}
	
	//환율 정보 입력
	public void insertExchangeList(ExchangeDto exchangeDto) throws Exception{
		mapper.insertExchangeList(exchangeDto);
	};
	
	//환율 정보 업데이트
	public void updateExchangeList(ExchangeDto exchangeDto) throws Exception{
		mapper.updateExchangeList(exchangeDto);
	};
	
}
