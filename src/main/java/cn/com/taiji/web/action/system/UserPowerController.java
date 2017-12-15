package cn.com.taiji.web.action.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.DeptService;
import cn.com.taiji.web.service.sys.RoleService;
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
	private RoleService roleService;
	@Inject
	DeptService deptService;
	
	/**
	 * 
	* @Title: toDeptTree 
	* @Description: 用户管理分配部门 
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value=ReqMapping+"todept_tree", method = {RequestMethod.GET })
	public Object toDeptTree(){
		return "portals/page/system/dept/depttreelist_haveparentbox";
	}
	
	/** 
	* @Title: getDeptTreeList 
	* @Description: 用户管理分配部门
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
	* @Description: 查看用户所属部门
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value=ReqMapping+"todeptuser_tree", method = {RequestMethod.GET })
	public Object toDeptUserTree(Model model,
			@RequestParam(value = "models", required = false) String models) {
		
		Map<String, Object> map  = deptService.getDeptUser( models);
		// 机构表格数据
		model.addAttribute("list", map.get("DtoList"));
		return "portals/page/system/user/userdepttreelist";
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
		
			Map<String, Object> map  = deptService.getDeptUserZtreeList(init(models));
			// 机构表格数据
			model.addAttribute("list", map.get("DtoList"));
	}
	
	
	/**
	 * 
	* @Title: toDeptTreeList 
	* @Description: 分配角色
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = ReqMapping+"toRolelist_tree", method = {RequestMethod.GET })
	public String toRoleTreeList(Model model) {
			return "portals/page/system/user/depttreelist";
	}
	
	/** 
	* @Title: getDeptTreeList 
	* @Description: 用户管理分配部门
	* @param @param model
	* @param @param models    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping(value = ReqMapping+"getRoleTreeList", method = {RequestMethod.POST })
	public void getRoleTreeList(Model model,
			@RequestParam(value = "models", required = false) String models) {
		
			Map<String, Object> map  = deptService.getDeptUserZtreeList(init(models));
			// 机构表格数据
			model.addAttribute("list", map.get("DtoList"));
	}
	
	
	//******* 用户分配角色   ****** ***************** ******************************	
	
		/**
		 * 
		 * @Description  加载  角色 目录
		 * @param model
		 * @return String  
		 * @throws
		 * @author rangxing
		 * @date 2017年3月15日
		 */
		@RequestMapping(value = "role-user-auth", method = { RequestMethod.GET })
		public String authRole() {
			return "portals/page/system/role/roletreelist_haveparentbox";
		}
		
		
		/**
		 * 
		 * @Description:  弹出框目录页面加载数据
		 * @param model
		 * @return String  
		 * @throws
		 * @author rangxing
		 * @date 2017年3月15日
		 */
		@RequestMapping(value = "role-user-authRoles", method = { RequestMethod.POST,RequestMethod.GET })
		public void authRoles( Model model,
				@RequestParam(value = "models", required = false) String models ,HttpSession httpSession ,HttpServletRequest request) {
			
			//记录开始日志
				UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
			try {
				Map searchParameters = init(models);
				 String id = "";
				 id = searchParameters.get("id").toString();

				  // 目录树数据
				  List<Map<Object,Object>> list =  roleService.getRoles(id); 
				  model.addAttribute("list", list); 
				  model.addAttribute("log_uopr",searchParameters );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 
		 * @Description:  弹出框目录   保存
		 * @param model
		 * @return String  
		 * @throws
		 * @author rangxing
		 * @date 2017年3月15日
		 */
		@RequestMapping(value = "role-authRoles-save", method = { RequestMethod.POST,RequestMethod.GET })
		public void authRolesSave( Model model,
				@RequestParam(value = "models", required = false) String models ,HttpSession httpSession ,HttpServletRequest request ) {
		
			//记录开始日志
					UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
				try {
					Map searchParameters = init(models);
					// 保存目录树数据
					roleService.saveAuthRoles(searchParameters);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		
		//******* 用户 所属权限详情  ****** ***************** ******************************	
		/**
		 * @Description  加载  角色 目录
		 * @param model
		 * @return String  
		 * @throws
		 * @author rangxing
		 * @date 2017年3月15日
		 */
		@RequestMapping(value = "role-authRoles-details", method = { RequestMethod.GET })
		public String authRoleDetail(Model model , HttpServletRequest request) {
			model.addAttribute("dto", request.getParameter("userId"));
			return "portals/page/system/user/user-authDetails";
		}
		
		
		/**
		 * 
		 * @Description:  弹出框目录页面加载数据
		 * @param model
		 * @return String  
		 * @throws
		 * @author rangxing
		 * @date 2017年3月15日
		 */
		@RequestMapping(value = "role-authRoles-detailsss", method = { RequestMethod.POST, RequestMethod.GET })
		public void authRolesDetails( Model model,@RequestParam(value = "roleId", required = false) String roleId ,
				@RequestParam(value = "userId", required = false) String userId ,HttpSession httpSession ,HttpServletRequest request) {
			 
			//记录开始日志
			try {
				 // 目录树数据
				  List<Map<String,Object>> list =  roleService.getSingleRoles(userId); 
				  model.addAttribute("list", list); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @Description:  弹出框目录页面加载数据
		 * @param model
		 * @return String  
		 * @throws
		 * @author rangxing
		 * @date 2017年3月15日
		 */
		@RequestMapping(value = "role-authMenus-detailsss", method = { RequestMethod.POST,RequestMethod.GET })
		public void authMenusDetails( Model model,@RequestParam(value = "roleId", required = false) String roleId ,
				@RequestParam(value = "userId", required = false) String userId , HttpSession httpSession ,HttpServletRequest request) {
			  
			//记录开始日志
				UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
			try {
				// 目录树数据
				 List<Menus> listMenus =  roleService.getMenus(roleId); 
				  List<Map<Object,Object>> list = new ArrayList<Map<Object, Object>>();
				  for(Menus dto:listMenus){
					  Map<Object,Object> map = new HashMap<Object, Object>();
					  map.put("id",dto.getMenuId()) ;
					  map.put("name",dto.getMenuName());
					  map.put("pId", "0".equals(dto.getParentId())?roleId:dto.getParentId());
					  map.put("open", true);
					  map.put("checked", "8".equals(dto.getState())?true:false);
				//	  map.put("chkDisabled", true);
					  list.add(map);
				  }
				  model.addAttribute("list", list);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
