package cn.com.king.repository.db1;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.com.king.domain.db1.UserInfo;


public interface UserInfoRepository extends JpaRepository<UserInfo, String>,JpaSpecificationExecutor<UserInfo>,PagingAndSortingRepository<UserInfo,String>{

	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	@Query("select u from UserInfo u where u.loginName =:loginName and u.flag=1")
	UserInfo findByLoginName(@Param("loginName") String loginName);
	
	/**
	 * 根据登录名称和密码查询用户
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Query("SELECT u FROM UserInfo u WHERE u.loginName = :loginName and u.password =:password and u.flag=1")
	UserInfo login(@Param("loginName") String loginName,@Param("password") String password);
	
	/**
	 * 标记为删除
	 * @param id
	 */
	@Modifying
	@Query("update UserInfo u set u.flag=0 where u.id=:id")
	void updateFlag(@Param("id") String id);
	
	/**
	 * 查询所有未标记为删除的用户集合
	 * @return
	 */
	@Query("select u.userName, u.deptName, u.id, u.deptId from UserInfo u where u.flag=1 order by u.deptName")
	List findAllUsers();
	
	/**
	 * 查询所有未标记为删除的抄送人用户集合
	 * @return
	 */
	@Query("select u.userName, u.deptName, u.id, u.deptId from UserInfo u where u.flag=1 and u.id != ?1   order by u.deptName")
	List findCopyPeopleUsers(String id);
	
	/**
	 * 修改密码
	 * @param userId
	 * @param pwd
	 */
	@Modifying
	@Query("update UserInfo u set u.password=:pwd where u.id=:userId")
	void updatePwd(@Param("userId") String userId,@Param("pwd") String pwd);
	
	/**
	 * 修改
	 * @param userId
	 * @param pwd
	 */
	@Modifying
	@Query("update UserInfo u set u.phoneNum=?1 , u.email=?2 where u.id= ?3")
	void upPhoneNumxxxEmail(  String phoneNum , String email ,String id);
	
	/**
	 * 根据loginName查询出记录
	 * @param loginName
	 * @return
	 */
	@Query("select u from UserInfo u where u.loginName = :loginName and u.id!= :id and u.flag=1")
	List<UserInfo> findByUserName(@Param("loginName") String loginName,@Param("id") String id);
	
}
