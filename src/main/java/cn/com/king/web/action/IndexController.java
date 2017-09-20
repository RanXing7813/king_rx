package cn.com.king.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.king.domain.db1.Monitor;
import cn.com.king.dto.InspectionConfigDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.web.service.MonitorService;

@Controller
public class IndexController extends BaseAction{
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	public static final String ReqMapping = "login/IndexController!" ;

	@Inject 
	MonitorService monitorService;
	
	/**
	 * 首页
	 * @param model
	 * @return
	 */
	@RequestMapping({"",ReqMapping+"to_index"})
	public String homePage(Model model,@RequestParam(value = "models", required = false) String models ,HttpServletRequest request) {
		System.out.println("login/IndexController!to_index ==>> return page/index-----首页");
		return "page/index";
	}
	
	/**
	 * monitor页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value=ReqMapping+"to_list",method = {RequestMethod.POST})
	public String portalsIndex2(Model model,  HttpServletResponse resp) {
		model.addAttribute("menuinit","yw_opsIndex");
		return "page/monitorlist";
	}
	
	
	/**
	 * 获取Monitor结果集
	 * @param out		输出结果集
	 * @param page		easyui实体类
	 * @param resp	
	 * @param models	前台data参数集
	 * @param rows		每页多少条
	 */
	@ResponseBody
	@RequestMapping(value=ReqMapping+"getList",method = {RequestMethod.POST})
	public void getList(Model model, PrintWriter out, PageUtil page, HttpServletResponse response, 
			@RequestParam( value = "models" , required = false) String models ,
			@RequestParam( value = "rows" , required = false) int rows) {
		
		try {
			out = response.getWriter();
			Map<String,Object> searchParameters = init(models);
			page.setSechMap(searchParameters);
			monitorService.getPageList(page,rows);
			out .print(JSON.toJSONString(page));
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	/**
	 * 获取Monitor结果集
	 * @param out		输出结果集
	 * @param page		easyui实体类
	 * @param resp	
	 * @param models	前台data参数集
	 * @param rows		每页多少条
	 */
	@ResponseBody
	@RequestMapping(value=ReqMapping+"getList2",method = {RequestMethod.POST})
	public Object getList2(Model model, PageUtil page,
			@RequestParam( value = "models" , required = false) String models ,
			@RequestParam( value = "rows" , required = false) int rows) {
		Map<String,Object> searchParameters = init(models);

		page.setSechMap(init(models));
		monitorService.getPageList(page , rows);
		
		return  page;
	}
	
	
	/**
	 * 删除结果集
	 * @param out		输出改变数据条数
	 * @param resp	
	 * @param rows		每页多少条
	 */
	@ResponseBody
	@RequestMapping(value=ReqMapping+"remove",method = {RequestMethod.POST})
	public void remove(Model model, PrintWriter out, HttpServletResponse response, HttpSession session, 
			@RequestParam( value = "ids" , required = true) String ids ) {
			UserDto userDto = (UserDto) session.getAttribute("user");
		try {
			out = response.getWriter();
			String [] id = ids.split(",");
			int data = monitorService.remove(id,userDto);
			out .print(data);
			//out .print("{\"data\":\""+data+"\"}");
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
	
	/**
	 * 删除结果集
	 * @param out		输出改变数据条数
	 * @param resp	
	 * @param rows		每页多少条
	 */
	@ResponseBody
	@RequestMapping(value=ReqMapping+"newData",method = {RequestMethod.POST})
	public void newData(Model model, PrintWriter out, HttpServletResponse response, HttpSession session, 
			@ModelAttribute InspectionConfigDto dto, @RequestParam( value = "models" , required = false) String models ) {
			UserDto userDto = (UserDto) session.getAttribute("user");
			
//			JSONObject jsonObject =JSONObject.parseObject(models);
//			InspectionConfigDto dto=(InspectionConfigDto)JSONObject.toJavaObject(jsonObject, InspectionConfigDto.class);
			dto=(InspectionConfigDto)JSONObject.toJavaObject(JSONObject.parseObject(models), InspectionConfigDto.class);
			
		try {
			out = response.getWriter();
			 monitorService.save(dto,userDto);
			out .print("");
			//out .print("{\"data\":\""+data+"\"}");
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
//	@Autowired
//	  ObjectMapper objectMapper;
//
//	/** 
//	* @Title: init 
//	* @Description: TODO 初始化方法前台json 转  map
//	* @param @param models
//	* @param @return    设定文件 
//	* @return Map<String,Object>    返回类型 
//	* @throws 
//	*/
//	@SuppressWarnings("rawtypes")
//	public  Map<String, Object>  init (String models){
//		Map<String, Object> searchParameters = new HashMap<String, Object>();
//		if (models != null && models.length() > 0) {
//			try {//json转map
//				searchParameters = objectMapper.readValue(models,
//						new TypeReference<Map>() {
//						});
//			} catch (JsonParseException e) {
//				log.error("JsonParseException{}:", e.getMessage());
//			} catch (JsonMappingException e) {
//				log.error("JsonMappingException{}:", e.getMessage());
//			} catch (IOException e) {
//				log.error("IOException{}:", e.getMessage());
//			}
//		}
//		
//		return searchParameters;
//	}
//	
//	
	
}
