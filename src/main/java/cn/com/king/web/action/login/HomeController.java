package cn.com.king.web.action.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cn.com.king.annotation.NeedntLogin;
import cn.com.king.dto.UserDto;
import cn.com.king.web.service.log.LogService;
import cn.com.king.web.service.login.AuthenticationService;
import cn.com.king.web.service.login.RoleService;
import cn.com.king.web.service.login.UserInfoService;

//import cn.com.taiji.comm.tools.RTools;
//import cn.com.taiji.dto.UserDto;
//import cn.com.taiji.service.AuthenticationService;
//import cn.com.taiji.service.RoleService;
//import cn.com.taiji.service.UserInfoService;
//import cn.com.taiji.service.log.LogBean;
//import cn.com.taiji.service.log.LogService;
//import cn.com.taiji.thymeleaf.web.NeedntLogin;
//import cn.com.taiji.util.VerifyCodeUtils;
//import cn.com.taiji.web.upload.LoadPropertiesDataUtils;
//import cn.com.taiji.web.upload.OAuthUtil;

@Controller
public class HomeController {
	
	public static final String ReqMapping = "/login/HomeController!" ;

	
//	@Inject
//	LogService logService;
	@Inject
	UserInfoService userInfoService;
	@Inject
	RoleService roleService;
	@Inject
	AuthenticationService authenticationService;
	/**
	 * 
	 * @Description: 登录页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author chixue
	 * @date 2016年9月23日
	 */
	@RequestMapping(value=ReqMapping+"login",method = RequestMethod.GET)
	public String login1(Model model,HttpServletRequest request) {
		return "login";
	}
	
	/**
	 * 
	 * @Description: 登录页面
	 * @param model
	 * @return String  
	 * @throws
	 * @author chixue
	 * @date 2016年9月23日
	 */
	@RequestMapping(value=ReqMapping+"login_new",method = RequestMethod.GET)
	public String login_new(Model model,HttpServletRequest request) {
		String method = (String) request.getParameter("method");
		model.addAttribute("method",method);
		return "index";
	}
	
	
//	@ResponseBody
	@NeedntLogin
	@RequestMapping(value=ReqMapping+"loginNew",method = { RequestMethod.POST})
	public String loginNew(Model model,HttpServletRequest request) {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		json.addProperty("errorMessage", "");
		//记录开始日志
	//	LogBean begin = logService.begin(request.getParameterMap());
		String code = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		String vcode= (String) request.getParameter("vcode");
		HttpSession httpSession = request.getSession();
//		if(!vcode.equals("xxzx")&&(vcode==null||vcode.equals("")||!vcode.toLowerCase().equals(httpSession.getAttribute("verifycode")))){
//			httpSession.setAttribute("verifycode", null);
//			//记录结束日志
//		//	logService.endFail(begin, "验证码输入错误", null, null,Thread.currentThread().getStackTrace()[1]);
//			json.addProperty("errorMessage", "验证码输入错误");
//		}
		UserDto userDto = authenticationService.login(code, password);
		userDto.setLoginName(code);
		if(userDto.getLoginName()==null){
			httpSession.setAttribute("verifycode", null);
			//记录结束日志
	//		logService.endFail(begin, "用户不存在或者密码错误", null, null,Thread.currentThread().getStackTrace()[1]);
			json.addProperty("errorMessage", "用户不存在或者密码错误");
		}
		//登录完成后将验证码进行清空
		httpSession.setAttribute("verifycode", null);
		httpSession.setAttribute("user", userDto);
		//httpSession.setMaxInactiveInterval(5);
		httpSession.setAttribute("LogindeptId",userDto.getDeptId());
		httpSession.setAttribute("LogindeptName",userDto.getUserName());
		//获得菜单列表,并判断是否存在管理员的角色
		boolean sysFlag=authenticationService.getFlagSys(userDto.getId());
		if(sysFlag){
			String method = request.getParameter("method");
			if(method==null||method.isEmpty()){
				json.addProperty("changeIndex", "/ywControlIndex");//ywControlIndex   ywSysConfigIndex
			}else{
				json.addProperty("changeIndex", method);
			}
		}
		//记录结束日志
	//	logService.endSuccess(begin, model.asMap(), userDto.getId(), userDto.getDeptId(),Thread.currentThread().getStackTrace()[1]);
		//return gson.toJson(json);
		return "page/index";
	}
	
	/**
	 * 重定向到跳转到index页面方法
	 */
	@NeedntLogin
	@RequestMapping(value=ReqMapping+"loginToIndex",method = {RequestMethod.GET,RequestMethod.POST})
	public String toIndex1(Model model,HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		UserDto userDto = (UserDto)httpSession.getAttribute("user");
		List menulist=authenticationService.getusermenu(userDto.getId());
		model.addAttribute("user",userDto);
		model.addAttribute("menulist",menulist);
		if(menulist.size()>0){
			model.addAttribute("menuinit",((Map)menulist.get(0)).get("request_url").toString());
		}
		return "index";
	}
	
	
	/**
	 * 退出功能
	 * 
	 * @return
	 * @return
	 */
//	@NeedntLogin
//	@RequestMapping(value="/logout",method = RequestMethod.GET)
//	public String logout(Model model, HttpServletRequest request) {
//		HttpSession httpSession = request.getSession();
//		//删除session
//		httpSession.removeAttribute("user");
//		httpSession.removeAttribute("LogindeptId");
//		httpSession.removeAttribute("LogindeptName");
//		return "redirect:ywPortalsIndex";//退出登录后默认到首页 
//	}
//	
//	
//	/**
//	 * 
//	 * @Description: 生成验证码
//	 * @param model
//	 * @return String  
//	 * @throws
//	 * @author chixue
//	 * @date 2016年9月23日
//	 */
//	@NeedntLogin
//	@RequestMapping(value="/verifycode",method = RequestMethod.GET)
//	public void code(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setHeader("Pragma", "No-cache");  
//        response.setHeader("Cache-Control", "no-cache");  
//        response.setDateHeader("Expires", 0);  
//        response.setContentType("image/jpeg");  
//        //生成随机字串  
//        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);  
//        //存入会话session  
//        HttpSession session = request.getSession(true);
//        session.setAttribute("verifycode", verifyCode.toLowerCase());  
//        //生成图片  
//        int w = 200, h = 80;  
//        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
//	}
//	
//	/**
//	 * 主页
//	 * 
//	 * @return
//	 * @return
//	 */
//	@NeedntLogin
//	@RequestMapping(value="/login",method = RequestMethod.POST)
//	public String passwd(Model model, HttpServletRequest request,RedirectAttributes attr) {
//		//记录开始日志
//		LogBean begin = logService.begin(request.getParameterMap());
//		
//		String code = (String) request.getParameter("username");
//		String password = (String) request.getParameter("password");
//		String vcode= (String) request.getParameter("vcode");
//		HttpSession httpSession = request.getSession();
//		if(!vcode.equals("xxzx")&&(vcode==null||vcode.equals("")||!vcode.toLowerCase().equals(httpSession.getAttribute("verifycode")))){
//			httpSession.setAttribute("verifycode", null);
//			//记录结束日志
//			logService.endFail(begin, "验证码输入错误", null, null);
//			return "errorcode";
//		}
//		UserDto userDto = authenticationService.login(code, password);
//		if(userDto.getLoginName()==null){
//			httpSession.setAttribute("verifycode", null);
//			//记录结束日志
//			logService.endFail(begin, "用户不存在或者密码错误", null, null);
//			return "error";
//		}
//		//登录完成后将验证码进行清空
//		httpSession.setAttribute("verifycode", null);
//		List menulist=authenticationService.getusermenu(userDto.getId());
//		httpSession.setAttribute("user", userDto);
//		//httpSession.setMaxInactiveInterval(10);  //缓存时间   优先级比session outtime 高
//		httpSession.setAttribute("menulist", menulist);
//		model.addAttribute("user",userDto);
//		model.addAttribute("menulist",menulist);
//		model.addAttribute("contextpath",request.getContextPath());
//		if(menulist.size()>0){
//			model.addAttribute("menuinit",((Map)menulist.get(0)).get("request_url").toString());
//		}
//		//记录结束日志
//		logService.endSuccess(begin, model.asMap(), userDto.getId(),userDto.getDeptId(),Thread.currentThread().getStackTrace()[1]);
//		if(userDto.getLoginName().equals(code)){
//			HttpSession session=request.getSession();
//			if(session.getAttribute("pathlogin")!=null){
//				String pathurl=(String) session.getAttribute("pathlogin");
//				session.setAttribute("pathlogin", null);
//				String pathlogin=pathurl.substring(pathurl.indexOf("/",8));
//				if(pathlogin.equals("/login")){
//					return "index3";
//				}
//				return "redirect:"+pathlogin;
//			}else{
//			return "index3";
//			}
//		}else{
//			HttpSession session=request.getSession();
//			session.setAttribute("LogindeptId",userDto.getDeptId());
//			session.setAttribute("LogindeptName",userDto.getUserName());
//			if(session.getAttribute("pathchange")!=null){
//				String pathurl=(String) session.getAttribute("pathchange");
//				String pathurlchange=pathurl.substring(pathurl.indexOf("/",8));
//				if(pathurlchange.equals("/login")){
//					return "index";
//				}
//				return "redirect:"+pathurlchange;
//			}else{
//				return "index";
//			}
//		}
//	}
//
//	
//	
//
//	/**
//	 * 首页
//	 * 
//	 * @return
//	 */
//	@NeedntLogin
//	@RequestMapping(value="/toIndex",method = RequestMethod.GET)
//	public String toIndex(Model model, HttpServletRequest request) {
//		HttpSession httpSession = request.getSession();
//		//删除session
//		if(httpSession.getAttribute("user")!=null){
//			UserDto userDto  = (UserDto) httpSession.getAttribute("user") ;
//			List menulist = (List)httpSession.getAttribute("menulist");
//			model.addAttribute("user",userDto);
//			model.addAttribute("menulist",menulist);
//			model.addAttribute("contextpath",request.getContextPath());
//			if(menulist.size()>0){
//				model.addAttribute("menuinit",((Map)menulist.get(0)).get("request_url").toString());
//			}
//			return "index3";
//		}else{
//			return "redirect:/login";
//		}
//	}
//	
//	
//	
//	/**
//	 * 退出功能
//	 * 
//	 * @return
//	 */
//	@NeedntLogin
//	@RequestMapping(value="/quit",method = RequestMethod.GET)
//	public String quit(Model model, HttpServletRequest request) {
//		HttpSession httpSession = request.getSession();
//		//删除session
//		httpSession.removeAttribute("user");
//		return "login";
//	}
//	
//	/**根据登录用户显示菜单
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/getMainMenu",method = {RequestMethod.GET,RequestMethod.POST})
//	public void getMainMenu(Model model, HttpServletRequest request) {
//		HttpSession httpSession = request.getSession();
//		UserDto userDto  = (UserDto) httpSession.getAttribute("user") ;
//		if(userDto != null){
//			String userId = userDto.getId();
//			String pmenu = request.getParameter("parentURL");
//			Map<String,List<Map<String, Object>>> dataList = authenticationService.getMenuList(userId,pmenu);
//			model.addAttribute("list", dataList);
//		}
//	}
//	
//	// 授权码(从OAuth服务端获取)
//			String code = "";
//
//
//			/**
//			 * 
//			 * @Description: 证书登录
//			 * @return String
//			 * @author fangzy
//			 * @date 2017年7月19日
//			 */
//			@NeedntLogin
//			@RequestMapping(value = "/authLoginSingle", method = { RequestMethod.GET, RequestMethod.POST })
//			public String authLoginSingle(Model model, HttpServletRequest request,HttpServletResponse response,RedirectAttributes attr) {
//				try {
//					response.setContentType("text/html;charset=utf-8");
//					response.setCharacterEncoding("UTF-8");
//					request.setCharacterEncoding("utf-8");
//					// 第一步：得到OAuth返回的授权码
//					code = request.getParameter("code");
//					//获取单点登录的tokenId
//					String gdbsTokenId=(String)request.getParameter("gdbsTokenId");
//					UserDto ud=null;
//					//System.out.println("请求获取code为：" + code);
//					if(null != gdbsTokenId && !"".equals(gdbsTokenId)){//判断单点登录进行重定向
//						//String dandianredirect="http://19.224.1.196:7007/am/oauth2/authorize?service=initService&response_type=code&client_id=zwtest&scope=cn+uid+mail+idCardNumber+telephoneNumber+version+userIdCode+createTime+credenceState+orgInfo+extProperties&client_secret=111111&redirect_uri="+redirectUri;
//						String dandianredirect=LoadPropertiesDataUtils.getoaValue("baseUrl")+LoadPropertiesDataUtils.getoaValue("console1")+LoadPropertiesDataUtils.getoaValue("redirectUri");
//						try{
//							return "redirect:"+dandianredirect;
//						}catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//					if(null != code && !"".equals(code)){//判断CA登录
//						// 第二步：通过授权码得到access_token
//						//String accessTokenUrlTemp = accessTokenUrl + "client_id=" + clientId + "&redirect_uri=" + URLEncoder.encode(redirectUri,"UTF-8") + "&scope=" + scope + "&client_secret=" + clientSecret + "&grant_type=" + grantType + "&code=" + code;
//						String accessTokenUrlTemp = LoadPropertiesDataUtils.getoaValue("baseUrl")+LoadPropertiesDataUtils.getoaValue("accessTokenUrl")+"client_id="+LoadPropertiesDataUtils.getoaValue("clientId")+ "&redirect_uri=" + URLEncoder.encode(LoadPropertiesDataUtils.getoaValue("redirectUri"),"UTF-8") + "&scope=" + LoadPropertiesDataUtils.getoaValue("scope") + "&client_secret=" + LoadPropertiesDataUtils.getoaValue("clientSecret") + "&grant_type=" + LoadPropertiesDataUtils.getoaValue("grantType") + "&code=" + code;
//						//System.out.println("请求获取access_token完整url地址为1：" + accessTokenUrlTemp);
//						String accessTokenJSON= OAuthUtil.getAccessToken(accessTokenUrlTemp);
//						//System.out.println("请求access_token后返回的数据为2：" + accessTokenJSON);
//						//{"expires_in":179,"token_type":"Bearer","access_token":"e6e031e6-d539-42d2-94cd-0542a6e9a501"}
//						Map jsonObj = (Map)JSONObject.parse(accessTokenJSON);
//					    // 通过返回的对象判断授权码是否已经过期
//					    String errorCode = (String) jsonObj.get("error_code");
//					    String errorMsg = (String) jsonObj.get("error_msg");
//					    if((errorCode != null & !"".equals(errorCode)) || (errorMsg != null && !"".equals(errorMsg))){
//					    	//return "redirect:"+adminPath+"/login";
//					    	System.out.println("统一认证失败:"+errorMsg+"====="+errorCode);
//					    }
//					    String accessToken = (String) jsonObj.get("access_token");
//					    request.setAttribute("gdbsAccessToken", accessToken);
//				        //System.out.println("accesstoken为：" + accessToken);
//				        // 第三步：根据access_token获取用户信息
//				        String tokenInfoUrlTemp = LoadPropertiesDataUtils.getoaValue("baseUrl")+LoadPropertiesDataUtils.getoaValue("tokenInfoUrl") + accessToken;
//				        //System.out.println("根据access_token请求用户信息的完整url地址为4：" + tokenInfoUrlTemp);
//				        String info = OAuthUtil.getUserInfo(tokenInfoUrlTemp);
//				        Map userInfoJson = (Map)JSONObject.parse(info);
//				        // 通过返回的对象判断access_token是否已经过期
//					    String userInfoErrorCode = (String) userInfoJson.get("error_code");
//					    String userInfoErrorMsg = (String) userInfoJson.get("error_msg");
//					    if((userInfoErrorCode != null && !"".equals(userInfoErrorCode)) || (userInfoErrorMsg != null && !"".equals(userInfoErrorMsg))){
//					    	//return "redirect:"+adminPath+"/login";
//					    }
//					    //request.setAttribute("userInfo", obj);
//					    ud=setUserDtoMap(userInfoJson);
//					}
//					ud=userInfoService.findOne("111");
//					if(ud!=null){//当获取的数据不为空时进行保存
//						//判断用户是否存在
//			        	UserDto userDto = authenticationService.login(ud.getLoginName(), "Qwer1234");
//			        	ud.setFlag(1);
//		        		ud.setCreatorId("tyrz");
//		        		ud.setPassword("Qwer1234");
//		        		ud.setCreateTime(RTools.dateTimeUtil.getNowTime());
//		        		ud.setDeptId("sgongsj");
//		        		ud.setDeptName("省工商局");
//		        		ud.setRemark("默认Dept信息为省工商局");
//			        	if(userDto.getLoginName() == null){
//			        		userDto.setDeptId(ud.getDeptId());
//			        		userDto.setUserName(ud.getUserName());
//			        		//保存用户
//			        		userInfoService.save(ud);
//			        		//赋予普通角色
//		        		roleService.saveAuthRoleUser("d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3",ud.getId());
//			        	}else{
//			        		//更新用户信息
//			        		userInfoService.updateUser(ud);
//			        	}
//			        	System.out.println("登录认证结束");
//			        	// 登录完成后将验证码进行清空
//			    		attr.addFlashAttribute("LogindeptId",userDto.getDeptId());
//			    		attr.addFlashAttribute("LogindeptName",userDto.getUserName());
//			    		attr.addFlashAttribute("user", ud);
//					}
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//				//跳转到登录的方法进行验证返回的用户名和密码
//				//return "/loginNew";
//				return "redirect:/portalsIndex";
//			}
//			
//			@NeedntLogin
//			@RequestMapping(value = "/getParameter", method = { RequestMethod.GET, RequestMethod.POST })
//			@ResponseBody
//			public String getParameter(Model model, HttpServletRequest request,HttpServletResponse response) {
//				Gson gson = new Gson();
//				JsonObject json = new JsonObject();
//				try {
//					json.addProperty("address", LoadPropertiesDataUtils.getoaValue("baseUrl")+LoadPropertiesDataUtils.getoaValue("console1")+URLEncoder.encode(LoadPropertiesDataUtils.getoaValue("redirectUri"),"UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return gson.toJson(json);
//			}
//			
//			private UserDto setUserDtoMap(Map userInfoJson){
//				//System.out.println("******用户信息打印开始******");
//				UserDto ud = new UserDto();
//				if(userInfoJson.containsKey("userIdCode")) {
//		            String useridcode = (String) userInfoJson.get("userIdCode");
//		            ud.setId(useridcode);
//		            //request.setAttribute("useridcode", useridcode);
//		            //System.out.println("省厅用户唯一标识："+ useridcode);
//		        }
//			    if(userInfoJson.containsKey("uid")) {
//		            String uid = (String) userInfoJson.get("uid");
//		            ud.setLoginName(uid);
//		            //request.setAttribute("uid", uid);
//		            //System.out.println("登录名："+ uid);
//		        }
//		    	if(userInfoJson.containsKey("cn")) {
//		            String cn = (String) userInfoJson.get("cn");
//		            ud.setUserName(cn);
//		            //request.setAttribute("cn", cn);
//		            //System.out.println("用户名称："+ cn);
//		        }
//		    	/*if(userInfoJson.containsKey("idCardNumber")) {
//		            String idcardnumber = (String) userInfoJson.get("idCardNumber");
//		            request.setAttribute("idcardnumber", idcardnumber);
//		            System.out.println("证件号码："+ idcardnumber);
//		        }*/
//		    	if(userInfoJson.containsKey("telephoneNumber")) {
//		            String telephonenumber = (String) userInfoJson.get("telephoneNumber");
//		            ud.setPhoneNum(telephonenumber);
//		            //request.setAttribute("telephonenumber", telephonenumber);
//		            //System.out.println("电话号码："+ telephonenumber);
//		        }
//		    	if(userInfoJson.containsKey("mail")) {
//		            String mail = (String) userInfoJson.get("mail");
//		            ud.setEmail(mail);
//		            //request.setAttribute("mail", mail);
//		            //System.out.println("邮箱："+ mail);
//		        }
//		    	/*if(userInfoJson.containsKey("tokenId")) {
//		            String tokenid = (String) userInfoJson.get("tokenId");
//		            request.setAttribute("gdbsTokenId", tokenid);
//		            System.out.println("tokenid："+ tokenid);
//		        }
//		    	if(userInfoJson.containsKey("pareobj")) {
//		            Map paerobjson = (Map)JSONObject.parse((String)userInfoJson.get("pareobj"));
//		            System.out.println("pareobj："+ paerobjson);
//		        	if(paerobjson.containsKey("usertype")) {
//		                String usertype = (String) paerobjson.get("usertype");
//		                System.out.println("用户类型："+ usertype);
//		            }
//			    }
//		    	if(userInfoJson.containsKey("extproperties")) {
//		    		 Map paerobjson = (Map)JSONObject.parse((String)userInfoJson.get("extproperties"));
//		            if(paerobjson!=null&&paerobjson.size()>0) {
//		                for(int i=0;i<paerobjson.size();i++) {
//		                	Map arrayitem = paerobjson.get(i);
//		                    String [] arrayitems = arrayitem.split("=");
//		                    if(arrayitems!=null&&arrayitems.length>0) {
//		                    	 if(arrayitems[0].trim().equals("area")) {
//		                           System.out.println("所属地区："+arrayitems[1].trim());
//		                           request.setAttribute("area", arrayitems[1].trim());
//		                    	 }
//		                    	 if(arrayitems[0].trim().equals("address")) {
//		                           System.out.println("地址："+arrayitems[1].trim());
//		                           request.setAttribute("address", arrayitems[1].trim());
//		                    	 }
//		                    	 if(arrayitems[0].trim().equals("origin")) {
//		                           System.out.println("账号来源："+arrayitems[1].trim());
//		                           request.setAttribute("origin", arrayitems[1].trim());
//		                    	 }
//		                    	 if(arrayitems[0].trim().equals("uversion")) {
//		                           System.out.println("用户信息版本："+arrayitems[1].trim());
//		                           request.setAttribute("uversion", arrayitems[1].trim());
//		                    	 }
//		                    }
//		                }
//		            }
//		    	}*/
//		    	if(userInfoJson.containsKey("orgInfo")) {
//		    		String orginfo=userInfoJson.get("orgInfo").toString();
//		            Map paerobjson = (Map)JSONObject.parse(orginfo.substring(1, orginfo.length()-1));
//		        	if(paerobjson.containsKey("orgFlowNo")) {
//		                String orgFlowNo = (String) paerobjson.get("orgFlowNo");
//		                ud.setOffice_id(orgFlowNo);
//		            }
//			    }
//		    	//System.out.println("******用户信息打印结束******");
//		    	return ud;
//			}
}
