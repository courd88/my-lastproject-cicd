package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import project.dto.IdealworldcupDto;
import project.dto.RawinfoDto;
import project.service.WorldcupService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WorldcupApiController {

	private static final String IdealworldcupDto = null;

	@Autowired
	private WorldcupService worldcupService;

	//
	@GetMapping("/api/worldcup")
	public ResponseEntity<List<IdealworldcupDto>> openWorldcupList() throws Exception {
		List<IdealworldcupDto> list = worldcupService.selectWorldcupList();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

	//삭제예정
	@GetMapping("/api/rawinfo")
	public ResponseEntity<List<RawinfoDto>> selectRawinfoList() throws Exception {
		List<RawinfoDto> list = worldcupService.selectRawinfoList();
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

	//카테고리별 로우정보 가져오기
	@GetMapping("/api/rawinfo/{triedCategoryIdx}")
	public ResponseEntity<List<RawinfoDto>> selectRawinfoList(@PathVariable int triedCategoryIdx) throws Exception {
		List<RawinfoDto> list = worldcupService.selectRawinfoListByCategory(triedCategoryIdx);
		if (list != null && list.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}

	//우승한 상품 정보 보기
	@GetMapping("/api/worldcup/{idealworldcupIdx}")
	public ResponseEntity<String> openWorldcupDetail(@PathVariable("idealworldcupIdx") int idealworldcupIdx)
			throws Exception {
		IdealworldcupDto idealworldcupDto = worldcupService.selectWorldcupDetail(idealworldcupIdx);
		if (idealworldcupDto == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(IdealworldcupDto);
		}
	}
	
	
	//우승 품목에 우승 횟수 + 1
	@PutMapping("/api/idealworldcup/{rawinfoIdx}")
	public ResponseEntity<Integer> updateRawinfoWincnt(@PathVariable("rawinfoIdx") int rawinfoIdx) throws Exception {
		int Wincnt = worldcupService.updateRawinfoWincnt(rawinfoIdx);
		if (Wincnt != 1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Wincnt);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(Wincnt);
		}
	}

	//우승한 품목 상세 정보 조회
	@GetMapping("/api/idealworldcupwin/{rawinfoIdx}")
	public ResponseEntity<RawinfoDto> selectRawinfowinDetail(@PathVariable("rawinfoIdx") int rawinfoIdx) throws Exception {
		RawinfoDto rawinfoDto = worldcupService.selectRawinfowinDetail(rawinfoIdx);
		if (rawinfoDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(rawinfoDto);
		}
	}
	
	//우승 총 횟수 가져오기
	@GetMapping("/api/idealworldcuptotalwincnt/{triedCategoryIdx}")
	public ResponseEntity<Integer> selectTotalwincnt(@PathVariable("triedCategoryIdx") int triedCategoryIdx) throws Exception{
		int totalWincnt = worldcupService.selectTotalwincnt(triedCategoryIdx);
		if (totalWincnt == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(totalWincnt);
		}
	}
}
