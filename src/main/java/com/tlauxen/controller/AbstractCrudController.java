package com.tlauxen.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tlauxen.model.Entity;
import com.tlauxen.service.CrudService;

public abstract class AbstractCrudController<T extends Entity> {

	protected abstract CrudService<T> getCrudService();
	
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/findById",method=RequestMethod.GET)
    public @ResponseBody T findById(@RequestParam Long id) {
    	
    	T entity = getCrudService().findById(id);
    	
    	if (entity == null) {
    		throw new RuntimeException(String.format("Entity not found with id %d", id));
    	}
    	
		return entity;
    	
    }
    
    @RequestMapping(headers ={"Accept=application/json"}, value="/ajax/save",method=RequestMethod.POST)
    public @ResponseBody T save(@RequestBody() T entity) {
    	
    	return getCrudService().saveOrUpdate(entity);
    	
    }
    
    @RequestMapping(value="/ajax/remove",method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void remove(@RequestBody() T entity) {
    	
    	getCrudService().remove(entity);
    	
    }
    
}
