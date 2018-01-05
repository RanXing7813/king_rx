package cn.com.taiji.web.service.sys;

import java.util.List;
import java.util.Map;

import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;

public interface MenuService {

	PageUtil getMainMenu(PageUtil page, Map<String, Object> searchParameters);

	PageUtil getDefaultMenu(PageUtil page);

	String getBackPage(String pId, String id, String groupname);

	List getMenuAll();

	Object findOne(String id) throws Exception;

	String saveOne(Menuinfo dto, UserDto userDto)  throws Exception;

	String remove(String id, UserDto userDto) throws Exception;

	void updMenuInfoState(String id) throws Exception;

}
