package com.tlauxen.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.Update;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlauxen.model.Entity;
import com.tlauxen.utils.ReflectionUtils;
import com.tlauxen.utils.SqlUtils;

public abstract class AbstractCrudDao<T extends Entity> {

	@Autowired
	protected DataSource dataSource;
	
	protected abstract Class<T> getEntityClass();

	public T findById(Long id) {
		
		DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {

    		String sql = SqlUtils.getSqlFindById(getEntityClass());
			List<Map<String, Object>> rs = h.select(sql, id);
    		for (Map<String,Object> row: rs) {
    			T e = getEntityClass().newInstance();
    			
    			e = ReflectionUtils.populate(e,row);
    			
    			return e;
    		}
    	} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
    		h.close();
    	}
    	return null;
	}

	protected Long nextVal() {
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Map<String, Object>> rs = h.select("select nextval('"+getEntityClass().getSimpleName()+"_seq') as id");
    		for (Map<String,Object> row: rs) {
    			return (Long) row.get("id");
    		}
    	} finally {
    		h.close();
    	}
    	return null;
	}

	public T create(final T entity) {
		final Long id = nextVal();
		entity.setId(id);
		
		final String sql = SqlUtils.getSqlInsert(entity);

		final Map<String, Object> props = ReflectionUtils.getFields(entity);
		
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle h, TransactionStatus arg1) throws Exception {
	    			Update statement = h.createStatement(sql);
	    			for (String key: props.keySet()) {
	    				statement.bind(key, props.get(key));
	    			}
	    			statement.execute();
	    			return null;
	    		}
			});
    	} finally {
    		h.close();
    	}

		return findById(id);
	}

	public T update(final T entity) {

		final String sql = SqlUtils.getSqlUpdate(entity);

		final Map<String, Object> props = ReflectionUtils.getFields(entity);

		DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle h, TransactionStatus arg1) throws Exception {
	    			Update statement = h.createStatement(sql);
	    			for (String key: props.keySet()) {
	    				statement.bind(key, props.get(key));
	    			}
	    			statement.execute();
	    			return null;
	    		}
			});
	    	return findById(entity.getId());
    	}finally {
    		h.close();
    	}

	}

	public void remove(final Entity entity) {
		
		final String sql = SqlUtils.getSqlRemove(entity);

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle arg0, TransactionStatus arg1) throws Exception {
	    			arg0.execute(sql, entity.getId());
	    			return null;
	    		}
			});
    	}finally {
    		h.close();
    	}
	}

}
