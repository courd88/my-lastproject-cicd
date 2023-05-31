package project.dto;

import lombok.Data;

@Data
public class IdealworldcupStaticDto {
	//이상형월드컵 통계
	int idealworldcupStaticIdx;			//통계결과인덱스
	int idealworldcupStaticWincnt;		//우승횟수
	int rawinfoIdx;						//로우정보인덱스(외래키)
}
