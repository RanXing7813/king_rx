package cn.com.king.page.util;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cn.com.king.dto.UserDto;


/** 
* @ClassName: JpaDb1BaseDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author ranxing 
* @date 2017年8月29日 上午11:46:10 
*  
*/
@Repository
public  class JpaDb1BaseDaoImpl implements BaseDao   {
	
	
	 //注入实体管理器
	@PersistenceContext(unitName="db1")
	private EntityManager em1;
	
	
		@Override
		public <T> T getById(Class<T> clazz, Object id) {
	        return em1.find(clazz, id);
	    }
	    
		@Override
	    public void save(Object entity, UserDto t) {
	        em1.persist(entity);
	    }


}
