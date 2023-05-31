package project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.mapper.LoginMapper;
import project.service.LoginService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	

	
// 	생성자를 이용한 의존 주입
	private LoginService loginService;
	private LoginMapper loginMapper;
	private BCryptPasswordEncoder passwordEncoder;
	private Environment env;
	private JwtTokenUtil jwtTokenUtil;
	private JwtRequestFilter jwtRequestFilter;
	
	public WebSecurity(LoginService loginService, LoginMapper loginMapper, BCryptPasswordEncoder passwordEncoder, Environment env, JwtTokenUtil jwtTokenUtil, JwtRequestFilter jwtRequestFilter) {
		this.loginService = loginService;
		this.loginMapper = loginMapper;
		this.passwordEncoder = passwordEncoder;
		this.env = env;
		this.jwtTokenUtil = jwtTokenUtil;
		this.jwtRequestFilter = jwtRequestFilter;
	}
	
	

	// 	접근통제와 관련한 설정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
//		 http.authorizeRequests().antMatchers("/**").permitAll();
		
//		http.authorizeRequests()
//			.antMatchers("**").authenticated()
//			.and().addFilter(getAuthenticationFilter());
		
		http.authorizeRequests()
			.antMatchers("/login", "/ws/**", "/api/login/google", "/api/idduplicatecheck", "/api/nicknameduplicatecheck", "/api/regist", "/error", "/api/something/mainpage", "/api/getImage/**", "/api/getimage/**", "/chatroombyuser", "/api/koreaprice", "/api/exchangeList", "/api/course").permitAll() //로그인 없이 허용할 주소 설정.
//			.antMatchers("**").permitAll()
			.anyRequest().authenticated()					//허용 외의 다른 요청들은 인증실행한다.
			.and().addFilter(getAuthenticationFilter())		//필터를 추가한다.(콜백으로 인증필터 실행)
			.addFilterBefore(jwtRequestFilter, AuthenticationFilter.class).cors();	//필터수행전에.
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(loginMapper, env, jwtTokenUtil);
		authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}
	
	//	인증 처리 방법을 설정
	// 	사용자 정보를 조회할 서비스와 패스워드 암호화에 사용할 방식을 지정
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService).passwordEncoder(passwordEncoder);
	}
}
