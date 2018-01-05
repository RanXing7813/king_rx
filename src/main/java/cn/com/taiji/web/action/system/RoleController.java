package cn.com.taiji.web.action.system;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Printer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.RoleDto;
import cn.com.king.dto.UserDto;
import cn.com.taiji.web.service.sys.RoleService;

/**
 * 
 * 类名称：RoleController 类描述： 创建人：ranxing 创建时间：2017‎年‎3‎月‎10‎日 ‎星期五 下午 2:14:13
 * 
 * @version
 */
@Controller
public class RoleController {
	private static final Logger log = LoggerFactory .getLogger(RoleController.class);
	public static final String ReqMapping = "sys/RoleController!" ;

	@Inject
	  ObjectMapper objectMapper;
	@Inject
	  RoleService roleService;

	private String salt = RandomStringUtils.randomAscii(20);
	/**
	 * 
	 * @Description:跳转至用户管理页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-list", method = { RequestMethod.GET })
	public String roleList(Model model) {
		return "portals/page/system/role/role-list";
	}

	/**
	 * 
	 * @Description: 查询用户信息(默认查询所有用户信息)
	 * @param model
	 * @param models
	 * @return String
	 * @throws
	 * @author rangxing
	 * @date 2017年3月10日
	 */
	@RequestMapping(value = ReqMapping+"role-list", method = { RequestMethod.POST })
	public void roleList(Model model, HttpServletRequest request,
			@RequestParam(value = "models", required = false) String models) {//data参数models分页参数{"pageSize":10,"page":1}
				Map searchParameters = new HashMap();
				if (models != null && models.length() > 0) {
					try {//json转map
						searchParameters = objectMapper.readValue(models,
								new TypeReference<Map>() {
								});
					} catch (JsonParseException e) {
						log.error("JsonParseException{}:", e.getMessage());
					} catch (JsonMappingException e) {
						log.error("JsonMappingException{}:", e.getMessage());
					} catch (IOException e) {
						log.error("IOException{}:", e.getMessage());
					}
				}
				
				Map map =roleService.getPage(searchParameters, salt);//!hNv&K|aD'/PnygJhPSY
				// 用户表格数据
				model.addAttribute("list", map.get("roles"));
				// 总条数
				model.addAttribute("total", map.get("total"));
				model.addAttribute("log_uopr",searchParameters );
	}

	
	
	
	/**
	 * 
	 * @Description:跳转至用户管理编辑页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-edit", method = { RequestMethod.GET })
	public String roleEditGet(Model model,HttpServletRequest request ,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "roleId", required = false) String roleId) {
				// 用户表格数据
				RoleDto roleDto = null ;
				if(roleId!=null){// 编辑
					roleDto = roleService.findDtoById(roleId, salt);
				}else{
					 roleDto = new RoleDto();
				}
				
				if(status!=null)//新增
				roleDto.setStatus(status);
				model.addAttribute("dto", roleDto);
		return "/portals/page/system/role/role-edit";
	}
	
	
	
	/**
	 * 
	 * @Description:跳转至用户管理编辑页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-edit", method = { RequestMethod.POST })
	public String roleEditPost(Model model,
			@RequestParam(value = "roleId", required = false) String roleId) {
		RoleDto roleDto = roleService.findDtoById(roleId, salt);
		// 用户表格数据
		model.addAttribute("dto", roleDto);
		return "/portals/page/system/role/role-edit";
	}

	/**
	 * 
	 * @Description:跳转至用户管理新增页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-add", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String roleAdd(Model model) {
		RoleDto roleDto = new RoleDto();
		model.addAttribute("dto", roleDto);
		return "portals/page/system/role/role-edit";
	}

	/**
	 * 
	 * @Description: 保存新增或者编辑    跳转至用户管理页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(ReqMapping+"role-save")
	public void roleUpdate(Model model,@ModelAttribute("dto") RoleDto roleDto,HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto  userDto = (UserDto) httpSession.getAttribute("user");
			if(!roleDto.getRoleId().isEmpty()){//编辑
				roleDto.setUpdaterId(userDto.getLoginName());
			}else{//新增
				roleDto.setCreatorId(userDto.getLoginName());
			}
			roleService.save(roleDto);
			model.addAttribute("message","SUCCESS" );
	}
	
	@RequestMapping(ReqMapping+"role-savename")
	public void roleSaveName(@ModelAttribute("dto") RoleDto roleDto, HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		boolean flag  = roleService.getRoleNameIsSigle(roleDto);
		 try {
			out = response.getWriter();
			out.println(flag?1:2);
		} 
		 catch (Exception e) {
	      		e.printStackTrace();
	      //		logService.endFail(begin, Thread.currentThread().getStackTrace()[1].getMethodName()+"|问题保存", null, null);
	      	}finally {
	      		 if (out != null) {  
			            out.close();  
			        }  
	      		//记录结束日志
	      	}
	}
	
	/**
	 * 
	 * @Description: 删除用户管理信息  跳转至用户管理页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-delete", method = { RequestMethod.POST  })
	public void roleDelete(Model model,  HttpServletRequest request,@RequestParam(value = "id", required = false) String id) {
		UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
		roleService.deleteRole(id,userDto);
	}
	
//******* 角色分配菜单目录  ****** ***************** ******************************		
	
	/**
	 * 
	 * @Description  加载  弹出角色授权   菜单tree页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-auth", method = { RequestMethod.POST,RequestMethod.GET })
	public String roleAuth(Model model,
			@RequestParam(value = "id", required = false) String id ,
			@RequestParam(value = "type", required = false) String type) {
		 model.addAttribute("_roleId", id); 
		 model.addAttribute("_type", type); 
		return "/portals/page/system/role/role-auth";
	}
	
	/**
	 * 
	 * @Description:  弹出角色授权   菜单tree
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-authMenus", method = { RequestMethod.POST,RequestMethod.GET })
	public String authMenus( Model model,
			@RequestParam(value = "models", required = false) String models ,HttpSession httpSession ,HttpServletRequest request) {
			UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
			Map searchParameters = new HashMap();
			 String id = "";
			if (models != null && models.length() > 0) {
				try {//json转map
					searchParameters = objectMapper.readValue(models,
							new TypeReference<Map>() {
							});
				 id = searchParameters.get("id").toString();
				} catch (JsonParseException e) {
					log.error("JsonParseException{}:", e.getMessage());
				} catch (JsonMappingException e) {
					log.error("JsonMappingException{}:", e.getMessage());
				} catch (IOException e) {
					log.error("IOException{}:", e.getMessage());
				}
			}
			  // 目录树数据
			  //List<Menus> listMenus =  roleService.getMenus(id); 
			  List<Menus> listMenus =  roleService.getMenuForMap(searchParameters); 
			  List<Map<Object,Object>> list = new ArrayList<Map<Object, Object>>();
			  for(Menus dto:listMenus){
				  Map<Object,Object> map = new HashMap<Object, Object>();
				  map.put("id",dto.getMenuId()) ;
				  map.put("name",dto.getMenuName());
				  map.put("pId", dto.getParentId());
				  map.put("open", "0".equals(dto.getParentId())?true:false);
				  map.put("checked", "8".equals(dto.getState())?true:false);
				  list.add(map);
			  }
			  model.addAttribute("list", list); 
			  model.addAttribute("log_uopr",searchParameters );
		return "/portals/page/system/role/role-auth";
	}
	
	/**
	 * 
	 * @Description:  弹出角色授权   菜单tree   保存
	 * @param model
	 * @return String  
	 * @throws
	 * @author rangxing
	 * @date 2017年3月15日
	 */
	@RequestMapping(value = ReqMapping+"role-authMenus-save", method = { RequestMethod.POST,RequestMethod.GET })
	public void authMenusSave( Model model,
			@RequestParam(value = "models", required = false) String models ,HttpSession httpSession ,HttpServletRequest request ) {
				UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
				Map searchParameters = new HashMap();
				if (models != null && models.length() > 0) {
					try {//json转map
						searchParameters = objectMapper.readValue(models,
								new TypeReference<Map>() {
								});
					} catch (JsonParseException e) {
						log.error("JsonParseException{}:", e.getMessage());
					} catch (JsonMappingException e) {
						log.error("JsonMappingException{}:", e.getMessage());
					} catch (IOException e) {
						log.error("IOException{}:", e.getMessage());
					}
				}
//				System.out.println(searchParameters.get("id").toString());
//				System.out.println(searchParameters.get("menuIds").toString());
				// 保存目录树数据
				roleService.saveAuthMenus(searchParameters);
				model.addAttribute("log_uopr",searchParameters );
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
	@RequestMapping(value = ReqMapping+"role-user-auth", method = { RequestMethod.GET })
	public String authRole() {
		return "/portals/page/system/user/user-auth";
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
	@RequestMapping(value = ReqMapping+"role-user-authRoles", method = { RequestMethod.POST,RequestMethod.GET })
	public String authRoles( Model model,
			@RequestParam(value = "models", required = false) String models ,HttpSession httpSession ,HttpServletRequest request) {
		
			UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
			Map searchParameters = new HashMap();
			 String id = "";
			if (models != null && models.length() > 0) {
				try {//json转map
					searchParameters = objectMapper.readValue(models,
							new TypeReference<Map>() {
							});
				} catch (JsonParseException e) {
					log.error("JsonParseException{}:", e.getMessage());
				} catch (JsonMappingException e) {
					log.error("JsonMappingException{}:", e.getMessage());
				} catch (IOException e) {
					log.error("IOException{}:", e.getMessage());
				}
			}
			 id = searchParameters.get("id").toString();

			  // 目录树数据
			  List<Map<Object,Object>> list =  roleService.getRoles(id); 
			  model.addAttribute("list", list); 
			  model.addAttribute("log_uopr",searchParameters );
		return "/portals/page/system/user/user-auth";
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
	@RequestMapping(value = ReqMapping+"role-authRoles-save", method = { RequestMethod.POST,RequestMethod.GET })
	public void authRolesSave( Model model,
			@RequestParam(value = "models", required = false) String models ,HttpSession httpSession ,HttpServletRequest request ) {
				UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
				Map searchParameters = new HashMap();
				if (models != null && models.length() > 0) {
					try {//json转map
						searchParameters = objectMapper.readValue(models,
								new TypeReference<Map>() {
								});
					} catch (JsonParseException e) {
						log.error("JsonParseException{}:", e.getMessage());
					} catch (JsonMappingException e) {
						log.error("JsonMappingException{}:", e.getMessage());
					} catch (IOException e) {
						log.error("IOException{}:", e.getMessage());
					}
				}
				// 保存目录树数据
				roleService.saveAuthRoles(searchParameters);
				model.addAttribute("log_uopr",searchParameters );
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
	@RequestMapping(value = ReqMapping+"role-authRoles-details", method = { RequestMethod.GET })
	public String authRoleDetail(Model model , HttpServletRequest request) {
		RoleDto roleDto = new RoleDto ();
		roleDto.setRemark(request.getParameter("userId"));
		model.addAttribute("dto", roleDto);
		return "/portals/page/system/user/user-authDetails";
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
	@RequestMapping(value = ReqMapping+"role-authRoles-detailsss", method = { RequestMethod.POST, RequestMethod.GET })
	public String authRolesDetails( Model model,@RequestParam(value = "roleId", required = false) String roleId ,
			@RequestParam(value = "userId", required = false) String userId ,HttpSession httpSession ,HttpServletRequest request) {
			UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
			 // 目录树数据
			  List<Map<String,Object>> list =  roleService.getSingleRoles(userId); 
			  model.addAttribute("list", list); 
		return "/portals/page/system/user/user-authDetails";
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
	@RequestMapping(value = ReqMapping+"role-authMenus-detailsss", method = { RequestMethod.POST,RequestMethod.GET })
	public String authMenusDetails( Model model,@RequestParam(value = "roleId", required = false) String roleId ,
			@RequestParam(value = "userId", required = false) String userId , HttpSession httpSession ,HttpServletRequest request) {
			UserDto userDto =  (UserDto)request.getSession().getAttribute("user") ;
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
		return "/portals/page/system/user/user-authDetails";
	}
	

}