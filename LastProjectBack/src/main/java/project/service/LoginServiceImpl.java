package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.dto.GoogleDto;
import project.dto.UserDto;
import project.mapper.LoginMapper;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override	//"/login" 내장
	public UserDto login(UserDto userDto) throws Exception{
		return loginMapper.login(userDto);
	}
	
	//UserDetailsService 인터페이스의 메소드
	//username은 고유값인 userId를 가지고 확인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto userDto = loginMapper.selectUserByUserId(username);
		//가져온 userDto내용이 없다면, 에러발생
		if ( userDto == null ) {
			throw new UsernameNotFoundException(username);
		}
		//있다면, 유저의 Id와 Pw를 기준으로 User객체 생성
		//User객체는 UserDetails 인터페이스를 상속.
		return new User(userDto.getUserId(), userDto.getUserPw(),
				true, true, true, true, new ArrayList<>());
	}
	
	@Override
	public UserDto selectUserByUserId(String userId) throws Exception {
		return loginMapper.selectUserByUserId(userId);
	}

	@Override
	public int registUser(UserDto userDto) throws Exception {
		userDto.setUserImg("profile" + ((int)(Math.random()*8)+1) + ".png");
		userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw()));
		return loginMapper.registUser(userDto);
	}
	
	@Override
	public String idDuplicateCheck(String userId) throws Exception{
		return loginMapper.idDuplicateCheck(userId);
	}
	
	@Override
	public String nicknameDuplicateCheck(String userNickname) throws Exception{
		return loginMapper.nicknameDuplicateCheck(userNickname);
	}
	
	
	
	@Override
	public List<UserDto> googlelogin(GoogleDto googleDto) throws Exception{
		String username = googleDto.getEmail();
		return loginMapper.googlelogin(username);
		
	}

}
