package cn.com.king.repository.db1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db1.InspectionConfig;
import cn.com.king.dto.UserDto;


public interface InspectionconfigRepository
		extends JpaRepository<InspectionConfig, String>, JpaSpecificationExecutor<InspectionConfig>, PagingAndSortingRepository<InspectionConfig,String> {

	@Query("SELECT i FROM InspectionConfig i")
	List<InspectionConfig>  ADSD();

	@Modifying
	@Query("update InspectionConfig c    set c.isDelete=0, c.updateId=?2, c.updateName=?3 where c.configId in (?1) ")
	int remove(String id,String userID, String userName);
	
	@Modifying
	@Query("update InspectionConfig c    set c.isDelete=0, c.updateId=?2, c.updateName=?3 where c.configId in (?1) ")
	int removeS(String [] ids,String userID, String userName);
	

}
