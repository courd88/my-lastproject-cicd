package project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import project.dto.KoreapriceDto;
import project.exchange.Exchange;
import project.exchange.ExchangeDto;
import project.service.KoreapriceService;

@RestController
public class KoreapriceController {

	@Autowired
	KoreapriceService service;
	
	@Autowired
	Exchange exchange;

	//물가 정보 조회
	@GetMapping("/api/koreaprice")
	public ResponseEntity<List<KoreapriceDto>> selectKoreapriceList() throws Exception {

		List<KoreapriceDto> koreapriceList = new ArrayList<>();

		koreapriceList = service.selectKoreapriceList();

		return ResponseEntity.status(HttpStatus.OK).body(koreapriceList);
	}
	
	//환율 정보 조회
	@GetMapping("/api/exchangeList")
	public ResponseEntity<List<ExchangeDto>> selectExchangeList() throws Exception{
		
		List<ExchangeDto> exchangeList = new ArrayList<>();
		
		exchangeList = service.selectExchangeList();
		
		return ResponseEntity.status(HttpStatus.OK).body(exchangeList);
	}
	
	@PostMapping("/api/exchangeList")
	public void insertExchangeList() throws Exception{
		
		exchange.getExchangeData();
	}
	
}
