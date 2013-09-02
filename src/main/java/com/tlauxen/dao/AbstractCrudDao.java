package com.tlauxen.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.skife.jdbi.v2.Update;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlauxen.model.Entity;

public abstract class AbstractCrudDao<T extends Entity> {

	@Autowired
	protected DataSource dataSource;
	
	protected abstract Class<T> getEntityClass();

	public T findById(Long id) {
		DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Map<String, Object>> rs = h.select("select * from "+getEntityClass().getSimpleName()+" where id = ?", id);
    		for (Map<String,Object> row: rs) {
    			T e = getEntityClass().newInstance();
    			
    			BeanUtils.populate(e, row);
    			
    			return e;
    		}
    	} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
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

	public T save(final T entity) {
		final Long id = nextVal();
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> props = getProps(entity);
		props.put("id", id);
		
		String fields = StringUtils.join(props.keySet(),",");
		String values = ":"+fields.replace(",", ",:");
		
		sql.append("insert into "+getEntityClass().getSimpleName()+" ("+fields+") values ("+values+")");

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle h, TransactionStatus arg1) throws Exception {
	    			Update statement = h.createStatement(sql.toString());
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
		final Map<String, Object> props = getProps(entity);
		props.remove("id");
		
		StringBuilder fields = new StringBuilder();
		for (String field: props.keySet()) {
			if (fields.length() > 0) {
				fields.append(",");
			}
			fields.append(field).append("=:").append(field);
		}

		final StringBuilder sql = new StringBuilder();
		sql.append("update "+getEntityClass().getSimpleName()+" set "+fields+" where id = :id");

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle h, TransactionStatus arg1) throws Exception {
	    			Update statement = h.createStatement(sql.toString());
	    			for (String key: props.keySet()) {
	    				statement.bind(key, props.get(key));
	    			}
	    			statement.bind("id", entity.getId());
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
		final StringBuilder sql = new StringBuilder();
		sql.append("delete from "+getEntityClass().getSimpleName()+" where id = ?");

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle arg0, TransactionStatus arg1) throws Exception {
	    			arg0.execute(sql.toString(),entity.getId());
	    			return null;
	    		}
			});
    	}finally {
    		h.close();
    	}
	}

	private Map<String, Object> getProps(final T entity) {
		final Map<String, Object> props;
		try {
			props = PropertyUtils.describe(entity);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		props.remove("class");
		return props;
	}

}
