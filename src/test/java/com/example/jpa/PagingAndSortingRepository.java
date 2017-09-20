package com.example.jpa;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PagingAndSortingRepository <T, ID extends Serializable>
							extends CrudRepository<T, ID> {
	
	Iterable<T> findAll(Sort sort);
	
	Page<T> findAll(Pageable pageable);
	
	
	
//	访问User页面大小为20 的第二页，您可以简单地执行以下操作：
//
//	PagingAndSortingRepository<User, Long> repository = // … get access to a bean
//	Page<User> users = repository.findAll(new PageRequest(1, 20));
}