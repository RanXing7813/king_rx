package cn.com.king.web.service;

import cn.com.king.dto.InspectionConfigDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;

public interface MonitorService {

	PageUtil getPageList(PageUtil page, int rows);

	int remove(String [] ids, UserDto userDto);

	int save(InspectionConfigDto dto, UserDto userDto);
	
	void newData(InspectionConfigDto dto, UserDto userDto);
  
}
