package cn.com.taiji.web.action.system;


import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.UserService;


/** 
* @ClassName: UserController 
* @Description: TODO(用户管理) 
* @author ranxing
* @date 2017年11月6日 下午10:41:14 
*  
*/
@Controller
public class UserController  extends BaseAction {
	
	public static final String ReqMapping = "sys/UserController!" ;
	
	@Inject
	UserService userService;
	
	/** 
	* @Title: getList 
	* @Description: TODO (列表) 
	* @param @param model
	* @param @param models
	* @param @param request    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@ResponseBody
	@RequestMapping(value = ReqMapping+"getList", method = { RequestMethod.POST })
	public Object getList(Model model,  
			PageUtil pageUtil,
			@RequestParam(value = "models", required = false) String models,
			HttpServletRequest request) {
		try {
			pageUtil =  userService.getPageAll( init(models), pageUtil);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageUtil;
	}
	
	
	/**
	 * 
	* @Title: toAdd 
	* @Description: TODO( 跳转新增页面  ) 
	* @param @param model
	* @param @param cf
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = ReqMapping+"to_add", method = { RequestMethod.POST })
	public String toAdd(Model model, UserDto cf){
		model.addAttribute("dto", cf);
		return "portals/page/system/user/edit";
	}

	/**
	 * 
	* @Title: toDetail 
	* @Description: TODO( 跳转详情页面  ) 
	* @param @param model
	* @param @param cf
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = ReqMapping+"to_detail", method = { RequestMethod.POST ,RequestMethod.GET})
	public String toDetail(Model model, @RequestParam(value="id", required=true) String id){
		try {
			model.addAttribute("dto", userService.findOne(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "portals/page/system/user/edit";
	}
	
	/**
	 * 
	* @Title: newDetail 
	* @Description: TODO( 新增数据  ) 
	* @param @param model
	* @param @param cf
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = ReqMapping+"new", method = { RequestMethod.POST })
	public Object  newDetail(Model model, PageUtil pageUtil,@ModelAttribute(value="dto") UserDto dto, HttpServletRequest request){
	    	try {
		    UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
			String str = userService.save(dto, userDto);
			
			pageUtil.setMessage(str);
			} catch (Exception e) {
				e.printStackTrace();
				pageUtil.setMessage("保存失败");
			}
		return pageUtil;
	}
	
	
	/**
	 * 
	* @Title: remove 
	* @Description: TODO(删除) 
	* @param @param model
	* @param @param pageUtil
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = ReqMapping+"remove", method = { RequestMethod.POST })
	public Object  removeDetail(Model model, PageUtil pageUtil, @RequestParam(value="id", required=true)String id, HttpServletRequest request ){
		
		 UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
		 String str;
		try {
			str = userService.remove(id, userDto);
			
			pageUtil.setMessage(str);
		} catch (Exception e) {
			e.printStackTrace();
			pageUtil.setMessage("操作失败");
		}
		
		return pageUtil;
	}
	
	
	/**
	 * 查询用户名重复
	 * 
	 * @param userDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = ReqMapping+"check", method = { RequestMethod.POST })
	public int userNameStatus(@RequestParam(value = "loginName", required = false) String loginName, HttpServletResponse response, HttpServletRequest request) {
			   	int s = userService.findName(loginName);
			 return  s ;	
	}
	
	
	
	
	/**
	 * 
	* @Title: toDeptTreeList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = ReqMapping+"todeptlist_tree", method = {RequestMethod.GET })
	public String toDeptTreeList(Model model) {
			return "portals/page/system/user/depttreelist";
	}
	
	
}
