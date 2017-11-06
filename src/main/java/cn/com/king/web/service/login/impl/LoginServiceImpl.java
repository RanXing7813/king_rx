package cn.com.king.web.service.login.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.king.domain.db1.InspectionConfig;
import cn.com.king.domain.db1.Monitor;
import cn.com.king.domain.db1.UserInfo;
import cn.com.king.dto.UserDto;
import cn.com.king.repository.db1.InspectionconfigRepository;
import cn.com.king.repository.db1.MonitorRepository;
import cn.com.king.repository.db1.UserInfoRepository;
import cn.com.king.repository.db2.LogZJTaskInfoRepository;
import cn.com.king.tools.StringTool;
import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.king.web.service.login.LoginService;
@Transactional
@Service
public class LoginServiceImpl extends DataSourceImpl implements LoginService {
	
	
	
	@Inject
	MonitorRepository  monitorRepository;
	
//	@Inject
//	LogZJTaskInfoRepository logZJTaskInfoRepository;
	@Inject
	InspectionconfigRepository  inspectionconfigRepository;
//    public boolean login(String userName, String password) {
//        System.out.println("login:" + userName + "," + password);
//        return true;
//    }
    
   
    public  void add(String num){
    	
    	
        System.out.println("login:add");
        Monitor monitor = new Monitor();
  		monitor.setMonitorid(num);
  		monitor.setIsDelete(1);
  		monitorRepository.save(monitor);
  		//Example<Monitor> example = Example.of(monitor);
//  		
//  		String sql = " select * from monitor c where c.systemName='广东公文交换系统1312' ";
//  		 Map map = new HashMap();
//  		map = JdbcTemplate2.queryForMap(sql);
//  		//JdbcTemplate2.
//  		monitor.setMonitorid("8");
//  		monitor.setSystemName("login:update");
//  		monitor.setIsDelete(7);
//  		monitorRepository.saveAndFlush(monitor);
//        throw new RuntimeException("Mock throw exception after insert foo");
    }
    public  void updateS(){
    	InspectionConfig cf = new InspectionConfig();
    	cf.setConfigId(StringTool.getUUID());
    	cf.setIsDelete(1);
    	cf.setInspectionName("1111");
    	inspectionconfigRepository.save(cf);
//        System.out.println("login:update");
//        Monitor monitor = new Monitor();
//  		monitor.setMonitorid("8");
//  		monitor.setSystemName("login:update8");
//  		monitor.setIsDelete(1);
//  		monitorRepository.delMsgModel("8");
  		//monitorRepository.UpdateMonitor(monitor);
  	//	monitorRepository.saveAndFlush(monitor);
       // throw new RuntimeException("Mock throw exception after insert foo");
    }
    public  String del(){
        System.out.println("login:del");
        return "delType=1";

    }
    public  List  findAll(){
    	List list = monitorRepository.findAll();
    	return list;
    }
    
    @Inject
	UserInfoRepository  userRepository;
	
	//连接rds数据库
	private static final String DRDS_INFO = "dataSource1";

	@Override
	public UserDto login(String loginName, String pword) {
		 String password=DigestUtils.sha256Hex(StringUtils.trimToEmpty(pword));
		 UserInfo user=userRepository.login(loginName, password);
		//UserInfo user=	userRepository.findByLoginName(loginName);
	    
		UserDto dto = new UserDto();
		if(user!=null){
			BeanUtils.copyProperties(user, dto);
			
			JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
			String getDeptlistsql="select d.dept_id, d.dept_name from  DEPT d  right join  DEPT_USER t on 1=1 where d.dept_id = t.dept_id and t.id='"+dto.getId()+"' order by d.dept_index desc ";
			List<Map<String,Object>> deptList=drds.queryForList(getDeptlistsql);
			if(deptList!=null && deptList.size()>0){
				dto.setDeptId(deptList.get(0).get("dept_id").toString());    //deptList.get(0).get("dept_id");
				dto.setDeptName(deptList.get(0).get("dept_name").toString());
				dto.setDepts(deptList);
//				String deptIds = "";
//				for (Map<String, Object> map : deptList) {
//					deptIds+= map.get("dept_id")+",";
//				}
//				if(deptIds.length()>0){
//					deptIds = deptIds.substring(0, deptIds.length()-1);
//				}
			}else{
				dto.setDeptId("0");    //deptList.get(0).get("dept_id");
				dto.setDeptName("无部门");
			}
			
			
			
			
		}
		return dto;
	}

	@Override
	public boolean getFlagSys(String userid) {
		List menulist=new ArrayList();
		//创建数据库连接
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		//通过用户id查询权限id
		String getrolelistsql="select role_id from role_user where user_id='"+userid+"'";
		List roleidlist=drds.queryForList(getrolelistsql);
		if(roleidlist.size()>0){
			String getflagsql="select sys_flag from role where role_id in (''";
			for(int i=0;i<roleidlist.size();i++){
				Map role=(Map)roleidlist.get(i);
				getflagsql+=",'"+role.get("role_id").toString()+"'";
			}
			getflagsql+=")";
			List flaglist=drds.queryForList(getflagsql);
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

}
