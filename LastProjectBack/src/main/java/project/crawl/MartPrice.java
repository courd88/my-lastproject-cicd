package project.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MartPrice {

	//한국물가정보 대형마트 가격정보 참조
	
	// CateCode
	// L002001 = 조미료
	// L002002 = 낙농물
	// L002003 = 면류
	// L002004 = 스낵류
	// L002005 = 씨리얼
	// L002006 = 통조림
	// L002007 = 차류
	// L002008 = 음료
	// L002009 = 일용품
	// L002010 = 즉석밥
	// L002011 = 주류
	// L002012 = 김치류
	// L002013 = 수산물
	// L002014 = 수산가공품
	private final String[] PRODUCT_CATEGORY_NUMBERS = { "L002001", "L002002", "L002003", "L002004", "L002005",
			"L002006", "L002007", "L002008", "L002009", "L002010", "L002011", "L002012", "L002013", "L002014" };

	private final String[] PRODUCT_CATEGORY_NAMES = {"조미료", "낙농물", "면류", "스낵류", "씨리얼",
			"통조림", "차류", "음료", "일용품", "즉석밥", "주류", "김치류", "수산물", "수산가공품" };
	// DateCode = 년도 & 월 + 숫자 1 = 2023011
	private String YEAR = "2023";
	private String MONTH = "01";
	private String PRODUCT_DATE_CODE = YEAR + MONTH + "1";

	int categoryNumber = 5;
	
	// URL샘플 =
	// "https://kpi.or.kr/www/life/info_detail.asp?CateCode=L002009&DateCode=2022021"
	String martPriceURL = "https://kpi.or.kr/www/life/info_detail.asp?CateCode=" + PRODUCT_CATEGORY_NUMBERS[categoryNumber]
			+ "&DateCode=" + PRODUCT_DATE_CODE;

	public void getMartProductPrice() throws IOException {
		Document doc = Jsoup.connect(martPriceURL).get();
		Elements e1 = doc.select("table");

		System.out.println(e1.toString());

		Elements e2 = e1.select("td");
		Elements tagTr = e1.select("tr");
		int countTagTr = tagTr.size()-1;
				

		System.out.println(e2.toString());

		List<MartProductDto> martProductList = new ArrayList<>();
		
		String category = PRODUCT_CATEGORY_NAMES[categoryNumber];
		
		System.out.println(countTagTr);
		
		for (int i = 0; i < (countTagTr*7); i += 7) {

			int x = 1;
			if(i != 0 ) {
				x = (i/7)+1;
			}
			
			MartProductDto martProduct = new MartProductDto();
			
			martProduct.setProductCategory(category);
			System.out.println(x + "번째 상품 카테고리, " + category);
	
			String e3 = e2.get(i).text();
			martProduct.setProductType(e3);
			System.out.println(x + "번째 상품 타입, " + e3);

			e3 = e2.get(i + 1).text();
			martProduct.setProductMaker(e3);
			System.out.println(x + "번째 상품 제조사, " + e3);

			e3 = e2.get(i + 2).text();
			martProduct.setProductName(e3);
			System.out.println(x + "번째 상품 명칭, " + e3);
			
			e3 = e2.get(i + 3).text();
			martProduct.setProductCapacity(e3);
			System.out.println(x + "번째 상품 용량, " + e3);

			e3 = e2.get(i + 4).text();
			martProduct.setProductPrice(Integer.parseInt(e3.replace(",", "")));
			System.out.println(x + "번째 상품 가격, " + e3);
			
			martProductList.add(martProduct);

		};
		
		
		
		System.out.println(martProductList.size());
		System.out.println(martProductList.toString());
//		Elements e1 = doc.getElementsByAttributeValue("class", "snapImg");
//
//		Element e2 = e1.get(0);
//		Elements e3 = e2.select("a");
//		String findString = e3.get(0).toString();
//		int stringEndIdx = e3.get(0).toString().indexOf("?");
//		String a = findString.substring(11, stringEndIdx);
//		System.out.println(a);
	}

}
