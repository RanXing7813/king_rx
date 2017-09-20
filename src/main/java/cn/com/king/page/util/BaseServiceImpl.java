package cn.com.king.page.util;

import java.util.List;
import java.util.UUID;


public class BaseServiceImpl<T,PK> implements BaseService<T, PK> {

//	@PersistenceContext
//	private EntityManager em;

	private BaseDao<T,PK> baseDao;

	protected String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	

	
	
	
	
	public void add(T t) {
		baseDao.add(t);
	}

	public void del(PK id) {
		baseDao.del(id);
	}

	public T getById(PK id) {
		return baseDao.getById(id);
	}

	public void upd(T t) {
		baseDao.upd(t);
	}

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}


	public List<T> getAll() {
		return this.baseDao.getAll();
	}


	@Override
	public PageUtil getPageList(PageUtil page) {
		List<T> list = this.baseDao.getPageList(page);
		page.setRows(list);
		return page;
	}
	
	public void delMore(String ids){
		this.baseDao.delMore(ids);
	}


}
