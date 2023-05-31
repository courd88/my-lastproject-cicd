package project.dto;

import lombok.Data;

@Data
public class TiermakerResultDto {
	//티어메이커결과
	int tiermakerResultIdx;			//티어메이커결과 인덱스
	String tiermakerResultTier;		//티어값
	int tiermakerIdx;				//티어글 인덱스
	int rawinfoIdx;					//로우데이터정보 인덱스
}
