package project.security;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import project.dto.UserDto;

@Slf4j
@Component
public class JwtTokenUtil {

	private String secret;
	private Long expirationTime;
	private Key hmacKey;
	
	public JwtTokenUtil(Environment env) {
		this.secret = env.getProperty("token.secret");
		this.expirationTime = Long.parseLong(env.getProperty("token.expiration-time"));
		this.hmacKey = new SecretKeySpec(
			Base64.getDecoder().decode(this.secret), SignatureAlgorithm.HS256.getJcaName()
		);
	}
	
	public String generateToken(UserDto userDto) {
		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
				.claim("nickname", userDto.getUserNickname())   //토큰생성시 claim에 넣을 키와 값 지정.
				.claim("name", userDto.getUserName())			//유저이름
				.claim("userImg", userDto.getUserImg())			//유저 이미지
				.claim("countryIdx", userDto.getCountryIdx())	//유저 국가IDX
				.setSubject(userDto.getUserId())				//해당 토큰이 발급되는 주체지정.
				.setId(UUID.randomUUID().toString())	//jwt토큰에 UUID(고유값)으로 지정.
				.setIssuedAt(Date.from(now))			//발행시간(현재)
				.setExpiration(Date.from(now.plus(this.expirationTime, ChronoUnit.MILLIS)))   //만료일(20시간)
				.signWith(this.hmacKey)					//암호화를 위해 지정해둔 문자
				.compact();								//위의 설정된 내용들을 기준으로 마무리.
		log.debug(jwtToken);
		return jwtToken;
	}
	
	private Claims getAllClaimsFromToken(String token) {
		Jws<Claims> jwt = Jwts.parserBuilder()
				.setSigningKey(this.hmacKey)
				.build()
				.parseClaimsJws(token);		//토큰을 가지고 서명 검증 후 정보(claim) 추출
		return jwt.getBody();
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public String getSubjectFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);		
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);	
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDto userDto) {
		// 토큰의 유효기간을 체크 
		if (isTokenExpired(token)) {
			return Boolean.FALSE;
		}
		
		// 토큰 내용을 검증 
		String subject = getSubjectFromToken(token);
		String userId = userDto.getUserId();
		if (subject != null && userId != null && subject.equals(userId)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
		
	}
}
