package cn.com.taiji.web.service.impl.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.com.king.domain.db1.Dept;
import cn.com.king.dto.DeptDto;
import cn.com.king.dto.UserDto;
import cn.com.king.page.util.PageUtil;
import cn.com.king.repository.db1.DeptRepository;
import cn.com.king.tools.StringTool;
import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.taiji.web.service.sys.DeptService;

/** 
* @ClassName: DeptServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author ranxing
* @date 2017年11月9日 下午3:51:36 
*  
*/
@Transactional
@Service
public class DeptServiceImpl extends DataSourceImpl implements DeptService {
	
	 DataSource datasource = getDriverManagerDataSource("dataSource1");// edas数据库

	
	@Inject
	DeptRepository  deptRepository;
	
	@Inject
	JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED
	@Override
	public PageUtil getPageAll(Map<String, Object> searchParameters, PageUtil pageUtil) throws Exception{
		int page = 0;
		int pageSize = 10;
		Page<Dept> pageList;
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
			Sort sort = new Sort(Direction.ASC, "deptIndex");
			pageable = new PageRequest(page, pageSize, sort);
		}
		
		Map<String, Object> filter = (Map<String, Object>) searchParameters.get("filter");
		if (filter != null) {
			// String logic = filter.get("logic").toString();
			final	 List<Map<String, Object>> filters = (List<Map<String, Object>>) filter.get("filters");
			Specification<Dept> spec = new Specification<Dept>() {
				@Override
				public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					
					List<Predicate> pl = new ArrayList<Predicate>();
					for (Map<String, Object> f : filters) {
						// String operator = ((String)
						// f.get("operator")).trim();
						String field = f.get("field").toString().trim();
						String value = f.get("value").toString().trim();
						if (value != null && value.length() > 0) {
							if ("deptName".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String> get(field), "%"
										+ value + "%"));
							} else if ("dept_zzjgdm".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String> get(field), "%"
										+ value + "%"));
							} else if ("status".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String> get(field), value ));
							}
						}

					}
					// 查询出未删除的
					pl.add(cb.equal(root.<Integer> get("flag"), 1));
					pl.add(cb.notEqual(root.<Integer> get("parentId"), "0"));

					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageList = deptRepository.findAll(spec, pageable);

		} else {
			Specification<Dept> spec = new Specification<Dept>() {
				public Predicate toPredicate(Root<Dept> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					// 查询出未删除的
					list.add(cb.equal(root.<Integer> get("flag"), 1));
					list.add(cb.notEqual(root.<Integer> get("parentId"), "0"));
					return cb.and(list.toArray(new Predicate[0]));
				}
			};
			pageList = deptRepository.findAll(spec, pageable);

		}
		List<Dept> list = pageList.getContent();
		pageUtil.setQueryObj(list);
		pageUtil.setTotal( pageList.getTotalElements());
		
		return pageUtil;
	}

	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED  SUPPORTS
	@Override
	public String saveDept(DeptDto dto, UserDto userDto) throws Exception{
		String message = "操作失败";
		Dept cf = new Dept();
		if (dto != null && dto.getDeptId() != null && !dto.getDeptId().trim().isEmpty()) {
			// 更新
			cf = this.deptRepository.findOne(dto.getDeptId());
			cf.setUpdateTime(new Date());
			cf.setUpdaterId(userDto.getId());
		} else {
			// 添加
			String id = StringTool.getUUID();
			dto.setDeptId(id);
			dto.setFlag(1);// 初始化
			
				cf.setCreateTime(new Date());
				cf.setUpdateTime(new Date());
				cf.setCreatorId(userDto.getId());
		}
		BeanUtils.copyProperties(dto, cf);
		if (cf.getDeptIndex() == null) {
			cf.setDeptIndex(0);
		}
		
	    deptRepository.saveAndFlush(cf);
	    message = "SUCCESS";
		return message;
	}

	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED  SUPPORTS
	@Override
	public Dept findOne(String id) {
		return deptRepository.findOne(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED  SUPPORTS
	@Override
	public String remove(String id, UserDto userDto)  throws Exception{
		
		String message = "操作失败";
		Dept cf = deptRepository.findOne(id);
		
		cf.setUpdateTime(new Date());
		cf.setUpdaterId(userDto.getId());
		cf.setFlag(0);
		deptRepository.saveAndFlush(cf);
		message = "删除成功";
		
		return message;
	}

	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED  SUPPORTS
	@Override
	public Map<String, Object> getDeptUser(String userId ) {
		// TODO Auto-generated method stub
		DataSource datasource = getDriverManagerDataSource("dataSource1");// edas数据库
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		String sql = " select c.id as id , c.dept_fullname as name , c.parent_id as pId , "
				+ "  c.dept_zzjgdm , c.creator_id , c.dept_name , c.dept_url , c.remark   from  dept c,dept_user d where c.flag = 1 and d.id='"
				+ userId + "' " + " and d.dept_id=c.id  ";
		List<Map<String, Object>> deptMap = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : deptMap) {
			if (map.containsKey("pId") && "0".equals(map.get("pId"))) {
				map.put("open", true);
				map.put("isParent", true);
				map.put("nocheck", true);
			} else {
				map.put("open", true);
				map.put("nocheck", true);
				// map.put("isParent", true);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if(deptMap.size()==0){
			Map<String, Object> dmap = new HashMap<String, Object>();
			dmap.put("id", "0");
			dmap.put("name", "暂未分配部门！");
			dmap.put("pId", "0");
			deptMap.add(dmap);
		}
		map.put("DtoList", deptMap);	  
		return map;
	}

	
	/**
	 * 部门treelist
	 */
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED  SUPPORTS
	@Override
	public Map<String, Object> getDeptUserZtreeList(Map<String, Object> init) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		//获取部门总列表
//		String sql = " select c.dept_id as id , c.dept_fullname as name , c.parent_id as pId   "
//				   + "       "
//			   	   + " from  dept c where c.flag = 1 and c.dept_state = '1'  ORDER BY dept_index ASC";
		String sql = " select c.id as id , c.dept_fullname as name , c.parent_id as pId , "
		+ "  c.dept_zzjgdm , c.creator_id , c.dept_name , c.dept_url , c.remark   from  dept c where c.flag = 1  and c.dept_state = '1'   ORDER BY dept_index ASC";
		List<Map<String, Object>> deptMapList = jdbcTemplate.queryForList(sql);
		
//		String sql = " select c.id as id , c.dept_fullname as name , c.parent_id as pId , "
//				+ "  c.dept_zzjgdm , c.creator_id , c.dept_name , c.dept_url , c.remark   from  dept c where c.flag = 1  ORDER BY dept_index ASC";

		//获取 用户 部门数据
		String[] ids = init.get("id").toString().split(",");
		Map<String, Object> userDeptMap = new HashMap<String, Object>();
		if (ids.length == 1 && !ids[0].isEmpty()) {
			sql = " select dept_id from dept_user where id = '"+ids[0]+"' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[] {});
			for (Map<String, Object> map : list) {
				// 存在与上面 获取到的目录菜单的话 给个状态
				userDeptMap.put(map.get("dept_id").toString(), true);
			}
		}
		
		Map<Object, Object> parentMap = new HashMap<Object, Object>();
		//转换
		for (Map<String, Object> map : deptMapList) {
			parentMap.put( map.get("pId"), map.get("pId"));
		}

		//转换
		for (Map<String, Object> map : deptMapList) {
			if (  map.containsKey("pId") && "0".equals(map.get("pId"))   ) {
				map.put("open", true);
				map.put("check", true);
				map.put("isParent", true);
				map.put("nocheck", true);
			} else if(parentMap.containsKey(map.get("id"))){
				map.put("isParent", true);
				map.put("nocheck", true);
			}else {
				map.put("open", false);
				map.put("checked", false);
				// map.put("isParent", true);
			}
			
			//map.get("id") 部门dept_id   
			if (userDeptMap.containsKey(map.get("id"))) {
				map.put("checked", true);
			}

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("DtoList", deptMapList);
		return map;
	}

	/**
	 * 部门treelist
	 */
	@Transactional(propagation = Propagation.SUPPORTS)//REQUIRED  SUPPORTS
	@Override
	public Map<String, Object>  getDeptZtreeList(Map<String, Object> init) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

		//获取部门总列表
//		String sql = " select c.dept_id as id , c.dept_fullname as name , c.parent_id as pId   "
//				   + "       "
//			   	   + " from  dept c where c.flag = 1 and c.dept_state = '1'  ORDER BY dept_index ASC";
		String sql = " select c.id as id , c.dept_fullname as name , c.parent_id as pId , "
		+ "  c.dept_zzjgdm , c.creator_id , c.dept_name , c.dept_url , c.remark   from  dept c where c.flag = 1     ORDER BY dept_index ASC";
		List<Map<String, Object>> deptMapList = jdbcTemplate.queryForList(sql);
		
//		String sql = " select c.id as id , c.dept_fullname as name , c.parent_id as pId , "
//				+ "  c.dept_zzjgdm , c.creator_id , c.dept_name , c.dept_url , c.remark   from  dept c where c.flag = 1  ORDER BY dept_index ASC";

		//获取 用户 部门数据
		String[] ids = init.get("id").toString().split(",");
		Map<String, Object> userDeptMap = new HashMap<String, Object>();
		if (ids.length == 1 && !ids[0].isEmpty()) {
			sql = " select dept_id from dept_user where id = '"+ids[0]+"' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, new Object[] {});
			for (Map<String, Object> map : list) {
				// 存在与上面 获取到的目录菜单的话 给个状态
				userDeptMap.put(map.get("dept_id").toString(), true);
			}
		}
		
		Map<Object, Object> parentMap = new HashMap<Object, Object>();

		//转换
		for (Map<String, Object> map : deptMapList) {
			if (  map.containsKey("pId") && "0".equals(map.get("pId"))   ) {
				map.put("open", true);
				map.put("check", true);
				map.put("isParent", true);
			//	map.put("nocheck", true);
			}  else {
				map.put("open", false);
				map.put("checked", false);
				// map.put("isParent", true);
			}
			
			//map.get("id") 部门dept_id   
			if (userDeptMap.containsKey(map.get("id"))) {
				map.put("checked", true);
			}

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("DtoList", deptMapList);
		return map;
	}
	
}
