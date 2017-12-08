package cn.com.taiji.web.action.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.king.annotation.NeedntLogin;

//import cn.com.taiji.dto.UserDto;
//import cn.com.taiji.reconcile.service.AuthenticationService;
//import cn.com.taiji.util.NeedntLogin;
//import cn.com.taiji.util.VerifyCodeUtils;

@Controller
public class PortalsIndexController {
	
	//tomcat部署"index",edas部署value={"","index"}，否则访问会出现问题
//	@Inject
//	AuthenticationService authenticationService;
	
	/**默认进入
	 * @param model
	 * @return
	 */
//	@NeedntLogin
//	@RequestMapping(value={"","index"})
//	public String homePage(Model model) {
//		return "redirect:nodeMonitorIndex";
//	}
	
	
//	/**
//	 * 进入首页
//	 * @param model
//	 * @return
//	 */
//	@NeedntLogin
//	@RequestMapping(value="portalsIndex",method = {RequestMethod.GET,RequestMethod.POST})
//	public String portalsIndex(Model model,HttpServletRequest request) {
//		model.addAttribute("menuinit","yw_opsIndex");
//		HttpSession httpSession = request.getSession();
//		UserDto userDto = (UserDto)httpSession.getAttribute("user");
//		if(userDto!=null){
//			httpSession.setAttribute("LogindeptId",userDto.getDeptId());
//			httpSession.setAttribute("LogindeptName",userDto.getUserName());
//		}
//		return "portals/indexs";
//	}
//	
	
	/**
	 * 节点监控首页
	 * @param model
	 * @return
	 */
	@NeedntLogin
	@RequestMapping(value={""},method = {RequestMethod.GET,RequestMethod.POST})
	public String nodeControlIndex(Model model,HttpServletRequest request) {
		model.addAttribute("menuinit","");
		HttpSession httpSession = request.getSession();
//		UserDto userDto = (UserDto)httpSession.getAttribute("user");
//		if(userDto!=null){
//			httpSession.setAttribute("LogindeptId",userDto.getDeptId());
//			httpSession.setAttribute("LogindeptName",userDto.getUserName());
//		}
		return "portals/indexs";
	}
	
	
	
	/**数据服务首页
	 * @param model
	 * @param request
	 * @return
	 */
	@NeedntLogin
	@RequestMapping(value="dataServiceIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String dataServiceIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","dataService_Index");
		return "portals/indexs";
	}
	
	/**服务目录首页
	 * @param model
	 * @param request
	 * @return
	 */
	@NeedntLogin
	@RequestMapping(value="serviceMenuIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String serviceMenuControlIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","serviceMenu_Index");
		return "portals/indexs";
	}
	/**系统首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="serviceMenu_Index",method = {RequestMethod.GET,RequestMethod.POST})
	public String toServiceMenuControlIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","system_Index");
		return "portals/page/system/systemIndex2";
	}
	
	/**项目目录首页
	 * @param model
	 * @param request
	 * @return
	 */
	@NeedntLogin
	@RequestMapping(value="projectMenuIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String projectMenuIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","projectMenu_Index");
		return "portals/indexs";
	}
	
	/**运行目录首页
	 * @param model
	 * @param request
	 * @return
	 */
	@NeedntLogin
	@RequestMapping(value="runningMonitorIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String runningMenuIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","runningMonitor_Index");
		return "portals/indexs";
	}
	
	/**系统首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="systemIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String systemIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","system_Index");
		return "portals/indexs";
	}
	/**系统首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="system_Index",method = {RequestMethod.GET,RequestMethod.POST})
	public String toSystemIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","system_Index");
		return "portals/page/system/systemIndex";
	}
	
	
	

		
	@RequestMapping(value="modelSystemIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String modelSystemIndex(Model model,HttpServletRequest request){
		model.addAttribute("menuinit","modelSystem_Index");
		return "portals/indexs";

	}
	
	
	
	
}
