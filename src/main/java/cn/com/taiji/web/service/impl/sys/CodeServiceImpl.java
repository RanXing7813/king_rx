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

import cn.com.king.domain.db1.Code;
import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.repository.db1.CodeRepository;
import cn.com.king.repository.db1.MenusRepository;
import cn.com.king.tools.StringTool;
import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.king.web.action.log.StringTooles;
import cn.com.taiji.web.service.sys.CodeService;
import cn.com.taiji.web.service.sys.MenuService;

@Transactional
@Service
public class CodeServiceImpl extends DataSourceImpl implements CodeService {

	@Inject
	CodeRepository codeRepository;
	
	@Inject
	JdbcTemplate jdbcTemplate;
	
	//连接rds数据库
	private static final String DRDS_INFO = "dataSource1";
	private static final String sqlAs = "t.menu_name as \"menu_name\", t.parent_id as \"parent_id\",  t.menu_icon as \"menu_icon\", t.menu_img as \"menu_img\",t.request_url as \"request_url\",t.menu_id as \"menu_id\"";

	
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
	public List getCodeByLevel() {
		String sql = "select r.id as id,r.parent_id as pId,r.code_show_name as name from code r where r.isEffective='Y' and (r.`level`=0 or r.`level`=1) order by code_index";
//		DataSource datasource = getDriverManagerDataSource(DRDS_INFO); 
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("pId")==null){
				list.get(i).put("pId",0);
				list.get(i).put("open",true);
				list.get(i).put("isParent", true);
				//list.get(i).put("icon", "/theme/zTree/img/icon-4.png");
				list.get(i).put("iconSkin", "diy02");
				//break;
			}else{ 
				//list.get(i).put("icon", "/theme/zTree/img/icon-3.png");
				list.get(i).put("iconSkin", "diy03");
			}
			//list.get(i).put("isParent", true);
			// icon:"/img/leaf.gif"
		}
		return list;
	}

	@Override
	public Object findOne(String id) throws Exception {
		return codeRepository.findOne(id);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)//REQUIRED
	public String saveOne(Code dto, UserDto userDto)  throws Exception {
		if(dto.getId() ==null ||  dto.getId().equals("")){
			dto.setId(StringTool.getUUID());
//			Date time=new Date();
//			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.setCreate_time(new Date()) ;
			dto.setUpdate_time(new Date()) ;
			dto.setState("Y");
			dto.setIsEffective("Y");
			
			codeRepository.saveAndFlush(dto);
			return dto.getId();
			
		}else{
			
			Code dto2 =	codeRepository.findOne(dto.getId());
			
			dto2.setCode_index(dto.getCode_index());
			dto2.setCode_int_value(dto.getCode_int_value());
			dto2.setCode_show_name(dto.getCode_show_name());
			dto2.setCode_varchar_value(dto.getCode_varchar_value());
			dto2.setRemark(dto.getRemark());
			dto2.setState(dto.getState());
			dto2.setUpdate_time(new Date());
			
			codeRepository.saveAndFlush(dto2);
			return dto2.getId();
			
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)//REQUIRED
	public String remove(String id, UserDto userDto)  throws Exception{
		// TODO Auto-generated method stub
		codeRepository.delete(id);
		return null;
	}

	@Override
	public List getCodeByPid(String id) {
		// TODO Auto-generated method stub
		return 	codeRepository.findListByPid(id);
	}

	
	
	
	
	
	
	
	
	
	
	
}
