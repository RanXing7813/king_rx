package cn.com.king.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.JDBCException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db1.InspectionConfig;
import cn.com.king.domain.db1.Monitor;
import cn.com.king.domain.db2.LogZJTaskInfo;
import cn.com.king.dto.InspectionConfigDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.BaseServiceImpl;
import cn.com.king.page.util.PageUtil;
import cn.com.king.repository.db1.InspectionconfigRepository;
import cn.com.king.repository.db1.MonitorRepository;
import cn.com.king.repository.db2.LogZJTaskInfoRepository;
import cn.com.king.tools.StringTool;
import cn.com.king.web.service.MonitorService;

@Transactional
@Service
public class MonitorServiceImpl  extends BaseServiceImpl<Monitor, Object> implements MonitorService {

	
	private EntityManagerFactory emf;
	@PersistenceUnit(unitName="db2")
	 public void setEntityManagerFactory(EntityManagerFactory emf) {
	  this.emf = emf;
	 }
	 
	
	
	@PersistenceContext(unitName="db1")  
	private EntityManager em;
	
	@Inject
	MonitorRepository  monitorRepository;
	
	@Inject
	InspectionconfigRepository inspectionconfigRepository;
	
//	@Inject
//	LogZJTaskInfoRepository logZJTaskInfoRepository;
	
	
	@Override
	public PageUtil getPageList  (PageUtil page , int rows) {
		PageUtil pages = getPageSingle(page);
		page.setPageSize(rows);
		return pages;
	}
	
	
	/**
	 * 删除数据返回删除数据条数
	 */
	@Override
	public int remove(String [] ids, UserDto userDto) {
		int x =	inspectionconfigRepository.removeS(ids,"2","aa");
		return x;
	}


	@Override
	public void newData (InspectionConfigDto dto, UserDto userDto) {
		try {
			InspectionConfig cf = new InspectionConfig();
			BeanUtils.copyProperties(cf, dto);
			inspectionconfigRepository.saveAndFlush(cf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public int save(InspectionConfigDto dto, UserDto userDto) {
		int x = 0;
		
		InspectionConfig cf = new InspectionConfig();
		if(dto.getConfigId()==null||dto.getConfigId().isEmpty()){
			dto.setConfigId(StringTool.getUUID());
		}
		BeanUtils.copyProperties(dto, cf);
		inspectionconfigRepository.saveAndFlush(cf);
			x = 1;
		return x;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageUtil getPageSingle(PageUtil pageUtil) {
		//logZJTaskInfoRepository.findAll();
		
//		List  ss2= 	logZJTaskInfoRepository.findAll();
	//	EntityManager em2  =   emf.createEntityManager();
		 
		
		try {
			
			
		     //	List<LogZJTaskInfo>  ss =	logZJTaskInfoRepository.findAll();
			
			  
				//System.out.println("1111111111111");
				StringBuilder jpql = new StringBuilder ("select c from InspectionConfig c where c.isDelete=1   ") ;
				StringBuilder jpqlCount = new StringBuilder ("select count(1) as total from InspectionConfig c where c.isDelete=1  ") ;

				int page = 0;
				int pageSize = 10;
				
				if (pageUtil.getPage() > 0 ) {
					page = pageUtil.getPage() - 1;
				}
				if (pageUtil.getRows() != null && pageUtil.getRows().size()==1 ) {
					pageSize = Integer.parseInt((String) pageUtil.getRows().get(0));
				}
				if (pageSize < 1)
					pageSize = 1;
				if (pageSize > 60)
					pageSize = 10;
			
				Map filter = (Map) pageUtil.getSechMap().get("filter");
				if (filter != null) {//模糊匹配查询
					final List<Map> filters = (List<Map>) filter.get("filters");
				
							for (Map f : filters) {
								String field = f.get("field").toString().trim();
								String value = f.get("value").toString().trim();
								if (value != null && value.length() > 0) {
									
									switch (field) {
									case "inspection_name":
										jpql.append(" and c.inspection_name like '%"+value+"%' ");
										jpqlCount.append(" and c.inspection_name like '%"+value+"%' ");
										break;
									case "date_from":
										jpql.append(" and c.createdTime >= str_to_date('"+value+"','%Y-%m-%d %T') ");
										jpqlCount.append(" and c.createdTime >= str_to_date('"+value+"','%Y-%m-%d %T') ");
										//date_add(c.update_Time, interval 30 day)>= str_to_date(now(),'%Y-%m-%d')
										break;
									case "date_to":
										jpql.append(" and c.inspection_name like '%"+value+"%' ");
										jpqlCount.append(" and c.inspection_name like '%"+value+"%' ");
										break;
									default:
										jpql.append(" and "+field+" = '"+value+"'");
										jpqlCount.append(" and "+field+" = '"+value+"'");	
										break;
									}
									
									
									
									
									
//									if("c.inspection_name".equalsIgnoreCase(field)){
//										jpql.append(" and c.inspection_name like '%"+value+"%' ");
//										jpqlCount.append(" and c.inspection_name like '%"+value+"%' ");
//									}else if("c.systemResult".equalsIgnoreCase(field)){
//										if("200".equals(value)){
//											jpql.append(" and c.systemResult >=200 and c.systemResult < 300 ");
//											jpqlCount.append(" and c.systemResult >=200 and c.systemResult < 300 ");
//										}else if("300".equals(value)){
//											jpql.append(" and c.systemResult >=300 ");
//											jpqlCount.append(" and c.systemResult >=300  ");
//										}else{
//											jpql.append(" and c.systemResult ='-1' ");
//											jpqlCount.append(" and c.systemResult = '-1'  ");
//										}
//									}
//									else{
//										jpql.append(" and "+field+" = '"+value+"'");
//										jpqlCount.append(" and "+field+" = '"+value+"'");								
//									}
								}
							}
				} 
				
				List<Map> orderMaps = (List<Map>) pageUtil.getSechMap().get("sort");// 排序 - begin  
				List<Order> orders = new ArrayList<Order>();
				if (orderMaps != null) {
					jpql.append(" order by ");
					for (Map m : orderMaps) {
						if (m.get("field") == null)
							continue;
						String field = m.get("field").toString();
						if (!StringUtils.isEmpty(field)) {
							String dir = m.get("dir").toString();
							if ("DESC".equalsIgnoreCase(dir)) {
								orders.add(new Order(Direction.DESC, field));
								jpql.append(field+" desc ");
							} else {
								orders.add(new Order(Direction.ASC, field));
								jpql.append(field+" asc ");
							}
						}
					}
				}
				
			//	em = emf.createEntityManager();
				// System.out.println(jpql.toString()); System.out.println(jpqlCount.toString());
				 Query query = em.createQuery(jpql.toString()); 
				  query.setFirstResult(page*pageSize); // query
				  query.setMaxResults(pageSize);
				  List list =  query.getResultList();
				  pageUtil.setRows(list);
				  
				  //总数
				  query = em.createQuery( jpqlCount.toString());
				  Object count =  query.getSingleResult();
				  pageUtil.setTotal(count);
				  
				//  emf = PersistenceUnitInfo.createEntityManagerFactory("db2");
						
//				  String sqls = "select c from LogZJTaskInfo c "; 
//				  query = em2.createQuery(sqls); 
//				  List list2 =  query.getResultList();
				  
				  
				  
				return pageUtil;
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
				//	em2.close();
					em .close();  
				}
				return null;
	}

	

}
