package cn.com.taiji.web.action.system;


import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.king.domain.db1.Dept;
import cn.com.king.dto.DeptDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.action.BaseAction;
import cn.com.taiji.web.service.sys.DeptTreeService;


/** 
* @ClassName: DeptTreeController 
* @Description: 部门树形展示新增编辑 
* @author ranxing
* @date 2017年10月31日 下午12:09:14 
*  
*/
@Controller
public class DeptTreeController  extends BaseAction {
	
	public static final String ReqMapping = "sys/DeptTreeController!" ;
	
	@Inject
	DeptTreeService deptTreeService;
	
	/** 
	* @Title: getDeptList 
	* @Description: TODO (部门列表) 
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
			pageUtil =  deptTreeService.getPageAll( init(models), pageUtil);
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
	public String toAdd(Model model, Dept cf){
		cf.setDept_fullname("新增页面");
		cf.setDeptState("0");//禁用
		cf.setParentId("centerdb");
		model.addAttribute("dto", cf);
		return "portals/page/system/depttree/edit";
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
		model.addAttribute("dto", deptTreeService.findOne(id));
		return "portals/page/system/depttree/edit";
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
	public Object  newDetail(Model model, PageUtil pageUtil,@ModelAttribute(value="dto") DeptDto dto, HttpServletRequest request){
	    	try {
		    UserDto userDto =	(UserDto) request.getSession().getAttribute("user");  
			String str = deptTreeService.saveDept(dto, userDto);
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
			str = deptTreeService.remove(id, userDto);
			pageUtil.setMessage(str);
		} catch (Exception e) {
			e.printStackTrace();
			pageUtil.setMessage("操作失败");
		}
		
		return pageUtil;
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
			return "portals/page/system/depttree/depttreelist_haveparentbox";
	}
	
	
	
	
	/** 
	* @Title: getDeptPublishList 
	* @Description: TODO(部门treelist) 
	* @param @param model
	* @param @param models    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping(value = ReqMapping+"getDeptTreeList", method = {RequestMethod.POST })
	public void getDeptPublishList(Model model,
			@RequestParam(value = "models", required = false) String models) {
		
			Map<String, Object> map  = deptTreeService.getDeptZtreeList(init(models));
			// 机构表格数据
			model.addAttribute("list", map.get("DtoList"));
	}

}
