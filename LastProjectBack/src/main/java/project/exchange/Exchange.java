package project.exchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.mapper.KoreapriceMapper;
import project.service.KoreapriceService;

@Service
public class Exchange {
	
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	private final String API_KEY ="N5loPWwtcg1d6koVMZt6dV7SukG01zjK"; 
	
	@Autowired
	private KoreapriceService service;
	
	@Autowired
	private KoreapriceMapper mapper;
	
	//매일 체크해서 환율 API 정보 가져와야 함
	@Scheduled(cron= " 0 0 9-18 * * *")
	public void getExchangeData() throws JSONException, Exception {
	
		try {
			HttpGet httpGet = new HttpGet("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + API_KEY + "&data=AP01");
			CloseableHttpResponse response1 = httpClient.execute(httpGet);
			try {
				System.out.println(response1.getStatusLine());
				
				ResponseHandler<String> handler = new BasicResponseHandler();
				String responseBody = handler.handleResponse(response1);
				System.out.println("응답결과 : "+responseBody);
				
				List<ExchangeDto> exchangeDtoList = new ArrayList<>();
				
				JSONArray jsonArray = new JSONArray(responseBody);
				
				for ( int i = 0; i < jsonArray.length() ; i ++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					String nationShort = jsonObj.getString("cur_unit");
					String nation = jsonObj.getString("cur_nm");
					String exchange = jsonObj.getString("kftc_deal_bas_r");
					
					double exchangeRate = Double.parseDouble(exchange.replace(",", ""));
					
					ExchangeDto exchangeDto = new ExchangeDto();
					exchangeDto.setExchangeNationShort(nationShort);
					exchangeDto.setExchangeNation(nation);
					exchangeDto.setExchangeRate(exchangeRate);
					
					mapper.updateExchangeList(exchangeDto);
					
					exchangeDtoList.add(exchangeDto);
					
				}
				System.out.println(exchangeDtoList.toString());
				
				//환율은 매매기준율 사용.
				HttpEntity entity1 = response1.getEntity();
				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
