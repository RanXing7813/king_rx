package cn.com.king.page.util;

import java.util.List;

public interface BaseDao<T,PK> {

	//添加
	public void add(T t);
	//删除(单个)
	public void del(PK id);
	//批量删除  1,2,3,4
	public void delMore( String ids);
	//修改
	public void upd(T t);
	//分页查询
	public List<T> getPageList(PageUtil page);
	//查询所有
	public List<T> getAll();
	//主键查询
	public T getById(PK id);
}
