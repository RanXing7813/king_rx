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

import cn.com.king.domain.db1.Menuinfo;
import cn.com.king.domain.db1.Menus;
import cn.com.king.domain.db1.Role;
import cn.com.king.dto.RoleDto;
import cn.com.king.dto.UserDto;
import cn.com.king.repository.db1.MenusRepository;
import cn.com.king.repository.db1.RoleRepository;
import cn.com.king.web.action.log.DataSourceImpl;
import cn.com.taiji.util.DateUtil;
import cn.com.taiji.web.service.sys.RoleService;
@Service
public class RoleServiceImpl extends DataSourceImpl implements RoleService {

	@Inject
	  RoleRepository roleRepository;

	@Inject
	  MenusRepository menusRepository;

	/**
	 * 用户管理数据list
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map getPage(Map searchParameters, String salt) {
		Map map = new HashMap();
		int page = 0;
		int pageSize = 10;
		Page<Role> pageList;
		// Page<RoleDto> pageDtoList;
		if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
			page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
		}
		if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
			pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
		}
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;
		List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (Map m : orderMaps) {
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
			Sort sort = new Sort(Direction.ASC, "roleIndex");
			pageable = new PageRequest(page, pageSize, sort);
		}
		Map filter = (Map) searchParameters.get("filter");
		if (filter != null) {
			// String logic = filter.get("logic").toString();
			final List<Map> filters = (List<Map>) filter.get("filters");
			Specification<Role> spec = new Specification<Role>() {
				@Override
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (Map f : filters) {
						// String operator = ((String)
						// f.get("operator")).trim();
						String field = f.get("field").toString().trim();
						String value = f.get("value").toString().trim();
						if (value != null && value.length() > 0) {
							if ("roleName".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String>get(field), "%" + value + "%"));
							} else if ("roleDesc".equalsIgnoreCase(field)) {
								pl.add(cb.like(root.<String>get(field), "%" + value + "%"));
							} else {
								pl.add(cb.equal(root.<Integer>get(field), value));
							}
						}

					}
					// 查询出未删除的
					pl.add(cb.equal(root.<Integer>get("flag"), 1));
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageList = roleRepository.findAll(spec, pageable);

		} else {
			Specification<Role> spec = new Specification<Role>() {
				public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					// 查询出未删除的
					list.add(cb.equal(root.<Integer>get("flag"), 1));
					return cb.and(list.toArray(new Predicate[0]));
				}
			};
			pageList = roleRepository.findAll(spec, pageable);

		}
		map.put("total", pageList.getTotalElements());
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		List<Role> list = pageList.getContent();
		if (list != null && list.size() > 0) {
			for (Role d : list) {
				RoleDto dto = new RoleDto();
				BeanUtils.copyProperties(d, dto);
				dto.generateToken(salt);
				dtos.add(dto);
			}
		}
		map.put("roles", dtos);

		return map;
	}

	/**
	 * 新增 编辑
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(RoleDto roleDto) {
		// 新增
		if (roleDto.getRoleId() == null || "".equals(roleDto.getRoleId())) {
			String roleId = UUID.randomUUID().toString().replaceAll("-", "");
			roleDto.setRoleId(roleId);
			roleDto.setFlag(1);
			roleDto.setCreateTime(DateUtil.getNowTime());
		}
		// 编辑
		else {
			roleDto.setFlag(1);
			roleDto.setUpdateTime(DateUtil.getNowTime());
		}
		Role role = new Role();
		BeanUtils.copyProperties(roleDto, role);
		role.setRoleIndex(2);
		role.setShowUsers(2);
		role.setUpdaterId("");
		role.setUpdateTime(DateUtil.getNowTime());
		roleRepository.saveAndFlush(role);
	}

	/**
	 * 单条删除
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(String roleId,UserDto userDto) {
		roleRepository.updateFlag(roleId, new Date(), "");
//		roleRepository.updateFlag(roleId, new Date(), userDto.getId());
	}

	/**
	 * 根据ID查询role表数据
	 * 
	 * @param id
	 *            salt
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public RoleDto findDtoById(String id, String salt) {
		RoleDto dto = new RoleDto();
		if (StringUtils.isNotEmpty(id)) {
			Role code = this.roleRepository.findOne(id);
			if (code != null) {
				BeanUtils.copyProperties(code, dto);
				dto.generateToken(salt);
			}
		}
		return dto;
	}

	/**
	 * 获得目录树
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<Menus> getMenus(String id) {
		List<Menus> menulist = new ArrayList<Menus>();
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		String sql = " select role_id , menu_id from role_menu where role_id in ( " + id + " ) ";
		// 通过 角色 id 得到已经关联的目录 menu_id , 放入到menus中
		List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
		if(id!=null && !id.isEmpty())   list = drds.queryForList(sql, new Object[] {});
		Map<Object, Object> menus = new HashMap<Object, Object>();
		// 获取关联表中的目录菜单
		for (Map<String, Object> map : list) {
			menus.put(map.get("menu_id"), true);// {baba-1-3-1=true,
												// baba-2=true, baba-1=true,
												// baba-1-3=true, baba-2-2=true}
		}
		// 获取status = 1 的目录树 , menuId对比是否存在 menus中,随便给了个标记字段
		List<Menuinfo> menuinfoList = menusRepository.findMenuss();// .findAll();

		for (Menuinfo d : menuinfoList) {
			Menus dto = new Menus();
			BeanUtils.copyProperties(d, dto);
			// 存在与上面 获取到的目录菜单的话 给个状态
			if (menus.get(dto.getMenuId()) != null) {
				dto.setState("8");
			}
			;
			menulist.add(dto);
		}
		return menulist;
	}

	/**
	 * 获得目录树
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Menus> getMenuForMap(Map searchParameters) {
		List<Menus> menulist = new ArrayList<Menus>();
		Map<Object, Object> menus = new HashMap<Object, Object>();
		
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		String sql = " select role_id , menu_id from role_menu where role_id = " + searchParameters.get("id") ; 
		
		// 通过 角色 id 得到已经关联的目录 menu_id , 放入到menus中
		List<Map<String, Object>> list = drds.queryForList(sql, new Object[] {});
		for (Map<String, Object> map : list) {
			menus.put(map.get("menu_id"), true);
		}
		
		// 获取status = 1 的目录树 , menuId对比是否存在 menus中,随便给了个标记字段
		if ("1".equals(searchParameters.get("type"))) {// mh
			
			List<Menuinfo> menuinfoList = menusRepository.findMhMenus();// .findMhMenus();
			for (Menuinfo d : menuinfoList) {
				Menus dto = new Menus();
				BeanUtils.copyProperties(d, dto);
				// 存在与上面 获取到的目录菜单的话 给个状态
				if (menus.get(dto.getMenuId()) != null) {
					dto.setState("8");
				}
				;
				menulist.add(dto);
			}
		} else if ("2".equals(searchParameters.get("type"))) {// yw
			List<Menuinfo> menuinfoList = menusRepository.findYwMenus();// .findYwMenus();
			for (Menuinfo d : menuinfoList) {
				Menus dto = new Menus();
				BeanUtils.copyProperties(d, dto);
				// 存在与上面 获取到的目录菜单的话 给个状态
				if (menus.get(dto.getMenuId()) != null) {
					dto.setState("8");
				}
				;
				menulist.add(dto);
			}
		} else {
			// 获取status = 1 的目录树 , menuId对比是否存在 menus中,随便给了个标记字段
			List<Menuinfo> menuinfoList = menusRepository.findMenuss();// .findAll();

			for (Menuinfo d : menuinfoList) {
				Menus dto = new Menus();
				BeanUtils.copyProperties(d, dto);
				// 存在与上面 获取到的目录菜单的话 给个状态
				if (menus.get(dto.getMenuId()) != null) {
					dto.setState("8");
				}
				;
				menulist.add(dto);
			}

		}

		return menulist;
	}

	/**
	 * 保存 选中的目录树结构 到 role_menu 表中 这里在前台控制了只能 单个角色进行增加
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAuthMenus(Map searchParameters) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		String[] roleIds = searchParameters.get("id").toString().split(",");
		String[] menuIds = searchParameters.get("menuIds").toString().split(",");
		for (int i = 0; i < roleIds.length; i++) {
			String delsql = " delete  from role_menu where role_id = " + roleIds[i];
			drds.update(delsql);// 先删除 已经存在的目录树 然后进行新增
			for (int j = 0; j < menuIds.length; j++) {
				String sql = " insert into role_menu  ( role_id , menu_id )"
						   + " SELECT " + roleIds[i]+ ",'" + menuIds[j] + "'  FROM dual "
						   + " WHERE not EXISTS ( select 1 from role_menu t   where t.role_id = " + roleIds[i] + " and t.menu_id = '"+ menuIds[j] + "' ) ";
				drds.execute(sql);
			}
		}
	}

	// 未使用
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map getRoleUser(String id) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		String sql = " select t.role_id from role_user t where t.user_id = ? group by t.role_id ";
		List<Map<String, Object>> list = drds.queryForList(sql, new Object[] { id });
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object obj : list) {

		}
		return map;
	}

	/**
	 * 获得目录树 最简单实现了,,, 还需要努力改进
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List getRoles(String id) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));

		// String sql =" select role_id , menu_id from role_menu where role_id =
		// ? ";
		// //通过 角色 id 得到已经关联的目录 menu_id , 放入到menus中
		// List<Map<String,Object>> list = drds.queryForList(sql, new
		// Object[]{id});
		// Map<Object,Object> menus = new HashMap<Object, Object>();
		// for (Map<String, Object> map : list) {
		// menus.put(map.get("menu_id"), true);
		// }
		// , case ui.remark when '!_f3s$' then 'true' ELSE 'true' END nocheck
		// String sql =" select ui.id , ui.login_name as name ,case ui.flag when
		// 1 then 0 end pId , case ui.flag when 1 then true end open from
		// userinfo ui where 1=1 " ;
		// if(id.length()>0){
		// sql += " and ui.id in ( '"+id+"' ) " ;
		// }
		// // List<Map<String,Object>> list = drds.queryForList(sql, null);
		// //new Object []{}
		// List<Map<String,Object>> list = drds.queryForList(sql); //用户list
		// ,ro.flag as checked
		// sql = " select ui.id as pId , ui.login_name , ro.role_id as id ,
		// ro.role_name as name from userinfo ui left JOIN role ro ON ro.flag =
		// '1' ";
		// if(id.length()>0){
		// sql += " and ui.id in ( '"+id+"' ) " ;
		// }区分角色分类 1管理员角色 2门户  3运维                                              																// ro.role_name as name 
		String sql = " select   ro.role_id as id , case ro.flag when 1 then 0 end pId ,case ro.flag when 1 then true end open , "
					+ " 	case status when '1' then CONCAT('管理员角色--',role_name)"
					+ " 	 when '2' then CONCAT('门户角色----',role_name) 	  "
					+ " 	 when '3' then CONCAT('运维角色----',role_name)   end  as name "
				    + " from   role ro   where  ro.flag = '1'  ORDER BY ro.status desc";
		List<Map<String, Object>> roleList = drds.queryForList(sql);// 角色list

		String[] ids = id.split(",");
		Map<String, Object> depts = new HashMap<String, Object>();
		if (ids.length == 1) {
			sql = " select role_id from role_user where user_id = '" + ids[0] + "' ";
																						
			// 通过 角色 id 得到已经关联的目录 menu_id , 放入到menus中
			List<Map<String, Object>> list = drds.queryForList(sql, new Object[] {});
			for (Map<String, Object> map : list) {
				// 存在与上面 获取到的目录菜单的话 给个状态
				depts.put(map.get("role_id").toString(), true);
			}
		}

		for (Map<String, Object> map : roleList) {
			if (map.containsKey("pId") && "0".equals(map.get("pId"))) {
				map.put("open", true);
				map.put("checked", false);
				map.put("isParent", true);
			} else {
				map.put("open", true);
				map.put("checked", false);
				// map.put("isParent", true);
			}
			if (depts.containsKey(map.get("id"))) {
				System.out.println(map.get("id"));
				map.put("checked", true);
			}

		}

		// list.addAll(roleList);
		// for (authRoleDto dto : list) {//用户list 关联角色list
		// dto.setRoleList(roleList);
		// }
		// Map<Object,Object> menus = new HashMap<Object, Object>();
		// for (Map<String, Object> map : list) {
		// menus.put(map.get("menu_id"), true);
		// }
		// //获取status = 1 的目录树 , menuId对比是否存在 menus中,随便给了个标记字段
		// sql = "select ui.id , ui.login_name as loginName from userinfo ui
		// where 1=1 ";
		// List<Map<String, Object>> roleList = drds.queryForList(sql);
		// for (Map d : roleList) {
		// Menus dto = new Menus();
		// BeanUtils.copyProperties(d, dto);
		// if(menus.get(dto.getMenuId())!=null){
		// dto.setState("8");
		// };
		// menulist.add(dto);
		// }
		return roleList;

	}

	/**
	 * 保存 选中的目录树结构 到 role_user 表中 这里在前台控制了只能
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAuthRoles(Map searchParameters) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		String[] userIds = searchParameters.get("id").toString().split(",");
		String[] roleIds = searchParameters.get("roleIds").toString().split(",");
		for (int i = 0; i < userIds.length; i++) {
			String delsql = " delete  from role_user where user_id = '" + userIds[i] + "'";
			drds.update(delsql);// 先删除 已经存在的目录树 然后进行新增
			for (int j = 0; j < roleIds.length; j++) {
				String sql = " insert into role_user  ( role_id , user_id ) values (  '" + roleIds[j]
						+ "','" + userIds[i] + "' ) ";
				drds.execute(sql);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Map<String, Object>> getSingleRoles(String userId) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		// , case ro.role_id WHEN ros.role_id then 'true' else 'false' end open

		String sql = " select ui.id , '角色信息' as name ,case ui.flag when 1 then 0 end pId , case ui.flag when 1 then 'true' end open ,case ui.flag when 1 then 'true' end chkDisabled  , case ui.flag when 1 then 'true' end checked   from userinfo ui  where ui.id= '"
				+ userId + "'";
		List<Map<String, Object>> list = drds.queryForList(sql); // 用户list
		sql = " select ui.id as pId , ro.role_id as id , ro.role_name as name  , case ro.role_id WHEN ros.role_id then 'true' else 'false' end checked    from  role ro  left JOIN  userinfo ui   ON ro.flag = '1' and ui.id =  '"
				+ userId
				+ "' LEFT JOIN ( select rus.role_id  from  userinfo uis , role_user rus  where uis.id = rus.user_id and uis.id='"
				+ userId + "' ) ros on ros.role_id = ro.role_id  where  ro.flag = '1' ";
		List<Map<String, Object>> roleList = drds.queryForList(sql);// 角色list

		list.addAll(roleList);
		return list;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAuthRoleUser(String role_id, String user_id) {
		JdbcTemplate drds = new JdbcTemplate(getDriverManagerDataSource("dataSource1"));
		String sql = " insert into role_user  (id , role_id , user_id ) values ( UUID(),'" + role_id + "','" + user_id
				+ "' ) ";
		drds.execute(sql);
	}

//	@Override
//	public void deleteRole(String roleId) {
//		// TODO Auto-generated method stub
//		DataSource datasource = getDriverManagerDataSource("dataSource");// edas数据库
//		JdbcTemplate ds = new JdbcTemplate(datasource);
//		ds.execute("update Role u set u.flag=0 where u.role_id= '" + roleId + "' ");
//	}

	@Override
	public boolean getRoleNameIsSigle(RoleDto roleDto) {
		
	 List list =	roleRepository.findByRoleName(roleDto.getRoleName());
	 if(list!=null && list.size() >0){
		 return false;
	 }
		
		return true;
	}
	

}
