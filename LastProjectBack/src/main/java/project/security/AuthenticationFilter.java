package project.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import project.dto.UserDto;
import project.mapper.LoginMapper;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private LoginMapper loginMapper;
	private Environment env;
	private JwtTokenUtil jwtTokenUtil;
	
	public AuthenticationFilter(LoginMapper loginMapper, Environment env, JwtTokenUtil jwtTokenUtil) {
		this.loginMapper = loginMapper;
		this.env = env;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException {
		try {
			UserDto creds = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.getUserId(),
					creds.getUserPw(),
					new ArrayList<>()
				)
			);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ((User)authResult.getPrincipal()).getUsername();
		UserDto userDto = loginMapper.selectUserByUserId(username);

		String jwtToken = jwtTokenUtil.generateToken(userDto);
		response.setHeader("token", jwtToken);
		response.getWriter().write(jwtToken);
	}
}
