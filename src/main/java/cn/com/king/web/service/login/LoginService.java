package cn.com.king.web.service.login;

import java.util.List;

import cn.com.king.dto.UserDto;

public interface LoginService {
   // boolean login(String userName, String password);
	void add(String num);
	List  findAll();
	 void updateS();
	boolean getFlagSys(String userid);
	UserDto login(String loginName, String pword);
}
