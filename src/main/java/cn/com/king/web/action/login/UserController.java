package cn.com.king.web.action.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.king.web.action.BaseAction;

@Controller
public class UserController extends BaseAction{
	private static final String ReqMapping = "user/UserController!" ;

	
	/**
	 * 新增页
	 * @param model
	 * @return
	 */
	@RequestMapping(ReqMapping+"user_add")
	public String userAdd(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
		HttpSession  ss = request.getSession();
		ss.getAttribute("name");
		System.out.println("request-----");
		model.addAttribute("NAME","11111");
		return "index";
	}
	
	
	/**
	 * 新增用户
	 * @param model
	 * @return
	 */
	@RequestMapping(ReqMapping+"addUser")
	public String addUser(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
		HttpSession  ss = request.getSession();
		ss.getAttribute("name");
		System.out.println("request-----");
		model.addAttribute("NAME","11111");
		
		return "index";
	}
	
	/**
	 * 编辑用户
	 * @param model
	 * @return
	 */
	@RequestMapping(ReqMapping+"user_edit")
	public String userAddEdit(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
		HttpSession  ss = request.getSession();
		ss.getAttribute("name");
		System.out.println("request-----首页");
		model.addAttribute("NAME","11111");
		
		return "index";
	}
	
	/**
	 * 编辑用户
	 * @param model
	 * @return
	 */
	@RequestMapping(ReqMapping+"editUser")
	public String editUser(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
		HttpSession  ss = request.getSession();
		ss.getAttribute("name");
		System.out.println("request-----首页");
		model.addAttribute("NAME","11111");
		
		return "index";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(ReqMapping+"get_list")
	public String getList(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
		HttpSession  ss = request.getSession();
		ss.getAttribute("name");
		System.out.println("request-----首页");
		model.addAttribute("NAME","11111");
		return "index";
	}
	
	
	
	
}
