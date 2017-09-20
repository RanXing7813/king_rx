package cn.com.king.web.service.login;

import java.util.List;

public interface LoginService {
    boolean login(String userName, String password);
	void add(String num);
	List  findAll();
	 void updateS();
}
