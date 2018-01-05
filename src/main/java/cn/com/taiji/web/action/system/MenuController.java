package cn.com.taiji.web.action.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.DeptDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.MenuService;
@Controller
public class MenuController extends BaseAction {
	
	public static final String ReqMapping = "sys/MenuController!" ;
	
	@Inject 
	MenuService menuService ;

	//门户菜单树
	@RequestMapping(value=ReqMapping+"getDeptTreeList",method={RequestMethod.POST})
	public void yw_MenusZree(Model model,HttpServletRequest request ){
			model.addAttribute("list", menuService.getMenuAll());
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
	public String toAdd(Model model, Menuinfo cf){
		cf.setMenuName("新增页面");
		cf.setState("1");;// 
		model.addAttribute("dto", cf);
		return "portals/page/system/menu/edit";
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
	@RequestMapping(value = ReqMapping+"to_detail", method = { RequestMethod.POST })
	public String toDetail(Model model, @RequestParam(value="id", required=true) String id){
		try {
			model.addAttribute("dto", menuService.findOne(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "portals/page/system/menu/edit";
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
	public Object  newDetail(Model model, PageUtil pageUtil,@ModelAttribute(value="dto") Menuinfo dto, HttpServletRequest request){
	    	try {
			    UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
				String id = menuService.saveOne(dto, userDto);
				pageUtil.setMessage("SUCCESS");
				pageUtil.setOrderStr(id);
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
			str = menuService.remove(id, userDto);
			pageUtil.setMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageUtil.setMessage("操作失败");
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
	@RequestMapping(value = ReqMapping+"updState", method = { RequestMethod.POST })
	public Object  updStateDetail(Model model, PageUtil pageUtil, @RequestParam(value="id", required=true)String id, HttpServletRequest request ){
		
		 UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
		try {
			// menuService.updMenuInfoState(id);
			pageUtil.setMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageUtil.setMessage("操作失败");
		}
		return pageUtil;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
