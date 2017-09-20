package com.example.demo;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.king.domain.db1.Monitor;
import cn.com.king.repository.db1.InspectionconfigRepository;
import cn.com.king.repository.db1.MonitorRepository;
import cn.com.king.repository.db2.LogZJTaskInfoRepository;
import cn.com.king.web.service.login.LoginService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/*.xml" })
public class ApplicationTests {

	@Inject
	private LoginService loginService;
	
	@Inject
	MonitorRepository monitorRepository;
	@Inject
	InspectionconfigRepository  inspectionconfigRepository;

	@Before  
	 public void init() {  
//	      ApplicationContext ctx = new FileSystemXmlApplicationContext( "classpath:spring/*.xml");  
//	      inspectionconfigRepository = (InspectionconfigRepository) ctx.getBean("inspectionconfigRepository");  
//	      monitorRepository 	     = (MonitorRepository) ctx.getBean("monitorRepository");  
//	      logZJTaskInfoRepository    = (LogZJTaskInfoRepository) ctx.getBean("logZJTaskInfoRepository");  
//   	  assertNotNull(inspectionconfigRepository);  
	 }    
	  	
	  	@Test
		public void run() {
	  		System.out.println(666);
	 // 		loginService.add("8");
	  		loginService.updateS();
	  	//	monitorRepository.countBySystemName();
	//  		System.out.println(monitorRepository.countBySystemName("login:update8"));
		}
	  	
}
