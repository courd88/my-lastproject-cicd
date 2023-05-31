package project.dto;

import lombok.Data;

@Data
public class GoogleDto {
	
	private String aud;				//Your server's client ID
	private String azp;				//위랑 동일
	private String email;			//구글메일 주소
	private boolean email_verified;	//메일 검증여부
	private String exp;				//
	private String family_name;		//성
	private String given_name;		//이름
	private int iat;				//??
	private String iss;				//??
	private String jti;				//??
	private String name;			//풀네임
	private int nbf;				//??
	private String picture;			//프로필 사진
	private String sub;				//??
}
