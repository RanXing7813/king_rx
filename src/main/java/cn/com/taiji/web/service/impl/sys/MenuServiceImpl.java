package cn.com.taiji.web.service.impl.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.repository.db1.MenusRepository;
import cn.com.king.tools.StringTool;
import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.king.web.action.log.StringTooles;
import cn.com.taiji.web.service.sys.MenuService;

@Transactional
@Service
public class MenuServiceImpl extends DataSourceImpl implements MenuService {

	@Inject
	MenusRepository menusRepository;
	
	//连接rds数据库
	private static final String DRDS_INFO = "dataSource1";
	private static final String sqlAs = "t.menu_name as \"menu_name\", t.parent_id as \"parent_id\",  t.menu_icon as \"menu_icon\", t.menu_img as \"menu_img\",t.request_url as \"request_url\",t.menu_id as \"menu_id\"";

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	public PageUtil getMainMenu(PageUtil page, Map<String,Object> searchParameters) {
		// TODO Auto-generated method stub
		Map<String, List<Map<String, Object>>> returnMap = new HashMap<String,List<Map<String, Object>>>();
		String pmenuId = (String) searchParameters.get("parentURL");
		UserDto userDto =(UserDto) searchParameters.get("userDto");
		String userId = null;
		
		if(userDto!=null){
			userId = userDto.getId() ; 
		}else{
			userId = "-1";//游客
		}
		
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource(DRDS_INFO));
		if(pmenuId != null && "mainnav".equals(pmenuId)){
			pmenuId = "1";
			returnMap.put("mainmenu", menuList (userId, pmenuId));
		}else{
			//根据第一级url获取子节点
			String sql = "select menu_id as \"menu_id\", request_url as \"request_url\" from menuinfo where request_url = ? and  status = '2' ";
			List<Map<String, Object>> dataList =  drds.queryForList(sql,new Object [] {pmenuId});
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
		page.setQueryObj(returnMap);
		return page;
	}
	
	
	public List<Map<String, Object>> menuList (String userId, String pmenuId){
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource(DRDS_INFO));
		List<Map<String, Object>> dataList;
		
		
		if(userId!=null &&  !userId.isEmpty()){
			String sql = "select "+sqlAs+" from menuinfo t  where t.menu_id  in ( "+
					" select rm.menu_id from role_menu rm where rm.role_id in( select ru.role_id  from role_user ru where ru.user_id =  ? ) "+
					" ) and t.status = '2' and t.state = '1' and t.parent_id = ?  order by t.order_id asc" ;
			dataList = drds.queryForList(sql,new Object [] {userId,pmenuId});
			if(dataList.size()==0 && pmenuId.equals("1")){
				String sql1="select "+sqlAs+" from menuinfo t  where t.m_level=1   order by t.order_id asc";
				dataList =drds.queryForList(sql1); //and MENU_ID !='7'
			}
		}else{
			String sql="select "+sqlAs+"  from menuinfo t where t.m_level=1  order by t.order_id asc";
			dataList =drds.queryForList(sql);//and MENU_ID !='7'
		}
		return dataList;
	}

	@Override
	public List getMenuAll() {
		String sql = "select r.MENU_ID as id,r.PARENT_ID as pId,r.MENU_NAME as name from MENUINFO r   where r.STATE='1' order by order_id";
		DataSource datasource = getDriverManagerDataSource("dataSource1");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		List<Map<String, Object>> menuList = jdbcTemplate.queryForList(sql);
		for (int i = 0; i < menuList.size(); i++) {
			if(menuList.get(i).get("pId")==null){
				menuList.get(i).put("pId",0);
				menuList.get(i).put("open",true);
				menuList.get(i).put("isParent", true);
				break;
			}
		}
		return menuList;
	}
	
	@Override
	public PageUtil getDefaultMenu(PageUtil page) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getBackPage(String pId, String id, String groupname) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
	    String sql = " select REQUEST_URL from MENUINFO where PARENT_ID = ? and MENU_ID = ?  and STATE = '1' ";
	    Map<String,Object> backMap =  drds.queryForMap(sql,new Object [] {pId,id});
	    return backMap!=null?StringTool.null2Empty(backMap.get("request_url")):"";
	}


	@Override
	public Object findOne(String id) throws Exception {
		return menusRepository.findOne(id);
	}


	@Override
	public String saveOne(Menuinfo dto, UserDto userDto)  throws Exception {
		if(dto.getMenuId() ==null ||  dto.getMenuId().equals("")){
			dto.setMenuId(StringTool.getUUID());
			Date time=new Date();
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.setInsetTime(sim.format(time));
			dto.setState("1");
		}else{
		}
		menusRepository.saveAndFlush(dto);
		return dto.getMenuId();
	}


	@Override
	public String remove(String id, UserDto userDto)  throws Exception{
		// TODO Auto-generated method stub
		menusRepository.delete(id);
		return null;
	}


	@Override
	public void updMenuInfoState(String id)  throws Exception {
		menusRepository.updState(id);
	}
	
	
	
	
	
	
	
	
	
	
	
}
