package project.imgTransform;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class ImgTransform {

	final String UPLOAD_DIR = "C:/java/upload/";
	
	public List<Map<String, String>> saveFiles(MultipartFile[] files) {
		List<Map<String, String>> resultList = new ArrayList<>();

		if (files != null) {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				System.out.println(">>>>>>>>>>>>>>>>>>" + originFileName);
				String savedFileName = System.currentTimeMillis() + originFileName;

				try {
					File f = new File(UPLOAD_DIR + savedFileName);
					mf.transferTo(f);

					Map<String, String> result = new HashMap<>();
					result.put("originalFileName", originFileName);
					result.put("savedFileName", savedFileName);
					resultList.add(result);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}
	
}
