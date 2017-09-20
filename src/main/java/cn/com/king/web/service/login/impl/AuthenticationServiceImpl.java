package cn.com.king.web.service.login.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.king.domain.db1.UserInfo;
import cn.com.king.dto.UserDto;
import cn.com.king.repository.db1.UserInfoRepository;
import cn.com.king.web.action.log.StringTooles;
import cn.com.king.web.service.login.AuthenticationService;

/**
 * 
 * 类名称：AuthenticationServiceImpl   
 * 类描述：   
 * 创建人：chixue   
 * 创建时间：2016年9月19日 上午11:10:20 
 * @version
 */
@Service
public class AuthenticationServiceImpl  implements AuthenticationService {
	@Inject
	UserInfoRepository  userRepository;
	
	private static final String jdbcTemplate_INFO = "dataSource1";
	
	private JdbcTemplate jdbcTemplate ;
	
	//连接rds数据库
//	public JdbcTemplate getJdbcTemplate() {
//		return jdbcTemplate;
//	}
	@Inject
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		jdbcTemplate.setDatabaseProductName(jdbcTemplate_INFO);
		this.jdbcTemplate = jdbcTemplate;
	}

	
	@Override
	public UserDto login(String loginName, String passWord) {
		//String pwd = StringUtils.trimToEmpty("123");
		String password=DigestUtils.sha256Hex(StringTooles.parseStr(passWord));
		UserInfo user=userRepository.login(loginName, password);
		//UserInfo user=	userRepository.findByLoginName(loginName);
    
		UserDto dto = new UserDto();
		if(user!=null){
		BeanUtils.copyProperties(user, dto);}
		return dto;
	}
	
	/**
	 * 获得用户角色菜单的权限
	 * 2016年11月10日17:52:53
	 * fangzy
	 * @return
	 */
	@Override
	public boolean getFlagSys(String userid){
		List menulist=new ArrayList();
		//创建数据库连接
		jdbcTemplate.setDatabaseProductName(jdbcTemplate_INFO);
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		//通过用户id查询权限id
		String getrolelistsql="select role_id from role_user where user_id='"+userid+"'";
		List roleidlist=jdbcTemplate.queryForList(getrolelistsql);
		if(roleidlist.size()>0){
			String getflagsql="select sys_flag from role where role_id in (''";
			for(int i=0;i<roleidlist.size();i++){
				Map role=(Map)roleidlist.get(i);
				getflagsql+=",'"+role.get("role_id").toString()+"'";
			}
			getflagsql+=")";
			List flaglist=jdbcTemplate.queryForList(getflagsql);
			for(int i=0;i<flaglist.size();i++){
				Map flag=(Map)flaglist.get(i);
				if("1".equals(flag.get("sys_flag").toString())){
					return true;
				}
			}
		}
		//通过权限id查询菜单id
		//通过菜单id获得菜单信息数据返回
		return false;
	}
	
	/**
	 * 获得用户角色菜单的权限
	 * 2016年11月10日17:52:53
	 * fangzy
	 * @return
	 */
	@Override
	public List getusermenu(String userid){
		List menulist=new ArrayList();
		//创建数据库连接
		//通过用户id查询权限id
		String getrolelistsql="select role_id from role_user where user_id='"+userid+"'";
		List roleidlist=jdbcTemplate.queryForList(getrolelistsql);
		if(roleidlist.size()!=0||roleidlist!=null){
			menulist=searchmenu(jdbcTemplate,roleidlist);
		}else{
			Map roleidmap=new HashMap();
			roleidmap.put("role_id", "d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3");//如果没有角色，那么给一个普通用户角色
			roleidlist.add(roleidmap);
			menulist=searchmenu(jdbcTemplate,roleidlist);
		}
		//通过权限id查询菜单id
		//通过菜单id获得菜单信息数据返回
		return menulist;
	}
	
	public List searchmenu(JdbcTemplate jdbcTemplate,List roleidlist){
		List menulist=new ArrayList();
		String getmenuidlistsql="select menu_id from role_menu where role_id in (''";
		for(int i=0;i<roleidlist.size();i++){
			Map roleidmap=(Map)roleidlist.get(i);
			getmenuidlistsql=getmenuidlistsql+",'"+roleidmap.get("role_id")+"'";
		}
		getmenuidlistsql=getmenuidlistsql+") group by menu_id";
		List menuidlist=jdbcTemplate.queryForList(getmenuidlistsql);
		if(menuidlist.size()!=0||menuidlist!=null){
			String getmenulistsql="select menu_name,request_url,menu_icon from menuinfo where state='1' and menu_id in (''";
			for(int i=0;i<menuidlist.size();i++){
				Map menuidmap=(Map)menuidlist.get(i);
				getmenulistsql=getmenulistsql+",'"+menuidmap.get("menu_id")+"'";
			}
			getmenulistsql=getmenulistsql+") order by order_id asc";
			menulist=jdbcTemplate.queryForList(getmenulistsql);
		}
		return menulist;
	}

	
	@Override
	public Map<String,List<Map<String, Object>>> getMenuList(String userId, String pmenuId) {
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String,List<Map<String, Object>>>();
		if(pmenuId != null && "mainnav".equals(pmenuId)){
			pmenuId = "2";
			returnMap.put("mainmenu", menuList (userId, pmenuId));
		}else{
			//根据第一级url获取子节点
			String sql = "select menu_id,request_url from menuinfo where request_url = ? and  status = '2' ";
			List<Map<String, Object>> dataList =  jdbcTemplate.queryForList(sql,new Object [] {pmenuId});
			if(dataList != null && dataList.size() > 0){
				for(int i=0;i<dataList.size();i++){
					if(dataList.get(i).get("menu_id")!=null && !dataList.get(i).get("menu_id").toString().isEmpty())
						pmenuId = dataList.get(i).get("menu_id").toString();
						List<Map<String, Object>> secondMenu = menuList (userId, pmenuId);
						
						if(secondMenu != null && secondMenu.size() >0){
							for(int j=0;j<secondMenu.size();j++){
								if(secondMenu.get(j).get("menu_id")!=null && !secondMenu.get(j).get("menu_id").toString().isEmpty()){
									String secondId = secondMenu.get(j).get("menu_id").toString();
									secondMenu.get(j).put("thirdMenu", menuList (userId, secondId));
								}
							}
						}
						returnMap.put("menuValue", secondMenu);
				}
			}
		}
		return returnMap;
	}
	
	@Override
	public List<Map<String, Object>> menuList (String userId, String pmenuId){
		String sql = "select t.menu_name, t.parent_id,  t.menu_icon , t.menu_img ,t.request_url,t.menu_id  from menuinfo t  where t.menu_id  in ( "+
				" select rm.menu_id from role_menu rm where rm.role_id in( select ru.role_id  from role_user ru where ru.user_id =  ? ) "+
				" ) and t.status = '2' and t.state = '1' and t.parent_id = ?  order by t.order_id asc" ;
		List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql,new Object [] {userId,pmenuId});
		return dataList;
	}
}
