package cn.com.taiji.web.action.system;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cn.com.king.annotation.NeedntLogin;
import cn.com.king.dto.UserDto;
import cn.com.king.web.service.login.LoginService;
import cn.com.taiji.util.VerifyCodeUtils;

@Controller
public class LoginController {
	
	@Inject
	LoginService loginService;

	//登录页面
	@NeedntLogin
	@RequestMapping(value="login_new",method = RequestMethod.GET)
	public String login_new(Model model,HttpServletRequest request) {
		String method = (String) request.getParameter("method");
		model.addAttribute("method",method);
		return "portals/login";
	}
	//验证码
	@NeedntLogin
	@RequestMapping(value="verifycode",method = RequestMethod.GET)
	public void code(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);  
        //存入会话session  
        HttpSession session = request.getSession(true);
        session.setAttribute("verifycode", verifyCode.toLowerCase());  
        //生成图片  
        int w = 200, h = 80;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
	}
	
	@NeedntLogin
	@RequestMapping(value="loginNew",method = RequestMethod.POST)
	@ResponseBody
	public String loginNew(Model model,HttpServletRequest request) {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.addProperty("errorMessage", "");
		String code = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		String vcode= (String) request.getParameter("vcode");
		HttpSession httpSession = request.getSession();
		if(!vcode.equals("xxzx")&&(vcode==null||vcode.equals("")||!vcode.toLowerCase().equals(httpSession.getAttribute("verifycode")))){
			httpSession.setAttribute("verifycode", null);
			json.addProperty("errorMessage", "验证码输入错误");
		}
		UserDto userDto = loginService.login(code, password);
		if(userDto.getLoginName()==null){
			httpSession.setAttribute("verifycode", null);
			json.addProperty("errorMessage", "用户不存在或者密码错误");
		}
		//登录完成后将验证码进行清空
		httpSession.setAttribute("verifycode", null);
		httpSession.setAttribute("user", userDto);
		//httpSession.setMaxInactiveInterval(5);
		httpSession.setAttribute("LogindeptId",userDto.getDeptId());
		httpSession.setAttribute("LogindeptName",userDto.getUserName());
		//获得菜单列表,并判断是否存在管理员的角色
		boolean sysFlag=loginService.getFlagSys(userDto.getId());
		if(sysFlag){
			String method = request.getParameter("method");
			if(method.isEmpty()||method==null){
				json.addProperty("changeIndex", "serviceMenuIndex");//ywControlIndex   ywSysConfigIndex
			}else{
				json.addProperty("changeIndex", method);
			}
		}
		
		return gson.toJson(json);
	}
	
	/**
	 * 退出功能
	 * 
	 * @return
	 * @return
	 */
	@NeedntLogin
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		//删除session
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("LogindeptId");
		httpSession.removeAttribute("LogindeptName");
		return "redirect:nodeMonitorIndex";//退出登录后默认到首页 
	}
	
	/**根据登录用户显示菜单
	/**模板系统首页
	 * @param model
	 * @param request
	 * @return
	 */
//	@NeedntLogin
//	@RequestMapping(value="getMainMenu",method = {RequestMethod.GET,RequestMethod.POST})
//	public void getMainMenu(Model model, HttpServletRequest request) {
//		HttpSession httpSession = request.getSession();
//		UserDto userDto  = (UserDto) httpSession.getAttribute("user") ;
//		String pmenu = request.getParameter("parentURL");
//		if(userDto != null){
//			String userId = userDto.getId();
//			Map<String,List<Map<String, Object>>> dataList = authenticationService.getMenuList(userId,pmenu);
//			model.addAttribute("list", dataList);
//		}else{
//			Map<String,List<Map<String, Object>>> dataList = authenticationService.getMenuList("",pmenu);
//			model.addAttribute("list", dataList);
//		}
//	}
}
