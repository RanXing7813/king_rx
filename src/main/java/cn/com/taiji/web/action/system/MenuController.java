package cn.com.taiji.web.action.system;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.MenuService;
@Controller
public class MenuController extends BaseAction {
	
	@Inject 
	MenuService menuService ;

//	/**
//	 * 进入首页
//	 * @param model
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value=ReqMapping+"listGetMonitor4",method = {RequestMethod.GET,RequestMethod.POST})
//	public Object portalsIndex4(Model model,HttpServletResponse resp, PageUtil page, Monitor cf, @RequestParam( value = "models" , required = false) String models) {
//		Map<String,Object> searchParameters = init(models);
//		page.setSechMap(searchParameters);
//		page = monitorService.getPageList(page,10);
//		model.addAttribute("pageUtil", page);
//		return  page;
//	}
	
	
	@ResponseBody
	@RequestMapping(value="getMainMenu",method = {RequestMethod.POST})
	public Object getMainMenu(Model model, 
			HttpServletResponse resp, 
			HttpServletRequest req, 
			PageUtil page, 
			@RequestParam( value = "models" , required = true) String models) {
		
			page = menuService.getMainMenu(page, initHaveUserDto(models, req));
		
		return  page;
	}
	
	
	@ResponseBody
	@RequestMapping(value="getDefaultMenu",method = {RequestMethod.POST})
	public Object getDefaultMenu(Model model, 
			HttpServletResponse resp, 
			HttpServletRequest req, 
			PageUtil page, 
			@RequestParam( value = "models" , required = true) String models) {
		
			page = menuService.getDefaultMenu(page);
			
		return  page;
	}
	
//	public static void main(String[] args) {
//		
//		HashMap map = new HashMap();
//		
//		long startTime=System.currentTimeMillis();   //获取开始时间
//		for (int i = 0; i < 2000000; i++) {
//			map.put("fasdfasdfa"+i, i);
//		}
//		long endTime=System.currentTimeMillis(); //获取结束时间
//		System.out.println("创建map程序运行时间： "+(endTime-startTime)+"ms");
//		
//		startTime=System.currentTimeMillis();   //获取开始时间
//		System.out.println(map.get("fasdfasdfa1987654"));
//		endTime=System.currentTimeMillis(); //获取结束时间
//		System.out.println("获取map程序运行时间： "+(endTime-startTime)+"ms");
//
//	}
	
	/**
	 * 系统管理 点击左侧菜单
	 * @author ranxing
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getBackPage", method = { RequestMethod.POST, RequestMethod.GET })
	public String getBackPage(
			@RequestParam(value = "pId", required = false) String pId,
			@RequestParam(value = "id", required = false) String id, 
			HttpServletRequest request) {
			String backPage = "" ;
			String groupname = "xtgl";//系统管理
	    	try {
				backPage=menuService.getBackPage(pId, id, groupname);
				if(backPage.isEmpty())  return "redirect:systemIndex";//运维服务台首页
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	 return backPage;
	}
	
	
	
}
