package cn.com.taiji.web.service;

import java.util.Map;

import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.log.DataSourceImpl;

public abstract class BaseService2<T>  extends DataSourceImpl{

	
	
	/** 
	* @Title: getPageAll 
	* @Description: 查询
	* @param @param init
	* @param @param page
	* @param @return
	* @param @throws Exception    设定文件 
	* @return PageUtil    返回类型 
	* @throws 
	*/
	public PageUtil getPageAll(Map<String, Object> init, PageUtil page) throws Exception {
		return null;
	}

	/** 
	* @Title: saveDept 
	* @Description: 保存
	* @param @param dto
	* @param @param userDto
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String saveS(T dto, T userDto) throws Exception {
		return null;
	}

	/** 
	* @Title: findOne 
	* @Description: 查询 
	* @param @param id
	* @param @return    设定文件 
	* @return Dept    返回类型 
	* @throws 
	*/
	public T findOneS(String id) throws Exception {
		return null;
	}

	/**
	 * @throws Exception  
	* @Title: remove 
	* @Description: 删除 
	* @param @param id
	* @param @param userDto
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String removeS(String id, T userDto) throws Exception {
		String message = "操作失败";
		return message;
	}

	
	
}
