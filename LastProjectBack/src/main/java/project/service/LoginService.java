package project.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.dto.GoogleDto;
import project.dto.UserDto;

public interface LoginService extends UserDetailsService {

	public UserDto login(UserDto userDto) throws Exception;
	public int registUser(UserDto userDto) throws Exception;
	public UserDto selectUserByUserId(String userId) throws Exception;
	public String idDuplicateCheck(String userId) throws Exception;
	public String nicknameDuplicateCheck(String userNickname) throws Exception;
	public List<UserDto> googlelogin(GoogleDto googleDto) throws Exception;
	
}
