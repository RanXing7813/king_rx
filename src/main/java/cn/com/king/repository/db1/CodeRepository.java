package cn.com.king.repository.db1;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.king.domain.db1.Code;


/**
 * 机构单位dao
 * @author SunJingyan
 * @date 2014-04-21
 *
 */
public interface CodeRepository  extends JpaRepository<Code, String>, JpaSpecificationExecutor<Code>, PagingAndSortingRepository<Code,String>{

	
	@Query("select c from Code c where c.parent_id = ?1")
	List findListByPid(String id);
	
	
	
	
}
