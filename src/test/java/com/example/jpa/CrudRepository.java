package com.example.jpa;

import java.io.Serializable;

import org.springframework.data.repository.Repository;

public interface CrudRepository<T, ID extends Serializable>
						extends Repository<T, ID> {

    <S extends T> S save(S entity); 
	
	T findOne(ID primaryKey);       
	
	Iterable<T> findAll();          
	
	Long count();                   
	
	void delete(T entity);          
	
	boolean exists(ID primaryKey);  
	
	// … more functionality omitted.
}