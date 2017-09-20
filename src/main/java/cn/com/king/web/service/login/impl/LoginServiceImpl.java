package cn.com.king.web.service.login.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.king.domain.db1.Monitor;
import cn.com.king.repository.db1.InspectionconfigRepository;
import cn.com.king.repository.db1.MonitorRepository;
import cn.com.king.repository.db2.LogZJTaskInfoRepository;
import cn.com.king.web.service.login.LoginService;
@Transactional
@Service
public class LoginServiceImpl implements LoginService {
	
	
	
	@Inject
	MonitorRepository  monitorRepository;
	
//	@Inject
//	LogZJTaskInfoRepository logZJTaskInfoRepository;

    public boolean login(String userName, String password) {
        System.out.println("login:" + userName + "," + password);
        return true;
    }
    
   
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
    	
        System.out.println("login:update");
        Monitor monitor = new Monitor();
  		monitor.setMonitorid("8");
  		monitor.setSystemName("login:update8");
  		monitor.setIsDelete(1);
  		monitorRepository.delMsgModel("8");
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

}
