package cn.com.taiji.web.action.system;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.DeptService;
import cn.com.taiji.web.service.sys.UserService;


/** 
* @ClassName: UserPowerController 
* @Description: TODO(用户管理了权限控制) 
* @author ranxing
* @date 2017年11月8日 下午2:20:51 
*  
*/
@Controller
public class UserPowerController extends BaseAction {
	
public static final String ReqMapping = "sys/UserPowerController!" ;
	
	@Inject
	UserService userService;
	

	@Inject
	DeptService deptService;
	
	/**
	 * 
	* @Title: toDeptTree 
	* @Description: TODO(跳转部门list页面) 
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value=ReqMapping+"todept_tree", method = {RequestMethod.GET })
	public Object toDeptTree(){
		return "portals/page/system/user/depttreelist";
	}
	
	/** 
	* @Title: getDeptTreeList 
	* @Description: TODO(部门treelist) 
	* @param @param model
	* @param @param models    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping(value = ReqMapping+"getDeptTreeList", method = {RequestMethod.POST })
	public void getDeptTreeList(Model model,
			@RequestParam(value = "models", required = false) String models) {
		
			Map<String, Object> map  = deptService.getDeptZtreeList(init(models));
			// 机构表格数据
			model.addAttribute("list", map.get("DtoList"));
	}
	
	/**
	 * 
	* @Title: toDeptTree 
	* @Description: TODO(跳转部门list页面) 
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value=ReqMapping+"todeptuser_tree", method = {RequestMethod.GET })
	public Object toDeptUserTree(Model model,
			@RequestParam(value = "models", required = false) String models) {
		
			Map<String, Object> map  = deptService.getDeptUserZtreeList(models);
			// 机构表格数据
			model.addAttribute("list", map.get("DtoList"));
		return "portals/page/system/user/deptusertreelist";
	}
	
	/** 
	* @Title: getDeptTreeList 
	* @Description: TODO(部门treelist) 
	* @param @param model
	* @param @param models    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping(value = ReqMapping+"getDeptUserTreeList", method = {RequestMethod.POST })
	public void getDeptUserTreeList(Model model,
			@RequestParam(value = "models", required = false) String models) {
		
		//	Map<String, Object> map  = deptService.getDeptUserZtreeList(init(models));
			// 机构表格数据
		//	model.addAttribute("list", map.get("DtoList"));
	}
	
	
	
	
	
	
	
	/**
	 * 
	* @Title: powerDeptTreeList 
	* @Description: TODO(保存部门分配) 
	* @param @param model
	* @param @param pageUtil
	* @param @param id
	* @param @param request
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = ReqMapping+"powerDeptTreeList", method = { RequestMethod.POST })
	public Object  powerDeptTreeList(Model model, PageUtil pageUtil, @RequestParam(value = "models", required = false) String models, HttpServletRequest request ){
		
		 UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
		try {
			 userService.powerDeptTreeList(init(models), userDto);
			 pageUtil.setMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageUtil.setMessage("操作失败");
		}
		return pageUtil;
	}
	

}
