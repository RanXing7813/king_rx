package cn.com.king.repository.db1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.king.domain.db1.Menuinfo;


public interface MenusRepository extends JpaRepository<Menuinfo, String>,
			JpaSpecificationExecutor<Menuinfo>,PagingAndSortingRepository<Menuinfo,String>{

		/**
		 *
		 * @param codeType
		 * @return
		 */
		@Query("select r from Menuinfo r   where r.state='1' ")
		List<Menuinfo> findMenuss();
		
		@Modifying
		@Query("update Menuinfo set state = '0' where menuId=?1")
		void del(String models);
		
		@Query("select r from Menuinfo r   where r.state='1' and r.status='2' ")
		List<Menuinfo> findYwMenus();

		@Query("select r from Menuinfo r   where r.state='1' and r.status='1' ")
		List<Menuinfo> findMhMenus();

		@Query("select r from Menuinfo r   where r.state='1' and r.status='2' ")
		List<Menuinfo> findMenus();

		
}
