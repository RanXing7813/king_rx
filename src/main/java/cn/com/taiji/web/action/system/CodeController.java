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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.king.domain.db1.Code;
import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.DeptDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.CodeService;
import cn.com.taiji.web.service.sys.MenuService;
@Controller
public class CodeController extends BaseAction {
	
	public static final String ReqMapping = "sys/CodeController!" ;
	
	@Inject 
	MenuService menuService ;
	
	@Inject
	CodeService codeService;
	
	//门户菜单树
	@RequestMapping(value=ReqMapping+"getCodeTreeList",method={RequestMethod.POST})
	public void yw_MenusZree(Model model,HttpServletRequest request ){
			model.addAttribute("list", codeService.getCodeByLevel());
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
	@RequestMapping(value = ReqMapping+"to_add", method = { RequestMethod.GET })
	public String toAdd(Model model, Code cf){
		cf.setState("Y");// 
		model.addAttribute("dto", cf);
		return "portals/page/system/code/edit";
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
	@RequestMapping(value = ReqMapping+"to_detail", method = { RequestMethod.GET })
	public String toDetail(Model model, @RequestParam(value="id", required=true) String id){
		try {
			 Code dto =     (Code) codeService.findOne(id);
			 if(dto.getParent_id()!=null){
				 Code dto2 =     (Code) codeService.findOne(dto.getParent_id());
					model.addAttribute("parentName", dto2.getCode_show_name());
			 }
			
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "portals/page/system/code/edit";
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
	public Object  newDetail(Model model, PageUtil pageUtil,@ModelAttribute(value="dto") Code dto, HttpServletRequest request){
	    	try {
			    UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
				String id = codeService.saveOne(dto, userDto);
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
		//	str = codeService.remove(id, userDto);
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
	@RequestMapping(value = ReqMapping+"to_list", method = { RequestMethod.POST })
	public String toList(Model model, Code cf,  @RequestParam(value="id", required=false)String id){
		//model.addAttribute("list", codeService.getCodeByPid(id));
		return "portals/page/system/code/list";
	}
	
	@RequestMapping(value=ReqMapping+"getCodeTreeList{id}",method={RequestMethod.POST})
	public void getCodeTreeList(@PathVariable("id") String id, Model model,HttpServletRequest request ){
			model.addAttribute("list", codeService.getCodeByPid(id));
	}
}
