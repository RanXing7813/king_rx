package cn.com.king.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.king.domain.db1.Monitor;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.service.MonitorService;

//@RequestMapping(value="/rsm")
@Controller
public class PortalsIndexController extends BaseAction {
	
	public static final String ReqMapping = "/rsm/ss/PortalsIndexController!" ;
	//tomcat部署"index",edas部署value={"","index"}，否则访问会出现问题
	
	
	@Inject 
	MonitorService monitorService;
	
	/**默认进入
	 * @param model
	 * @return
	 */
//	@RequestMapping({"",ReqMapping+"index"})
//	public String homePage(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
//		HttpSession  ss = request.getSession();
//		ss.getAttribute("name");
//		System.out.println("request-----首页");
//		model.addAttribute("NAME","11111");
//		
//		return "index";
//	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value=ReqMapping+"index1")
	public String portalsIndex1(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index1";//page/index1
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value=ReqMapping+"getMonitor_list",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex2(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/monitorlist2";
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value=ReqMapping+"listGetMonitor",method = {RequestMethod.POST})
	public void portalsIndex3(Model model,HttpServletResponse resp, PageUtil page, Monitor cf, @RequestParam( value = "models" , required = false) String models) {
		PrintWriter out  = null;
		
		try {
			out =	resp.getWriter();
		Map<String,Object> searchParameters = init(models);
		page.setSechMap(searchParameters);
		///page = 
				monitorService.getPageList(page,10);
		model.addAttribute("pageUtils", page);
		JSONObject json = new JSONObject();
		
		out .print(json.toJSONString(page));
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			
			out.flush();
			out.close();
		}
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value=ReqMapping+"listGetMonitor4",method = {RequestMethod.GET,RequestMethod.POST})
	public Object portalsIndex4(Model model,HttpServletResponse resp, PageUtil page, Monitor cf, @RequestParam( value = "models" , required = false) String models) {
		Map<String,Object> searchParameters = init(models);
		page.setSechMap(searchParameters);
		page = monitorService.getPageList(page,10);
		model.addAttribute("pageUtil", page);
		return  page;
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index5",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex5(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index5";
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index6",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex6(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index6";
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index7",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex7(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index7";
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index8",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex8(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index8";
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index9",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex9(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index9";
	}
	
	
	/**
	 * 进入首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index10",method = {RequestMethod.GET,RequestMethod.POST})
	public String portalsIndex10(Model model) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/index10";
	}
	
	
	
	
	
	
}
