package com.tlauxen.service;

import com.tlauxen.model.Entity;

public interface CrudService<T extends Entity> {

	public T findById(Long id);
	
	public T saveOrUpdate(T entity);

	public void remove(T entity);

}
