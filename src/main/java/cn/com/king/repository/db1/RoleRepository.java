package cn.com.king.repository.db1;

import java.awt.Menu;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.com.king.domain.db1.Role;


/**
 * 角色表dao
 * @author SunJingyan
 * @date 2014年4月21日
 *
 */
public interface  RoleRepository extends JpaRepository<Role, String>,
		JpaSpecificationExecutor<Role>,PagingAndSortingRepository<Role, String>{
	
	/**
	 * 根据角色id查询菜单集合
	 * @param roleId
	 * @return
	 */
/*	@Query("SELECT u.menus FROM Role u WHERE u.roleId = :roleId")
	List<Menu> findMenuByRoleId(@Param("roleId") String roleId);*/

	/**
	 * 标记为删除
	 * @param id
	 */
	@Modifying
	@Query("update Role m set m.flag=0,m.updateTime=:updateTime,m.updaterId=:updaterId where m.roleId=:id")
	void updateFlag(@Param("id") String id,@Param("updateTime") Date updateTime,@Param("updaterId") String updaterId);
	
	/**
	 * 查询所有未标记为删除的角色集合
	 * @return
	 */
	@Query("select r from Role r where r.flag=1")
	List<Role> findAllRoles();
	
	/**
	 * 根据roleName查询出记录
	 * @param roleName
	 * @return
	 */
	@Query("select r from Role r where r.roleName = :roleName and r.roleId!= :id and r.flag=1")
	List<Role> findByRoleName(@Param("roleName") String roleName,@Param("id") String id);
	
	/**
	 * 根据roleName查询出记录
	 * @param roleName
	 * @return
	 */
	@Query("select r from Role r where r.roleName = :roleName and r.flag=1")
	List<Role> findByRoleName(@Param("roleName") String roleName);
}
