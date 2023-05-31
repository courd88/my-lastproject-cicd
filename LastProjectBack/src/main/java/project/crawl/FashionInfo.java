package project.crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FashionInfo {
	
	private final String fashionInfoURL = "https://www.musinsa.com";
	private String fashionInfoRecentURL = "";
	
	public void getFashionInfoUpdateCheck() throws IOException{
	
		Document doc = Jsoup.connect("http://192.168.0.46:3000/notice/write").get();
		
		//스타일 첫페이지 검색해서 첫번째 사진의 날짜데이터만 가져옴.
		//날짜가 현재 DB에 있는 최신 데이터 날짜랑 같다면 크롤링 해서 DB에 값을 넣을 필요가 없음.
		//최신 날짜 가져오는 service랑 mapper 로직 추가 필요
		

		System.out.println(doc.text());
		
	}
	
	//가장 최근 
	public void getFashionInfoRecentAddress() throws Exception{
		
		
		Document doc = Jsoup.connect("https://www.musinsa.com/mz/streetsnap?_mon=&gender=&p=1#listStart").get();
		
		Elements e1 = doc.getElementsByAttributeValue("class","articleImg");
		Element e2 = e1.get(0);
		System.out.println(e2.toString());
		String e3 = e2.select("a").attr("href");
//		Elements e3 = e2.getElementsByAttributeValue("a","href");
		System.out.println(e3.toString());
		fashionInfoRecentURL = e3;
		
	}
	
	
	public void getFashionInfoByDay() throws IOException{
		
		Document doc = Jsoup.connect(fashionInfoURL + fashionInfoRecentURL).get();
		
		Elements e1 = doc.getElementsByAttributeValue("class","snapImg");
		Element e2 = e1.get(0);
		Elements e3 = e2.select("a");
		String findString = e3.get(0).toString();
		int stringEndIdx = e3.get(0).toString().indexOf("?");
		String a = findString.substring(11, stringEndIdx);
		System.out.println(a);
		
	}
}
