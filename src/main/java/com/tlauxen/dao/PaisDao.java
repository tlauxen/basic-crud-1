package com.tlauxen.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.TransactionCallback;
import org.skife.jdbi.v2.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tlauxen.model.Pais;

@Repository
public class PaisDao {
	
	@Autowired
	private DataSource dataSource;
	
	public Pais findById(Long id) {
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Map<String, Object>> rs = h.select("select id,sigla,nome from pais where id = ?", id);
    		for (Map<String,Object> row: rs) {
    			Pais pais = new Pais();
    			pais.setId((Long) row.get("id"));
    			pais.setSigla((String) row.get("sigla"));
    			pais.setNome((String) row.get("nome"));
    			return pais;
    		}
    	} finally {
    		h.close();
    	}
    	return null;
	}
	
	public List<Pais> find(Pais filter) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select id,sigla,nome from pais where 1=1");
		
		if (StringUtils.isNotBlank(filter.getSigla())) {
			sql.append(" and upper(sigla) like upper(?)");
			params.add("%"+filter.getSigla()+"%");
		}
		
		if (StringUtils.isNotBlank(filter.getNome())) {
			sql.append(" and upper(nome) like upper(?)");
			params.add("%"+filter.getNome()+"%");
		}
		
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Pais> toReturn = new ArrayList<Pais>();
    		List<Map<String, Object>> rs = h.select(sql.toString(), params.toArray(new Object[]{}));
    		for (Map<String,Object> row: rs) {
    			Pais pais = new Pais();
    			pais.setId((Long) row.get("id"));
    			pais.setSigla((String) row.get("sigla"));
    			pais.setNome((String) row.get("nome"));
    			
    			toReturn.add(pais);
    		}
    		return toReturn;
    	} finally {
    		h.close();
    	}
	}
	
	private Long nextVal() {
    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
    		List<Map<String, Object>> rs = h.select("select nextval('pais_seq') as id");
    		for (Map<String,Object> row: rs) {
    			return (Long) row.get("id");
    		}
    	} finally {
    		h.close();
    	}
    	return null;
	}

	public Pais save(final Pais pais) {
		final Long id = nextVal();
		final StringBuilder sql = new StringBuilder();
		sql.append("insert into pais (id,sigla,nome) values (?,?,?)");

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle arg0, TransactionStatus arg1) throws Exception {
	    			arg0.execute(sql.toString(),id,pais.getSigla(),pais.getNome());
	    			return null;
	    		}
			});
    	} finally {
    		h.close();
    	}

		return findById(id);
	}

	public Pais update(final Pais pais) {
		final StringBuilder sql = new StringBuilder();
		sql.append("update pais set sigla = ?, nome = ? where id = ?");

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle arg0, TransactionStatus arg1) throws Exception {
	    			arg0.execute(sql.toString(),pais.getSigla(),pais.getNome(),pais.getId());
	    			return null;
	    		}
			});
	    	return findById(pais.getId());
    	}finally {
    		h.close();
    	}

	}

	public void remove(final Pais model) {
		final StringBuilder sql = new StringBuilder();
		sql.append("delete from pais where id = ?");

    	DBI dbi = new DBI(dataSource);
    	Handle h = dbi.open();
    	try {
	    	h.inTransaction(new TransactionCallback<Object>() {
	    		public Object inTransaction(Handle arg0, TransactionStatus arg1) throws Exception {
	    			arg0.execute(sql.toString(),model.getId());
	    			return null;
	    		}
			});
    	}finally {
    		h.close();
    	}
	}

}
