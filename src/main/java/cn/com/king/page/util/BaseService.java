package cn.com.king.page.util;

import java.util.List;

import cn.com.king.dto.UserDto;


public interface BaseService<T,PK> {
	  /**
     * 保存实体
     * @param entity
	 * @return 
     */
    String save(Object entity, UserDto t);
    
    /**
     * 根据主键获取对象
     * @param <T>
     * @param clazz 实体类
     * @param id    主键
     * @return
     */
    <T> T getById(Class<T> clazz,Object id);
}
