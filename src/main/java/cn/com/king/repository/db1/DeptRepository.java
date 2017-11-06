package cn.com.king.repository.db1;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.king.domain.db1.Dept;
import cn.com.king.domain.db1.Monitor;


/**
 * 机构单位dao
 * @author SunJingyan
 * @date 2014-04-21
 *
 */
public interface DeptRepository  extends JpaRepository<Dept, String>,
	JpaSpecificationExecutor<Dept>
, PagingAndSortingRepository<Dept,String>{
///*	*//**
//	 * 查询机构树
//	 * @return
//	 *//*
//	@Query("select d from Dept d left join fetch d.children")
//	List<Dept> findDeptTree();
//*/
///*	*//**
//	 * 查询树根
//	 * @return
//	 *//*
//	@Query("select d from Dept d where d.parent is null and d.flag=1 order by d.deptIndex")
//	List<Dept> findRoots();*/
//	
//	/**
//	 * 标记为删除
//	 * @param id
//	 */
//	@Modifying
//	@Query("update Dept d set d.flag=0,d.updateTime=:updateTime,d.updaterId=:updaterId where d.deptId=:id")
//	void updateFlag(@Param("id") String id,@Param("updateTime") Date updateTime,@Param("updaterId") String updaterId);
//	
	/**
	 * 查询所有（未标记为删除的）
	 * @return
	 */
	@Query("select d from Dept d where d.flag=1 and d.parentId!='0'")
	List<Dept> findAllDepts();
//	
//	/**
//	 * 查询用户下所有(发送)
//	 * @return
//	 */
//	@Query("select d from Dept d where d.deptId IN (select t.jsfName from TransterRel t where t.fsfName= ?1 and t.ywlxName= ?2) and flag='1'")
//	List<Dept> findDeptfs(String dept_id,String ywlx_name);
//	
//	/**
//	 * 查询用户下所有(接收)
//	 * @return
//	 */
//	@Query("select d from Dept d where d.deptId IN (select t.fsfName from TransterRel t where t.jsfName= ?1 and t.ywlxName= ?2) and flag='1'")
//	List<Dept> findDeptjs(String dept_id,String ywlx_name);
//	
//	/**
//	 * 查询用户列表
//	 * @return
//	 */
//	@Query("select d from Dept d where d.deptId in ?1 and flag='1'")
//	List<Dept> findDepts(String[] dept_id);
//	
///*	*//**
//	 * 根据机构ID和角色ID查询用户集合
//	 * @param deptId
//	 * @param roleId
//	 * @return
//	 *//*
//	@Query("select u.userId from Dept d JOIN d.users u JOIN u.roles r  where d.deptId = :deptId and r.roleId = :roleId")
//	List<String> findUsersByDeptRole(@Param("deptId") String deptId,@Param("roleId") String roleId);
//*/
}
