package cn.com.taiji.web.service.sys;

import java.util.Map;

import cn.com.king.page.util.PageUtil;

public interface MenuService {

	PageUtil getMainMenu(PageUtil page, Map<String, Object> searchParameters);

	PageUtil getDefaultMenu(PageUtil page);

	String getBackPage(String pId, String id, String groupname);


}
