package cn.com.taiji.web.service.sys;

import java.util.Map;

import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;

/** * @author King_RX * */
/** 
* @ClassName: UserService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author ranxing
* @date 2017年11月2日 下午4:25:41 
*  
*/
public interface UserService   {


	
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
	PageUtil getPageAll(Map<String, Object> init, PageUtil page) throws Exception;

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
	String save(UserDto dto, UserDto userDto) throws Exception;

	/** 
	* @Title: findOne 
	* @Description: 查询 
	* @param @param id
	* @param @return    设定文件 
	* @return Dept    返回类型 
	* @throws 
	*/
	UserDto findOne(String id);

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
	String remove(String id, UserDto userDto) throws Exception;

	int findName(String cnm);

	void powerDeptTreeList(Map<String, Object> init, UserDto userDto) throws Exception;

}
