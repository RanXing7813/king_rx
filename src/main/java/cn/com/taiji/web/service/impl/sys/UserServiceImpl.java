package cn.com.taiji.web.service.impl.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db1.UserInfo;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.repository.db1.UserInfoRepository;
import cn.com.king.tools.StringTool;
import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.taiji.web.service.sys.UserService;

@Transactional
@Service
public class UserServiceImpl extends DataSourceImpl implements UserService  {
	
	@Inject
	UserInfoRepository  userInfoRepository;
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	@Override
	public PageUtil getPageAll(Map<String, Object> searchParameters, PageUtil pageUtil) throws Exception{
		int page = 0;
		int pageSize = 10;
		Page<UserInfo> pageList;
		if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
			page = Integer.parseInt(StringTool.null2Empty(searchParameters.get("page").toString())) - 1;
		}
		if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
			pageSize = Integer.parseInt(StringTool.null2Empty(searchParameters.get("pageSize").toString()));
		}
		if (pageSize < 1)  pageSize = 1;
		if (pageSize > 100)  pageSize = 100;
		
		List<Map<String,Object>> orderMaps = (List<Map<String,Object>>) searchParameters.get("sort");
		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (Map<String, Object> m : orderMaps) {
				if (m.get("field") == null)
					continue;
				String field = m.get("field").toString();
				if (!StringUtils.isEmpty(field)) {
					String dir = m.get("dir").toString();
					if ("DESC".equalsIgnoreCase(dir)) {
						orders.add(new Order(Direction.DESC, field));
					} else {
						orders.add(new Order(Direction.ASC, field));
					}
				}
			}
		}
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			Sort sort = new Sort(Direction.ASC, "loginName");
			pageable = new PageRequest(page, pageSize, sort);
		}
		
		Map<String, Object> filter = (Map<String, Object>) searchParameters.get("filter");
		if (filter != null) {
			// String logic = filter.get("logic").toString();
			final	 List<Map<String, Object>> filters = (List<Map<String, Object>>) filter.get("filters");
			Specification<UserInfo> spec = new Specification<UserInfo>() {
				@Override
				public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> pl = new ArrayList<Predicate>();
					for (Map<String, Object> f : filters) {
						// String operator = ((String)
						// f.get("operator")).trim();
						String field = f.get("field").toString().trim();
						String value = f.get("value").toString().trim();
						if (value != null && value.length() > 0) {
							if ("loginName".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String> get(field), "%"
										+ value + "%"));
							} else if ("userName".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String> get(field), "%"
										+ value + "%"));
							} else if ("email".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String> get(field), value ));
							}
						}

					}
					// 查询出未删除的
					pl.add(cb.equal(root.<Integer> get("flag"), 1));
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageList = userInfoRepository.findAll(spec, pageable);

		} else {
			Specification<UserInfo> spec = new Specification<UserInfo>() {
				public Predicate toPredicate(Root<UserInfo> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					// 查询出未删除的
					list.add(cb.equal(root.<Integer> get("flag"), 1));
					return cb.and(list.toArray(new Predicate[0]));
				}
			};
			pageList = userInfoRepository.findAll(spec, pageable);

		}
		List<UserInfo> list = pageList.getContent();
		pageUtil.setQueryObj(list);
		pageUtil.setTotal( pageList.getTotalElements());
		
		return pageUtil;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	public String save(UserDto dto, UserDto userDto) throws Exception{
		String message = "操作失败";
//		UserInfo cf = new UserInfo();
//		if (dto != null && dto.getDeptId() != null && !dto.getDeptId().trim().isEmpty()) {
//			// 更新
//			cf = this.userInfoRepository.findOne(dto.getDeptId());
//			cf.setUpdateTime(new Date());
//			cf.setUpdaterId(userDto.getId());
//		} else {
//			// 添加
//			String id = StringTool.getUUID();
//			dto.setDeptId(id);
//			dto.setFlag(1);// 初始化
//			
//				cf.setCreateTime(new Date());
//				cf.setUpdateTime(new Date());
//				cf.setCreatorId(userDto.getId());
//		}
//		BeanUtils.copyProperties(dto, cf);
//		
//	    userInfoRepository.saveAndFlush(cf);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		if(StringUtils.isEmpty(dto.getToken()))
		{
			/*if(!findByName(dto.getLoginName(),dto.getId()))
			{
					throw new BusinessException("用户名["+dto.getLoginName()+"]已存在！");
			}*/
			//新增
			if(dto.getId()==null||"".equals(dto.getId())){
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				//dto.setDeptId("");
				dto.setId(id);
				dto.setFlag(1);// 默认初始化
				UserInfo user = new UserInfo();
				BeanUtils.copyProperties(dto, user);
				this.userInfoRepository.saveAndFlush(user);
				
				if(dto.getDeptId()!=null && !dto.getDeptId().isEmpty()){
					String []deptIds=dto.getDeptId().split(",");
					String sql="insert into dept_user(dept_id,id) values(?,?)";
					if(deptIds.length>0)
					for(String s:deptIds){
						Object[] parasInsert = {s,id};
						jdbcTemplate.update(sql, parasInsert);
					}
				}
			}else{
				UserInfo user = new UserInfo();
				BeanUtils.copyProperties(dto, user);
				user.setFlag(1);// 默认初始化
				this.userInfoRepository.saveAndFlush(user);
				//this.userRepository.upPhoneNumxxxEmail(dto.getPhoneNum(),dto.getEmail(),dto.getId(),"",dto.getState());
				
				
				
				String sql="delete from dept_user where id='"+dto.getId()+"'";
				jdbcTemplate.update(sql);
				
				if(dto.getDeptId()!=null && !dto.getDeptId().isEmpty()){
					String [] deptIds=dto.getDeptId().split(",");
					String insertSql="insert into dept_user(dept_id,id) values(?,?)";
					for(String s:deptIds){
						Object[] parasInsert = {s,dto.getId()};
						jdbcTemplate.update(insertSql, parasInsert);
					}
				}
				
			}
		}
	    message = "SUCCESS";
		return message;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	public UserDto findOne(String id) {
		UserInfo cf = userInfoRepository.findOne(id);
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(cf, dto);
		dto.setCreateTime(null);
		return dto;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	public String remove(String id, UserDto userDto)  throws Exception{
		
		String message = "操作失败";
		UserInfo cf = userInfoRepository.findOne(id);
		
		cf.setUpdateTime(new Date());
		cf.setUpdaterId(userDto.getId());
		cf.setFlag(0);
		userInfoRepository.saveAndFlush(cf);
		message = "删除成功";
		
		return message;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	public int findName(String cnm) {
		// TODO Auto-generated method stub
		String sql="select  count(1) from userinfo  where login_name='"+cnm+"'";
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		 int x = drds.queryForInt(sql);
		return x;
	}

	
	@Inject 
	JdbcTemplate jdbcTemplate;
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	public void powerDeptTreeList(Map<String, Object> init, UserDto userDto)  throws Exception{
		// TODO Auto-generated method stub
		
		DataSource datasource = getDriverManagerDataSource("dataSource1");// edas数据库
		jdbcTemplate.setDataSource(datasource);
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String[] userids = init.get("id").toString().split(",");
		String[] deptIds = init.get("deptIds").toString().split(",");
		String sql ="";
		for (int i = 0; i < userids.length; i++) {
			
			
			
			String delsql = " delete  from dept_user where id = '" + userids[i] + "'";
			jdbcTemplate.update(delsql);// 先删除 已经存在的目录树 然后进行新增
			  
//			 throw new RuntimeException("Mock throw exception after insert foo");
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for (int j = 0; j < deptIds.length; j++) {
				 sql = " insert into dept_user (id,dept_id) values (?,?)";
				Object []  arrgs = new  Object [2] ;
				arrgs[0]= userids[i];
				arrgs[1]= deptIds[j];
				batchArgs.add(arrgs);
			}
			jdbcTemplate.batchUpdate(sql, batchArgs);
		}		
		
	}





}
