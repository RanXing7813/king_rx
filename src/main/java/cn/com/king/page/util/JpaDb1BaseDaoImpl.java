package cn.com.king.page.util;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/** 
* @ClassName: JpaDb1BaseDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author ranxing 
* @date 2017年8月29日 上午11:46:10 
*  
*/
@SuppressWarnings("rawtypes")
public abstract class JpaDb1BaseDaoImpl implements BaseDao   {
	
	@PersistenceContext(unitName="db1")
	private EntityManager em;
	

	

	

	


}
