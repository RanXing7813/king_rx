package cn.com.king.repository.db1;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db1.Monitor;
import cn.com.king.dto.UserDto;


public interface MonitorRepository    extends JpaRepository<Monitor, String>,
 							JpaSpecificationExecutor<Monitor>
, PagingAndSortingRepository<Monitor,String>{
		
	 /**
	  * @param groupName 分组名称
	  * @return
	  */
//	Long countBySystemName(String systemName);
	@Modifying
	@Query("update Monitor c    set c.isDelete=1 where c.monitorid=?1 ")
	void delMsgModel(String id);
	
	@Modifying
	@Query("update Monitor c    set c.isDelete=0 where c.monitorid=?1 ")
	void del(String id,UserDto userDto);
	
//	@Modifying
//	@Query("select c from Monitor c where c.isDelete=1 ")
//	List<Monitor> fisdnfsdMonitorAll(  );
//
//	@Query("select c from ValueList c where c.isDelete=1 and c.groupName=:groupName and c.name=:name")
//	ValueList getName(@Param("groupName") String groupName,@Param("name") String name);
//	
//	@Query("select c from ValueList c where c.isDelete=1 and c.groupName=:name and c.numRemark=:numRemark")
//	List<ValueList> findModuleLists(@Param("name") String name,@Param("numRemark") int numRemark);
}
