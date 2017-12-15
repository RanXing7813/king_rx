package cn.com.taiji.web.service.sys;

import java.util.List;
import java.util.Map;

import cn.com.king.domain.db1.Menus;
import cn.com.king.dto.RoleDto;
import cn.com.king.dto.UserDto;



public interface RoleService {

	public Map getPage(final Map searchParameters ,String salt);
	public void save(RoleDto dto);
	public void deleteRole(String roleId);
	public RoleDto findDtoById(String id, String salt);
	public List<Menus> getMenus(String id);
	public void saveAuthMenus(Map searchParameters);
	public Map getRoleUser(String id);
	public List getRoles(String id);
	public void saveAuthRoles(Map searchParameters);
	public List<Map<String, Object>> getSingleRoles(String userId);
	 public List<Menus> getMenuForMap(Map searchParameters);
	 public void saveAuthRoleUser(String role_id,String user_id);
	public void deleteRole(String id, UserDto userDto);
	public boolean getRoleNameIsSigle(RoleDto roleDto);

}
