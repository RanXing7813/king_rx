package cn.com.king.web.service.login;

import java.util.List;
import java.util.Map;

import cn.com.king.dto.UserDto;



public interface AuthenticationService {
	 UserDto login(String str,String str2);
	 List getusermenu(String userid);
	 boolean getFlagSys(String userid);
	
	//获取菜单列表
	 Map<String,List<Map<String, Object>>> getMenuList(String userId, String pmenuId);
	 List<Map<String, Object>> menuList (String userId, String pmenuId);
}
