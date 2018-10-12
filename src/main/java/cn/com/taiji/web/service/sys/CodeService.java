package cn.com.taiji.web.service.sys;

import java.util.List;
import java.util.Map;

import cn.com.king.domain.db1.Code;
import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;

public interface CodeService {

	List getCodeByLevel();

	Object findOne(String id) throws Exception;

	String saveOne(Code dto, UserDto userDto)  throws Exception;

	String remove(String id, UserDto userDto) throws Exception;

	List getCodeByPid(String id);

}
